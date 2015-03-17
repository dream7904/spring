package kr.co.leem.system.domains;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * SysInfo.
 * 
 * @author 임 성천.
 */
public class SysInfo implements Serializable {
	
	private static final long serialVersionUID = 5594664763820167378L;
	
	private NativeInfo nativeInfo;
	
	private OsInfo osInfo;
	
	private JavaInfo javaInfo;
	
	private UptimeInfo uptimeInfo;
	
	private CpuInfo cpuInfo;
	
	private MemoryInfo memoryInfo;
	
	private List<FileSystemInfo> fileSystems;
	
	private FileSystemUsageInfo fileSystemUsageInfo;
	
	private Map<String, FileSystemUsageInfo> fileSystemUsageMap;
	
	private ProcessStatInfo processStatInfo;
	
	private Map<Long, ProcessInfo> processInfoMap;
	
	private NetworkInfo networkInfo;
	
	private Map<String, NetworkInfo> networkInfoMap;
	
	private UlimitInfo ulimitInfo;
	
	public NativeInfo getNativeInfo() {
		return nativeInfo;
	}
	
	public void setNativeInfo(NativeInfo nativeInfo) {
		this.nativeInfo = nativeInfo;
	}
	
	public OsInfo getOsInfo() {
		return osInfo;
	}
	
	public void setOsInfo(OsInfo osInfo) {
		this.osInfo = osInfo;
	}
	
	public JavaInfo getJavaInfo() {
		return javaInfo;
	}
	
	public void setJavaInfo(JavaInfo javaInfo) {
		this.javaInfo = javaInfo;
	}
	
	public UptimeInfo getUptimeInfo() {
		return uptimeInfo;
	}
	
	public void setUptimeInfo(UptimeInfo uptimeInfo) {
		this.uptimeInfo = uptimeInfo;
	}
	
	public CpuInfo getCpuInfo() {
		return cpuInfo;
	}
	
	public void setCpuInfo(CpuInfo cpuInfo) {
		this.cpuInfo = cpuInfo;
	}
	
	public MemoryInfo getMemoryInfo() {
		return memoryInfo;
	}
	
	public void setMemoryInfo(MemoryInfo memoryInfo) {
		this.memoryInfo = memoryInfo;
	}
	
	public List<FileSystemInfo> getFileSystems() {
		return fileSystems;
	}
	
	public void setFileSystems(List<FileSystemInfo> fileSystems) {
		this.fileSystems = fileSystems;
	}
	
	public FileSystemUsageInfo getFileSystemUsageInfo() {
		return fileSystemUsageInfo;
	}
	
	public void setFileSystemUsageInfo(FileSystemUsageInfo fileSystemUsageInfo) {
		this.fileSystemUsageInfo = fileSystemUsageInfo;
	}
	
	public Map<String, FileSystemUsageInfo> getFileSystemUsageMap() {
		return fileSystemUsageMap;
	}
	
	public void setFileSystemUsageMap(Map<String, FileSystemUsageInfo> fileSystemUsageMap) {
		this.fileSystemUsageMap = fileSystemUsageMap;
	}
	
	public ProcessStatInfo getProcessStatInfo() {
		return processStatInfo;
	}
	
	public void setProcessStatInfo(ProcessStatInfo processStatInfo) {
		this.processStatInfo = processStatInfo;
	}
	
	public Map<Long, ProcessInfo> getProcessInfoMap() {
		return processInfoMap;
	}
	
	public void setProcessInfoMap(Map<Long, ProcessInfo> processInfoMap) {
		this.processInfoMap = processInfoMap;
	}
	
	public NetworkInfo getNetworkInfo() {
		return networkInfo;
	}
	
	public void setNetworkInfo(NetworkInfo networkInfo) {
		this.networkInfo = networkInfo;
	}
	
	public Map<String, NetworkInfo> getNetworkInfoMap() {
		return networkInfoMap;
	}
	
	public void setNetworkInfoMap(Map<String, NetworkInfo> networkInfoMap) {
		this.networkInfoMap = networkInfoMap;
	}
	
	public UlimitInfo getUlimitInfo() {
		return ulimitInfo;
	}
	
	public void setUlimitInfo(UlimitInfo ulimitInfo) {
		this.ulimitInfo = ulimitInfo;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}