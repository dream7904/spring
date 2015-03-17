package kr.co.leem.system.scripts;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kr.co.leem.system.ScriptPropertiesLoader;
import kr.co.leem.system.SystemInfoUtil.MemRegion;
import kr.co.leem.system.BaseSystemUtils;
import kr.co.leem.utils.lang.StringUtils;

/**
 * DefaultScriptExecutor.
 *
 * @author 임 성천.
 *
 */
public class DefaultScriptExecutor extends BaseSystemUtils implements ScriptExecutor {
	
	private static final DefaultScriptExecutor instance = new DefaultScriptExecutor();
	
	/**
	 * 인스턴스를 반환함.
	 * 
	 * @return ScriptExecutor
	 */
	public static ScriptExecutor getInstance() {
		return instance;
	}
	
	/**
	 * 운영체제 타입.
	 */
	private static OsType os;
	
	/**
	 * 스크립트.
	 */
	private static Properties scripts = new Properties();
	
	private static boolean isUserSpecified;
	
	static {
		ScriptPropertiesLoader loader = new ScriptPropertiesLoader();
		loader.load();
		if (loader.isLoaded()) {
			scripts = loader.getProperties();
		}
		
		isUserSpecified = scripts.getProperty("userSpecifiedOS") != null;
		
		String osName = System.getProperty("os.name");
		
		osName = osName.replaceAll("\\W", "");
		
		if (osName == null) {
			os = OsType.Other;
		} else if (isUserSpecified) {
			os = OsType.UserSpecified;
		} else {
			for (OsType osType : OsType.values()) {
				if (osName.equalsIgnoreCase(osType.toString())) {
					os = osType;
					break;
				}
			}
			if (os == null) {
				if(osName.toLowerCase().contains("windows")) {
					os = OsType.Windows;
				} else {
					os = OsType.Other;
				}
			}
		}
	}
	
	/**
	 * 쉘 스크립트 템플릿.
	 * 
	 * @param commands 명령어 배열 값.
	 * @param shellScriptCallBack callback interface.
	 * @return
	 */
	public final static <T> T processShell(String[] commands, ShellScriptCallback<T> shellScriptCallBack) {
		InputStream inputStream = null;
		BufferedReader br = null;
		PrintWriter out = null;
		Process p = null;
		
		try {
			p = Runtime.getRuntime().exec(os.is(OsType.Windows) ? commands : new String[] { "/bin/sh" });
			inputStream = new DataInputStream(p.getInputStream());
			if (os.is(OsType.Windows)) {
				br = new BufferedReader(new InputStreamReader(inputStream, "EUC-KR"));
			} else {
				br = new BufferedReader(new InputStreamReader(inputStream));
			}
			
			if (!os.is(OsType.Windows)) {
				out = new PrintWriter(p.getOutputStream());
				out.println(StringUtils.concatStrArray(commands));
				out.close();
			}
			
			return shellScriptCallBack.executeProcessShell(br);
		} catch (IOException e) {
			throw new RuntimeException("processShell IOException occured : " + e.getMessage(), e);
		} catch (RuntimeException re) {
			throw re;
		} catch (Exception e) {
			throw new RuntimeException("processShell Exception occured : " + e.getMessage(), e);
		} finally {
			if (inputStream != null)
				try {
					inputStream.close();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			if (br != null)
				try {
					br.close();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			if (out != null) {
				out.close();
			}
			if (p != null) {
				p.destroy();
			}
		}
	}
	
	
	/**
	 * ShellScriptCallback interface.
	 * 
	 * @author 임 성천.
	 *
	 * @param <T>
	 */
	public interface ShellScriptCallback<T> {
		/**
		 * @param reader 리더.
		 * @return 결과.
		 * @throws Exception
		 */
		public T executeProcessShell(BufferedReader reader) throws Exception;
	}
	
	/**
	 * 실행된 명령에 대한 결과값을 반환함.
	 * 
	 * 
	 * @param commands 명령어 목록.
	 * @return 실행된 명령에 대한 결과값.
	 */
	protected static List<String> getDirectResult(String[] commands) {
		final List<String> result = new ArrayList<String>();
		
		return processShell(commands, new ShellScriptCallback<List<String>>() {
			public List<String> executeProcessShell(BufferedReader br) throws Exception {
				String presentLine;
				while ((presentLine = br.readLine()) != null) {
					result.add(presentLine);
				}
				return result;
			}
		});
	}
	
	/**
	 * OS 타입을 반환함.
	 * @return type of os
	 */
	public static OsType getOsType() {
		return os;
	}
	
	/**
	 * OS 타입을 설정함.
	 * @param os
	 */
	public static void setOsType(OsType os) {
		DefaultScriptExecutor.os = os;
	}
	
	/**
	 * 스크립트 값을 설정함.
	 * 
	 * @param scripts 스크립트 값.
	 */
	public static void setScripts(Properties scripts) {
		DefaultScriptExecutor.scripts = scripts;
	}
	
	/**
	 * 스크립트 값을 반환함.
	 * 
	 * @return 스크립트 값.
	 */
	public static Properties getScripts() {
		return DefaultScriptExecutor.scripts;
	}
	
	/**
	 * 키 값에 대한 프로퍼티 값을 반환함.(scripts.properties)
	 * 
	 * @param key 프로퍼티의 키값.
	 * @return  키 값에 대한 프로퍼티 값.
	 */
	public static String getProperty(String key) {
		if (scripts.containsKey(key)) {
			return scripts.getProperty(key);
		}
		else {
			throw new IllegalStateException("[" + key + "] 키값이 존재하지 않습니다.");
		}
	}
	
	/**
	 * 스크립트와 정규식을 실행한 결과를 반환함.
	 *
	 * @param commands 명령어 배열 값.
	 * @param patternStr 정규식.
	 * @return 실행결과.
	 */
	protected String processShellExpMatcher(String[] commands, final String patternStr) {
		return processShell(commands, new ShellScriptCallback<String>() {
			public String executeProcessShell(BufferedReader br) throws Exception {
				String presentLine;
				Pattern pattern = Pattern.compile(patternStr);
				
				Matcher matcher = null;
				while ((presentLine = br.readLine()) != null) {
					matcher = pattern.matcher(presentLine);
					if (matcher.matches()) {
						return matcher.group(1);
					}
				}
				return "unknown";
			}
		});
	}
	
	/**
	 * 스크립트 실행에 대한 결과 값을 반환함.
	 *
	 * @param commands 명령어 목록.
	 * @return 스크립트 실행에 대한 결과 값.
	 */
	protected String processShellExactResult(String[] commands) {
		return processShell(commands, new ShellScriptCallback<String>() {
			public String executeProcessShell(BufferedReader br) throws Exception {
				String presentLine;
				while ((presentLine = br.readLine()) != null) {
					if (presentLine != null) {
						return presentLine;
					}
				}
				return "unknown";
			}
		});
	}
	
	/**
	 * 디렉토리 정보를 반환함.
	 * 
	 * @param dirPath 디렉토리 경로 값.
	 * @return 디렉토리 정보.
	 */
	public List<String> getDirInfo(String dirPath) {
		String[] commands = getProperty("unix.getDirInformation").replaceAll("\\$1", dirPath).split(" ");
		
		return getDirectResult(commands);
	}
	
	/**
	 * 디렉토리 용량을 반환함.
	 * 
	 * @param dirPath 디렉토리 경로 값.
	 * @return 디렉토리 용량을 반환함.
	 */
	public String getDirSize(String dirPath) {
		
		String[] commands = getProperty("unix.getDirSizeStr").replaceAll("\\$1", dirPath).split(" ");
		
		List<String> result = getDirectResult(commands);
		if (!result.isEmpty()) {
			String totalSizeStr = result.get(result.size() - 1);
			return totalSizeStr.split("\\s+")[0];
		}
		return "unknown";
	}
	
	/**
	 * 맥어드레스 값을 반환함.
	 * 
	 * @return 맥어드레스 값.
	 */
	public String getMacAddr() {
		String[] commands = getProperty("unix.getMacAddress").split(" ");
		
		String macAddressPattern = ".*?(\\p{XDigit}{2}:\\p{XDigit}{2}:\\p{XDigit}{2}:\\p{XDigit}{2}:\\p{XDigit}{2}:\\p{XDigit}{2}).*";
		return processShellExpMatcher(commands, macAddressPattern);
	}
	
	/**
	 * 포트 스캔 결과를 반환함.
	 * 
	 * @return 포트 스캔 결과.
	 */
	public List<String> getNetInfo() {
		String[] commands = getProperty("unix.getPortScan").split(" ");
		
		return getDirectResult(commands);
	}
	
	/**
	 * 메모리 용량 값을 반환함.
	 * 
	 * @param memRegion TOTAL/USED/FREE.
	 * @return 메모리 용량 값.
	 */
	public float getMemoryCapacity(MemRegion memRegion) {
		switch (memRegion) {
		case TOTAL:
			return getMemoryCapacityTotal();
		case USED:
			return getMemoryCapacityUsed();
		case FREE:
			return getMemoryCapacityFree();
		}
		return 0;
	}
	
	/**
	 * 메모리 총 용량 값을 반환함.
	 * @return 메모리 총 용량 값.
	 */
	public float getMemoryCapacityTotal() {
		String[] commands = getProperty("unix.getMemoryCapacityTotal").split(" ");
		
		return Float.parseFloat(processShellExactResult(commands));
	}
	
	/**
	 * 사용된 메모리 용량 값을 반환함.
	 * 
	 * @return 사용된 메모리 용량 값.
	 */
	public float getMemoryCapacityUsed() {
		String[] commands = getProperty("unix.getMemoryCapacityUsed").split(" ");
		
		return Float.parseFloat(processShellExactResult(commands));
	}
	
	/**
	 * 사용되지 않은 메모리 용량 값을 반환함.
	 * @return 사용되지 않은 메모리 용량 값.
	 */
	public float getMemoryCapacityFree() {
		String[] commands = getProperty("unix.getMemoryCapacityFree").split(" ");
		
		return Float.parseFloat(processShellExactResult(commands));
	}
}