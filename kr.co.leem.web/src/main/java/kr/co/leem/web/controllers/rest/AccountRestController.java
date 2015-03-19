package kr.co.leem.web.controllers.rest;

import kr.co.leem.constants.ResultType;
import kr.co.leem.constants.ResultValue;
import kr.co.leem.domains.account.Account;
import kr.co.leem.domains.account.AccountReq;
import kr.co.leem.web.services.AccountService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015-03-18.
 */
@RestController
@RequestMapping(value = "rest/account")
public class AccountRestController {
	@Autowired private AccountService accountService;

	@RequestMapping(value = "getAccounts")
	public Map<ResultType, Object> getAccounts(AccountReq accountReq) throws Exception {
		Map<ResultType, Object> resultMap = new HashMap<ResultType, Object>();

		accountService.getAccounts(accountReq, resultMap);

		return resultMap;
	}

	@RequestMapping(value = "getAccount")
	public Map<ResultType, Object> getAccount(@RequestBody AccountReq accountReq) throws Exception {
		Map<ResultType, Object> resultMap = new HashMap<ResultType, Object>();

		accountService.getAccount(accountReq, resultMap);

		resultMap.put(ResultType.resultCode, ResultValue.success);

		return resultMap;
	}

	@RequestMapping(value = "saveAccount")
	 public Map<ResultType, Object> saveAccount(Account account) throws Exception {
		Map<ResultType, Object> resultMap = new HashMap<ResultType, Object>();

		accountService.saveAccount(account, resultMap);

		resultMap.put(ResultType.resultCode, ResultValue.success);

		return resultMap;
	}

	@RequestMapping(value = "delAccount")
	public Map<ResultType, Object> delAccount(@RequestBody AccountReq accountReq) throws Exception {
		Map<ResultType, Object> resultMap = new HashMap<ResultType, Object>();

		if (StringUtils.equalsIgnoreCase(accountReq.getAccountId(), "admin")) {
			resultMap.put(ResultType.resultCode, ResultValue.isSystemAdministrator);
			return resultMap;
		}

		accountService.delAccount(accountReq, resultMap);

		resultMap.put(ResultType.resultCode, ResultValue.success);

		return resultMap;
	}
}
