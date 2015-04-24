package kr.co.leem.configs;

import kr.co.leem.libs.security.services.UserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.security.web.authentication.session.*;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

import java.util.ArrayList;
import java.util.List;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Bean(name = "authenticationManager")
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return this.authenticationManager();
	}

	@Bean
	public SecurityContextRepository securityContextRepository() {
		HttpSessionSecurityContextRepository repo = new HttpSessionSecurityContextRepository();

		return repo;
	}

	@Bean
	public UserDetailService userDetailService() {
		UserDetailService userDetailService = new UserDetailService();

		return userDetailService;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

		return bCryptPasswordEncoder;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.eraseCredentials(true)
			.userDetailsService(this.userDetailService())
			.passwordEncoder(passwordEncoder());
	}

	@Bean
	SimpleUrlLogoutSuccessHandler logoutSuccessHandler() {
		SimpleUrlLogoutSuccessHandler simpleUrlLogoutSuccessHandler = new SimpleUrlLogoutSuccessHandler();

		simpleUrlLogoutSuccessHandler.setDefaultTargetUrl("/");
		simpleUrlLogoutSuccessHandler.setAlwaysUseDefaultTargetUrl(true);

		return simpleUrlLogoutSuccessHandler;
	}

	@Bean
	public SessionRegistry sessionRegistry() {
		SessionRegistry sessionRegistry = new SessionRegistryImpl();

		return sessionRegistry;
	}

	@Bean
	public CompositeSessionAuthenticationStrategy sas() {
		ConcurrentSessionControlAuthenticationStrategy concurrentSessionControlAuthenticationStrategy = new ConcurrentSessionControlAuthenticationStrategy(sessionRegistry());

		concurrentSessionControlAuthenticationStrategy.setMaximumSessions(1);
		concurrentSessionControlAuthenticationStrategy.setExceptionIfMaximumExceeded(true);

		SessionFixationProtectionStrategy sessionFixationProtectionStrategy = new SessionFixationProtectionStrategy();

		RegisterSessionAuthenticationStrategy sessionAuthenticationStrategy = new RegisterSessionAuthenticationStrategy(sessionRegistry());

		List<SessionAuthenticationStrategy> delegateStrategies = new ArrayList<SessionAuthenticationStrategy>();

		delegateStrategies.add(concurrentSessionControlAuthenticationStrategy);
		delegateStrategies.add(sessionFixationProtectionStrategy);
		delegateStrategies.add(sessionAuthenticationStrategy);

		CompositeSessionAuthenticationStrategy compositeSessionAuthenticationStrategy = new CompositeSessionAuthenticationStrategy(delegateStrategies);

		return compositeSessionAuthenticationStrategy;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
			.antMatchers("/rest/**", "/favicon.ico", "/resources/**", "/login").permitAll()
			.anyRequest().authenticated()
			.and()
			.sessionManagement().sessionAuthenticationStrategy(sas()).and()
			.formLogin()
				.loginPage("/login")
				.permitAll()
				.failureUrl("/login")
			.and()
			.logout()
				.logoutUrl("/logout")
				.permitAll()
				.logoutSuccessHandler(logoutSuccessHandler())
			.and()
				.csrf()
			.and()
				.headers()
					.cacheControl()
					.and()
					.frameOptions()
					.and()
					.xssProtection()
					.and()
					.httpStrictTransportSecurity()
					.and()
					.contentTypeOptions()
					.and()
					.xssProtection();
	}
}