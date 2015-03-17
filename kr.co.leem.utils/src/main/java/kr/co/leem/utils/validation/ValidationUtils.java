package kr.co.leem.utils.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kr.co.leem.utils.lang.StringUtils;

/**
 * Validation Utils.
 * 
 * @author 임성천.
 */
public class ValidationUtils {

	private ValidationUtils() {
		throw new AssertionError();
	}
	
	/**
	 * 주민등록번호 유효성 결과를 반환.
	 *
	 * @param regno 주민등록번호.
	 * @return 유효한 주민등록번호인 경우에만 true.
	 */
	public static boolean isResiRegNum(String regno) {
		String pattern = "^([0-9]{2}(?:0[1-9]|1[0-2])(?:0[1-9]|[12][0-9]|3[01]))-([1|2|3|4][0-9]{6})$";
		if (!isRegexPatternMatch(regno, pattern)) {
			return false;
		}
		String replaceno = regno.replace("-", "");
		
		int sum = 0;
		int last = replaceno.charAt(12) - 0x30;
		String bases = "234567892345";
		
		for (int index = 0; index < 12; index++) {
			if (StringUtils.isEmpty(replaceno.substring(index, index + 1))) {
				return false;
			}
			sum += (replaceno.charAt(index) - 0x30) * (bases.charAt(index) - 0x30);
		}
		
		int mod = sum % 11;
		
		return ((11 - mod) % 10 == last) ? true : false;
	}
	
	/**
	 * 법인등록번호 유효성 결과를 반환.
	 *
	 * @param corpNumber 법인등록번호.
	 * @return 유효한 법인등록번호인 경우에만 true.
	 */
	public static boolean isCorpCertNum(String corpNumber) {
		String pattern = "^((\\d{6})-(\\d{7}))$";
		
		if (!isRegexPatternMatch(corpNumber, pattern)) {
			return false;
		}
		String replaceno = corpNumber.replace("-", "");
		
		int checkSum = 0;
		
		for (int index = 0; index < 12; index++) {
			checkSum += (Character.getNumericValue(replaceno.charAt(index)) * ((index % 2 == 0) ? 1 : 2));
		}
		
		if ((10 - (checkSum % 10)) % 10 == Character.getNumericValue(replaceno.charAt(12)))
			return true;
		else
			return false;
	}
	
	/**
	 * 사업자등록번호 유효성 결과를 반환.
	 *
	 * @param corpNumber 사업자등록번호.
	 * @return 유효한 사업자등록번호인 경우에만 true.
	 */
	public static boolean isBizRegNum(String bizNumber) {
		String pattern = "^((\\d{3})-(\\d{2})-(\\d{5}))$";
		
		if (!isRegexPatternMatch(bizNumber, pattern)) {
			return false;
		}
		String replaceno = bizNumber.replace("-", "");
		
		int checkSum = 0;
		int checkDigit[] = { 1, 3, 7, 1, 3, 7, 1, 3, 5 };
		
		for (int i = 0; i < 9; i++) {
			checkSum += (Character.getNumericValue(replaceno.charAt(i)) * checkDigit[i]);
		}
		
		checkSum += (Character.getNumericValue(replaceno.charAt(8)) * 5) / 10;
		
		if ((10 - (checkSum % 10)) % 10 == Character.getNumericValue(replaceno.charAt(9)))
			return true;
		else
			return false;
	}
	
	/**
	 * 전화번호 유효성 검증 결과를 반환함.
	 *
	 * @param telNum 전화번호.
	 * @return 유효한 전화번호인 경우에만 true.
	 */
	public static boolean isTelNum(String telNum) {
		String pattern = "^\\d{2,4}-\\d{3,4}-\\d{4}$";
		if (!isRegexPatternMatch(telNum, pattern))
			return false;
		else
			return true;
	}
	
	/**
	 * 핸드폰번호 유효성 검증 결과를 반환함.
	 *
	 * @param cellPhoneNumber 핸드폰번호
	 * @return 유효한 핸드폰번호인 경우에만 true.
	 */
	public static boolean isCellphoneNumber(String cellPhoneNumber) {
		String pattern = "^(01(0|1|6|7|8|9))-\\d{3,4}-\\d{4}$";
		
		if (!isRegexPatternMatch(cellPhoneNumber, pattern)) 
			return false;
		else
			return true;
	}
	
	/**
	 * 이메일 주소 유효성 검증 결과를 반환함.
	 *
	 * @param email 이메일 주소
	 * @return 유효한 이메일 주소인 경우에만 true.
	 */
	public static boolean isEmailAddr(String email) {
		String pattern = "([\\w-\\.]+)@((?:[\\w]+\\.)+)([a-zA-Z]{2,4})$";
		
		if (!isRegexPatternMatch(email, pattern))
			return false;
		else
			return true;
	}
	
	/**
	 * 신용카드 번호 유효성 검증 결과를 반환함.
	 *
	 * @param cardNumber 신용카드 번호
	 * @return 유효한 신용카드 번호인 경우에만 true.
	 */
	public static boolean isCardNumber(String cardNumber) {
		String pattern = "^\\d{4}[\\s\\-]?\\d{4}[\\s\\-]?\\d{4}[\\s\\-]?\\d{4}$";
		if (!isRegexPatternMatch(cardNumber, pattern))
			return false;
		else
			return true;
	}
	
	/**
	 * 문자열의 길이가 최소값과 최대값 사이의 길이를 가지는지 여부를 반환함.
	 *
	 * @param str 문자열 값.
	 * @param min 최소 길이.
	 * @param max 최대 길이.
	 * @return 문자열의 길이가 최소값과 최대값 사이의 길이를 가지면 true.
	 */
	public static boolean isRangeLength(String str, int min, int max) {
		if (StringUtils.getLength(str) >= min && StringUtils.getLength(str) <= max)
			return true;
		else
			return false;
	}
	
	/**
	 * 문자열을 바이트 기준으로 계산했을 경우, 최소값과 최대값 사이의 길이를 가지는지 여부를 반환함. 
	 *
	 * @param str 문자열 값.
	 * @param min 최소 길이.
	 * @param max 최대 길이.
	 * @return 문자열을 바이트 기준으로 계산했을 경우, 문자열의 길이가 최소값과 최대값 사이의 길이를 가지면 true.
	 */
	public static boolean isRangeByteLength(String str, int min, int max) {
		if (StringUtils.getByteLength(str) >= min && StringUtils.getByteLength(str) <= max)
			return true;
		else
			return false;
	}
	
	/**
	 * 문자열의 meta문자를 escape처리하여 반환.
	 *
	 * @param orgPattern original string
	 * @return escaping 문자열의 meta문자를 escape처리한 문자열.
	 */
	private static String regexMetaCharEscape(String orgPattern) {
		return orgPattern.replaceAll("([\\[\\\\\\^\\$\\.\\|\\?\\*\\+\\(\\)])", "\\\\$1");
	}
	
	/**
	 * 문자열에 지정된 형식의 문자가 입력되었는지 여부를 판별하여 반환함.
	 *
	 * <pre>
	 * ValidationUtil.isUserFormat(&quot;123-456&quot;, &quot;###-###&quot;) = true;
	 * ValidationUtil.isUserFormat(&quot;123.456&quot;, &quot;###.###&quot;) = true;
	 * </pre>
	 *
	 * @param str 문자열 값.
	 * @param pattern 검사할 형식.
	 * @return 문자열에 지정된 형식의 문자가 입력되었으면 true.
	 */
	public static boolean isUserFormat(String str, String pattern) {
		String metaChange = regexMetaCharEscape(pattern);
		String regexChange = metaChange.replaceAll("#", "\\\\d").replaceAll("S", "[a-zA-Z]");
		return str.matches(regexChange);
	}
	
	/**
	 * 문자열이 정규식 패턴에 맞는지 여부를 판별하여 반환함.
	 *
	 * <pre>
	 * ValidationUtil.isRegexPatternMatch(&quot;aaaaab&quot;, &quot;a*b&quot;) = true;
	 * ValidationUtil.isRegexPatternMatch(&quot;cabbbb&quot;, &quot;a*b&quot;) = false;
	 * </pre>
	 *
	 * @param str 문자열 값.
	 * @param pattern 정규식 값.
	 * @return 문자열이 정규식 패턴에 맞는 경우 true.
	 */
	public static boolean isRegexPatternMatch(String str, String pattern) {
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(str);
		return m.matches();
	}
	
	/**
	 * 문자열이 정규식 패턴에 맞는지 여부를 판별하여 반환함.(*는 전체 문자)
	 *
	 * <pre>
	 * ValidationUtil.isPatternMatching("abc-def', "*-*") 	= true
	 * ValidationUtil.isPatternMatching("abc", "*-*") 	    = false
	 * </pre>
	 *
	 * @param str 문자열 값.
	 * @param pattern 정규식 값.
	 * @return 문자열이 정규식 패턴에 맞으면 true.
	 */
	public static boolean isPatternMatching(String str, String pattern) {
		if (pattern.indexOf('*') >= 0) {
			pattern = pattern.replaceAll("\\*", ".*");
		}
		
		pattern = "^" + pattern + "$";
		
		return Pattern.matches(pattern, str);
	}
	
	/**
	 * 문자열이 검증패턴에 의한 정규식 패턴에 맞는지 여부를 판별하여 반환함.(*는 전체 문자)
	 *  
	 * <pre>
	 * ValidationUtil.isPatternInclude(&quot;asdf@5456&quot;, &quot;s&quot;) = true;
	 * ValidationUtil.isPatternInclude(&quot;-&quot;, &quot;s&quot;) = true;
	 * ValidationUtil.isPatternInclude(&quot;한&quot;, &quot;k&quot;) = true;
	 * ValidationUtil.isPatternInclude(&quot;123가32&quot;, &quot;k&quot;) = true;
	 * ValidationUtil.isPatternInclude(&quot;asdfsdfsdf&quot;, &quot;e&quot;) = true;
	 * ValidationUtil.isPatternInclude(&quot;asdfs1dfsdf&quot;, &quot;e&quot;) = true;
	 * ValidationUtil.isPatternInclude(&quot;123123123&quot;, &quot;n&quot;) = true;
	 * ValidationUtil.isPatternInclude(&quot;asdfs1dfsdf&quot;, &quot;n&quot;) = true;
	 * </pre>
	 *
	 * @param str 문자열
	 * @param param 검증패턴(s : 특수문자, k : 한국어, e : 영어, n : 숫자)
	 * @return 문자열이 검증패턴에 의한 정규식 패턴에 맞으면 true.
	 */
	public static boolean isPatternInclude(String str, String param) {
		if (param.indexOf("s") >= 0) {
			return isRegexPatternMatch(str, ".*[~!@\\#$%<>^&*\\()\\-=+_\\'].*");
		}
		if (param.indexOf("k") >= 0) {
			return isRegexPatternMatch(str, ".*[ㄱ-ㅎ|ㅏ-ㅣ|가-힣].*");
		}
		if (param.indexOf("e") >= 0) {
			return isRegexPatternMatch(str, ".*[a-zA-Z].*");
		}
		if (param.indexOf("n") >= 0) {
			return isRegexPatternMatch(str, ".*\\d.*");
		}
		return true;
	}
	
	/**
	 * 문자열이 정규식 패턴을 포함하는지 여부를 판별.
	 *
	 * <pre>
	 * ValidationUtil.isRegexPatternInclude("cabbbb", "a*b"))  = true
	 * </pre>
	 *
	 * @param str 문자열 값.
	 * @param pattern 정규식 값.
	 * @return 문자열이 정규식 패턴을 포함하면 true.
	 */
	public static boolean isRegexPatternInclude(String str, String pattern) {
		return isRegexPatternMatch(str, ".*" + pattern + ".*");
	}
}