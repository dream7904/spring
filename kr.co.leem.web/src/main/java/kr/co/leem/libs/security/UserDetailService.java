package kr.co.leem.libs.security;

import kr.co.leem.domains.User;
import kr.co.leem.repositories.jpa.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
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
	@Autowired private PasswordEncoder passwordEncoder;
	@Autowired private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByAccount(username);
		
		String id = user.getAccount();
		String pwd = user.getPassword();
		
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		org.springframework.security.core.userdetails.User customUserDetail = new org.springframework.security.core.userdetails.User(id, pwd, authorities);
		
		return customUserDetail;
	}
}
