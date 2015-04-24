package kr.co.leem.repositories.jpa;

import kr.co.leem.domains.account.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator on 2015-03-13.
 */
public interface AccountRepository extends JpaRepository<Account, Long> {
	public Account findByAccountId(String account);
	public Page<Account> findByAccountIdContainingOrNameContainingOrTelNumContainingOrHpNumContainingOrAddressContainingOrAddrDetailContainingOrPostNumContaining(Pageable pageable, String account, String accountName, String TelNum, String hpNum, String address, String addrDetail, String postNum);
	public void deleteByAccountId(String account);
}