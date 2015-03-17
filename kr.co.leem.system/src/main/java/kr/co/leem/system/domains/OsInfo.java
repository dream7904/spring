package kr.co.leem.system.domains;

import java.io.Serializable;

/**
 * OsInfo.
 * 
 * @author 임 성천.
 */
public class OsInfo implements Serializable {

	private static final long serialVersionUID = 5076932159591309262L;
	
	private String currentUser;
	
	private String description;
	
	private String name;
	
	private String arch;
	
	private String machine;
	
	private String version;
	
	private String patchLevel;
	
	private String vendor;
	
	private String vendorVersion;
	
	private String codeName;
	
	private String dataModel;
	
	private String cpuEndian;
	
	public void setCurrentUser(String currentUser) {
		this.currentUser = currentUser;
	}
	
	public String getCurrentUser() {
		return currentUser;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getArch() {
		return arch;
	}
	
	public void setArch(String arch) {
		this.arch = arch;
	}
	
	public String getMachine() {
		return machine;
	}
	
	public void setMachine(String machine) {
		this.machine = machine;
	}
	
	public String getVersion() {
		return version;
	}
	
	public void setVersion(String version) {
		this.version = version;
	}
	
	public String getPatchLevel() {
		return patchLevel;
	}
	
	public void setPatchLevel(String patchLevel) {
		this.patchLevel = patchLevel;
	}
	
	public String getVendor() {
		return vendor;
	}
	
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	
	public String getVendorVersion() {
		return vendorVersion;
	}
	
	public void setVendorVersion(String vendorVersion) {
		this.vendorVersion = vendorVersion;
	}
	
	public String getCodeName() {
		return codeName;
	}
	
	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}
	
	public String getDataModel() {
		return dataModel;
	}
	
	public void setDataModel(String dataModel) {
		this.dataModel = dataModel;
	}
	
	public String getCpuEndian() {
		return cpuEndian;
	}
	
	public void setCpuEndian(String cpuEndian) {
		this.cpuEndian = cpuEndian;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}