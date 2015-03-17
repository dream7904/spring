package kr.co.leem.system.scripts;

/**
 * LinuxScriptExecutor.
 * 
 * @author 임 성천.
 */
public class LinuxScriptExecutor extends DefaultScriptExecutor {
	
	private static LinuxScriptExecutor instance = new LinuxScriptExecutor();
	
	public static ScriptExecutor getInstance() {
		return instance;
	}
	
	/**
	 * 총 메모리 사이즈 값을 반환함.
	 * 
	 * @return 총 메모리 사이즈 값.
	 */
	public float getMemoryCapacityTotal() {
		String[] commands = getProperty("linux.getMemoryCapacity").split(" ");
		
		String memPattern = "^Mem:\\s+?(\\d+?)\\s+?.*$";
		return Float.parseFloat(processShellExpMatcher(commands, memPattern));
	}
	
	/**
	 * 사용중인 메모리 사이즈 값을 반환함.
	 * 
	 * @return 사용중인 메모리 사이즈 값.
	 */
	public float getMemoryCapacityUsed() {
		String[] commands = getProperty("linux.getMemoryCapacity").split(" ");
		
		String memPattern = "^Mem:\\s+?(?:\\d+?)\\s+?(\\d+?)\\s+?.*$";
		return Float.parseFloat(processShellExpMatcher(commands, memPattern));
	}
	
	/**
	 * 남아 있는 메모리 사이즈 값을 반환함.
	 * 
	 * @return 남아 있는 메모리 사이즈 값.
	 */
	public float getMemoryCapacityFree() {
		String[] commandArr = getProperty("linux.getMemoryCapacity").split(" ");
		
		String memPattern = "^Mem:\\s+?(?:\\d+?)\\s+?(?:\\d+?)\\s+?(\\d+?)\\s+?.*$";
		return Float.parseFloat(processShellExpMatcher(commandArr, memPattern));
	}
}