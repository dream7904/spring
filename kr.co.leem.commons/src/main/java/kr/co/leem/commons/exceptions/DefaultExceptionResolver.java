package kr.co.leem.commons.exceptions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.leem.commons.constants.Default;
import kr.co.leem.commons.messages.Messages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

/**
 * Exception Resolver
 * 
 * @author Dream.
 *
 */
public class DefaultExceptionResolver extends SimpleMappingExceptionResolver {
	@Autowired
	private Messages messages;
	
	private boolean isAjaxRequest(String header) {	//request => request.getHeader(ServiceConstants.Header.X_REQUESTED_WITH)
		if ("XMLHttpRequest".equalsIgnoreCase(header) || "Ajax".equalsIgnoreCase(header)) {
			return true;
		}
		return false;
	}
	
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		ModelAndView mav = super.resolveException(request, response, handler, ex);
		
		if (ex instanceof UserHandleableException) {
			UserHandleableException duhe = (UserHandleableException)ex;
			mav.addObject("resultCode", duhe.getErrorCode());
			mav.addObject("resultMsg", messages.getMessage(duhe.getErrorCode()));
		} else {
			ex.printStackTrace();
			mav.addObject("resultCode", Default.ResultType.FAIL);
		}
		if (isAjaxRequest(request.getHeader(Default.Header.X_REQUESTED_WITH))) {
			mav.setViewName("jsonView");
		} else {
			mav.setViewName("errorPage");
		}
		
		return mav;
	}
}