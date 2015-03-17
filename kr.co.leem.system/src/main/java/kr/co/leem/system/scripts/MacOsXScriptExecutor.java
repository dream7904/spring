package kr.co.leem.system.scripts;

/**
 * MacOsXScriptExecutor.
 * 
 * @author 임 성천.
 *
 */
public class MacOsXScriptExecutor extends DefaultScriptExecutor {
	
	private static MacOsXScriptExecutor instance = new MacOsXScriptExecutor();
	
	public static ScriptExecutor getInstance() {
		return instance;
	}
	
	/**
	 * 총 메모리 사이즈 값을 반환함.
	 * 
	 * @return 총 메모리 사이즈 값.
	 */
	public float getMemoryCapacityTotal() {
		String[] commands = getProperty("macosx.getMemoryCapacityTotal").split(" ");
		
		return Float.parseFloat(processShellExactResult(commands).replaceAll("\\D", ""));
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
	 * 남아있는 메모리 사이즈 값을 반환함.
	 * 
	 * @return 남아있는 메모리 사이즈 값.
	 */
	public float getMemoryCapacityFree() {
		String[] commands = getProperty("macosx.getMemoryCapacityFree").split(" ");
		
		return Float.parseFloat(processShellExactResult(commands));
	}
}