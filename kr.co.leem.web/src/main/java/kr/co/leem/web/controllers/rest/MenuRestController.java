package kr.co.leem.web.controllers.rest;

import kr.co.leem.constants.ResultType;
import kr.co.leem.constants.ResultValue;
import kr.co.leem.domains.menu.MenuReq;
import kr.co.leem.domains.menu.TopMenuGrp;
import kr.co.leem.web.services.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015-03-19.
 */
@RestController
@RequestMapping(value = "rest/menu")
public class MenuRestController {
	@Autowired private MenuService menuService;

	@RequestMapping(value = "getTopMenuGrps")
	public Map<ResultType, Object> getTopMenuGrps(MenuReq menuReq) throws Exception {
		Map<ResultType, Object> resultMap = new HashMap<ResultType, Object>();

		menuService.getTopMenuGrps(menuReq, resultMap);

		return resultMap;
	}

	@RequestMapping(value = "getTopMenuGrp")
	public Map<ResultType, Object> getTopMenuGrp(@RequestBody MenuReq menuReq) throws Exception {
		Map<ResultType, Object> resultMap = new HashMap<ResultType, Object>();

		menuService.getTopMenuGrp(menuReq, resultMap);

		resultMap.put(ResultType.resultCode, ResultValue.success);

		return resultMap;
	}

	@RequestMapping(value = "saveTopMenuGrp")
	public Map<ResultType, Object> saveTopMenuGrp(TopMenuGrp topMenuGrp) throws Exception {
		Map<ResultType, Object> resultMap = new HashMap<ResultType, Object>();

		menuService.saveTopMenuGrp(topMenuGrp, resultMap);

		resultMap.put(ResultType.resultCode, ResultValue.success);

		return resultMap;
	}

	@RequestMapping(value = "delTopMenuGrp")
	public Map<ResultType, Object> delTopMenuGrp(@RequestBody MenuReq menuReq) throws Exception {
		Map<ResultType, Object> resultMap = new HashMap<ResultType, Object>();

		menuService.delTopMenuGrp(menuReq, resultMap);

		resultMap.put(ResultType.resultCode, ResultValue.success);

		return resultMap;
	}
}