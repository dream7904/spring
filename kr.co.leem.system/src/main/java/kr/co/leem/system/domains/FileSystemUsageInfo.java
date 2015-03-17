package kr.co.leem.system.domains;

import java.io.Serializable;

/**
 * FileSystemUsageInfo.
 * 
 * @author 임 성천.
 */
public class FileSystemUsageInfo implements Serializable {

	private static final long serialVersionUID = 5693514024482043739L;
	
	private long total;
	
	private long free;
	
	private long diskUsed;
	
	private long avail;
	
	private long files;
	
	private long freeFiles;
	
	private long diskReads;
	
	private long diskWrites;
	
	private long diskReadBytes;
	
	private long diskWriteBytes;
	
	private double diskQueue;
	
	private double diskServiceTime;
	
	private double usePercent;
	
	public long getTotal() {
		return total;
	}
	
	public void setTotal(long total) {
		this.total = total;
	}
	
	public long getFree() {
		return free;
	}
	
	public void setFree(long free) {
		this.free = free;
	}
	
	public long getDiskUsed() {
		return diskUsed;
	}
	
	public void setDiskUsed(long diskUsed) {
		this.diskUsed = diskUsed;
	}
	
	public long getAvail() {
		return avail;
	}
	
	public void setAvail(long avail) {
		this.avail = avail;
	}
	
	public long getFiles() {
		return files;
	}
	
	public void setFiles(long files) {
		this.files = files;
	}
	
	public long getFreeFiles() {
		return freeFiles;
	}
	
	public void setFreeFiles(long freeFiles) {
		this.freeFiles = freeFiles;
	}
	
	public long getDiskReads() {
		return diskReads;
	}
	
	public void setDiskReads(long diskReads) {
		this.diskReads = diskReads;
	}
	
	public long getDiskWrites() {
		return diskWrites;
	}
	
	public void setDiskWrites(long diskWrites) {
		this.diskWrites = diskWrites;
	}
	
	public long getDiskReadBytes() {
		return diskReadBytes;
	}
	
	public void setDiskReadBytes(long diskReadBytes) {
		this.diskReadBytes = diskReadBytes;
	}
	
	public long getDiskWriteBytes() {
		return diskWriteBytes;
	}
	
	public void setDiskWriteBytes(long diskWriteBytes) {
		this.diskWriteBytes = diskWriteBytes;
	}
	
	public double getDiskQueue() {
		return diskQueue;
	}
	
	public void setDiskQueue(double diskQueue) {
		this.diskQueue = diskQueue;
	}
	
	public double getDiskServiceTime() {
		return diskServiceTime;
	}
	
	public void setDiskServiceTime(double diskServiceTime) {
		this.diskServiceTime = diskServiceTime;
	}
	
	public double getUsePercent() {
		return usePercent;
	}
	
	public void setUsePercent(double usePercent) {
		this.usePercent = usePercent;
	}
}