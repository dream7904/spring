package kr.co.leem.system.domains;

import java.io.Serializable;

/**
 * ProcessExeInfo.
 * 
 * @author 임 성천.
 */
public class ProcessExeInfo implements Serializable {
	
	private static final long serialVersionUID = 12323451L;
	
	private String cwd;
	
	private String name;
	
	public String getCwd() {
		return cwd;
	}
	
	public void setCwd(String cwd) {
		this.cwd = cwd;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}