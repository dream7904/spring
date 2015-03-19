package kr.co.leem.utils.lang;

public class DoubleUtils {
	
	public static boolean isEmpty(Double doubleValue) throws Exception {
		return doubleValue == null;
	}
	
	public static boolean isNotEmpty(Double doubleValue) throws Exception {
		return DoubleUtils.isEmpty(doubleValue) == false;
	}
	
	public static Double defaultIfEmpty(Double doubleValue, Double defaultDoubleValue) throws Exception {
		return DoubleUtils.isEmpty(doubleValue) ? defaultDoubleValue : doubleValue;
	}
}
