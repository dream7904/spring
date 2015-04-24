package kr.co.leem.utils.lang;

public class BeanUtils extends org.springframework.beans.BeanUtils {
	public static boolean isEmpty(Object object) throws Exception {
		if (object == null) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean isNotEmpty(Object object) throws Exception {
		return isEmpty(object) == false;
	}
}