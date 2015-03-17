package kr.co.leem.system.domains;

import java.io.Serializable;

/**
 * ProcessMemoryInfo.
 * 
 * @author 임 성천.
 */
public class ProcessMemoryInfo implements Serializable {
	
	private static final long serialVersionUID = 345091023L;
	
	private String size;
	
	private String resident;
	
	private String share;
	
	private String minorFaults;
	
	private String majorFaults;
	
	private String pageFaults;
	
	public String getSize() {
		return size;
	}
	
	public void setSize(String size) {
		this.size = size;
	}
	
	public String getResident() {
		return resident;
	}
	
	public void setResident(String resident) {
		this.resident = resident;
	}
	
	public String getShare() {
		return share;
	}
	
	public void setShare(String share) {
		this.share = share;
	}
	
	public String getMinorFaults() {
		return minorFaults;
	}
	
	public void setMinorFaults(String minorFaults) {
		this.minorFaults = minorFaults;
	}
	
	public String getMajorFaults() {
		return majorFaults;
	}
	
	public void setMajorFaults(String majorFaults) {
		this.majorFaults = majorFaults;
	}
	
	public String getPageFaults() {
		return pageFaults;
	}
	
	public void setPageFaults(String pageFaults) {
		this.pageFaults = pageFaults;
	}
}