package kr.co.leem.libs.interceptors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DefaultInterceptor extends HandlerInterceptorAdapter {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		boolean result = true;

		logger.debug("DefaultInterceptor");
		logger.debug(request.getRequestURI());
		logger.debug(request.getRequestURL().toString());

		try {
			logger.debug("****인터셉터 처리 구현****");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
}