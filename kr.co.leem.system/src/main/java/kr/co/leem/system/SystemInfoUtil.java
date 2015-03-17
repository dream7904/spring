package kr.co.leem.system;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import kr.co.leem.system.domains.CpuInfo;
import kr.co.leem.system.domains.FileInfo;
import kr.co.leem.system.domains.FileSystemInfo;
import kr.co.leem.system.domains.FileSystemUsageInfo;
import kr.co.leem.system.domains.OsInfo;
import kr.co.leem.system.domains.ProcessInfo;
import kr.co.leem.system.domains.ProcessStatInfo;
import kr.co.leem.system.domains.SigarAccessor;
import kr.co.leem.system.scripts.DefaultScriptExecutor;
import kr.co.leem.system.scripts.OsType;
import kr.co.leem.utils.validation.ValidationUtils;

import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.shell.ShellCommandExecException;
import org.hyperic.sigar.shell.ShellCommandUsageException;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.util.StringUtils;

/**
 * SystemInfoUtils.
 *
 * @author 임 성천.
 */
public abstract class SystemInfoUtil extends BaseSystemUtils {
	
	
	private static final String[] MOBILE_OS = new String[] { "Android", "iPhone OS",
			"iPhone", "iPod", "iPad" };
	
	private static final String[] DESK_OS = new String[] { "Windows", "Win9", "Linux",
			"Macintosh", "Mac", "FreeBSD", "IRIX", "SunOS", "BeOS" };
	
	private static final Pattern FIRST_PAREN_PATTERN = Pattern.compile("^.*?\\((.+?)\\).*$");
	
	/**
	 * TOTAL, USED, FREE
	 * @author 임 성천.
	 *
	 */
	public enum MemRegion {
		TOTAL, USED, FREE
	}
	
	/**
	 * 파일 시스템 전체 정보를 반환함.
	 *
	 * @return disk information
	 */
	public static List<FileSystemInfo> getDiskProperty() {
		return processIO(new IOCallback<List<FileSystemInfo>>() {
			public List<FileSystemInfo> doInProcessIO() throws IOException, SigarException {
				return SigarAccessor.getSysInfo().getFileSystems();
			}
		});
	}
	
	/**
	 * 파일 시스템 정보를 반환함.
	 *
	 * @param name 경로명.
	 * @return 파일 시스템 정보.
	 */
	public static FileSystemInfo getDiskProperty(final String dirPath) {
		return processIO(new IOCallback<FileSystemInfo>() {
			public FileSystemInfo doInProcessIO() throws IOException, SigarException {
				int index = 0;
				boolean flag = false;
				List<FileSystemInfo> fileSystemInfo = SigarAccessor.getSysInfo().getFileSystems();
				for (FileSystemInfo fs : fileSystemInfo) {
					String dirName = fs.getDirName();
					String path = dirPath;
					
					if (DefaultScriptExecutor.getOsType().is(OsType.Windows)) {
						path = replacePath(path);
					} if (path.equals(dirName)) {
						flag = true;
						break;
					} else if (fileSystemInfo.size() == index + 1) {
						flag = false;
					}
					
					index++;
				}
				
				if (!flag) {
					throw new SigarException("경로를 찾을 수 없습니다.");
				}
				
				return SigarAccessor.getSysInfo().getFileSystems().get(index);
			}
		});
	}
	
	/**
	 * 파일 시스템 정보를 반환함.
	 *
	 * @return 파일 시스템 정보를 반환.
	 */
	public static Map<String, FileSystemUsageInfo> getDiskCapacity() {
		return processIO(new IOCallback<Map<String, FileSystemUsageInfo>>() {
			public Map<String, FileSystemUsageInfo> doInProcessIO() throws IOException, SigarException {
				return SigarAccessor.getSysInfo().getFileSystemUsageMap();
			}
		});
	}
	
	/**
	 * 파일 시스템 정보를 반환함.
	 *
	 * @param name file system to be searched
	 * @return file system information
	 */
	public static FileSystemUsageInfo getDiskCapacity(final String name) {
		return processIO(new IOCallback<FileSystemUsageInfo>() {
			public FileSystemUsageInfo doInProcessIO() throws IOException, SigarException {
				if (DefaultScriptExecutor.getOsType().is(OsType.Windows)) {
					SigarAccessor.gatherFileSystemUsage(replacePath(name));
				} else {
					SigarAccessor.gatherFileSystemUsage(name);
				}
				return SigarAccessor.getSysInfo().getFileSystemUsageInfo();
			}
		});
	}
	
	/**
	 * Show valid information when input file system is mounted. Entire memory
	 * size, used size, percentage, usable memory size
	 *
	 * @param name file system to be searched
	 * @return file system information
	 */
	public static FileSystemUsageInfo getMountedDiskCapacity(final String name) {
		return processIO(new IOCallback<FileSystemUsageInfo>() {
			public FileSystemUsageInfo doInProcessIO() throws IOException, SigarException {
				if (DefaultScriptExecutor.getOsType().is(OsType.Windows)) {
					SigarAccessor.setMountedFileSystemUsage(replacePath(name));
				} else {
					SigarAccessor.setMountedFileSystemUsage(name);
				}
				return SigarAccessor.getSysInfo().getFileSystemUsageInfo();
			}
		});
	}
	
	/**
	 * Change path to fit Window OS
	 *
	 * @param path input path
	 * @return path that is changed to fit Window OS
	 */
	public static String replacePath(final String path) {
		return processIO(new IOCallback<String>() {
			public String doInProcessIO() throws IOException, SigarException {
				String replacePath = path.toUpperCase();
				if (ValidationUtils.isPatternMatching(replacePath, "[a-zA-Z]:"))
					replacePath = path + File.separator;
				else if (ValidationUtils.isPatternMatching(replacePath, "[a-zA-Z]:\\/"))
					replacePath = StringUtils.replace(path.toUpperCase(), "/", File.separator);
				return replacePath;
			}
		});
	}
	
	/**
	 * System OS information
	 * @return system OS information
	 */
	public static OsInfo getSystemInfo() {
		return processIO(new IOCallback<OsInfo>() {
			public OsInfo doInProcessIO() throws IOException, SigarException {
				return SigarAccessor.getSysInfo().getOsInfo();
			}
		});
	}
	
	/**
	 * Process information. Entire number, used number, process number by state,
	 * thread number, etc.
	 *
	 * @return process information
	 */
	public static ProcessStatInfo getProcessStat() {
		return processIO(new IOCallback<ProcessStatInfo>() {
			public ProcessStatInfo doInProcessIO() throws IOException, SigarException {
				return SigarAccessor.getSysInfo().getProcessStatInfo();
			}
		});
	}
	
	/**
	 * Process execution information search
	 *
	 * @param args command option
	 * @return process execution information list
	 */
	public static Map<Long, ProcessInfo> getProcessList(final String[] args) {
		return processIO(new IOCallback<Map<Long, ProcessInfo>>() {
			public Map<Long, ProcessInfo> doInProcessIO() throws IOException, SigarException {
				if (args != null && args.length > 0) {
					SigarAccessor.setProcessInfo(args);
				}
				return SigarAccessor.getSysInfo().getProcessInfoMap();
			}
		});
	}
	
	/**
	 * Return information that has the same format as ps execution result.<br>
	 * execution state of process currently executed
	 *
	 * @param args command option
	 * @return process execution information list
	 */
	public static List<String> getPs(final String[] args) {
		return processIO(new IOCallback<List<String>>() {
			public List<String> doInProcessIO() throws IOException, SigarException {
				List<String> info = new ArrayList<String>();
				
				if (args != null && args.length > 0) {
					SigarAccessor.setProcessInfo(args);
				}
				Map<Long, ProcessInfo> processInfoMap = SigarAccessor.getSysInfo().getProcessInfoMap();
				
				Set<Long> keySet = processInfoMap.keySet();
				Iterator<Long> iterator = keySet.iterator();
				
				while (iterator.hasNext()) {
					long key = iterator.next();
					ProcessInfo processInfo = processInfoMap.get(key);
					
					info.add(String.valueOf(key));
					info.add(processInfo.getProcessStateInfo().getUser());
					info.add(processInfo.getProcessCpuInfo().getStartTime());
					info.add(processInfo.getProcessMemoryInfo().getSize());
					info.add(processInfo.getProcessMemoryInfo().getResident());
					info.add(processInfo.getProcessMemoryInfo().getShare());
					info.add(processInfo.getProcessStateInfo().getState());
					info.add(processInfo.getProcessCpuInfo().getTotal());
					info.add(processInfo.getProcessStateInfo().getDesc());
				}
				return info;
			}
		});
	}
	
	/**
	 * Return information that has the same format as Who execution result.<br>
	 * check user currently accessing system
	 *
	 * @return who information
	 */
	public static List<String> getWho() {
		return processIO(new IOCallback<List<String>>() {
			public List<String> doInProcessIO() throws IOException, SigarException, ShellCommandUsageException,
					ShellCommandExecException {
				return SigarAccessor.getWho();
			}
		});
	}
	
	/**
	 * memRegion Show entire memory size, used size, usable memory size according to input value.
	 *
	 * @param memRegion region of memory
	 * @return memory information according to memRegion
	 */
	public static long getMemoryCapacity(final MemRegion memRegion) {
		return processIO(new IOCallback<Long>() {
			public Long doInProcessIO() throws IOException, SigarException {
				switch (memRegion) {
				case TOTAL:
					return SigarAccessor.getSysInfo().getMemoryInfo().getMemTotal();
				case USED:
					return SigarAccessor.getSysInfo().getMemoryInfo().getMemUsed();
				case FREE:
					return SigarAccessor.getSysInfo().getMemoryInfo().getMemFree();
				default:
					return SigarAccessor.getSysInfo().getMemoryInfo().getMemTotal();
				}
			}
		});
	}
	
	/**
	 * Return information that has the same format as ls execution result.<br>
	 * information for related directory and file
	 *
	 * @param name file or directory information
	 * @return ls information
	 */
	public static String getLs(final String name) {
		return processIO(new IOCallback<String>() {
			public String doInProcessIO() throws IOException, SigarException, ShellCommandUsageException,
					ShellCommandExecException {
				FileInfo fileInfo = SigarAccessor.getFileInfo(name);
				List<String> info = new ArrayList<String>();
				info.add(String.valueOf(fileInfo.getTypeChar()));
				info.add(fileInfo.getPermission());
				info.add(String.valueOf(fileInfo.getUid()));
				info.add(String.valueOf(fileInfo.getGid()));
				info.add(String.valueOf(fileInfo.getSize()));
				info.add(getDate(fileInfo.getMtime()));
				info.add(StringUtils.getFilename(StringUtils.cleanPath(name)));
				if (fileInfo.getTypeChar() == 6)
					info.add(name + " -> " + fileInfo.getCanonicalPath());
				else
					info.add("\t");
				return SigarAccessor.print(info, 8).replaceAll("\\n", "");
				// return SigarAccessor.getLs(name);
			}
		});
	}
	
	/**
	 * Get the Is list of files under related directory.
	 *
	 * @param name directory information
	 * @return ls list
	 */
	public static List<String> getLsList(final String name) {
		return processIO(new IOCallback<List<String>>() {
			public List<String> doInProcessIO() throws IOException, SigarException {
				List<String> info = new ArrayList<String>();
				
				File[] fileList = FileSystemUtils.getFileList(name, new String[] { "all" }, true);
				for (File file : fileList) {
					info.add(getLs(file.getAbsolutePath()));
				}
				return info;
			}
		});
	}
	
	/**
	 * Search Cpu information of related system
	 * @return Cpu information
	 */
	public static CpuInfo getCpuInfo() {
		return processIO(new IOCallback<CpuInfo>() {
			public CpuInfo doInProcessIO() throws IOException, SigarException {
				return SigarAccessor.getSysInfo().getCpuInfo();
			}
		});
	}
	
	private static String getDate(long mtime) {
		String fmt = "MMM dd  yyyy";
		DateTime dt = new DateTime(mtime);
		DateTimeFormatter wantedfmt = DateTimeFormat.forPattern(fmt);
		return wantedfmt.print(dt);
	}
	
	/**
	 * Search IP address information of client
	 *
	 * @param request HttpServletRequest
	 * @return IP address
	 */
	public static String getClientIP(final HttpServletRequest request) {
		return processIO(new IOCallback<String>() {
			public String doInProcessIO() throws IOException {
				String ipAddress = request.getRemoteAddr();
				return ipAddress;
			}
		});
	}
	
	/**
	 * Search OS information of client
	 * @param request HttpServletRequest
	 * @return client OS information
	 */
	public static String getClientOS(final HttpServletRequest request) {
		return processIO(new IOCallback<String>() {
			public String doInProcessIO() throws IOException {
				String userAgent = request.getHeader("user-agent");
				return findOsIfPossible(userAgent);
			}
		});
	}
	
	/**
	 * Client browser and version information
	 *
	 * @param request HttpServletRequest
	 * @return browser and version information
	 */
	public static String getClientBrowser(final HttpServletRequest request) {
		return processIO(new IOCallback<String>() {
			public String doInProcessIO() throws IOException {
				String userAgent = request.getHeader("user-agent");
				return userAgent.substring(0, userAgent.indexOf(" "));
			}
		});
	}
	
	protected static String findOsIfPossible(String str) {
		Matcher matcher = FIRST_PAREN_PATTERN.matcher(str);
		if (matcher.find()) {
			return StringUtils.trimWhitespace(tryWithDelimiter(matcher.group(1)));
		}
		return str;
	}
	
	private static String tryWithDelimiter(String firstParen) {
		int divCount = StringUtils.countOccurrencesOf(
				firstParen, ";");
		if (divCount > 0) {
			String candidate = findCandidate(firstParen.split(";"));
			if (!"".equals(candidate)) {
				return candidate;
			}
			return firstParen;
		}
		return firstParen;
	}
	
	private static String findCandidate(String[] split) {
		for (String candidate : split) {
			if (candidateCheck(candidate)) {
				return candidate;
			}
		}
		return "";
	}
	
	private static boolean candidateCheck(String candidate) {
		for (String os : MOBILE_OS) {
			if (candidate.contains(os)) {
				return true;
			}
		}
		
		for (String os : DESK_OS) {
			if (candidate.contains(os)) {
				return true;
			}
		}
		return false;
	}
}