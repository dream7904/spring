package kr.co.leem.system.domains;

import java.io.Serializable;

/**
 * UptimeInfo.
 * 
 * @author 임 성천.
 */
public class UptimeInfo implements Serializable {
	
	private static final long serialVersionUID = 5926436871334759737L;
	
	private String infoAll;
	
	private String currentTime;
	
	private String uptime;
	
	private double uptimeSeconds;
	
	private String loadAverage;
	
	public String getInfoAll() {
		return infoAll;
	}
	
	public void setInfoAll(String infoAll) {
		this.infoAll = infoAll;
	}
	
	public String getCurrentTime() {
		return currentTime;
	}
	
	public void setCurrentTime(String currentTime) {
		this.currentTime = currentTime;
	}
	
	public String getUptime() {
		return uptime;
	}
	
	public void setUptime(String uptime) {
		this.uptime = uptime;
	}
	
	public double getUptimeSeconds() {
		return uptimeSeconds;
	}
	
	public void setUptimeSeconds(double uptimeSeconds) {
		this.uptimeSeconds = uptimeSeconds;
	}
	
	public String getLoadAverage() {
		return loadAverage;
	}
	
	public void setLoadAverage(String loadAverage) {
		this.loadAverage = loadAverage;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}