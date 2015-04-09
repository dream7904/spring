package kr.co.leem.web.services;

import kr.co.leem.commons.constants.ResultType;
import kr.co.leem.domains.menu.LowMenu;
import kr.co.leem.domains.menu.MenuReq;
import kr.co.leem.domains.menu.MidMenuGrp;
import kr.co.leem.domains.menu.TopMenuGrp;

import java.util.Map;

/**
 * Created by Administrator on 2015-03-19.
 */
public interface MenuService {
	public void getTopMenuGrps(MenuReq menuReq, Map<ResultType, Object> resultMap) throws Exception;
	public void getTopMenuGrp(MenuReq menuReq, Map<ResultType, Object> resultMap) throws Exception;
	public void saveTopMenuGrp(TopMenuGrp topMenuGrp, Map<ResultType, Object> resultMap) throws Exception;
	public void delTopMenuGrp(MenuReq menuReq, Map<ResultType, Object> resultMap) throws Exception;

	public void getMidMenuGrps(MenuReq menuReq, Map<ResultType, Object> resultMap) throws Exception;
	public void getMidMenuGrp(MenuReq menuReq, Map<ResultType, Object> resultMap) throws Exception;
	public void saveMidMenuGrp(MidMenuGrp midMenuGrp, Map<ResultType, Object> resultMap) throws Exception;
	public void delMidMenuGrp(MenuReq menuReq, Map<ResultType, Object> resultMap) throws Exception;

	public void getLowMenus(MenuReq menuReq, Map<ResultType, Object> resultMap) throws Exception;
	public void getLowMenu(MenuReq menuReq, Map<ResultType, Object> resultMap) throws Exception;
	public void saveLowMenu(LowMenu lowMenu, Map<ResultType, Object> resultMap) throws Exception;
	public void delLowMenu(MenuReq menuReq, Map<ResultType, Object> resultMap) throws Exception;
}