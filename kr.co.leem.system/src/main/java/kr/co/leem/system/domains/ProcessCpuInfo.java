package kr.co.leem.system.domains;

import java.io.Serializable;

/**
 * ProcessCpuInfo.
 * 
 * @author 임 성천.
 */
public class ProcessCpuInfo implements Serializable {
	
	private static final long serialVersionUID = 123476123342167378L;
	
	private String percent;
	
	private String lastTime;
	
	private String startTime;
	
	private String user;
	
	private String sys;
	
	private String total;
	
	public String getPercent() {
		return percent;
	}
	
	public void setPercent(String percent) {
		this.percent = percent;
	}
	
	public String getLastTime() {
		return lastTime;
	}
	
	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}
	
	public String getStartTime() {
		return startTime;
	}
	
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	
	public String getUser() {
		return user;
	}
	
	public void setUser(String user) {
		this.user = user;
	}
	
	public String getSys() {
		return sys;
	}
	
	public void setSys(String sys) {
		this.sys = sys;
	}
	
	public String getTotal() {
		return total;
	}
	
	public void setTotal(String total) {
		this.total = total;
	}
}