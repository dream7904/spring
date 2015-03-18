package kr.co.leem.libs.exceptions.handlers;

import kr.co.leem.constants.ResultType;
import kr.co.leem.constants.ResultValue;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.HashMap;
import java.util.Map;

public class JsonExceptionResponse {
    private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ModelAndView asModelAndView() {
        MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
	    Map<String, String> exceptionMap = new HashMap<String, String>();

	    exceptionMap.put(ResultType.resultCode.toString(), ResultValue.fail.toString());
	    exceptionMap.put(ResultType.exception.toString(), message);

        return new ModelAndView(jsonView, exceptionMap);
    }
}