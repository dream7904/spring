package kr.co.leem.repositories.jpa.criterias;

import kr.co.leem.domains.menu.LowMenu;
import kr.co.leem.domains.menu.MenuReq;

import java.util.List;

/**
 * Created by Administrator on 2015-03-20.
 */
public interface LowMenuDAO {
	public long cntTotalLowMenus(MenuReq menuReq) throws Exception;
	public List<LowMenu> getLowMenus(MenuReq menuReq) throws Exception;
}