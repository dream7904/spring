package kr.co.leem.libs.security.services;

import kr.co.leem.domains.account.Account;
import kr.co.leem.repositories.jpa.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Administrator on 2015-01-13.
 */
@Service
public class UserDetailService implements org.springframework.security.core.userdetails.UserDetailsService {
	@Autowired private AccountRepository accountRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account account = accountRepository.findByAccountId(username);
		
		String id = account.getAccountId();
		String pwd = account.getPassword();
		
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

		org.springframework.security.core.userdetails.User customUserDetail = new org.springframework.security.core.userdetails.User(id, pwd, authorities);
		
		return customUserDetail;
	}
}