package kr.co.leem.utils.lang;

public class ObjectUtils extends org.apache.commons.lang.ObjectUtils {
	public static int getLengthIfNull(Object[] objArr) {
		if (objArr != null) {
			return objArr.length;
		} else {
			return 0;
		}
	}
	
	public static String objectToString(Object obj) {
		return obj != null ? String.valueOf(obj) : null;
	}
}