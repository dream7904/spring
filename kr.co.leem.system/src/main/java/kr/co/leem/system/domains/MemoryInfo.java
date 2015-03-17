package kr.co.leem.system.domains;

import java.io.Serializable;

/**
 * MemoryInfo.
 * 
 * @author 임 성천.
 */
public class MemoryInfo implements Serializable {
	
	private static final long serialVersionUID = 4754733125680962841L;
	
	private long memTotal;
	
	private long memUsed;
	
	private long memFree;
	
	private long actualUsed;
	
	private long actualFree;
	
	private long swapTotal;
	
	private long swapUsed;
	
	private long swapFree;
	
	private long ram;
	
	public long getMemTotal() {
		return memTotal;
	}
	
	public void setMemTotal(long memTotal) {
		this.memTotal = memTotal;
	}
	
	public long getMemUsed() {
		return memUsed;
	}
	
	public void setMemUsed(long memUsed) {
		this.memUsed = memUsed;
	}
	
	public long getMemFree() {
		return memFree;
	}
	
	public void setMemFree(long memFree) {
		this.memFree = memFree;
	}
	
	public long getActualUsed() {
		return actualUsed;
	}
	
	public void setActualUsed(long actualUsed) {
		this.actualUsed = actualUsed;
	}
	
	public long getActualFree() {
		return actualFree;
	}
	
	public void setActualFree(long actualFree) {
		this.actualFree = actualFree;
	}
	
	public long getSwapTotal() {
		return swapTotal;
	}
	
	public void setSwapTotal(long swapTotal) {
		this.swapTotal = swapTotal;
	}
	
	public long getSwapUsed() {
		return swapUsed;
	}
	
	public void setSwapUsed(long swapUsed) {
		this.swapUsed = swapUsed;
	}
	
	public long getSwapFree() {
		return swapFree;
	}
	
	public void setSwapFree(long swapFree) {
		this.swapFree = swapFree;
	}
	
	public long getRam() {
		return ram;
	}
	
	public void setRam(long ram) {
		this.ram = ram;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}