package kr.co.leem.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtils {
	/**
	 * 가져오기를 원하는 쿠키를 cookieName으로 가져온다.
	 * @param request
	 * @param cookieName
	 * @return
	 */
	public static Cookie getCookie(HttpServletRequest request, String cookieName) {
		Cookie cookie = null;
		Cookie [] cookies = request.getCookies();
	
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals(cookieName)) {
					cookie = cookies[i];
					break;
				}
			}
		}
		return cookie;
	}
	
	/**
	 * 쿠키를 response에 추가한다.
	 * @param response
	 * @param cookie
	 */
	public static void addCookie(HttpServletRequest request,HttpServletResponse response,Cookie cookie) {
		addCookie(request,response, cookie, "/cap", 60 * 60 * 24 * 14);
	}
	public static void addCookie(HttpServletRequest request,HttpServletResponse response,Cookie cookie, String path, int maxAge) {
		if (cookie != null) {
			cookie.setPath(path);
			cookie.setMaxAge(maxAge);
			cookie.setDomain(request.getLocalAddr());
			response.addCookie(cookie);
		}
	}
	
	/**
	 * 해당쿠키를 삭제하도록 response에 실어 보낸다.
	 * @param response
	 * @param cookie
	 */
	public static void removeCookie(HttpServletResponse response,Cookie cookie) {
		removeCookie(response, cookie, "/cap");
	}
	public static void removeCookie(HttpServletResponse response,Cookie cookie,String path) {
		if (cookie != null) {
			cookie = new Cookie(cookie.getName(), ""); //내용이 빈쿠키로 만든다.
			cookie.setPath(path);
			cookie.setMaxAge(-1);
			response.addCookie(cookie);
		}
	}
}
