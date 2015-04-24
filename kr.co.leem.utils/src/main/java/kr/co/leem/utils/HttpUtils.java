package kr.co.leem.utils;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

@SuppressWarnings("unchecked")
public class HttpUtils {
	/**
	 * Request객체에서 파라메터를 전부 가져온 후 QueryString 형태로 만들어서 반환함.
	 * 
	 * @param request
	 * @return
	 */
	public static String getParamStr(HttpServletRequest request) {
		StringBuffer sb = new StringBuffer();
		
		Enumeration<String> parameterNames = request.getParameterNames();

		for (int i = 0; parameterNames.hasMoreElements(); i++) {
			String id = parameterNames.nextElement();
			String value = request.getParameter(id);
			sb.append(i == 0 ? "?" : "&");
			sb.append(id);
			sb.append("=");
			sb.append(value);
		}

		return sb.toString();
	}
}