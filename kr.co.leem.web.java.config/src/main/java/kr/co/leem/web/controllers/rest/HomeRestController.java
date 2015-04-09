package kr.co.leem.web.controllers.rest;

import kr.co.leem.commons.constants.ResultType;
import kr.co.leem.commons.constants.ResultValue;
import kr.co.leem.domains.account.Account;
import kr.co.leem.web.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
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
 * Created by Administrator on 2015-04-08.
 */
@RestController
@RequestMapping(value = "rest/home")
public class HomeRestController {
	@Autowired
	private MessageSource messageSource;
	@Autowired
	@Qualifier("authenticationManager")
	private AuthenticationManager authenticationManager;
	@Autowired
	private SecurityContextRepository securityContextRepository;
	@Autowired
	private AccountService accountService;

	@RequestMapping(value = "getDefault")
	public Map<String, Object> getDefault() throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		resultMap.put("test", "1234");

		return resultMap;
	}

	@RequestMapping(value = "setDefaultAccount")
	public Map<ResultType, Object> setDefault() throws Exception {
		Map<ResultType, Object> resultMap = new HashMap<ResultType, Object>();

		resultMap.put(ResultType.resultCode, ResultValue.success);

		return resultMap;
	}

	@RequestMapping(value = "login")
	public Map<ResultType, Object> login(HttpServletRequest request, HttpServletResponse response, Account account) throws Exception {
		Map<ResultType, Object> resultMap = new HashMap<ResultType, Object>();

		try {
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(account.getAccountId(), account.getPassword());

			Authentication auth = authenticationManager.authenticate(token);
			SecurityContextHolder.getContext().setAuthentication(auth);

			securityContextRepository.saveContext(SecurityContextHolder.getContext(), request, response);

			resultMap.put(ResultType.resultCode, ResultValue.success);
		} catch (Exception e) {
			resultMap.put(ResultType.resultCode, ResultValue.fail);
			e.printStackTrace();
		}

		return resultMap;
	}

	@RequestMapping(value = "logout")
	public Map<ResultType, Object> logout(HttpSession session, Account account) {
		Map<ResultType, Object> resultMap = new HashMap<ResultType, Object>();

		try {
			session.invalidate();
			resultMap.put(ResultType.resultCode, ResultValue.success);
		} catch (Exception e) {
			resultMap.put(ResultType.resultCode, ResultValue.fail);
			e.printStackTrace();
		}

		return resultMap;
	}
}