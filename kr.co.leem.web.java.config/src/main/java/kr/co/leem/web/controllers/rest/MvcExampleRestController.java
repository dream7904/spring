package kr.co.leem.web.controllers.rest;

import kr.co.leem.commons.constants.ResultType;
import kr.co.leem.web.services.DefaultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015-04-22.
 */
@RestController
@RequestMapping(value = "rest/mvc/example")
public class MvcExampleRestController {
	@Autowired
	private DefaultService defaultService;

	@RequestMapping(value = "dtl/{userId}")
	public Map<ResultType, Object> pathVariableTest(@PathVariable String userId) throws Exception {
		Map<ResultType, Object> resultMap = new HashMap<ResultType, Object>();
		defaultService.regTest();
		resultMap.put(ResultType.userId, userId);

		return resultMap;
	}

//	enable the use of matrix variables, you must set the removeSemicolonContent property of RequestMappingHandlerMapping to false.
//	By default it is set to true.
//	@RequestMapping(value = "data/{mainData}/sub/{subData}")
//	public Map<ResultType, Object> matrixVariableTest(
//			@MatrixVariable(pathVar = "mainData") Map<String, String> mainData,
//			@MatrixVariable(pathVar = "subData") Map<String, String> subData) throws Exception {
//		Map<ResultType, Object> resultMap = new HashMap<ResultType, Object>();
//
//		resultMap.put(ResultType.mainData, mainData);
//		resultMap.put(ResultType.subData, subData);
//
//		return resultMap;
//	}

	@RequestMapping(value = "showHeaderInfo")
	public Map<String, Object> showHeaderInfo(
			@RequestHeader("Accept-Encoding") String acceptEncoding,
			@RequestHeader("Accept") List<String> accept) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		resultMap.put("Accept-Encoding", acceptEncoding);
		resultMap.put("Accept", accept);

		return resultMap;
	}
}