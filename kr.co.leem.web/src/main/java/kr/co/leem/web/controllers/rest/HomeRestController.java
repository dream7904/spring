package kr.co.leem.web.controllers.rest;

import kr.co.leem.constants.ResultType;
import kr.co.leem.constants.ResultValue;
import kr.co.leem.domains.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015-03-13.
 */
@RestController
@RequestMapping(value = "home/rest")
public class HomeRestController {
	@Autowired
	@Qualifier("authenticationManager") private AuthenticationManager authenticationManager;
	@Autowired private SecurityContextRepository securityContextRepository;

	@RequestMapping(value = "login")
	public Map<String, Object> login(HttpServletRequest request, HttpServletResponse response, User user) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getAccount(), user.getPassword());
			Authentication auth = authenticationManager.authenticate(token);

			SecurityContextHolder.getContext().setAuthentication(auth);
			securityContextRepository.saveContext(SecurityContextHolder.getContext(), request, response);

			resultMap.put(ResultType.resultCode.toString(), ResultValue.success.toString());
		} catch (Exception e) {
			resultMap.put(ResultType.resultCode.toString(), ResultValue.fail.toString());
			e.printStackTrace();
		}

		return resultMap;
	}

	@RequestMapping(value = "logout")
	public Map<String, Object> logout(HttpSession session, User user) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			session.invalidate();
			resultMap.put(ResultType.resultCode.toString(), ResultValue.success.toString());
		} catch (Exception e) {
			resultMap.put(ResultType.resultCode.toString(), ResultValue.fail.toString());
			e.printStackTrace();
		}

		return resultMap;
	}
}
