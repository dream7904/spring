package kr.co.leem.web.services;

import kr.co.leem.constants.ResultType;
import kr.co.leem.domains.account.Account;
import kr.co.leem.domains.account.AccountReq;

import java.util.Map;

/**
 * Created by Administrator on 2015-03-13.
 */
public interface AccountService {
	public void getAccounts(AccountReq accountReq, Map<ResultType, Object> resultMap) throws Exception;
	public void getAccount(AccountReq accountReq, Map<ResultType, Object> resultMap) throws Exception;
	public void saveAccount(Account account, Map<ResultType, Object> resultMap) throws Exception;
	public void delAccount(AccountReq accountReq, Map<ResultType, Object> resultMap) throws Exception;
	public void setDefaultAccount(Map<ResultType, Object> resultMap) throws Exception;
}