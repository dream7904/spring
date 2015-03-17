package kr.co.leem.repositories.jpa;

import kr.co.leem.domains.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Administrator on 2015-03-13.
 */
public interface UserRepository  extends JpaRepository<User, Long> {
	public User findByAccount(String account);
}