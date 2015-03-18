package kr.co.leem.libs.exceptions.handlers;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2015-02-02.
 */
@ControllerAdvice
public class ExceptionHandlerController {
	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ModelAndView handleError(HttpServletRequest req, Exception exception) {
		String header = req.getHeader("x-requested-with");

		if (StringUtils.equalsIgnoreCase("XMLHttpRequest", header) || StringUtils.equalsIgnoreCase("Ajax", header)) {
			JsonExceptionResponse jsonExceptionResponse = new JsonExceptionResponse();
			jsonExceptionResponse.setMessage(exception.getMessage());

			return jsonExceptionResponse.asModelAndView();
		} else {
			ModelAndView mav = new ModelAndView();
			mav.setViewName("redirect:commons/exception/default");

			return mav;
		}
	}
}
