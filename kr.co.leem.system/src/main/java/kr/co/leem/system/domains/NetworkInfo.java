package kr.co.leem.system.domains;

import java.io.Serializable;

/**
 * NetworkInfo.
 * 
 * @author 임 성천.
 */
public class NetworkInfo implements Serializable {
	
	private static final long serialVersionUID = 234346123L;

	private String address;

	private String hwaddr;

	private String netmask;

	private String hostName;

	private String domainName;

	private String defaultGateway;

	private String primaryDns;

	private String secondaryDns;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getHwaddr() {
		return hwaddr;
	}

	public void setHwaddr(String hwaddr) {
		this.hwaddr = hwaddr;
	}

	public String getNetmask() {
		return netmask;
	}

	public void setNetmask(String netmask) {
		this.netmask = netmask;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public String getDefaultGateway() {
		return defaultGateway;
	}

	public void setDefaultGateway(String defaultGateway) {
		this.defaultGateway = defaultGateway;
	}

	public String getPrimaryDns() {
		return primaryDns;
	}

	public void setPrimaryDns(String primaryDns) {
		this.primaryDns = primaryDns;
	}

	public String getSecondaryDns() {
		return secondaryDns;
	}

	public void setSecondaryDns(String secondaryDns) {
		this.secondaryDns = secondaryDns;
	}
}
