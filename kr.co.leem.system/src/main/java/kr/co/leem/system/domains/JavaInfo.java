package kr.co.leem.system.domains;

import java.io.Serializable;

/**
 * JavaInfo.
 * 
 * @author 임 성천.
 */
public class JavaInfo implements Serializable {
	
	private static final long serialVersionUID = 6453591327503523299L;
	
	private String vmVersion;
	
	private String vmVendor;
	
	private String javaHome;
	
	public String getVmVersion() {
		return vmVersion;
	}
	
	public void setVmVersion(String vmVersion) {
		this.vmVersion = vmVersion;
	}
	
	public String getVmVendor() {
		return vmVendor;
	}
	
	public void setVmVendor(String vmVendor) {
		this.vmVendor = vmVendor;
	}
	
	public String getJavaHome() {
		return javaHome;
	}
	
	public void setJavaHome(String javaHome) {
		this.javaHome = javaHome;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}