package kr.co.leem.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.xerces.impl.dv.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Digest Utils. 
 * 
 * @author 임 성천.
 */
public class DigestUtils {
	private DigestUtils() {
		throw new AssertionError();
	}
	
	/** The <code>Log</code> 로그설정. */
	private static Logger logger = LoggerFactory.getLogger(DigestUtils.class);
	
	/**
	 * 문자열을 지정된  캐릭터셋으로 인코딩하여 반환함.
	 * 
	 * @param str 문자열값.
	 * @param charsetName 캐릭터셋.
	 * @return 인코딩된 문자열값.
	 */
	public static String encodeCharset(String str, String charsetName) {
		String result = "";

		try {
			result = new String(str.getBytes(charsetName));
		} catch (UnsupportedEncodingException e) {
			logger.error("Exception: {}", new Object[] { e });
			throw new RuntimeException("UnsupportedEncodingException : " + e.getMessage(), e);
		}

		return result;
	}
	
	/**
 	 * 문자열을 지정된  캐릭터셋으로 디코딩하여 반환함.
 	 * 
	 * @param str 문자열값.
	 * @param charsetName 캐릭터셋.
	 * @return 디코딩된 문자열값.
	 */
	public static String decodeCharset(String str, String charsetName) {
		return encodeCharset(str, charsetName);
	}
	
	/**
	 * 문자열을 Base64으로 인코딩하여 반환함.
	 * @param str : 문자열값.
	 * @return Base64로 인코딩된 문자열.
	 */
	public static String encodeBase64(String str) {
		return Base64.encode(str.getBytes());
	}
	
	/**
	 * 문자열을 Base64 디코딩하여 반환함.
	 * 
	 * @param str : 문자열값.
	 * @return 디코딩된 문자열값.
	 */
	public static String decodeBase64(String str) {
		return new String(Base64.decode(str));
	}
	
	/**
	 * 지정된 알고리즘으로 문자열을 인코딩하여 반환함.
	 * 
	 * @param password : 패스워드 문자열.
	 * @param algorithm : 인코딩할 알고리즘.
	 * @return 지정된 알고리즘으로 인코딩된 패스워드 문자열.
	 */
	public static String encodePassword(String password, String algorithm) {
		byte[] unencodedPassword = password.getBytes();
		
		MessageDigest md = null;
		
		try {
			md = MessageDigest.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e) {
			logger.error("NoSuchAlgorithmException: {}", new Object[] { e });
			throw new RuntimeException("NoSuchAlgorithmException : " + e.getMessage(), e);
		}
		
		md.reset();
		
		md.update(unencodedPassword);
		
		byte[] encodedPassword = md.digest();
		
		StringBuilder buf = new StringBuilder();
		
		for (int i = 0; i < encodedPassword.length; i++) {
			if (((int) encodedPassword[i] & 0xff) < 0x10) {
				buf.append("0");
			}
			
			buf.append(Long.toString((int) encodedPassword[i] & 0xff, 16));
		}
		
		return buf.toString();
	}
}