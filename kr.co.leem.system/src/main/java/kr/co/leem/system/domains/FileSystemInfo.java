package kr.co.leem.system.domains;

import java.io.Serializable;

/**
 * FileSystemInfo.
 * 
 * @author 임 성천.
 */
public class FileSystemInfo implements Serializable {
	
	private static final long serialVersionUID = 5693514024482043739L;
	
	private String dirName;
	
	private String devName;
	
	private String typeName;
	
	private String sysTypeName;
	
	private String options;
	
	private int type;
	
	private long flags;
	
	public String getDirName() {
		return dirName;
	}
	
	public void setDirName(String dirName) {
		this.dirName = dirName;
	}
	
	public String getDevName() {
		return devName;
	}
	
	public void setDevName(String devName) {
		this.devName = devName;
	}
	
	public String getTypeName() {
		return typeName;
	}
	
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	public String getSysTypeName() {
		return sysTypeName;
	}
	
	public void setSysTypeName(String sysTypeName) {
		this.sysTypeName = sysTypeName;
	}
	
	public String getOptions() {
		return options;
	}
	
	public void setOptions(String options) {
		this.options = options;
	}
	
	public int getType() {
		return type;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	public long getFlags() {
		return flags;
	}
	
	public void setFlags(long flags) {
		this.flags = flags;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}