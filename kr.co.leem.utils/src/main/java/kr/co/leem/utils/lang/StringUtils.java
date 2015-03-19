package kr.co.leem.utils.lang;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.web.util.HtmlUtils;

import kr.co.leem.utils.DigestUtils;

/**
 * String Utils.
 * 
 * @author 임 성천.
 */
public class StringUtils extends org.apache.commons.lang.StringUtils {
	
	private StringUtils() {
		throw new AssertionError();
	}
	
	private static final char[] alphabet = new char[] { 'A', 'B', 'C', 'D',
			'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
			'R', 'S', 'T', 'U', 'X', 'Y', 'V', 'W', 'Z', 'a', 'b', 'c', 'd',
			'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
			'r', 's', 't', 'u', 'x', 'y', 'v', 'w', 'z' };

	public static final String DEFAULT_EMPTY_STRING = "";
	
	private static final Random generator = new Random(
			System.currentTimeMillis());
	
	/** UTF-8 캐릭터셋, 1 바이트 코드 */
	private static final int ONE_BYTE = 0x00007F;
	
	/** UTF-8 캐릭터셋, 3바이트 코드 */
	private static final int THREE_BYTE = 0x00FFFF;
	
	/** UTF-8 캐릭터셋, 2바이트 코드 */
	private static final int TWO_BYTE = 0x0007FF;
	
	/**
	 * 설정한 길이만큼 공백 문자열을 붙임.(설정된 길이가 0보다 작을 경우 무시함.)
	 * 
	 * @param str 문자열 값.
	 * @param length 공백문자열의 수.
	 * @return 공백 문자열이 추가된 문자열.
	 */
	public static String addSpace(String str, int length) {
		StringBuffer stringBuffer = new StringBuffer();
		if (str == null) {
			if (length <= 0) {
				return null;
			}
		} else {
			stringBuffer.append(str);
		}
		
		for (int j = 0; j < length; j++) {
			stringBuffer.append(' ');
		}
		
		return stringBuffer.toString();
	}
	
	/**
	 * 기존 배열에 문자열 배열을 추가한 배열을 반환함.
	 * 
	 * @param array 배열값.
	 * @param str 추가할 문자열.
	 * @return 문자열 배열이 추가된 배열.
	 */
	public static String[] addStringToArray(String array[], String str) {
		String newArray[] = new String[array.length + 1];

		System.arraycopy(array, 0, newArray, 0, array.length);
		newArray[array.length] = str;
		
		return newArray;
	}
	
	/**
	 * 입력 문자열에 두음법칙 적용.<br>
	 * 
	 * <div class="ko"> 입력된 문자열에 두음법칙을 적용하여 반환한다.<br>
	 * 1단계는 1번째 한글 문자 치환<br>
	 * 예) 라로량리림랑류뢰란 -> 나노양이임낭유뇌난<br>
	 * 2번째 한글 문자부터 치환<br>
	 * 례륭란률래로량락라님림련년니리륜랑룰린람녕령롱룡료립록류렬릉녀려뇨뉴렴념닉력루르론뢰 ->
	 * 예융난율내노양낙나임임연연이이윤낭울인남영영농용요입녹유열능여여요뉴염염익역누느논뇌<br>
	 * 
	 * <pre>
	 * applyInitialLaw("림업례제") => "임업예제"
	 * </pre>
	 * 
	 * </div>
	 * 
	 * @param str 적용할 문자열 값.
	 * @return 두음법칙이 적용된 문자열.
	 */
	public static String applyInitialLaw(String str) {
		String[] fstPtnP = { "라", "로", "량", "리", "림", "랑", "류", "뢰", "란" };
		String[] fstPtnN = { "나", "노", "양", "이", "임", "낭", "유", "뇌", "난" };
		
		String[] sndPtnP = { "례", "륭", "란", "률", "래", "로", "량", "락", "라", "님",
				"림", "련", "년", "니", "리", "륜", "랑", "룰", "린", "람", "녕", "령",
				"롱", "룡", "료", "립", "록", "류", "렬", "릉", "녀", "려", "뇨", "뉴",
				"렴", "념", "닉", "력", "루", "르", "론", "뢰" };
		String[] sndPtnN = { "예", "융", "난", "율", "내", "노", "양", "낙", "나", "임",
				"임", "연", "연", "이", "이", "윤", "낭", "울", "인", "남", "영", "영",
				"농", "용", "요", "입", "녹", "유", "열", "능", "여", "여", "요", "뉴",
				"염", "염", "익", "역", "누", "느", "논", "뇌" };
		
		String outStr = "";
		String inStrAry[] = null;
		
		str = str.trim();
		
		int inStrSize = str.length();
		char[] chStrAry = str.toCharArray();
		
		if (inStrSize > 0) {
			inStrAry = new String[inStrSize];
			
			for (int i = 0; i < inStrSize; i++) {
				inStrAry[i] = String.valueOf(chStrAry[i]);
			}
			
			for (int i = 0; i < fstPtnP.length; i++) {
				if (inStrAry[0].compareTo(fstPtnP[i]) == 0) {
					inStrAry[0] = fstPtnN[i];
				}
				outStr = inStrAry[0];
			}
		}
		
		StringBuffer sb = new StringBuffer();
		
		if (inStrSize > 1) {
			inStrAry = new String[inStrSize];
			
			for (int i = 1; i < inStrSize; i++) {
				boolean isExsit = false;
				inStrAry[i] = String.valueOf(chStrAry[i]);
				
				for (int j = 0; j < sndPtnP.length; j++) {
					if (inStrAry[i].compareTo(sndPtnP[j]) == 0
							&& isExsit == false) {
						sb.append(sndPtnN[j]);
						isExsit = true;
					}
				}
				if (isExsit == false) {
					sb.append(inStrAry[i]);
				}
			}
			outStr += sb.toString();
		}
		
		return outStr;
	}
	
	/**
	 * Object배열을 입력받아 delimiter로 각 값을 연결한 문자열을 반환함.(delimiter : ',')
	 * 
	 * @param array Object배열 값.
	 * @return ,로 연결된 문자열 값.
	 */
	public static String arrayToCommaDelimitedString(Object array[]) {
		return arrayToDelimitedString(array, ",");
	}
	
	/**
	 * Object배열을 입력받아 delimiter로 각 값을 연결한 문자열을 반환함.
	 * 
	 * @param array Object배열값.
	 * @param delimiter 구분문자 값.
	 * @return 구분문자로 연결된 문자열 값.
	 */
	public static String arrayToDelimitedString(Object array[], String delimiter) {
		if (array == null) {
			return null;
		}
		
		StringBuffer sb = new StringBuffer();
		
		for (int i = 0; i < array.length; i++) {
			if (i > 0 && delimiter != null) {
				sb.append(delimiter);
			}
			sb.append(array[i]);
		}
		
		return sb.toString();
	}
	
	/**
	 * 문자열에 '*' 또는 '**'가 있는 경우 공백문자로 치환한 값을 반환함.
	 * 
	 * @param str 문자열 값
	 * @return 치환된 문자값.
	 */
	public static String asteriskToSpace(String str) {
		String target = "";
		
		target = str.replaceAll("\\*\\*", " ");
		target = target.replaceAll("\\*", " ");
		
		return target;
	}
	
	/**
	 * 첫번째 글자를 대문자 또는 소문자로 변환하여 반환함.
	 * 
	 * @param capitalize true : 대문자 : true, 소문자 : false.
	 * @param str : 문자열 값.
	 * @return 변환된 문자열.
	 */
	private static String changeFirstCharacterCase(String str,
			boolean capitalize) {
		int strLen;
		
		if (str == null || (strLen = str.length()) == 0) {
			return str;
		}
		
		StringBuffer buf = new StringBuffer(strLen);
		
		if (capitalize) {
			buf.append(Character.toUpperCase(str.charAt(0)));
		} else {
			buf.append(Character.toLowerCase(str.charAt(0)));
		}
		
		buf.append(str.substring(1));
		
		return buf.toString();
	}
	
	/**
	 * Collection 값을 ","(delimiter)로 연결한 문자열을 반환.(null인 경우 null을 반환함.)
	 * 
	 * @param collection 컬렉션값.
	 * @return Collection 값을 ","(delimiter)로 연결한 문자열.
	 */
	public static String collectionToCommaDelimitedString(Collection<String> collection) {
		return collectionToDelimitedString(collection, ",");
	}
	
	/**
	 * Collection 값을 delimiter로 연결한 문자열을 반환.(null인 경우 null을 반환함.)
	 * 
	 * @param collection 컬렉션값.
	 * @param delimiter 구분문자값.
	 * @return 구분자로 연결된 문자열 값.
	 */
	public static String collectionToDelimitedString(Collection<String> collection, String delimiter) {
		if (collection == null) {
			return null;
		}
		
		StringBuffer sb = new StringBuffer();
		Iterator<String> it = collection.iterator();
		
		int i = 0;
		
		for (; it.hasNext(); sb.append(it.next())) {
			if (i++ > 0 && delimiter != null) {
				sb.append(delimiter);
			}
		}
		
		return sb.toString();
	}
	
	/**
	 * 문자열 값을 ","(delimiter)로 tokenize한 후 Set에 저장하여 반환함. Converts a single
	 * String with comma delimiter to Set of String.
	 * 
	 * @param str 문자열 값.
	 * @return Set.
	 */
	public static Set<String> commaDelimitedStringToSet(String str) {
		Set<String> set = new HashSet<String>();
		String tokens[] = commaDelimitedStringToStringArray(str);
		
		if (tokens == null) {
			return null;
		}
		
		for (int i = 0; i < tokens.length; i++) {
			set.add(tokens[i]);
		}
		
		return set;
	}
	
	/**
	 * 문자열 값을 ","(delimiter)로 tokenize한 후 문자열 배열에 저장하여 반환함.
	 * 
	 * @param str : 문자열 값.
	 * @return 문자열 배열.
	 */
	public static String[] commaDelimitedStringToStringArray(String str) {
		return delimitedStringToStringArray(str, ",");
	}
	
	/**
	 * 두 문자열을 비교함.(String의 compareTo 기준에 준함.)
	 * 
	 * @param sourceStr 문자열 값. input string
	 * @param anotherStr 비교할 문자열 값.
	 * @return -1 : 입력된 문자열 값이 null, 0 : 같음, 다름 : 0, -1 이외의 값.
	 * @see String#compareTo(String)
	 */
	public static int compareTo(String sourceStr, String anotherStr) {
		if (sourceStr == null || anotherStr == null) {
			return -1;
		}
		
		return sourceStr.compareTo(anotherStr);
	}
	
	/**
	 * 두 문자열을 비교함.(String의 compareToIgnoreCase 기준에 준함.)
	 * 
	 * @param sourceStr 문자열 값.
	 * @param anotherStr 비교할 문자열 값.
	 * @return -1 : 입력된 문자열 값이 null, 0 : 같음, 다름 : 0, -1 이외의 값.
	 * @see String#compareTo(String)
	 */
	public static int compareToIgnoreCase(String sourceStr, String anotherStr) {
		if (sourceStr == null || anotherStr == null) {
			return -1;
		}

		return sourceStr.compareToIgnoreCase(anotherStr);
	}
	
	/**
	 * 문자열에 특정 문자가 있는지 여부를 반환함.
	 * 
	 * @param str 문자열 값.
	 * @param invalidChars 특정문자값.
	 * @return true : 포함하고 있음. false : 포함하고 있지 않거나 입력된 값이 없음.
	 */
	public static boolean containsInvalidChars(String str, char[] invalidChars) {
		if (str == null || invalidChars == null) {
			return false;
		}
		
		int strSize = str.length();
		int validSize = invalidChars.length;
		
		for (int i = 0; i < strSize; i++) {
			char ch = str.charAt(i);
			for (int j = 0; j < validSize; j++) {
				if (invalidChars[j] == ch) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	/**
	 * 문자열에 특정 문자가 있는지 여부를 반환함.
	 * 
	 * @param str 문자열 값.
	 * @param invalidChars 특정문자값.
	 * @return true : 포함하고 있음. false : 포함하고 있지 않거나 입력된 값이 없음.
	 */
	public static boolean containsInvalidChars(String str, String invalidChars) {
		if (str == null || invalidChars == null) {
			return true;
		}
		
		return containsInvalidChars(str, invalidChars.toCharArray());
	}
	
	/**
	 * 문자열 내에 같은 값을 가진 문자열의 수와 설정한 문자열의 수가 같은지 여부를 반환함.
	 * 
	 * @param str 문자열 값.
	 * @param maxSeqNumber 반복된 횟수값.
	 * @return maxSeqNumber가 문자열 내에 같은 값을 가진 수와 같으면 true.
	 */
	public static boolean containsMaxSequence(String str, String maxSeqNumber) {
		int occurence = 1;
		int max = NumberUtils.stringToInteger(maxSeqNumber);
		
		if (str == null) {
			return false;
		}
		
		int sz = str.length();
		
		for (int i = 0; i < (sz - 1); i++) {
			if (str.charAt(i) == str.charAt(i + 1)) {
				occurence++;
				
				if (occurence == max)
					return true;
			} else {
				occurence = 1;
			}
		}
		
		return false;
	}
	
	/**
	 * '_'를 기준으로 camel case 변환.(첫글자 대문자.)
	 * 
	 * @param str 문자열 값.
	 * @return camel case 변환된 값.
	 */
	public static String convertToCamelCase(String str) {
		return convertToCamelCase(str, '_');
	}
	
	/**
	 * posChar를 기준으로 camel case 변환.(첫글자 대문자.)
	 * 
	 * @param targetString 문자열 값
	 * @param posChar 구분자값.
	 * @return posChar를 기준으로 camel case 변환된 값.
	 */
	public static String convertToCamelCase(String targetString, char posChar) {
		StringBuilder result = new StringBuilder();
		boolean nextUpper = false;
		String allLower = targetString.toLowerCase();
		
		for (int i = 0; i < allLower.length(); i++) {
			char currentChar = allLower.charAt(i);

			if (currentChar == posChar) {
				nextUpper = true;
			} else {
				if (nextUpper) {
					currentChar = Character.toUpperCase(currentChar);
					nextUpper = false;
				}

				result.append(currentChar);
			}
		}
		
		return result.toString();
	}
	
	/**
	 * "_"를 기준으로 소문자 변환한 값을 반환함.(첫글자 소문자.)
	 * 
	 * @param str 문자열 값.
	 * @return "_"를 기준으로 소문자 변환한 값.
	 */
	public static String convertToUnderScore(String str) {
		String result = "";
		
		for (int i = 0; i < str.length(); i++) {
			char currentChar = str.charAt(i);

			if (i > 0 && Character.isUpperCase(currentChar)) {
				result = result.concat("_");
			}

			result = result.concat(Character.toString(currentChar).toLowerCase());
		}
		
		return result;
	}
	
	/**
	 * 문자열 값에 패턴이 다른 문자열 값이 몇번 나타나는지 횟수를 반환함. (countPattern("aaa", "aa") => 1)
	 * 
	 * @param str 문자열 값.
	 * @param pattern 검색할 패턴.
	 * @return 반복되는 횟수.
	 */
	public static int countPattern(String str, String pattern) {
		if (StringUtils.isEmpty(str) || StringUtils.isEmpty(pattern)) {
			return 0;
		}
		
		int count = 0;
		int pos = 0;
		
		for (int index = 0; (index = str.indexOf(pattern, pos)) != -1;) {
			count++;
			pos = index + pattern.length();
		}
		
		return count;
	}
	
	/**
	 * 첫번째, 두번째 문자열 값을 비교하고 같으면 세번째 문자열 값, 다르면 네번째 문자열 값을 반환함.
	 * 
	 * @param source 문자열 값.
	 * @param target 비교할 문자열 값.
	 * @param result 같을 경우 출력되는 문자열 값.
	 * @param base 다를 경우 출력되는 문자열 값.
	 * @return result 또는 base 문자열 값.
	 */
	public static String decode(String source, String target, String result,
			String base) {
		if (source == null && target == null) {
			return result;
		} else if (source == null && target != null) {
			return base;
		} else if (source.trim().equals(target)) {
			return result;
		}
		return base;
	}
	
	/**
	 * 문자열 내에 특정 패턴의 문자열 값을 제거함.
	 * 
	 * @param str 문자열 값.
	 * @param delStr 제거할 문자열 값.
	 * @return 문자열 내에 특정 패턴의 문자열 값.
	 */
	public static String removeTxt(String str, String delStr) {
		if (str == null || delStr == null) {
			return str;
		}
		
		String value = str;
		
		for (int i = 0; i < delStr.length(); i++) {
			value = delChar(value, delStr.charAt(i));
		}
		
		return value;
	}
	
	/**
	 * 문자열 내에 특정 패턴의 문자열 값을 제거함.
	 * 
	 * @param str 문자열 값.
	 * @param delStr 제거할 문자열 값.
	 * @return 문자열 내에 특정 패턴의 문자열 값.
	 */
	public static String deletePattern(String str, String delStr) {
		return replacePattern(str, delStr, "");
	}
	
	/**
	 * 문자열을 delimiter를 기준으로 분리한 문자열 배열을 반환함.
	 * 
	 * @param str 문자열 값.
	 * @param delimiter 구분문자값.
	 * @return delimiter를 기준으로 분리한 문자열 배열.
	 */
	public static String[] delimitedStringToStringArray(String str,
			String delimiter) {
		if (str == null) {
			return null;
		}

		if (delimiter == null) {
			return new String[] { str };
		}

		List<String> tokens = new ArrayList<String>();
		int pos = 0;

		for (int index = 0; (index = str.indexOf(delimiter, pos)) != -1;) {
			tokens.add(str.substring(pos, index));
			pos = index + delimiter.length();
		}

		if (pos <= str.length()) {
			tokens.add(str.substring(pos));
		}
		return tokens.toArray(new String[tokens.size()]);
	}
	
	/**
	 * 문자열 추가.<br>
	 * 문자열 길이 기준으로 왼쪽부터 추가됨.<br>
	 * 만약 문자열 길이보다 num값이 작으면 null을 반환하고,<br>
	 * 문자열 길이보다 num값이 크면 num값에서 문자열 값 길이를 뺀 수만큼 특정 문자가 추가됨.
	 * 
	 * @param originalStr 문자열 값.
	 * @param ch 추가될 문자값.
	 * @param num 설정값.
	 * 
	 * @return 문자열이 추가된 문자열.
	 */
	public static String fillLeftString(String originalStr, char ch, int num) {
		int originalStrLength = originalStr.length();
		
		if (num < originalStrLength)
			return null;
		
		int difference = num - originalStrLength;
		
		StringBuilder strBuf = new StringBuilder();
		
		for (int i = 0; i < difference; i++)
			strBuf.append(ch);
		
		strBuf.append(originalStr);
		
		return strBuf.toString();
	}
	
	/**
	 * 바이트 길이 반환.
	 * 
	 * @param cha 문자값.
	 * @return 문자값의 바이트 길이.
	 */
	public static int getByteLength(char cha) {
		int charCode = cha;
		
		if (charCode <= ONE_BYTE) {
			return 1;
		} else if (charCode <= TWO_BYTE) {
			return 2;
		} else if (charCode <= THREE_BYTE) {
			return 3;
		} else {
			return 4;
		}
	}
	
	/**
	 * 문자열의 바이트 길이를 반환.
	 * 
	 * @param str 문자열 값.
	 * @return 문자열의 바이트 길이
	 */
	public static int getByteLength(String str) {
		if (str == null) {
			return -1;
		}
		
		int size = 0;
		
		for (int i = 0; i < str.length(); i++) {
			size += getByteLength(str.charAt(i));
		}
		
		return size;
	}
	
	/**
	 * 문자열에 특정문자가 포함된 횟수를 반환함.
	 * 
	 * @param str 문자열 값.
	 * @param chars 검색할 문자값.
	 * @return 문자열에 특정문자가 포함된 횟수.
	 */
	public static int getContainsCount(String str, char[] chars) {
		if (str == null || chars == null) {
			return -1;
		}

		int strSize = str.length();
		int validSize = chars.length;
		int check = 0;

		for (int i = 0; i < strSize; i++) {
			char ch = str.charAt(i);
			for (int j = 0; j < validSize; j++) {
				if (chars[j] == ch) {
					check += 1;
				}
			}
		}

		return check;
	}
	
	/**
	 * 문자열에 특정문자열이 포함된 횟수를 반환함.
	 * 
	 * @param str 문자열 값.
	 * @param sub 검색할 문자열 값.
	 * @return 문자열에 특정문자열이 포함된 횟수.
	 * @see org.springframework.util.StringUtils#countOccurrencesOf(String,
	 *      String)
	 */
	public static int getContainsCount(String str, String sub) {
		return org.springframework.util.StringUtils.countOccurrencesOf(str, sub);
	}
	
	/**
	 * 문자열에 특정문자가 포함된 횟수를 반환함.(IgnoreCase)
	 * 
	 * @param str 문자열 값.
	 * @param chars 검색할 문자값.
	 * @return 문자열에 특정문자가 포함된 횟수.
	 */
	public static int getContainsCountIgnoreCase(String str, char[] chars) {
		char[] lowerChar = new char[chars.length];
		for (int j = 0; j < chars.length; j++) {
			String res = String.valueOf(chars[j]).toLowerCase();
			lowerChar[j] = res.charAt(0);
		}
		return getContainsCount(str.toLowerCase(), lowerChar);
	}
	
	/**
	 * 문자열에 특정문자열이 포함된 횟수를 반환함.(IgnoreCase)
	 * 
	 * @param str 자열 값.
	 * @param sub 검색할 문자열 값.
	 * @return 문자열에 특정문자열이 포함된 횟수.
	 * @see org.springframework.util.StringUtils#countOccurrencesOf(String,
	 *      String)
	 */
	public static int getContainsCountIgnoreCase(String str, String sub) {
		return org.springframework.util.StringUtils.countOccurrencesOf(str.toLowerCase(), sub.toLowerCase());
	}
	
	/**
	 * 문자열을 특정길이만큼 자른 문자열을 반환.
	 * 
	 * @param str 문자열 값.
	 * @param length 길이 값.
	 * @return 특정길이만큼 자른 문자열.
	 */
	public static String getCutString(String str, int length) {
		String result = "";
		
		if (str != null) {
			if (getLength(str) > length)
				result = str.substring(0, length);
			else
				result = str;
		}
		
		return result;
	}
	
	/**
	 * 문자열에서 특정문자를 검색하고 마지막에 위치한 값을 반환.
	 * 
	 * <pre>
	 * StringUtil.getLastString(&quot;test*test*a*b*c&quot;, &quot;*&quot;) = &quot;c&quot;
	 * </pre>
	 * 
	 * @param origStr 문자열 값.
	 * @param strToken 검색할 문자열.
	 * @return 특정문자를 검색하고 마지막에 위치한 값.
	 */
	public static String getLastString(String origStr, String strToken) {
		StringTokenizer str = new StringTokenizer(origStr, strToken);
		String lastStr = "";

		while (str.hasMoreTokens()) {
			lastStr = str.nextToken();
		}
		return lastStr;
	}
	
	/**
	 * 문자열의 길이를 반환.<br>
	 * 값이 null인 경우, -1을 반환함.
	 * 
	 * @param str 문자열 값.
	 * @return 문자열 길이.
	 */
	public static int getLength(String str) {
		if (str == null) {
			return -1;
		}
		return str.length();
	}
	
	/**
	 * 설정한 길이만큼 랜덤한 문자열을 생성하여 반환함.
	 * 
	 * @param length 길이 값.
	 * @return 설정한 길이만큼 랜덤한 문자열.
	 */
	public static String getRandomString(int length) {
		return randomAlphabetic(length);
	}
	
	/**
	 * 설정한 길이만큼 랜덤한 문자열을 생성하여 반환함.
	 * 
	 * @param length 생성할 길이.
	 * @param startChar 생성시 사용할 문자의 시작 문자.
	 * @param endChar 생성시 사용할 문자의 마지막 문자.
	 * @return 랜덤한 문자열.
	 */
	public static String getRandomString(int length, char startChar,
			char endChar) {
		int startInt = Integer.valueOf(startChar);
		int endInt = Integer.valueOf(endChar);

		int gap = endInt - startInt;
		StringBuilder buf = new StringBuilder();

		for (int i = 0; i < length; i++) {
			int chInt;
			do {
				chInt = StringUtils.generator.nextInt(gap + 1) + startInt;
			} while (!Character.toString((char) chInt).matches("^[a-zA-Z]$"));
			buf.append((char) chInt);
		}
		return buf.toString();
	}
	
	/**
	 * 최소, 최대 길이 범위 내에서 랜덤한 문자열 생성.
	 * 
	 * @param minSize 길이의 최소값.
	 * @param maxSize 길이의 최대값.
	 * @return 랜덤한 문자열.
	 */
	public static String getRandomString(int minSize, int maxSize) {
		Random generator = new Random(System.currentTimeMillis());
		int randomLength = generator.nextInt(maxSize - minSize) + minSize;
		
		return randomAlphabetic(randomLength);
	}
	
	/**
	 * 랜덤한 문자열 생성.(charset설정.)
	 * 
	 * @param length 길이 값.
	 * @param charset 캐릭터셋명.
	 * @return 랜덤한 문자열
	 */
	public static String getRandomStringByCharset(int length, String charset) {
		String randomStr = getRandomString(length);

		return DigestUtils.encodeCharset(randomStr, charset);
	}
	
	/**
	 * 랜덤한 한글 문자열 생성. (UTF-8만).
	 * 
	 * @param count 길이 값.
	 * @return 랜덤한 한글 문자열.
	 * @throws UnsupportedEncodingException
	 */
	public static String getRandomStringByKorean(int count)
			throws UnsupportedEncodingException {
		StringBuilder buf = new StringBuilder();

		for (int i = 0; i < count; i++) {
			buf.append((char) (StringUtils.generator.nextInt(11172) + 0xAC00));
		}

		return buf.toString();
	}
	
	/**
	 * strToken을 기준으로 문자열을 분리하여 문자열 배열을 반환함. 문자열 내에 strToken이 없으면 원본을 그대로 출력.
	 * 
	 * @param str 문자열 값.
	 * @param strToken 토큰값.
	 * @return 문자열 배열.
	 */
	public static String[] getStringArray(String str, String strToken) {
		if (str.indexOf(strToken) != -1) {
			StringTokenizer st = new StringTokenizer(str, strToken);
			String[] stringArray = new String[st.countTokens()];

			for (int i = 0; st.hasMoreTokens(); i++) {
				stringArray[i] = st.nextToken();
			}

			return stringArray;
		}

		return new String[] { str };
	}
	
	/**
	 * ","을 기준으로 문자열을 분리하여 문자열 배열을 반환함.
	 * 
	 * @param str 문자열 값.
	 * @return ","을 기준으로 문자열을 분리하여 문자열 배열
	 */
	public static List<String> getTokens(String str) {
		return getTokens(str, ",");
	}
	
	/**
	 * 구분문자를 기준으로 문자열을 분리하여 문자열 배열을 반환함.
	 * 
	 * @param str 문자열 값.
	 * @param separator 구분문자값.
	 * @return 구분문자를 기준으로 문자열을 분리하여 문자열 배열.
	 */
	public static List<String> getTokens(String str, String separator) {
		List<String> tokens = new ArrayList<String>();
		
		if (str != null) {
			StringTokenizer st = new StringTokenizer(str, separator);
			while (st.hasMoreTokens()) {
				String en = st.nextToken().trim();
				tokens.add(en);
			}
		}
		
		return tokens;
	}
	
	/**
	 * hex코드를 문자열로 변환하여 반환함.(유니코드)
	 * 
	 * @param str 문자열 값. the String to convert
	 * @return UniCode String
	 */
	public static String hexToString(String str) {
		String inStr = str;
		char inChar[] = inStr.toCharArray();
		StringBuffer sb = new StringBuffer();
		
		for (int i = 0; i < inChar.length; i += 4) {
			String hex = str.substring(i, i + 4);
			sb.append((char) Integer.parseInt(hex, 16));
		}
		
		return sb.toString();
	}
	
	/**
	 * 숫자를 문자열로 변환하여 반환.
	 * 
	 * @param integer 정수값.
	 * @return 문자열 값.
	 */
	public static String integerToString(int integer) {
		return ("" + integer);
	}
	
	/**
	 * 문자열의 값이 알파벳인지 여부를 반환함.
	 * 
	 * @param str 문자열 값.
	 * @return 모두 알파벳인 경우 true
	 */
	public static boolean isAlphaOnly(String str) {
		if (str == null) {
			return false;
		}

		int sz = str.length();

		if (sz == 0)
			return false;

		for (int i = 0; i < sz; i++) {
			if (!Character.isLetter(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 문자열의 값이 알파벳 또는 숫자인지 여부를 반환함.
	 * 
	 * @param str 문자열 값.
	 * @return 문자열의 값이 알파벳 또는 숫자일 경우 true.
	 */
	public static boolean isAlphaOrNumericOnly(String str) {
		if (str == null) {
			return false;
		}

		int sz = str.length();

		if (sz == 0)
			return false;
		for (int i = 0; i < sz; i++) {
			if (!Character.isLetterOrDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 문자열이 숫자로만 구성되어 있는지 여부를 반환함.
	 * 
	 * @param str 문자열 값.
	 * @return 문자열이 숫자로만 구성되어 있으면 true.
	 */
	public static boolean isNumberOnly(String str) {
		if (str == null) {
			return false;
		}
		char chars[] = str.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (!Character.isDigit(chars[i])) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 문자열에 값이 존재하는지 여부를 반환함.(trim.)
	 * 
	 * @param str 문자열 값.
	 * @return 값이 존재하지 않르면 true.
	 */
	public static final boolean isEmptyTrimmed(String str) {
		return (str == null || str.trim().length() == 0);
	}
	
	/**
	 * 문자열이 특정한 패턴으로 구성되었는지 여부를 반환함.
	 * 
	 * @param str 문자열 값.
	 * @param pattern 패턴값.(정규식)
	 * @return 문자열이 특정한 패턴으로 구성되어 있을 경우 true.
	 */
	public static boolean isPatternedString(String str, String pattern) {
		if (str == null || pattern == null) {
			return false;
		} else {
			return str.matches(pattern);
		}
	}
	
	/**
	 * 문자가 한글인지 여부를 반환함.
	 * 
	 * @param cha 문자값.
	 * @return 문자가 한글이면 true.
	 */
	public static boolean isHangul(char cha) {
		String unicodeBlock = Character.UnicodeBlock.of(cha).toString();
		return unicodeBlock.equals("HANGUL_JAMO")
				|| unicodeBlock.equals("HANGUL_SYLLABLES")
				|| unicodeBlock.equals("HANGUL_COMPATIBILITY_JAMO");
	}
	
	/**
	 * 문자가 한글인지 여부를 반환함.
	 * 
	 * @param str 문자열 값.
	 * @param isCheckForAll
	 *            true : 문자열 전체 검색, false : 전체검색 하지 않음.
	 * @return 문자열 값이 한글일 경우 true. (isCheckForAll이 true일 경우, 문자열 전체가 한글이어야 함.)
	 */
	public static boolean isHangul(String str, boolean isCheckForAll) {
		char chars[] = str.toCharArray();
		if (!isCheckForAll) {
			for (int i = 0; i < chars.length; i++) {
				if (isHangul(chars[i])) {
					return true;
				}
			}
			return false;
		} else {
			for (int i = 0; i < chars.length; i++) {
				if (!isHangul(chars[i])) {
					return false;
				}
			}
			return true;
		}
	}
	
	/**
	 * 문자로만 구성되어 있는지 여부 반환함..
	 * 
	 * @param str 문자열 값.
	 * @return 문자열로만 구성되어 있으면 true.
	 */
	public static boolean isLetterOnly(String str) {
		if (str == null) {
			return false;
		}
		char chars[] = str.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (!Character.isLetter(chars[i])) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 문자열이 문자나 숫자로만 구성되어 있는지 여부를 반환함.
	 * 
	 * @param str 문자열 값.
	 * @return 문자나 숫자인 경우에만 true.
	 */
	public static boolean isLetterOrDigitOnly(String str) {
		if (str == null) {
			return false;
		}
		char chars[] = str.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (!Character.isLetterOrDigit(chars[i])) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 문자열이 숫자가 아닌 문자가 포함되어 있는지를 반환함.
	 * 
	 * @param str 문자열값
	 * @return 문자열이 숫자가 아닌 문자가 포함되어 있으면 true.
	 */
	public static boolean isNotNumeric(String str) {
		if (str == null) {
			return false;
		}
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if (Character.isDigit(str.charAt(i)) == false) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 문자열이 공백문자로만 구성되어 있는지를 반환.
	 * 
	 * @param str 문자열값.
	 * @return 문자열이 공백문자로만 구성되어 있는 경우에만 true.
	 */
	public static boolean isSpaceOnly(String str) {
		if (str == null) {
			return false;
		} else {
			return str.trim().length() <= 0;
		}
	}
	
	/**
	 * 설정된 길이만큼의 문자열을 반환함. 만약 문자열의 길이가 설정된 길이보다 작을 경우 원래 문자 그대로를 반환함.
	 * 
	 * @param str 문자열값
	 * @param length 길이 값.
	 * @return 설정된 길이만큼의 문자열.
	 */
	public static String left(String str, int length) {
		if (str == null) {
			return null;
		} else if (length <= 0 || str.length() <= length) {
			return str;
		} else {
			return str.substring(0, length);
		}
	}
	
	/**
	 * 설정된 길이를 기반으로 문자열 왼쪽에 공백 문자를 삽입하여 반환함.
	 * 
	 * <pre>
	 * StringUtil.leftPad(null, *) = null
	 * StringUtil.leftPad("", 3) = "   "
	 * StringUtil.leftPad("test", 3) = "test"
	 * StringUtil.leftPad("test", 5) = "  test"
	 * StringUtil.leftPad("test", 1) = "test"
	 * StringUtil.leftPad("test", -1) = "test"
	 * </pre>
	 * 
	 * @param str 문자열 값.
	 * @param length 문자열 길이 값.
	 * @return 설정된 길이를 기반으로 문자열 왼쪽에 공백 문자가 삽입된 문자열.
	 */
	public static String leftPad(String str, int length) {
		return leftPad(str, length, ' ');
	}
	
	/**
	 * 설정된 길이 값을 기준으로 문자를 왼쪽에 삽입함.
	 * 
	 * <pre>
	 * StringUtil.leftPad(null, *, *) = null
	 * StringUtil.leftPad("", 3, 'xxx') = "xxx"
	 * StringUtil.leftPad("test", 3, 'z') = "test"
	 * StringUtil.leftPad("test", 5, 'z') = "zztest"
	 * StringUtil.leftPad("test", 1, 'z') = "test"
	 * StringUtil.leftPad("test", -1, 'z') = "test"
	 * </pre>
	 * 
	 * @param str 문자열 값.
	 * @param length 길이 값.
	 * @param padChar 삽입할 글자 값.
	 * @return 설정된 길이 값을 기준으로 문자를 왼쪽에 삽입한 문자열.
	 */
	public static String leftPad(String str, int length, char padChar) {
		return padChar(str, length, padChar, true);
	}
	
	/**
	 * 설정된 길이 값을 기준으로 문자열을 왼쪽에 삽입함.
	 * string.<br>
	 * 
	 * <pre>
	 * StringUtil.leftPad(null, *, *) = null
	 * StringUtil.leftPad("", 3, "z") = "zzz"
	 * StringUtil.leftPad("test", 3, "yz") = "test"
	 * StringUtil.leftPad("test", 5, "yz") = "yztest"
	 * StringUtil.leftPad("test", 8, "yz") = "yzyzytest"
	 * StringUtil.leftPad("test", 1, "yz") = "test"
	 * StringUtil.leftPad("test", -1, "yz") = "test"
	 * StringUtil.leftPad("test", 5, null) = "  test"
	 * StringUtil.leftPad("test", 5, "") = "  test"
	 * </pre>
	 * 
	 * @param str 문자열 값.
	 * @param length 길이 값.
	 * @param padStr 삽입할 문자열 값.
	 * @return 설정된 길이 값을 기준으로 문자열을 왼쪽에 삽입한 문자열.
	 */
	public static String leftPad(String str, int length, String padStr) {
		return padString(str, length, padStr, true);
	}
	
	/**
	 * 문자열의 왼쪽 공백을 삭제한 문자열을 반환함.
	 * 
	 * @param str 문자열 값.
	 * @return 문자열의 왼쪽 공백을 삭제한 문자열.
	 * @see org.springframework.util.StringUtils#trimLeadingWhitespace(String)
	 */
	public static String leftTrim(String str) {
		return org.springframework.util.StringUtils.trimLeadingWhitespace(str);
	}
	
	/**
	 * 개행문자를 space문자로 변환하여 반환함.
	 * 
	 * @param str 문자열 값.
	 * @return 개행문자를 space문자로 변환한 문자열.
	 */
	public static String newLineToSpace(String str) {
		String output;
		
		output = str.replace("\r\n", " ");
		
		return output;
	}
	
	/**
	 * double 형태의 숫자를 10진수 형태의 문자열로 변환하여 반환함.<br>
	 * java.text.DecimalFormat을 사용함.<br>
	 * numberFormat(12345.67d, "###,###.#") => "12,345.7"<br>
	 * 
	 * @param d double 값.
	 * @param format 변환할 형식.
	 * @return 변환된 문자열.
	 */
	public static String numberFormat(double d, String format) {
		DecimalFormat decimalformat = new DecimalFormat(format);
		return decimalformat.format(d);
	}
	
	/**
	 * float 형태의 숫자를 10진수 형태의 문자열로 변환하여 반환함.<br>
	 * numberFormat(12345.67f, "###,###.#") => "12,345.7"<br>
	 * 
	 * @param f float 값.
	 * @param format 변환할 형식.
	 * @return 변환된 문자열.
	 */
	public static String numberFormat(float f, String format) {
		DecimalFormat decimalformat = new DecimalFormat(format);
		return decimalformat.format(f);
	}
	
	/**
	 * int 형태의 숫자를 10진수 형태의 문자열로 변환하여 반환함. 
	 * numberFormat(12345, "###,###") => "12,345"
	 * 
	 * @param i int 값.
	 * @param format 변환할 형식.
	 * @return 변환된 문자열.
	 */
	public static String numberFormat(int i, String format) {
		DecimalFormat decimalformat = new DecimalFormat(format);
		return decimalformat.format(i);
	}
	
	/**
	 * long 형태의 숫자를 10진수 형태의 문자열로 변환하여 반환함.<br>
	 * numberFormat(12345.67L, "###,###.#") => "12,345.7"
	 * 
	 * @param l long 값.
	 * @param format 변환할 형식.
	 * @return 변환된 문자열.
	 */
	public static String numberFormat(long l, String format) {
		DecimalFormat decimalformat = new DecimalFormat(format);
		return decimalformat.format(l);
	}
	
	/**
	 * short 형태의 숫자를 10진수 형태의 문자열로 변환하여 반환함.<br>
	 * numberFormat(12345, "###,###") => "12,345"<br>
	 * 
	 * @param s short 값.
	 * @param format 변환할 형식.
	 * @return 변환된 문자열.
	 */
	public static String numberFormat(short s, String format) {
		DecimalFormat decimalformat = new DecimalFormat(format);
		return decimalformat.format(s);
	}
	
	/**
	 * 지정된 길이를 기준으로 문자열의 좌/우측에 특정 문자를 추가하여 반환함.
	 * 
	 * @param str 문자열 값.
	 * @param length 길이 값.
	 * @param padChar 문자값.
	 * @param isLeft 왼쪽 : true, 오른쪽 : false. 
	 * @return 지정된 길이를 기준으로 문자열의 좌/우측에 특정 문자를 추가한 문자열.
	 */
	private static String padChar(String str, int length, char padChar, boolean isLeft) {
		if (str == null) {
			return null;
		}
		int originalStrLength = str.length();
		
		if (length < originalStrLength)
			return str;
		
		int difference = length - originalStrLength;
		
		StringBuilder strBuf = new StringBuilder();

		if (isLeft == false) {
			strBuf.append(str);
		}
		
		for (int i = 0; i < difference; i++)
			strBuf.append(padChar);
		
		if (isLeft) {
			strBuf.append(str);
		}
		
		return strBuf.toString();
	}
	
	/**
	 * 특정 문자를 설정된 크기만큼 반복한 문자열을 만들어 반환.
	 * 
	 * @param roopNum 반복 횟수.
	 * @param ch 반복할 문자.
	 * @return 특정 문자를 설정된 크기만큼 반복한 문자열.
	 */
	public static String padding(int roopNum, char ch) {
		if (roopNum < 0) {
			return null;
		}
		
		StringBuffer buffer = new StringBuffer(roopNum);
		
		for (int i = 0; i < roopNum; i++) {
			buffer.insert(i, ch);
		}
		
		return buffer.toString();
	}
	
	/**
	 * 지정된 길이를 기준으로 문자열의 좌/우측에 특정 문자열을 추가하여 반환함.
	 * 
	 * @param str 문자열 값.
	 * @param length 길이 값.
	 * @param padStr 반복할 문자열.
	 * @param isLeft true : 왼쪽에 삽입, false : 오른쪽에 삽입.
	 * @return 지정된 길이를 기준으로 문자열의 좌/우측에 특정 문자열이 추가된 문자열.
	 */
	private static String padString(String str, int length, String padStr, boolean isLeft) {
		if (str == null) {
			return null;
		}
		
		int originalStrLength = str.length();
		
		if (length < originalStrLength)
			return str;
		
		int difference = length - originalStrLength;
		
		String tempPad = "";
		
		if (difference > 0) {
			if (StringUtils.isEmpty(padStr)) {
				padStr = " ";
			}
			do {
				for (int j = 0; j < padStr.length(); j++) {
					tempPad += padStr.charAt(j);
					if (str.length() + tempPad.length() >= length) {
						break;
					}
				}
			} while (difference > tempPad.length());
			if (isLeft) {
				str = tempPad + str;
			} else {
				str = str + tempPad;
			}
		}
		
		return str;
	}
	
	/**
	 * path 문자열을 표준화하여 반환함.
	 * '\\' = > '/', 상대경로를 절대 경로로 변환함.
	 * 
	 * pathClean("../aaaa\\bbbb\\cccc\\dddd") => "aaaa/bbbb/cccc/dddd"
	 * 
	 * @param path path 문자열 값.
	 * @return 표준화된 path문자열.
	 */
	public static String pathClean(String path) {
		if (path == null) {
			return null;
		}
		
		String p = replacePattern(path, "\\", "/");
		String pArray[] = delimitedStringToStringArray(p, "/");
		
		List<String> pList = new LinkedList<String>();
		
		int tops = 0;
		
		for (int i = pArray.length - 1; i >= 0; i--) {
			if (StringUtils.equals(".", pArray[i])) {
				continue;
			} else if ("..".equals(pArray[i])) {
				tops++;
				continue;
			}
			if (tops > 0) {
				tops--;
			} else {
				pList.add(0, pArray[i]);
			}
		}
		
		return collectionToDelimitedString(pList, "/");
	}
	
	/**
	 * 두 개의 path 문자열을 표준화하여 서로 같은지 여부를 반환함. 
	 * pathEquals("../aaaa\\bbbb\\cccc\\dddd", "aaaa/bbbb/cccc/dddd") => true
	 * 
	 * @param path1 : path문자열 값1.
	 * @param path2 : path문자열 값2.
	 * @return 두 path문자열 값이 같은 경우 true.
	 */
	public static boolean pathEquals(String path1, String path2) {
		if (path1 == null) {
			if (path2 == null) {
				return true;
			} else {
				return false;
			}
		}
		return pathClean(path1).equals(pathClean(path2));
	}
	
	/**
	 * 설정된 길이만큼 랜덤한 영문자 문자열을 생성하여 반환함.
	 * @param length 길이 값.
	 * @return 설정된 길이만큼 랜덤한 영문자 문자열.
	 */
	private static String randomAlphabetic(int length) {
		StringBuilder buf = new StringBuilder();
		for (int i = 0; i < length; i++) {
			buf.append(alphabet[StringUtils.generator.nextInt(52)]);
		}
		return buf.toString();
	}
	
	/**
	 * 문자열에서 특정 문자열이 삭제된 문자열을 반환함.
	 * 
	 * @param str 문자열 값.
	 * @param delStr 삭제할 문자열 값.
	 * @return 문자열에서 특정 문자열이 삭제된 문자열.
	 * @see org.springframework.util.StringUtils#deleteAny(String, String)
	 */
	public static String delStr(String str, String delStr) {
		return org.springframework.util.StringUtils.deleteAny(str, delStr);
	}
	
	/**
	 * 문자열에서 특정 문자가 삭제된 문자열을 반환함.
	 * 
	 * @param str 문자열 값.
	 * @param remove 문자 값.
	 * @return 문자열에서 특정 문자가 삭제된 문자열.
	 */
	public static String delChar(String str, char remove) {
		return replacePattern(str, String.valueOf(remove), "");
	}
	
	/**
	 * 문자열 내에 설정된 특정 문자들을 삭제한 문자열을 반환함.<br>
	 * 삭제대상 문자 : + { '/', '-', ':', ',', '.', '%' }<br>
	 * 
	 * @param str 문자열 값.
	 * @return 문자열 내에 설정된 특정 문자들이삭제된 문자열.
	 */
	public static String delCharAll(String str) {
		char[] targetCharacters = { '/', '-', ':', ',', '.', '%' };
		return delCharAll(str, targetCharacters);
	}
	
	/**
	 * 문자열 내에 설정된 특정 문자들을 삭제한 문자열을 반환함.<br>
	 * 
	 * @param str 문자열 값.
	 * @param delChar 삭제할 문자 배열.
	 * @return 문자열 내에 설정된 특정 문자들을 삭제한 문자열
	 */
	public static String delCharAll(String str, char[] delChar) {
		String value = str;
		for (int i = 0; i < delChar.length; i++) {
			value = delChar(value, delChar[i]);
		}
		return value;
	}
	
	/**
	 * 문자열 내에 escaped문자를 html코드로 변환한 문자열을 반환함.
	 * removeEscapeChar("&lt;html&gt;Test&lt;html&gt;") => <html>Test<html>
	 * 
	 * @param str 문자열 값.
	 * @return 변환된 문자열.
	 * @see HtmlUtils#htmlUnescape(String)
	 */
	public static String removeEscapeChar(String str) {
		return HtmlUtils.htmlUnescape(str);
	}
	
	/**
	 * 문자열 내에 공백문자를 제거한 문자열을 반환함.
	 * 
	 * @param str 문자열 값.
	 * @return 공백문자를 제거한 문자열.
	 * @see org.springframework.util.StringUtils#trimAllWhitespace(String)
	 */
	public static String removeWhitespace(String str) {
		return org.springframework.util.StringUtils.trimAllWhitespace(str);
	}
	
	/**
	 * 문자열 내의 특정 문자열을 치환한 문자열을 반환함.
	 * 
	 * @param str 문자열 값.
	 * @param replacedStr 치환할 문자열.
	 * @param replaceStr 치환할 문자열에 대입되는 문자열. 
	 * @return 특정 문자열을 치환한 문자열.
	 */
	public static String replace(String str, String replacedStr,
			String replaceStr) {
		String newStr = "";
		if (str.indexOf(replacedStr) != -1) {
			String s1 = str.substring(0, str.indexOf(replacedStr));
			String s2 = str.substring(str.indexOf(replacedStr) + 1);
			newStr = s1 + replaceStr + s2;
		}
		
		return newStr;
	}
	
	/**
	 * 문자열 내의 특정 문자열을 정규식으로 치환한 문자열을 반환함.
	 * 
	 * @param str 문자열 값.
	 * @param regex 대입할 정규식.
	 * @param replacement 치환할 문자.
	 * @return 특정 문자열을 정규식으로 치환한 문자열.
	 * @see String#replaceAll(String, String)
	 */
	public static String replaceAll(String str, String regex, String replacement) {
		if (str == null) {
			return null;
		}
		return str.replaceAll(regex, replacement);
	}
	
	/**
	 * 문자열 내에 입력할 문자열과 일치하는 첫번째 문자열을 치환하여 반환함.
	 * 
	 * @param str 문자열 값.
	 * @param regex 정규식 검색 조건 값.
	 * @param replacement 치환할 문자열 값.
	 * @return 치환된 문자열.
	 * @see String#replaceFirst(String, String)
	 */
	public static String replaceFirst(String str, String regex,
			String replacement) {
		if (str == null) {
			return null;
		}
		return str.replaceFirst(regex, replacement);
	}
	
	/**
	 * 문자열 내에 escaped되어 있지 않는 문자를 html escaped문자로 변환하여 반환함.
	 * replaceHtmlEscape("<html>Test</html>") => "&lt;html&gt;Test&lt;html&gt;"
	 * 
	 * @param input 문자열 값.
	 * @return 변환된 문자열.
	 * @see HtmlUtils#htmlEscape(String)
	 */
	public static String replaceHtmlEscape(String input) {
		return HtmlUtils.htmlEscape(input);
	}
	
	/**
	 * 문자열 내에 입력할 문자열과 일치하는 마지막 문자열을 치환하여 반환함.
	 * 
	 * @param str 문자열 값.
	 * @param regex 검색할 정규식 조건값.
	 * @param replacement 치환할 문자열 값.
	 * @return 치환된 문자열.
	 */
	public static String replaceLast(String str, String regex, String replacement) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		
		if (matcher.find() == false) {
			return str;
		}
		
		int lastMatchStart = 0;
		
		do {
			lastMatchStart = matcher.start();
		} while (matcher.find());
		matcher.find(lastMatchStart);
		StringBuffer sb = new StringBuffer(str.length());
		matcher.appendReplacement(sb, replacement);
		matcher.appendTail(sb);
		
		return sb.toString();
	}
	
	/**
	 * 문자열 내에 특정 문자열을 치환한 문자열을 반환함.
	 * 
	 * 왼쪽부터 중첩되지 않는 형태로 처리함.(변환 후에도 원래 존재하고 있었던 패턴이 존재할 수 있음.)
	 * 
	 * @param str 문자열 값.
	 * @param searchStr 검색할 문자열 값.
	 * @param replacement 치환할 문자열 값.
	 * @return 문자열 내에 특정 문자열을 치환한 문자열.
	 */
	public static String replacePattern(String str, String searchStr,
			String replacement) {
		if (str == null) {
			return null;
		}
		
		if (searchStr == null || replacement == null) {
			return str;
		}
		
		StringBuffer sbuf = new StringBuffer();
		
		int pos = 0;
		int index = str.indexOf(searchStr);
		int patLen = searchStr.length();
		for (; index >= 0; index = str.indexOf(searchStr, pos)) {
			sbuf.append(str.substring(pos, index));
			sbuf.append(replacement);
			pos = index + patLen;
		}
		sbuf.append(str.substring(pos));
		return sbuf.toString();
	}
	
	/**
	 * 문자열의 순서를 반전시킨 문자열을 반환함.
	 * 
	 * @param str 문자열 값.
	 * @return 문자열의 순서를 반전시킨 문자열.
	 */
	public static String reverse(String str) {
		if (str == null) {
			return null;
		}
		
		return new StringBuilder(str).reverse().toString();
	}
	
	/**
	 * 설정된 길이만큼 오른쪽 부분을 자른 문자열을 반환함. (0부터 시작.)
	 * 
	 * @param str 문자열 값.
	 * @param length 길이 값.
	 * @return 설정된 길이만큼 오른쪽 부분을 자른 문자열.
	 */
	public static String right(String str, int length) {
		if (str == null) {
			return null;
		} else if (length <= 0 || str.length() <= length) {
			return str;
		} else {
			return str.substring(str.length() - length);
		}
	}
	
	/**
	 * 설정된 길이를 기준으로 공백 문자를 삽입한 문자열을 반환한다.
	 * 길이값은 입력된 문자열보다 반드시 길어야 하며, 그렇지 않은 경우에는 원본 문자열을 반환함.
	 * 
	 * @param str 문자열 값.
	 * @param length 길이 값.
	 * @return 설정된 길이를 기준으로 공백 문자를 삽입한 문자열.
	 */
	public static String rightPad(String str, int length) {
		return rightPad(str, length, ' ');
	}
	
	/**
	 * 설정된 길이를 기준으로 특정 문자를 삽입한 문자열을 반환한다.<br>
	 * 길이값은 입력된 문자열보다 반드시 길어야 하며, 그렇지 않은 경우에는 원본 문자열을 반환함.
	 * 
	 * @param str 문자열 값.
	 * @param length 길이 값.
	 * @param ch 삽입할 문자열.
	 * @return 설정된 길이를 기준으로 특정 문자를 삽입한 문자열.
	 */
	public static String rightPad(String str, int length, char ch) {
		return padChar(str, length, ch, false);
	}
	
	/**
	 * 설정된 길이를 기준으로 특정 문자를 삽입한 문자열을 반환한다.
	 * 길이값은 입력된 문자열보다 반드시 길어야 하며, 그렇지 않은 경우에는 원본 문자열을 반환함.
	 * 
	 * <pre>
	 * StringUtil.rightPad(null, *, *) = null
	 * StringUtil.rightPad("", 3, "z") = "zzz"
	 * StringUtil.rightPad("test", 3, "yz") = "test"
	 * StringUtil.rightPad("test", 5, "yz") = "testyz"
	 * StringUtil.rightPad("test", 8, "yz") = "testyzyzy"
	 * StringUtil.rightPad("test", 1, "yz") = "test"
	 * StringUtil.rightPad("test", -1, "yz") = "test"
	 * StringUtil.rightPad("test", 5, null) = "test  "
	 * StringUtil.rightPad("test", 5, "") = "test  "
	 * </pre>
	 * 
	 * @param str
	 *            the String to pad out, may be null
	 * @param size
	 *            the size to pad to
	 * @param padStr
	 *            the String to pad with, null or empty treated as single space
	 * @return string that is padded <code>null</code> if null String input
	 */
	public static String rightPad(String str, int size, String padStr) {
		return padString(str, size, padStr, false);
	}
	
	/**
	 * 문자열의 오른쪽 공백만 제거한 문자열을 반환함.
	 * 
	 * @param str 문자열 값.
	 * @return 오른쪽 공백만 제거한 문자열.
	 * @see org.springframework.util.StringUtils#trimTrailingWhitespace(String)
	 */
	public static String rightTrim(String str) {
		return org.springframework.util.StringUtils.trimTrailingWhitespace(str);
	}
	
	/**
	 * 문자열을 특정문자 기준으로 분리하여 문자열 배열로 반환함.
	 * 
	 * @param str 문자열 값.
	 * @param separator 분리시 사용할 문자열 값.
	 * @return 특정문자 기준으로 분리된 문자열 배열.
	 */
	public static String[] split(String str, char separator) {
		StringBuffer tempStringBuffer = new StringBuffer();
		tempStringBuffer.append(separator);
		return tokenizeToStringArray(str, tempStringBuffer.toString(), false, false);
	}
	
	/**
	 * 지정한 길이만큼 문자열의 앞부분을 잘라내어 반환함.
	 * 
	 * @param str 문자열 값.
	 * @param length 길이 값.
	 * @return 지정된 길이만큼의 앞부분을 자른 문자열.
	 */
	public static String splitHead(String str, int length) {
		if (str == null) {
			return "";
		}
		
		if (str.length() > length) {
			str = str.substring(0, length);
		}
		
		return str;
	}
	
	/**
	 * 지정한 길이만큼 문자열의 앞부분을 잘라내고 뒤에 '...'을 붙인 문자열을 반환함.
	 * 
	 * 
	 * @param str 문자열 값.
	 * @param length 길이 값.
	 * @return 지정한 길이만큼 문자열의 앞부분을 잘라내고 뒤에 '...'을 붙인 문자열.
	 */
	public static String splitHeadWithEllipsis(String str, int length) {
		if (str == null) {
			return null;
		} else if (length <= 0 || str.length() <= length) {
			return str;
		} else {
			return str.substring(0, length) + "...";
		}
	}
	
	/**
	 * 지정된 길이만큼 문자열 뒷부분을 잘라낸 문자열을 반환함.
	 * 
	 * @param str 문자열 값.
	 * @param length 길이 값.
	 * @return 지정된 길이만큼 문자열 뒷부분을 잘라낸 문자열.
	 */
	public static String splitTail(String str, int length) {
		if (str == null) {
			return "";
		}
		
		if (str.length() > length) {
			str = str.substring(str.length() - length);
		}
		
		return str;
	}
	
	/**
	 * 지정된 길이만큼 문자열 뒷부분을 잘라내고 '...'을 붙인 문자열을 반환함.
	 * 
	 * @param str 문자열 값.
	 * @param length 길이 값.
	 * @return 지정된 길이만큼 문자열 뒷부분을 잘라내고 '...'을 붙인 문자열.
	 */
	public static String splitTailWithEllipsis(String str, int length) {
		if (str == null) {
			return null;
		} else if (length <= 0 || str.length() <= length) {
			return str;
		} else {
			return "..." + str.substring(str.length() - length);
		}
	}
	
	/**
	 * 문자열 값을 int값으로 변환한 값을 반환함.
	 * 
	 * @param str 문자열 값.
	 * @return 문자열 값을 int값으로 변환한 값.
	 */
	public static int stringToInteger(String str) {
		int ret = Integer.parseInt(str.trim());
		
		return ret;
	}
	
	/**
	 * 문자열 값을 BigDecimal로 변환한 값을 반환함.
	 * 
	 * @param str 문자열 값.
	 * @return 문자열 값을 BigDecimal로 변환한 값.
	 */
	public static BigDecimal stringToBigDecimal(String str) {
		if (StringUtils.isEmpty(rightTrim(str)))
			return new BigDecimal(0);
		else
			return new BigDecimal(str);
	}
	
	/**
	 * 정해진 위치값을 기준으로 문자열을  잘라내어 BigDecimal로 변환하여 반환함.(시작위치는 0이 기준임.)
	 * 
	 * @param str 문자열 값.
	 * @param startPos 시작 위치.
	 * @param endPos 마지막 위치.
	 * @return 정해진 위치값을 기준으로 문자열을  잘라내낸 BigDecimal.
	 */
	public static BigDecimal stringToBigDecimal(String str, int startPos, int endPos) {
		if (StringUtils.isEmpty(rightTrim(str)))
			return new BigDecimal(0);
		else if (str.length() < startPos + endPos)
			return stringToBigDecimal(leftPad(str, startPos + endPos, "0"));
		else
			return stringToBigDecimal(str.substring(startPos, startPos + endPos));
	}
	
	/**
	 * 문자열을 hex코드로 변환한 문자열을 반환함.
	 * 
	 * @param str 문자열 값.
	 * @return 문자열을 hex코드로 변환한 문자열.
	 */
	public static String stringToHex(String str) {
		
		String inStr = str;
		
		char inChar[] = inStr.toCharArray();
		StringBuffer sb = new StringBuffer();
		
		for (int i = 0; i < inChar.length; i++) {
			String hex = Integer.toHexString((int) inChar[i]);
			if (hex.length() == 2) {
				hex = "00" + hex;
			}
			sb.append(hex);
		}
		
		return sb.toString();
	}
	
	/**
	 * 문자열을 정수형으로 변환하여 반환함.
	 * 
	 * @param str 문자열.
	 * @return integer.
	 */
	public static int stringToNum(String str) {
		if ("".equals(rightTrim(str)))
			return 0;
		else
			return Integer.parseInt(str);
	}
	
	/**
	 * 정해진 위치값을 기준으로 문자열을  잘라내어 정수형으로 변환하여 반환함.(시작위치는 0이 기준임.)
	 * 
	 * @param str 문자열 값.
	 * @param startPos 시작위치 값.
	 * @param endPos 마지막 위치 값.
	 * @return 정해진 위치값을 기준으로 문자열을  잘라낸 정수.
	 */
	public static int stringToNum(String str, int startPos, int endPos) {
		if ("".equals(rightTrim(str)))
			return 0;
		else if (str.length() < startPos + endPos)
			return stringToNum(leftPad(str, startPos + endPos, "0"));
		else
			return stringToNum(str.substring(startPos, startPos + endPos));
	}
	
	/**
	 * 문자열의 첫부분을 대/소문자로 변환한 문자열을 반환함.<br>
	 * 문자열의 첫글자가 대문자인 경우에는 소문자로, 반대로는 대문자로 변환함.
	 * 
	 * @param str 문자열 값.
	 * @return 문자열의 첫부분을 대/소문자로 변환한 문자열.
	 */
	public static String swapFirstLetterCase(String str) {
		StringBuilder sbuf = new StringBuilder(str);
		sbuf.deleteCharAt(0);
		if (Character.isLowerCase(str.substring(0, 1).toCharArray()[0])) {
			sbuf.insert(0, str.substring(0, 1).toUpperCase());
		} else {
			sbuf.insert(0, str.substring(0, 1).toLowerCase());
		}
		return sbuf.toString();
	}
	
	/**
	 * 사업자 등록번호 형태로 변환한 문자열을 반환함.<br>
	 * 숫자로만 구성되어져야 하며, 그렇지 않은 경우에는 ""를 반환함.
	 * 
	 * @param str 문자열 값.
	 * @return 사업자 등록번호 형태로 변환한 문자열
	 */
	public static String toBizNumPattern(String str) {
		if (str == null) {
			return "";
		}
		if (str.length() != 10 || isNumberOnly(str) == false) {
			return "";
		} else {
			StringBuffer buffer = new StringBuffer();
			buffer.append(str.substring(0, 3));
			buffer.append('-');
			buffer.append(str.substring(3, 5));
			buffer.append('-');
			buffer.append(str.substring(5, 10));
			return buffer.toString();
		}
	}
	
	/**
	 * 문자열을 delimiter로 tokenize한 문자열 배열을 반환함.
	 * 
	 * @param str 문자열 값.
	 * @param delimiter 분리할 때 사용할 구분 문자.
	 * @param trimTokens trim적용여부.
	 * @param ignoreEmptyTokens ""인 값의 포함 여부. 
	 * @return 문자열을 delimiter로 tokenize한 문자열 배열
	 */
	public static String[] tokenizeToStringArray(String str, String delimiter,
			boolean trimTokens, boolean ignoreEmptyTokens) {
		if (str == null) {
			return null;
		}
		
		if (delimiter == null) {
			return new String[] { str };
		}
		
		StringTokenizer st = new StringTokenizer(str, delimiter);
		List<String> tokens = new ArrayList<String>();
		
		do {
			if (!st.hasMoreTokens()) {
				break;
			}
			String token = st.nextToken();
			if (trimTokens) {
				token = token.trim();
			}
			if (!ignoreEmptyTokens || token.length() != 0) {
				tokens.add(token);
			}
		} while (true);
		
		return tokens.toArray(new String[tokens.size()]);
	}
	
	/**
	 * 문자열의 첫번째 값을 소문자로 변환한 문자열을 반환함.
	 * 
	 * @param str 문자열 값.
	 * @return 문자열의 첫번째 값을 소문자로 변환한 문자열.
	 */
	public static String toLowercase(String str) {
		return changeFirstCharacterCase(str, false);
	}
	
	/**
	 * 문자열을 주민등록번호 형태로 변환한 문자열을 반환함.
	 * 숫자로만 구성되어져야 하며, 그렇지 않을 경우 ""를 반환함. 
	 * 
	 * @param str 문자열 값.
	 * @return 문자열을 주민등록번호 형태로 변환한 문자열.
	 */
	public static String toSsnPattern(String str) {
		if (str == null) {
			return "";
		}
		if (str.length() != 13 || isNumberOnly(str) == false) {
			return "";
		} else {
			StringBuffer buffer = new StringBuffer();
			buffer.append(str.substring(0, 6));
			buffer.append('-');
			buffer.append(str.substring(6));
			return buffer.toString();
		}
	}
	
	/**
	 * 문자열을 전화번호 형식으로 변환한 문자열을 반환함.<br>
	 * 숫자만을 대상으로 변환함.
	 * 
	 * @param str 문자열 값.
	 * @return 문자열을 전화번호 형식으로 변환한 문자열.
	 */
	public static String toPhoneNumberFormat(String str) {
		
		int endNumberDigit = 4;
		int minNumberDigit = 7;
		
		if (StringUtils.isEmpty(str)) {
			return null;
		}
		
		String origin = str.trim();
		String tempNumber;
		
		int originLength = origin.length();
		
		// extract numeric chars only
		if (StringUtils.isNotNumeric(origin)) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < originLength; i++) {
				if (Character.isDigit(origin.charAt(i))) {
					sb.append(origin.charAt(i));
				}
			}
			tempNumber = sb.toString();
		} else {
			tempNumber = origin;
		}
		
		int numberLength = tempNumber.length();
		
		if (numberLength < minNumberDigit) {
			return tempNumber;
		}
		
		String firstNumber = "";
		String secondNumber = "";
		String thirdNumber = "";
		
		if (tempNumber.charAt(0) == '0') {
			if (tempNumber.charAt(1) == '2') {
				firstNumber = tempNumber.substring(0, 2);
				secondNumber = tempNumber.substring(2, numberLength - endNumberDigit);
				thirdNumber = tempNumber.substring(numberLength - endNumberDigit, numberLength);
			} else { 
				firstNumber = tempNumber.substring(0, 3);
				secondNumber = tempNumber.substring(3, numberLength - endNumberDigit);
				thirdNumber = tempNumber.substring(numberLength - endNumberDigit, numberLength);
			}
			
			return firstNumber + "-" + secondNumber + "-" + thirdNumber;
		} else {
			firstNumber = tempNumber.substring(0, numberLength - endNumberDigit);
			secondNumber = tempNumber.substring(numberLength - endNumberDigit, numberLength);
			
			return firstNumber + "-" + secondNumber;
		}
	}
	
	/**
	 * 문자열의 첫번째 값을 대문자로 변환한 문자열을 반환함.
	 * 
	 * @param str 문자열 값.
	 * @return 문자열의 첫번째 값을 대문자로 변환한 문자열.
	 */
	public static String toUpperCase(String inputString) {
		return changeFirstCharacterCase(inputString, true);
	}
	
	/**
	 * 문자열을 우편번호 형식으로 변환한 문자열을 반환함.
	 * 
	 * @param str 문자열 값.
	 * @return 문자열을 우편번호 형식으로 변환한 문자열.
	 */
	public static String toZipCdPattern(String str) {
		if (str == null) {
			return "";
		}
		
		if (str.length() != 6 || isNumberOnly(str) == false) {
			return "";
		} else {
			StringBuffer buffer = new StringBuffer();
			buffer.append(str.substring(0, 3));
			buffer.append('-');
			buffer.append(str.substring(3, 6));
			return buffer.toString();
		}
	}
	
	/**
	 * 두 문자열을 trim처리 하고 서로 같은 문자열인지 여부를 반환함.
	 * 
	 * @param str1 문자열 값.
	 * @param str2 비교할 대상 문자열.
	 * @return 같으면 true.
	 */
	public static boolean trimEquals(String str1, String str2) {
		if (str1 == null) {
			if (str2 == null) {
				return true;
			} else {
				return false;
			}
		} else if (str2 == null) {
			return false;
		} else {
			String trimBaseStr = str1.trim();
			String trimTargetStr = str2.trim();
			return trimBaseStr.equals(trimTargetStr);
		}
	}
	
	/**
	 * 문자열에 length 값을 기준으로 왼쪽에 지정된 문자를 반복해서 추가한 문자열을 반환함.
	 * 
	 * <pre>
	 * String str = fillString("test", 'e', 6); => eetest
	 * </pre>
	 * 
	 * @param str 문자열 값.
	 * @param ch 치환할 문자값.
	 * @param length ciphers 값.
	 * @return filled String
	 */
	public static String fillString(String str, char ch, int length) {
		int originalStrLength = str.length();
		
		if (length < originalStrLength)
			return null;
		
		int difference = length - originalStrLength;
		
		StringBuilder strBuf = new StringBuilder();
		for (int i = 0; i < difference; i++)
			strBuf.append(ch);
		
		strBuf.append(str);
		return strBuf.toString();
	}
	
	/**
	 * 문자열 배열 값을 하나의 문자열로 연결한 값을 반환함.(구분자는''.)
	 * @param strArr 문자열 배열 값.
	 * @return 문자열 배열 값을 하나의 문자열로 연결한 값.
	 */
	public static String concatStrArray(String[] strArr) {
		StringBuilder sb = new StringBuilder();
		for (String tmp : strArr) {
			sb.append(tmp);
			sb.append(" ");
		}
		return sb.toString().trim();
	}
	
	public static String lastIdxSubString(String imgUrl) {
		imgUrl = StringUtils.defaultIfEmpty(imgUrl, "");
		imgUrl = StringUtils.substringBefore(imgUrl, "?");
		imgUrl = StringUtils.substring(imgUrl, StringUtils.lastIndexOf(imgUrl, "/") + 1);
		return imgUrl;
	}
}