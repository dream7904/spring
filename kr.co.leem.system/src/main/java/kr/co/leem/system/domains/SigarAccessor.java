package kr.co.leem.system.domains;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import kr.co.leem.system.ScriptPropertiesLoader;
import kr.co.leem.system.scripts.DefaultScriptExecutor;
import kr.co.leem.system.scripts.OsType;
import kr.co.leem.utils.lang.DateUtils;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.hyperic.sigar.DirUsage;
import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.NetInfo;
import org.hyperic.sigar.NetInterfaceConfig;
import org.hyperic.sigar.NetRoute;
import org.hyperic.sigar.OperatingSystem;
import org.hyperic.sigar.ProcCpu;
import org.hyperic.sigar.ProcCredName;
import org.hyperic.sigar.ProcExe;
import org.hyperic.sigar.ProcMem;
import org.hyperic.sigar.ProcStat;
import org.hyperic.sigar.ProcState;
import org.hyperic.sigar.ProcUtil;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.SigarPermissionDeniedException;
import org.hyperic.sigar.Swap;
import org.hyperic.sigar.cmd.Ls;
import org.hyperic.sigar.cmd.Netstat;
import org.hyperic.sigar.cmd.Ps;
import org.hyperic.sigar.cmd.Route;
import org.hyperic.sigar.cmd.Shell;
import org.hyperic.sigar.cmd.SigarCommandBase;
import org.hyperic.sigar.cmd.Ulimit;
import org.hyperic.sigar.cmd.Uptime;
import org.hyperic.sigar.cmd.Version;
import org.hyperic.sigar.cmd.Who;
import org.hyperic.sigar.pager.PageControl;
import org.hyperic.sigar.shell.ShellCommandExecException;
import org.hyperic.sigar.shell.ShellCommandInitException;
import org.hyperic.sigar.shell.ShellCommandUsageException;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.util.ReflectionUtils;

/**
 * SigarAccessor.
 *
 * @author 임 성천.
 */
@SuppressWarnings("unchecked")
public class SigarAccessor extends org.hyperic.sigar.cmd.SysInfo {
	
	public static final String DEFAULT_SIGAR_LIB_PATH = "sigar-bin/lib";
	
	private static SysInfo sysInfo;
	
	private static Shell shell;
	
	private static Method flushPrintfItems = ReflectionUtils.findMethod(SigarCommandBase.class, "flushPrintfItems");
	
	private static Field output = ReflectionUtils.findField(SigarCommandBase.class, "output");
	
	static {
		
		ScriptPropertiesLoader loader = new ScriptPropertiesLoader();
		loader.load();
		
		String javaLibPath = System.getProperty("java.library.path");
		String sigarLibPath = loader.isLoaded() && loader.getProperties().containsKey("sigar.library.path") ? loader
				.getProperties().getProperty("sigar.library.path") : DEFAULT_SIGAR_LIB_PATH;
				
		if (javaLibPath == null) {
			System.setProperty("java.library.path", sigarLibPath);
		} else {
			if (!javaLibPath.contains("sigar")) {
				String delimiter = DefaultScriptExecutor.getOsType().is(OsType.Windows) ? ";" : ":";
				System.setProperty("java.library.path", javaLibPath + delimiter + sigarLibPath);
			}
		}
		
		try {
			shell = new Shell();
			shell.init("sigar", System.out, System.err);
			shell.setPageSize(PageControl.SIZE_UNLIMITED);
			shell.registerCommands();
			shell.setInteractive(true);
			
			ReflectionUtils.makeAccessible(flushPrintfItems);
			ReflectionUtils.makeAccessible(output);
		} catch (ShellCommandInitException e) {
			e.printStackTrace();
		}
	}
	
	private static SigarAccessor instance = new SigarAccessor(shell);
	
	public SigarAccessor(Shell shell) {
		super(shell);
	}
	
	public static SigarAccessor getInstance() {
		return instance;
	}
	
	/**
	 * 시스템 정보 설정.
	 * @return 시스템 정보.
	 * @throws SigarException
	 * @throws IOException
	 * @throws ShellCommandExecException
	 * @throws ShellCommandUsageException
	 */
	public static SysInfo getSystemInfo() throws SigarException, IOException, ShellCommandUsageException,
			ShellCommandExecException {
		
		if (sysInfo == null) {
			setSysInfo(new SysInfo());
		}
		
		setVersionInfo();
		
		setUptimeInfo();
		
		setCpuInfo();
		
		setMemoryInfo();
		
		setFileSystemInfo();
		
		setFileSystemUsageMap();
		
		setProcessStatInfo();
		
		setProcessInfo(null);
		
		setNetworkInfoMap();
		
		setNetworkInfo(null);
		
		setUlimitInfo();
		
		return sysInfo;
	}
	
	/**
	 * 시스템 자원 제한 값을 설정함.
	 * 
	 * @throws SigarException
	 * @throws ShellCommandExecException
	 * @throws ShellCommandUsageException
	 */
	public static void setUlimitInfo() throws SigarException, ShellCommandUsageException, ShellCommandExecException {
		setUlimitInfo(false);
	}
	
	/**
	 * 시스템 자원 제한 정보 값 를 설정함.
	 * 
	 * @param isModeMax 명령어 옵션.
	 * @throws SigarException
	 * @throws ShellCommandExecException
	 * @throws ShellCommandUsageException
	 * @see Ulimit
	 */
	public static void setUlimitInfo(boolean isModeMax) throws SigarException, ShellCommandUsageException,
			ShellCommandExecException {
		UlimitInfo ulimitInfo = new UlimitInfo();
		Ulimit ulimit = new Ulimit(shell);
		ulimit.processCommand(isModeMax ? new String[] { "-H" } : new String[] {});
		forceFlushPrintfItems(ulimit);
		parseUlimitInfo(forceGetOutput(ulimit), ulimitInfo);
		sysInfo.setUlimitInfo(ulimitInfo);
	}
	
	/**
	 * 네트워크 정보를 설정함.
	 * 
	 * @throws SigarException
	 * @see {@link Sigar#getNetConnectionList(int)}
	 */
	public static void setNetworkInfoMap() throws SigarException {
		Map<String, NetworkInfo> networkInfoMap = new LinkedHashMap<String, NetworkInfo>();
		List<String> nifs = Arrays.asList(getInstance().sigar.getNetInterfaceList());
		
		for (String nif : nifs) {
			setNetworkInfo(nif);
			networkInfoMap.put(sysInfo.getNetworkInfo().getAddress(), sysInfo.getNetworkInfo());
		}
		
		sysInfo.setNetworkInfoMap(networkInfoMap);
	}
	
	/**
	 * 네트워크 정보를 설정함.(ip address, subnet address, gateway address, host등.)
	 * name, domain name, etc. information search
	 *
	 * @param network 네트워크 명.
	 * @throws SigarException
	 * @see NetInfo
	 * @see NetInterfaceConfig
	 */
	public static void setNetworkInfo(String network) throws SigarException {
		NetInfo ni = getInstance().sigar.getNetInfo();
		NetInterfaceConfig nic = getInstance().sigar.getNetInterfaceConfig(network);
		NetworkInfo networkInfo = new NetworkInfo();
		
		networkInfo.setAddress(nic.getAddress());
		networkInfo.setHwaddr(nic.getHwaddr());
		networkInfo.setNetmask(nic.getNetmask());
		networkInfo.setHostName(ni.getHostName());
		networkInfo.setDomainName(ni.getDomainName());
		networkInfo.setDefaultGateway(ni.getDefaultGateway());
		networkInfo.setPrimaryDns(ni.getPrimaryDns());
		networkInfo.setSecondaryDns(ni.getSecondaryDns());
		
		sysInfo.setNetworkInfo(networkInfo);
	}
	
	/**
	 * 파일 시스템 기본정보를 설정함.
	 *
	 * @throws SigarException
	 * @see {@link Sigar#getFileSystemList()}
	 * @see org.hyperic.sigar.FileSystem
	 */
	public static void setFileSystemInfo() throws SigarException {
		List<FileSystemInfo> fileSystems = new ArrayList<FileSystemInfo>();
		for (org.hyperic.sigar.FileSystem fs : getInstance().sigar.getFileSystemList()) {
			FileSystemInfo fileSystemInfo = new FileSystemInfo();
			fileSystemInfo.setDirName(fs.getDirName());
			fileSystemInfo.setDevName(fs.getDevName());
			fileSystemInfo.setTypeName(fs.getTypeName());
			fileSystemInfo.setSysTypeName(fs.getSysTypeName());
			fileSystemInfo.setOptions(fs.getOptions());
			fileSystemInfo.setType(fs.getType());
			fileSystemInfo.setFlags(fs.getFlags());
			
			fileSystems.add(fileSystemInfo);
		}
		sysInfo.setFileSystems(fileSystems);
	}
	
	/**
	 * 시스템 사용정보를 설정함.
	 *
	 * @throws SigarException
	 * @see {@link Sigar#getFileSystemList()}
	 */
	public static void setFileSystemUsageMap() throws SigarException {
		Map<String, FileSystemUsageInfo> fileSystemUsageMap = new LinkedHashMap<String, FileSystemUsageInfo>();
		for (org.hyperic.sigar.FileSystem fs : getInstance().sigar.getFileSystemList()) {
			if (DefaultScriptExecutor.getOsType().is(OsType.Windows)) {
				if (org.hyperic.sigar.FileSystem.TYPE_LOCAL_DISK == fs.getType()) {
					gatherFileSystemUsage(fs.getDirName());
					fileSystemUsageMap.put(fs.getDirName(), sysInfo.getFileSystemUsageInfo());
				}
			} else {
				gatherFileSystemUsage(fs.getDirName());
				fileSystemUsageMap.put(fs.getDirName(), sysInfo.getFileSystemUsageInfo());
			}
		}
		sysInfo.setFileSystemUsageMap(fileSystemUsageMap);
	}
	
	/**
	 * 시스템 사용정보를 설정함.
	 *
	 * @param name directory name
	 * @throws SigarException
	 * @see {@link org.hyperic.sigar.Sigar#getFileSystemUsage(String)	 */
	public static void gatherFileSystemUsage(String name) throws SigarException {
		try {
			org.hyperic.sigar.FileSystemUsage fsu = getInstance().sigar.getFileSystemUsage(name);
			FileSystemUsageInfo fileSystemUsage = new FileSystemUsageInfo();
			fileSystemUsage.setTotal(fsu.getTotal());
			fileSystemUsage.setFree(fsu.getFree());
			fileSystemUsage.setDiskUsed(fsu.getUsed());
			fileSystemUsage.setAvail(fsu.getAvail());
			fileSystemUsage.setFiles(fsu.getFiles());
			fileSystemUsage.setFreeFiles(fsu.getFreeFiles());
			fileSystemUsage.setDiskReads(fsu.getDiskReads());
			fileSystemUsage.setDiskWrites(fsu.getDiskWrites());
			fileSystemUsage.setDiskReadBytes(fsu.getDiskReadBytes());
			fileSystemUsage.setDiskWriteBytes(fsu.getDiskWriteBytes());
			fileSystemUsage.setDiskQueue(fsu.getDiskQueue());
			fileSystemUsage.setDiskServiceTime(fsu.getDiskServiceTime());
			fileSystemUsage.setUsePercent(fsu.getUsePercent() * 100);
			
			sysInfo.setFileSystemUsageInfo(fileSystemUsage);
		} catch (SigarPermissionDeniedException spe) {
			System.err.println("SigarPermissionDeniedException occurred [" + name + "] " + spe.getMessage());
		}
	}
	
	/**
	 * 파일 시스템 정보를 설정함.
	 *
	 * @param name directory name
	 * @throws SigarException
	 * @see FileSystemUsage
	 */
	public static void setMountedFileSystemUsage(String name) throws SigarException {
		FileSystemUsage fsu = getInstance().sigar.getMountedFileSystemUsage(name);
		FileSystemUsageInfo fileSystemUsageInfo = new FileSystemUsageInfo();
		fileSystemUsageInfo.setTotal(fsu.getTotal());
		fileSystemUsageInfo.setFree(fsu.getFree());
		fileSystemUsageInfo.setDiskUsed(fsu.getUsed());
		fileSystemUsageInfo.setAvail(fsu.getAvail());
		fileSystemUsageInfo.setFiles(fsu.getFiles());
		fileSystemUsageInfo.setFreeFiles(fsu.getFreeFiles());
		fileSystemUsageInfo.setDiskReads(fsu.getDiskReads());
		fileSystemUsageInfo.setDiskWrites(fsu.getDiskWrites());
		fileSystemUsageInfo.setDiskReadBytes(fsu.getDiskReadBytes());
		fileSystemUsageInfo.setDiskWriteBytes(fsu.getDiskWriteBytes());
		fileSystemUsageInfo.setDiskQueue(fsu.getDiskQueue());
		fileSystemUsageInfo.setDiskServiceTime(fsu.getDiskServiceTime());
		fileSystemUsageInfo.setUsePercent(fsu.getUsePercent() * 100);
		
		sysInfo.setFileSystemUsageInfo(fileSystemUsageInfo);
	}
	
	/**
	 * 시스템 메모리 정보를 설정함.
	 *
	 * @throws SigarException
	 * @see Mem
	 * @see Swap
	 */
	public static void setMemoryInfo() throws SigarException {
		MemoryInfo memoryInfo = new MemoryInfo();
		Mem mem = getInstance().sigar.getMem();
		Swap swap = getInstance().sigar.getSwap();
		memoryInfo.setMemTotal(mem.getTotal() / 1024); // KB
		memoryInfo.setMemUsed(mem.getUsed() / 1024);
		memoryInfo.setMemFree(mem.getFree() / 1024);
		memoryInfo.setActualUsed(mem.getActualUsed() / 1024);
		memoryInfo.setActualFree(mem.getActualFree() / 1024);
		memoryInfo.setSwapTotal(swap.getTotal() / 1024);
		memoryInfo.setSwapUsed(swap.getUsed() / 1024);
		memoryInfo.setSwapFree(swap.getFree() / 1024);
		memoryInfo.setRam(mem.getRam());
		sysInfo.setMemoryInfo(memoryInfo);
	}
	
	/**
	 * 시스템 프로세스 상태 정보를 설정함.
	 *
	 * @throws SigarException
	 * @see {@link org.hyperic.sigar.Sigar#getProcStat()}
	 */
	public static void setProcessStatInfo() throws SigarException {
		
		ProcStat procStat = getInstance().sigar.getProcStat();
		ProcessStatInfo processStatInfo = new ProcessStatInfo();
		processStatInfo.setTotal(procStat.getTotal());
		processStatInfo.setIdle(procStat.getIdle());
		processStatInfo.setRunning(procStat.getRunning());
		processStatInfo.setSleeping(procStat.getSleeping());
		processStatInfo.setStopped(procStat.getStopped());
		processStatInfo.setZombie(procStat.getZombie());
		processStatInfo.setThreads(procStat.getThreads());
		
		sysInfo.setProcessStatInfo(processStatInfo);
	}
	
	/**
	 * 프로세스 정보를 설정함. 
	 *
	 * @param args 명령 옵션 값.
	 * @throws SigarException
	 * @see {@link org.hyperic.sigar.Sigar#getProcList()}
	 */
	public static void setProcessInfo(String[] args) throws SigarException {
		Map<Long, ProcessInfo> procInfoMap = new LinkedHashMap<Long, ProcessInfo>();
		
		long[] pids = getInstance().sigar.getProcList();
		
		if (args == null || args.length == 0) {
			pids = getInstance().sigar.getProcList();
		} else {
			pids = shell.findPids(args);
		}
		
		for (Long pid : pids) {
			ProcessInfo procInfo = new ProcessInfo();
			ProcessCpuInfo processCpuInfo = gatherProcessCpu(pid);
			ProcessExeInfo processExeInfo = setProcessExe(pid);
			ProcessStateInfo processStateInfo = setProcessState(pid);
			ProcessMemoryInfo processMemoryInfo = getProcessMemory(pid);
			
			procInfo.setProcessCpuInfo(processCpuInfo);
			procInfo.setProcessExeInfo(processExeInfo);
			procInfo.setProcessStateInfo(processStateInfo);
			procInfo.setProcessMemoryInfo(processMemoryInfo);
			
			procInfoMap.put(pid, procInfo);
		}
		sysInfo.setProcessInfoMap(procInfoMap);
	}
	
	/**
	 * CPU정보를 반환함.
	 *
	 * @param pid process ID
	 * @return CPU정보.
	 * 
	 * @see ProcCpu
	 */
	public static ProcessCpuInfo gatherProcessCpu(long pid) {
		ProcessCpuInfo processCpuInfo = new ProcessCpuInfo();
		String unknown = "???";
		try {
			ProcCpu procCpu = getInstance().sigar.getProcCpu(pid);
			processCpuInfo.setPercent(String.valueOf((procCpu.getPercent() * 100)));
			processCpuInfo.setLastTime(getStartTime(procCpu.getLastTime()));
			processCpuInfo.setStartTime(getStartTime(procCpu.getStartTime()));
			processCpuInfo.setUser(String.valueOf(procCpu.getUser()));
			processCpuInfo.setSys(String.valueOf(procCpu.getSys()));
			processCpuInfo.setTotal(Ps.getCpuTime(procCpu.getTotal()));
		}
		catch (Exception e) {
			System.err.println("ProcessCpuInfo pid=" + pid + ", " + e.getMessage());
			processCpuInfo.setPercent(unknown);
			processCpuInfo.setLastTime(unknown);
			processCpuInfo.setStartTime(unknown);
			processCpuInfo.setUser(unknown);
			processCpuInfo.setSys(unknown);
			processCpuInfo.setTotal(unknown);
		}
		return processCpuInfo;
	}
	
	/**
	 * 프로세스에 대한 작업 정보를 반환함.
	 *
	 * @param pid 실행중인 프로세스 아이디.
	 * @return ProcessExeInfo
	 * 
	 * @see ProcExe
	 */
	public static ProcessExeInfo setProcessExe(long pid) {
		ProcessExeInfo processExeInfo = new ProcessExeInfo();
		String unknown = "???";
		try {
			ProcExe procExe = getInstance().sigar.getProcExe(pid);
			processExeInfo.setCwd(procExe.getCwd());
			processExeInfo.setName(procExe.getName());
		}
		catch (Exception e) {
			System.err.println("ProcessExeInfo pid=" + pid + ", " + e.getMessage());
			processExeInfo.setCwd(unknown);
			processExeInfo.setName(unknown);
		}
		return processExeInfo;
	}
	
	/**
	 * 프로세스에 대한 작업 정보를 반환함.
	 *
	 * @param pid 실행중인 프로세스 아이디.
	 * @return 프로세스에 대한 작업 정보.
	 * 
	 * @see ProcState
	 * @see ProcCredName
	 */
	public static ProcessStateInfo setProcessState(long pid) {
		ProcessStateInfo processStateInfo = new ProcessStateInfo();
		String unknown = "???";
		
		try {
			ProcState procState = getInstance().sigar.getProcState(pid);
			processStateInfo.setName(procState.getName());
			processStateInfo.setNice(String.valueOf(procState.getNice()));
			processStateInfo.setPpid(String.valueOf(procState.getPpid()));
			processStateInfo.setPriority(String.valueOf(procState.getPriority()));
			processStateInfo.setProcessor(String.valueOf(procState.getProcessor()));
			processStateInfo.setState(String.valueOf(procState.getState()));
			processStateInfo.setThreads(String.valueOf(procState.getThreads()));
			processStateInfo.setTty(String.valueOf(procState.getTty()));
		} catch (Exception e) {
			System.err.println("ProcessStateInfo pid=" + pid + ", " + e.getMessage());
			processStateInfo.setName(unknown);
			processStateInfo.setNice(unknown);
			processStateInfo.setPpid(unknown);
			processStateInfo.setPriority(unknown);
			processStateInfo.setProcessor(unknown);
			processStateInfo.setState(unknown);
			processStateInfo.setThreads(unknown);
			processStateInfo.setTty(unknown);
		}
		
		try {
			ProcCredName procCredName = getInstance().sigar.getProcCredName(pid);
			String name = ProcUtil.getDescription(getInstance().sigar, pid);
			
			processStateInfo.setUser(procCredName.getUser());
			processStateInfo.setGroup(procCredName.getGroup());
			processStateInfo.setDesc(name);
			
		} catch (SigarException e) {
			processStateInfo.setUser(unknown);
			processStateInfo.setUser(unknown);
		}
		return processStateInfo;
	}
	
	/**
	 * 프로세스 메모리 정보를 반환함.
	 *
	 * @param pid processID
	 * @return ProcessMemoryInfo process memory VO
	 * @see ProcMem
	 */
	public static ProcessMemoryInfo getProcessMemory(long pid) {
		ProcessMemoryInfo processMemoryInfo = new ProcessMemoryInfo();
		String unknown = "???";
		try {
			ProcMem pm = getInstance().sigar.getProcMem(pid);
			processMemoryInfo.setSize(Sigar.formatSize(pm.getSize()));
			processMemoryInfo.setResident(String.valueOf(pm.getResident()));
			processMemoryInfo.setShare(Sigar.formatSize(pm.getShare()));
			processMemoryInfo.setMinorFaults(String.valueOf(pm.getMinorFaults()));
			processMemoryInfo.setMajorFaults(String.valueOf(pm.getMajorFaults()));
			processMemoryInfo.setPageFaults(String.valueOf(pm.getPageFaults()));
		} catch (Exception e) {
			System.err.println("ProcessMemoryInfo pid=" + pid + ", " + e.getMessage());
			processMemoryInfo.setSize(unknown);
			processMemoryInfo.setResident(unknown);
			processMemoryInfo.setShare(unknown);
			processMemoryInfo.setMinorFaults(unknown);
			processMemoryInfo.setMajorFaults(unknown);
			processMemoryInfo.setPageFaults(unknown);
		}
		return processMemoryInfo;
	}
	
	/**
	 * CPU 정보를 설정함.
	 *
	 * @throws SigarException
	 * @see CpuInfo
	 */
	public static void setCpuInfo() throws SigarException {
		CpuInfo cpuInfo = new CpuInfo();
		org.hyperic.sigar.CpuInfo info = getInstance().sigar.getCpuInfoList()[0];
		cpuInfo.setVendor(info.getVendor());
		cpuInfo.setModel(info.getModel());
		cpuInfo.setMhz(info.getMhz());
		cpuInfo.setTotalCpus(info.getTotalCores());
		if ((info.getTotalCores() != info.getTotalSockets()) || (info.getCoresPerSocket() > info.getTotalCores())) {
			cpuInfo.setPhysicalCpus(info.getTotalSockets());
			cpuInfo.setCoresPerCpu(info.getCoresPerSocket());
		}
		long cacheSize = info.getCacheSize();
		if (cacheSize != Sigar.FIELD_NOTIMPL) {
			cpuInfo.setCacheSize(cacheSize);
		}
		sysInfo.setCpuInfo(cpuInfo);
	}
	
	/**
	 * 시스템 부팅 시간 및 실행정보를 설정함.
	 * 
	 * @throws SigarException
	 */
	public static void setUptimeInfo() throws SigarException {
		
		double uptime = getInstance().sigar.getUptime().getUptime();
		String uptimeResult = Uptime.getInfo(getInstance().sigar);
		
		UptimeInfo uptimeInfo = new UptimeInfo();
		uptimeInfo.setInfoAll(uptimeResult);
		uptimeInfo.setCurrentTime(DateUtils.getCurrentTimeString());
		uptimeInfo.setUptimeSeconds(uptime);
		parseUptimeInfo(uptimeResult, uptimeInfo);
		sysInfo.setUptimeInfo(uptimeInfo);
	}
	
	/**
	 * 버전 정보를 설정함.
	 *
	 * @throws IOException
	 * 
	 * @see Version
	 * @see OperatingSystem
	 */
	public static void setVersionInfo() throws IOException {
		
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(os);
		Version.printInfo(ps);
		String result = os.toString();
		
		NativeInfo nativeInfo = new NativeInfo();
		nativeInfo.setSigarVersionJava(Sigar.VERSION_STRING);
		nativeInfo.setSigarVersionNative(Sigar.NATIVE_VERSION_STRING);
		nativeInfo.setBuildDateJava(Sigar.BUILD_DATE);
		nativeInfo.setBuildDateNative(Sigar.NATIVE_BUILD_DATE);
		nativeInfo.setScmRevJava(Sigar.SCM_REVISION);
		nativeInfo.setScmRevNative(Sigar.NATIVE_SCM_REVISION);
		parseNativeInfo(result, nativeInfo);
		sysInfo.setNativeInfo(nativeInfo);
		
		OsInfo osInfo = new OsInfo();
		osInfo.setCurrentUser(System.getProperty("user.name"));
		OperatingSystem sys = OperatingSystem.getInstance();
		osInfo.setDescription(sys.getDescription());
		osInfo.setName(sys.getName());
		osInfo.setArch(sys.getArch());
		osInfo.setMachine(sys.getMachine());
		osInfo.setVersion(sys.getVersion());
		osInfo.setPatchLevel(sys.getPatchLevel());
		osInfo.setVendor(sys.getVendor());
		osInfo.setVendorVersion(sys.getVendorVersion());
		osInfo.setCodeName(sys.getVendorCodeName());
		osInfo.setDataModel(sys.getDataModel());
		osInfo.setCpuEndian(sys.getCpuEndian());
		sysInfo.setOsInfo(osInfo);
		
		JavaInfo javaInfo = new JavaInfo();
		javaInfo.setVmVersion(System.getProperty("java.vm.version"));
		javaInfo.setVmVendor(System.getProperty("java.vm.vendor"));
		javaInfo.setJavaHome(System.getProperty("java.home"));
		sysInfo.setJavaInfo(javaInfo);
		
		os.close();
		ps.close();
	}
	
	/**
	 * 시스템 부팅 시간 및 실행정보를 설정함.
	 *
	 * @param uptime uptime객체로부터 얻어온 시간 문자열 값.
	 * @param uptimeInfo uptimeInfo 객체.
	 */
	private static void parseUptimeInfo(String uptime, UptimeInfo uptimeInfo) {
		// ex. 11:06 오전 up 1 day, 3:8, (load average unknown)
		uptime = uptime.split(" up ")[1];
		String daysAndTime = "";
		String loadAverage = "";
		if (!uptime.contains("load average: ")) {
			int lastComma = uptime.lastIndexOf(',');
			daysAndTime = uptime.substring(0, lastComma);
			loadAverage = uptime.substring(lastComma + 1).trim();
		}
		else {
			String daysAndTimePattern = "^((\\d+ days?,)? (\\d{1,2}:\\d{1,2})),.*$";
			if (uptime.matches(daysAndTimePattern)) {
				daysAndTime = uptime.replaceAll(daysAndTimePattern, "$1");
			}
			loadAverage = uptime.split("load average: ")[1];
		}
		uptimeInfo.setUptime(daysAndTime);
		uptimeInfo.setLoadAverage(loadAverage);
	}

	/**
	 * 시스템 기본 정보를 설정함.
	 *
	 * @param nativeAndOsInfo
	 * @param nativeInfo
	 */
	private static void parseNativeInfo(String nativeAndOsInfo, NativeInfo nativeInfo) {
		// @formatter:off
		/* ex.) NativeInfo sample
			Sigar version.......java=1.6.4.129, native=1.6.4.127
			Build date..........java=04/28/2010 04:26 PM, native=04/28/2010 04:26 PM
			SCM rev.............java=4b67f57, native=4b67f57
			Archlib.............sigar-x86-winnt.dll
			Current fqdn........xx.xx.xx.xx
			Hostname............woo
			Language............1042:Korean (Korea)
			Perflib lang id.....012
		*/
		// @formatter:on
		String archlibPattern = "(?s).*Archlib\\.+([^\r\n]+)\r?\n.*(?s)";
		String fqdnPattern = "(?s).*Current fqdn\\.+([^\r\n]+)\r?\n.*(?s)";
		String hostnamePattern = "(?s).*Hostname\\.+([^\r\n]+)\r?\n.*(?s)";
		String languagePattern = "(?s).*Language\\.+([^\r\n]+)\r?\n.*(?s)";
		String perflibLangIdPattern = "(?s).*Perflib lang id\\.+([^\r\n]+)\r?\n.*(?s)";
		
		if (nativeAndOsInfo.matches(archlibPattern)) {
			nativeInfo.setArchlib(nativeAndOsInfo.replaceAll(archlibPattern, "$1"));
		}
		
		if (nativeAndOsInfo.matches(fqdnPattern)) {
			nativeInfo.setCurrentFqdn(nativeAndOsInfo.replaceAll(fqdnPattern, "$1"));
		}
		
		if (nativeAndOsInfo.matches(hostnamePattern)) {
			nativeInfo.setHostname(nativeAndOsInfo.replaceAll(hostnamePattern, "$1"));
		}
		
		if (nativeAndOsInfo.matches(languagePattern)) {
			nativeInfo.setLanguage(nativeAndOsInfo.replaceAll(languagePattern, "$1"));
		}
		
		if (nativeAndOsInfo.matches(perflibLangIdPattern)) {
			nativeInfo.setPerflibLangId(nativeAndOsInfo.replaceAll(perflibLangIdPattern, "$1"));
		}
		
		if (!nativeAndOsInfo.contains("Hostname")) {
			nativeInfo.setHostname(nativeInfo.getCurrentFqdn());
		}
		
	}

	/**
	 * 시스템 제한 정보 값을 설정함.
	 *
	 * @param output 출력할 정보 객체.
	 * @param ulimitInfo 시스템 제한 정보 값을 설정할 객체.
	 */
	private static void parseUlimitInfo(List<String> output, UlimitInfo ulimitInfo) {
		// @formatter:off
		/* ex.) Ulimit sample
			core file size.......-1
			data seg size........-1
			file size............-1
			pipe size............-1
			max memory size......-1
			open files...........-1
			stack size...........258912
			cpu time.............-1
			max user processes...-1
			virtual memory.......2147483648
		*/
		// @formatter:on
		for (String line : output) {
			if (line.indexOf("core") == 0) {
				ulimitInfo.setCoreFileSize(line.split("\\.+")[1]);
			}
			else if (line.indexOf("data") == 0) {
				ulimitInfo.setDataSegSize(line.split("\\.+")[1]);
			}
			else if (line.indexOf("file") == 0) {
				ulimitInfo.setFileSize(line.split("\\.+")[1]);
			}
			else if (line.indexOf("pipe") == 0) {
				ulimitInfo.setPipeSize(line.split("\\.+")[1]);
			}
			else if (line.indexOf("max memory") == 0) {
				ulimitInfo.setMemory(line.split("\\.+")[1]);
			}
			else if (line.indexOf("open") == 0) {
				ulimitInfo.setOpenFiles(line.split("\\.+")[1]);
			}
			else if (line.indexOf("stack") == 0) {
				ulimitInfo.setStackSize(line.split("\\.+")[1]);
			}
			else if (line.indexOf("cpu") == 0) {
				ulimitInfo.setCpuTime(line.split("\\.+")[1]);
			}
			else if (line.indexOf("max user") == 0) {
				ulimitInfo.setMaxUserProcesses(line.split("\\.+")[1]);
			}
			else if (line.indexOf("virtual") == 0) {
				ulimitInfo.setVirtualMemory(line.split("\\.+")[1]);
			}
		}
	}
	
	/**
	 * 네트워크 라우트 정보 값을 반환함.
	 *
	 * @return 라우트 정보 값.
	 * @throws SigarException
	 * @throws ShellCommandExecException
	 * @throws ShellCommandUsageException
	 * @see NetRoute
	 */
	public static List<String> getNetworkRouteInfo() throws SigarException, ShellCommandUsageException,
			ShellCommandExecException {
		Route route = new Route(shell);
		route.processCommand(new String[] {});
		forceFlushPrintfItems(route);
		return forceGetOutput(route);
	}
	
	/**
	 * 네트워크 상태 정보 값을 반환함.
	 *
	 * @param args (ex. new String[]{ "a" })
	 * @return netstat List<String> of string resulting from output execution
	 * @throws SigarException
	 * @throws ShellCommandExecException
	 * @throws ShellCommandUsageException
	 * @see Netstat
	 * @see org.hyperic.sigar.Sigar#getNetConnectionList(int)
	 */
	public static List<String> getNetStat(String[] args) throws SigarException, ShellCommandUsageException,
			ShellCommandExecException {
		Netstat netstat = new Netstat(shell);
		netstat.processCommand(args);
		forceFlushPrintfItems(netstat);
		return forceGetOutput(netstat);
	}
	
	/**
	 * 시스템에 로그인 된 사용자 목록을 반환함.
	 * 
	 * @return 시스템에 로그인 된 사용자 목록.
	 * @throws SigarException
	 * @throws ShellCommandExecException
	 * @throws ShellCommandUsageException
	 * @see org.hyperic.sigar.Who
	 */
	public static List<String> getWho() throws SigarException, ShellCommandUsageException, ShellCommandExecException {
		Who who = new Who(shell);
		who.processCommand(new String[] {});
		forceFlushPrintfItems(who);
		return forceGetOutput(who);
	}
	
	/**
	 * ls명령을 실행하고 그 결과 값을 반환함.
	 * 
	 * @param filePath 파일경로.
	 * @return ls실행 결과.
	 * @throws SigarException
	 * @throws ShellCommandUsageException
	 * @throws ShellCommandExecException
	 */
	public static List<String> getLs(String filePath) throws SigarException, ShellCommandUsageException,
			ShellCommandExecException {
		Ls ls = new Ls(shell);
		ls.processCommand(new String[] { filePath });
		forceFlushPrintfItems(ls);
		return forceGetOutput(ls);
	}
	
	/**
	 * 파일 또는 디렉토리 정보를 검색한 결과를 반환함.
	 *
	 * @param name 파일 또는 디렉토리.
	 * @return 파일 또는 디렉토리 정보를 검색한 결과. 
	 * @throws SigarException
	 * @throws IOException
	 * 
	 * @see org.hyperic.sigar.FileInfo
	 * @see DirUsage
	 */
	public static FileInfo getFileInfo(String name) throws SigarException, IOException {
		org.hyperic.sigar.FileInfo fi = getInstance().sigar.getFileInfo(name);
		org.hyperic.sigar.FileInfo fiLink = getInstance().sigar.getLinkInfo(name);
		
		FileInfo fileInfo = new FileInfo();
		fileInfo.setName(fi.getName());
		fileInfo.setPermission(fi.getPermissionsString());
		fileInfo.setMode(fi.getMode());
		fileInfo.setType(fi.getTypeString());
		fileInfo.setSize(fi.getSize());
		fileInfo.setUid(fi.getUid());
		fileInfo.setGid(fi.getGid());
		fileInfo.setInode(fi.getInode());
		fileInfo.setDevice(fi.getDevice());
		fileInfo.setNlink(fi.getNlink());
		fileInfo.setAtime(fi.getAtime());
		fileInfo.setMtime(fi.getMtime());
		fileInfo.setCtime(fi.getCtime());
		
		if (fiLink.getType() == 6) {
			fileInfo.setCanonicalPath(new File(name).getCanonicalPath());
		}
		
		fileInfo.setTypeChar(fiLink.getTypeChar());
		
		if (fi.getType() == org.hyperic.sigar.FileInfo.TYPE_DIR) {
			DirUsage ds = getInstance().sigar.getDirUsage(name);
			fileInfo.setTotal(ds.getTotal());
			fileInfo.setFiles(ds.getFiles());
			fileInfo.setSubdirs(ds.getSubdirs());
			fileInfo.setSymlinks(ds.getSymlinks());
			fileInfo.setChrdevs(ds.getChrdevs());
			fileInfo.setBlkdevs(ds.getBlkdevs());
			fileInfo.setSockets(ds.getSockets());
			fileInfo.setDiskUsage(ds.getDiskUsage());
		}
		
		return fileInfo;
	}
	
	private static String getStartTime(long time) {
		if (time == 0) {
			return "00:00";
		}
		
		long timeNow = System.currentTimeMillis();
		String fmt = "MMMd";
		
		if ((timeNow - time) < ((60 * 60 * 24) * 1000)) {
			fmt = "HH:mm";
		}
		
		DateTime dt = new DateTime(time);
		DateTimeFormatter wantedfmt = DateTimeFormat.forPattern(fmt);
		return wantedfmt.print(dt);
	}
	
	public static String print(List<String> info, int mod) {
		StringBuilder buf = new StringBuilder();
		Iterator<String> i = info.iterator();
		boolean hasNext = i.hasNext();
		int index = 0;
		while (hasNext) {
			index++;
			buf.append((String) i.next());
			hasNext = i.hasNext();
			if (index % mod == 0)
				buf.append("\n");
			else if (hasNext)
				buf.append("\t");
			
		}
		return buf.toString();
	}
	
	/**
	 * Sigar 객체를 반환함.
	 * @return Sigar객체.
	 */
	public Sigar getSigar() {
		return sigar;
	}
	
	/**
	 * 시스템 정보를 설정함.
	 * 
	 * @param sysInfo
	 */
	protected static void setSysInfo(SysInfo sysInfo) {
		SigarAccessor.sysInfo = sysInfo;
	}
	
	/**
	 * 시스템 정보를 반환함.
	 * @return
	 */
	public static SysInfo getSysInfo() {
		return SigarAccessor.sysInfo;
	}
	
	public static void directRun(String registeredCmd, String[] args) throws ShellCommandUsageException,
			ShellCommandExecException {
		shell.processCommand(shell.getHandler(registeredCmd), args);
	}
	
	private static void forceFlushPrintfItems(SigarCommandBase target) {
		ReflectionUtils.invokeMethod(flushPrintfItems, target);
	}
	
	private static List<String> forceGetOutput(SigarCommandBase target) {
		return (List<String>) ReflectionUtils.getField(output, target);
	}

}
