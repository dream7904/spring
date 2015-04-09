package kr.co.leem.configs;

import kr.co.leem.libs.security.services.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan(basePackages = "kr.co.leem")
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Bean(name="authenticationManager")
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

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.authorizeRequests()
				.antMatchers("/rest/**", "/favicon.ico", "/resources/**", "/login").permitAll()
				.anyRequest().authenticated()
				.and()
				.formLogin()
					.loginPage("/login")
					.permitAll()
					.failureUrl("/login")
				.and()
				.logout()
					.logoutUrl("/logout")
 				.permitAll()
				.logoutSuccessUrl("/");
	}

/*
* <http use-expressions="true">
		<csrf/>

		<headers>
			<cache-control/>
			<content-type-options/>
			<hsts/>
			<frame-options/>
			<xss-protection/>
		</headers>

		<intercept-url pattern="/ws/**" access="permitAll" />
		<intercept-url pattern="/resources/**" access="permitAll"/>

		<form-login login-page="/login" username-parameter="accountId" password-parameter="password"/>

		<logout logout-url="/logout" invalidate-session="true" success-handler-ref="logoutSuccessHandler" />
	</http>

	<beans:bean id="logoutSuccessHandler"
		class="org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler">
		<beans:property name="defaultTargetUrl" value="/" />
		<beans:property name="alwaysUseDefaultTargetUrl" value="true" />
	</beans:bean>

	<beans:bean id="userDetailsService" class="kr.co.leem.libs.security.UserDetailService"/>

	<beans:bean id="passwordEncoder" class="org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder"/>

	<authentication-manager alias="authenticationManager">
		<authentication-provider user-service-ref="userDetailsService">
			<password-encoder ref="passwordEncoder"/>
		</authentication-provider>
	</authentication-manager>*/
}