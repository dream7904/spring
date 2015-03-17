package kr.co.leem.system.domains;

import java.io.Serializable;

/**
 * ProcessStateInfo.
 * 
 * @author 임 성천.
 */
public class ProcessStateInfo implements Serializable {
	
	private static final long serialVersionUID = 17862344557L;
	
	private String name;
	
	private String nice;
	
	private String ppid;
	
	private String priority;
	
	private String processor;
	
	private String state;
	
	private String threads;
	
	private String tty;
	
	private String user;
	
	private String group;
	
	private String desc;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getNice() {
		return nice;
	}
	
	public void setNice(String nice) {
		this.nice = nice;
	}
	
	public String getPpid() {
		return ppid;
	}
	
	public void setPpid(String ppid) {
		this.ppid = ppid;
	}
	
	public String getPriority() {
		return priority;
	}
	
	public void setPriority(String priority) {
		this.priority = priority;
	}
	
	public String getProcessor() {
		return processor;
	}
	
	public void setProcessor(String processor) {
		this.processor = processor;
	}
	
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	public String getThreads() {
		return threads;
	}
	
	public void setThreads(String threads) {
		this.threads = threads;
	}
	
	public String getTty() {
		return tty;
	}
	
	public void setTty(String tty) {
		this.tty = tty;
	}
	
	public String getUser() {
		return user;
	}
	
	public void setUser(String user) {
		this.user = user;
	}
	
	public String getGroup() {
		return group;
	}
	
	public void setGroup(String group) {
		this.group = group;
	}
	
	public String getDesc() {
		return desc;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}
}