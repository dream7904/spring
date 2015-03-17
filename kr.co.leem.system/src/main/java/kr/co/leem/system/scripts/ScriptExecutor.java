package kr.co.leem.system.scripts;

import java.util.List;

import kr.co.leem.system.SystemInfoUtil.MemRegion;

/**
 * ScriptExecutor.
 * 
 * @author 임 성천.
 *
 */
public interface ScriptExecutor {
	
	/**
	 * 디렉토리 정보를 반환함.
	 * 
	 * @param dirPath 디렉토리 경로.
	 * @return 디렉토리 정보.
	 */
	public List<String> getDirInfo(String dirPath);
	
	/**
	 * 디렉토리 사이즈를 반환함.
	 * 
	 * @param dirPath 디렉토리 경로.
	 * @return 디렉토리 사이즈.
	 */
	public String getDirSize(String dirPath);
	
	/**
	 * 맥어드레스를 반환함.
	 * 
	 * @return 맥어드레스.
	 */
	public String getMacAddr();
	
	/**
	 * netstat 정보 목록을 반환함.
	 * 
	 * @return netstat 정보 목록.
	 */
	public List<String> getNetInfo();
	
	/**
	 * 메모리 정보를 반환함.
	 * 
	 * @param memRegion TOTAL, USED, FREE
	 * @return 메모리 정보.
	 */
	public float getMemoryCapacity(MemRegion memRegion);
}