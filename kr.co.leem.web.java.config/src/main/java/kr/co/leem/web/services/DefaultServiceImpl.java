package kr.co.leem.web.services;

import kr.co.leem.domains.account.Account;
import kr.co.leem.repositories.jpa.jpa.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by Administrator on 2015-04-23.
 */
@Service
@Transactional(readOnly = true)
public class DefaultServiceImpl implements DefaultService {
	@Autowired
	private AccountRepository accountRepository;

	@Override
	@Transactional(readOnly = false)
	public void regTest() throws Exception {
		Account account = new Account();
		account.setAccountId("test1");
		account.setHpNum("010-1111-1111");
		account.setName("test");
		account.setPassword("asdf1234");
		account.setRegDate(new Date());

		accountRepository.save(account);

		account.setAccountId(null);
		accountRepository.save(account);
	}
}
