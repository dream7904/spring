package kr.co.leem.system.domains;

import java.io.Serializable;

/**
 * CpuInfo.
 * 
 * @author 임 성천.
 */
public class CpuInfo implements Serializable {

	private static final long serialVersionUID = -179811367165929651L;

	private String vendor;

	private String model;

	private int mhz;

	private int totalCpus;

	private int physicalCpus;

	private int coresPerCpu;

	private long cacheSize;

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getMhz() {
		return mhz;
	}

	public void setMhz(int mhz) {
		this.mhz = mhz;
	}

	public int getTotalCpus() {
		return totalCpus;
	}

	public void setTotalCpus(int totalCpus) {
		this.totalCpus = totalCpus;
	}

	public int getPhysicalCpus() {
		return physicalCpus;
	}

	public void setPhysicalCpus(int physicalCpus) {
		this.physicalCpus = physicalCpus;
	}

	public int getCoresPerCpu() {
		return coresPerCpu;
	}

	public void setCoresPerCpu(int coresPerCpu) {
		this.coresPerCpu = coresPerCpu;
	}

	public long getCacheSize() {
		return cacheSize;
	}

	public void setCacheSize(long cacheSize2) {
		this.cacheSize = cacheSize2;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
