package kr.co.leem.utils;

public class CommonUtils {
	/**
	 * Ajax 요청 여부 체크.
	 * 
	 * @param header
	 * @return
	 */
	public static boolean isAjaxRequest(String header) {	//request => request.getHeader(ServiceConstants.Header.X_REQUESTED_WITH)
		if ("XMLHttpRequest".equalsIgnoreCase(header) || "Ajax".equalsIgnoreCase(header)) {
			return true;
		}

		return false;
	}
}