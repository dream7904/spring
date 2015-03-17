package kr.co.leem.system;

import java.io.IOException;

import org.hyperic.sigar.SigarException;

/**
 * BaseSystemUtils.
 * 
 * @author 임 성천.
 *
 */
public class BaseSystemUtils {
	
	/**
	 * IO 핸들링 템플릿 메소드.
	 * 
	 * @param <T>
	 * @param io IOCallback<T>
	 * @return <T>
	 */
	public static <T> T processIO(IOCallback<T> io) {
		try {
			return io.doInProcessIO();
		} catch (IOException e) {
			throw new RuntimeException("processIO IOException occured : " + e.getMessage(), e);
		} catch (SigarException se) {
			throw new RuntimeException("SigarException occured : " + se.getMessage(), se);
		} catch (Exception e) {
			throw new RuntimeException("processIO Exception occured : " + e.getMessage(), e);
		}
	}
	
	/**
	 * IO callback interface
	 * @author 임 성천.
	 *
	 * @param <T>
	 */
	public interface IOCallback<T> {
		public T doInProcessIO() throws IOException, SigarException, Exception;
	}
}