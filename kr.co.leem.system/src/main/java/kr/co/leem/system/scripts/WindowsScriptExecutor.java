package kr.co.leem.system.scripts;

import java.util.List;

/**
 * WindowsScriptExecutor
 * 
 * @author 임 성천.
 *
 */
public class WindowsScriptExecutor extends DefaultScriptExecutor {

	private static WindowsScriptExecutor instance = new WindowsScriptExecutor();

	public static ScriptExecutor getInstance() {
		return instance;
	}

	/**
	 * 디렉토리 정보를 반환함.
	 * 
	 * @param dirPath 디렉토리 경로.
	 * @return 디렉토리 정보.
	 */
	public List<String> getDirInfo(String targetDir) {
		String[] commandArr = getProperty("windows.getDirInformation").replaceAll("\\$1", targetDir).split(" ");

		return getDirectResult(commandArr);
	}

	/**
	 * 디렉토리 사이즈를 반환함.
	 * 
	 * @param dirPath 디렉토리 경로.
	 * @return 디렉토리 사이즈.
	 */
	public String getDirSize(String targetDir) {

		String[] commandArr = getProperty("windows.getDirSizeStr").replaceAll("\\$1", targetDir).split(" ");

		List<String> result = getDirectResult(commandArr);
		if (!result.isEmpty()) {
			String totalSizeStr = result.get(result.size() - 2);
			return totalSizeStr.split("\\s+")[3];
		}
		return "unknown";
	}
	
	/**
	 * 맥어드레스를 반환함.
	 * 
	 * @return mac address of the current system.
	 */
	public String getMacAddr() {
		String[] commandArr = getProperty("windows.getMacAddress").split(" ");

		String macAddressPattern = ".*?(\\p{XDigit}{2}-\\p{XDigit}{2}-\\p{XDigit}{2}-\\p{XDigit}{2}-\\p{XDigit}{2}-\\p{XDigit}{2})";
		return processShellExpMatcher(commandArr, macAddressPattern);
	}

	/**
	 * netstat 정보 목록을 반환함.
	 * 
	 * @return netstat 정보 목록.
	 */
	public List<String> getNetInfo() {
		String[] commandArr = getProperty("windows.getPortScan").split(" ");

		return getDirectResult(commandArr);
	}

	/**
	 * 총 메모리 사이즈 값을 반환함.
	 * 
	 * @return 총 메모리 사이즈 값.
	 */
	public float getMemoryCapacityTotal() {
		String[] commandArr = getProperty("windows.getMemoryCapacity").split(" ");

		List<String> result = getDirectResult(commandArr);
		for (String tmp : result) {
			if (tmp.indexOf("총 실제 메모리") >= 0) {
				if (tmp.indexOf("MB") > 0) {
					return Float.parseFloat(tmp.replaceAll("\\D", "")) * 1024 * 1024;
				}
				else if (tmp.indexOf("GB") > 0) {
					return Float.parseFloat(tmp.replaceAll("\\D", "")) * 1024 * 1024 * 1024;
				}
			}
		}

		return -1;
	}

	/**
	 * 사용중인 메모리 사이즈 값을 반환함.
	 * 
	 * @return 사용중인 메모리 사이즈 값.
	 */
	public float getMemoryCapacityUsed() {
		return getMemoryCapacityTotal() - getMemoryCapacityFree();
	}

	/**
	 * 남아 있는 메모리 사이즈 값을 반환함.
	 * 
	 * @return 남아 있는 메모리 사이즈 값.
	 */
	public float getMemoryCapacityFree() {
		String[] commandArr = getProperty("windows.getMemoryCapacity").split(" ");

		List<String> result = getDirectResult(commandArr);
		for (String tmp : result) {
			if (tmp.indexOf("사용 가능한 실제 메모리") >= 0) {
				if (tmp.indexOf("MB") > 0) {
					return Float.parseFloat(tmp.replaceAll("\\D", "")) * 1024 * 1024;
				}
				else if (tmp.indexOf("GB") > 0) {
					return Float.parseFloat(tmp.replaceAll("\\D", "")) * 1024 * 1024 * 1024;
				}
			}
		}

		return -1;
	}

}
