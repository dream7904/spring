package kr.co.leem.utils.lang;

public class IntegerUtils {
	
	public static boolean isEmpty(Integer integer) throws Exception {
		return integer == null;
	}
	
	public static boolean isNullOrZero(Integer integer) throws Exception {
		return integer == null || integer == 0;
	}
	
	public static boolean isNotEmpty(Integer integer) throws Exception {
		return !IntegerUtils.isEmpty(integer);
	}
	
	public static Integer defaultIfEmpty(Integer integer, Integer defaultInteger) throws Exception {
		return IntegerUtils.isEmpty(integer) ? defaultInteger : integer;
	}
	
	public static Integer defaultIfEmpty(Integer integer) throws Exception {
		return IntegerUtils.isEmpty(integer) ? 0 : integer;
	}
	
	public static Integer defaultIfEmpty(String str) throws Exception {
		return StringUtils.isEmpty(str) ? 0 : Integer.valueOf(str);
	}
	
	public static Integer defaultIfZero(int integer, Integer defaultInteger) throws Exception {
		return integer == 0 ? defaultInteger : integer;
	}
}