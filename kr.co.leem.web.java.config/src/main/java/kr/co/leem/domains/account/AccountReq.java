package kr.co.leem.domains.account;

import kr.co.leem.libs.jpa.params.PageParams;

/**
 * Created by Administrator on 2015-03-18.
 */
public class AccountReq extends PageParams {
	private String accountId;
	private String name;

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}