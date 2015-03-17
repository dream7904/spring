package kr.co.leem.system;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import kr.co.leem.system.domains.NetworkInfo;
import kr.co.leem.system.domains.SigarAccessor;

import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.shell.ShellCommandExecException;
import org.hyperic.sigar.shell.ShellCommandUsageException;

/**
 * NetworkStateUtils.
 * 
 * @author 임 성천.
 */
public class NetworkStateUtils extends BaseSystemUtils {
	
	/**
	 * 네트워크 상태 체크.(ping)
	 * 
	 * @param ipAddr IP주소.
	 * @param timeout 타임아웃(milliseconds)
	 * @return ping테스트가 정상적일 경우 true.
	 * @see InetAddress#getByName(String)
	 * @see InetAddress#isReachable(int)
	 */
	public static boolean ping(final String ipAddr, final int timeout) {
		return processIO(new IOCallback<Boolean>() {
			public Boolean doInProcessIO() throws IOException {
				return InetAddress.getByName(ipAddr).isReachable(timeout);
			}
		});
	}
	
	/**
	 * 네트워크 정보 목록을 반환함.
	 * 
	 * @return 네트워크 정보.
	 */
	public static Map<String, NetworkInfo> getNetworkInfoList() {
		return processIO(new IOCallback<Map<String, NetworkInfo>>() {
			public Map<String, NetworkInfo> doInProcessIO() throws IOException, SigarException {
				return SigarAccessor.getSysInfo().getNetworkInfoMap();
			}
		});
	}
	
	/**
	 * 네트워크 정보를 반환함.
	 * 
	 * @return 네트워크 정보.
	 */
	public static NetworkInfo getNetworkInfo() {
		return processIO(new IOCallback<NetworkInfo>() {
			public NetworkInfo doInProcessIO() throws IOException, SigarException {
				return SigarAccessor.getSysInfo().getNetworkInfo();
			}
		});
	}
	
	/**
	 * ip주소 목록을 반환함.
	 * 
	 * @return ip주소 목록.
	 */
	public static List<String> getIPList() {
		return processIO(new IOCallback<List<String>>() {
			public List<String> doInProcessIO() throws IOException, SigarException {
				Map<String, NetworkInfo> networkInfoMap = SigarAccessor.getSysInfo().getNetworkInfoMap();
				List<String> myIPList = new ArrayList<String>();
				
				Set<String> keySet = networkInfoMap.keySet();
				Iterator<String> iterator = keySet.iterator();
				
				while (iterator.hasNext()) {
					String key = iterator.next();
					NetworkInfo networkInfo = networkInfoMap.get(key);
					
					myIPList.add(networkInfo.getAddress());
				}
				return myIPList;
			}
		});
	}
	
	/**
	 * 맥어드레스 목록을 반환함.
	 * 
	 * @return 맥어드레스 목록.
	 */
	public static List<String> getMacAddressList() {
		return processIO(new IOCallback<List<String>>() {
			public List<String> doInProcessIO() throws IOException, SigarException {
				Map<String, NetworkInfo> networkInfoMap = SigarAccessor.getSysInfo().getNetworkInfoMap();
				List<String> myIPList = new ArrayList<String>();
				
				Set<String> keySet = networkInfoMap.keySet();
				Iterator<String> iterator = keySet.iterator();
				
				while (iterator.hasNext()) {
					String key = iterator.next();
					NetworkInfo networkInfo = networkInfoMap.get(key);
					
					myIPList.add(networkInfo.getHwaddr());
				}
				return myIPList;
			}
		});
	}
	
	/**
	 * netstat 정보 목록을 반환함.
	 * 
	 * @return netstat 정보 목록.
	 */
	public static List<String> getNetstat() {
		return getNetstat(new String[] { "a", "p" });
	}
	
	/**
	 * netstat 정보 목록을 반환함.(netstat)
	 * 
	 * @param args netstat 옵션. ("l", "a", "n", "p", "s", "t", "u", "w", "x")
	 * @return netstat 정보 목록.
	 */
	public static List<String> getNetstat(final String[] args) {
		return processIO(new IOCallback<List<String>>() {
			public List<String> doInProcessIO() throws IOException, SigarException, Exception {
				return SigarAccessor.getNetStat(args);
			}
		});
	}
	
	/**
	 * route 정보 목록을 반환함.
	 * 
	 * @return route 정보 목록.
	 */
	public static List<String> getRoute() {
		return processIO(new IOCallback<List<String>>() {
			public List<String> doInProcessIO() throws IOException, SigarException, ShellCommandUsageException,
					ShellCommandExecException {
				return SigarAccessor.getNetworkRouteInfo();
			}
		});
	}
}