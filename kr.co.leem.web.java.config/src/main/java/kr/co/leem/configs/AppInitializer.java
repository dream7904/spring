package kr.co.leem.configs;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * Created by Administrator on 2015-04-08.
 */
public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
//	public void onStartup(ServletContext container) throws ServletException {
//
//		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
//		rootContext.register(AppConfig.class);
//
//		// Manage the lifecycle of the root application context
//		container.addListener(new ContextLoaderListener(rootContext));
//		container.addListener(new HttpSessionEventPublisher());
//
//		// Create the dispatcher servlet's Spring application context
//		AnnotationConfigWebApplicationContext dispatcherServlet = new AnnotationConfigWebApplicationContext();
//		dispatcherServlet.register(MvcConfig.class);
//
//		ServletRegistration.Dynamic dispatcher = container.addServlet("dispatcher", new DispatcherServlet(dispatcherServlet));
//		dispatcher.setLoadOnStartup(1);
//		dispatcher.addMapping("/");
//		dispatcher.setAsyncSupported(true);
//
//		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
//
//		characterEncodingFilter.setEncoding("UTF-8");
//		characterEncodingFilter.setForceEncoding(true);
//
//		container.addFilter("encodingFilter", characterEncodingFilter);
//
//		HiddenHttpMethodFilter hiddenHttpMethodFilter = new HiddenHttpMethodFilter();
//
//		container.addFilter("httpMethodFilter", hiddenHttpMethodFilter).addMappingForUrlPatterns(null, true, "/*");
//
//		DelegatingFilterProxy delegatingFilterProxy = new DelegatingFilterProxy();
//
//		container.addFilter("springSecurityFilterChain", delegatingFilterProxy).addMappingForUrlPatterns(null, true, "/*");
//	}
//
//	@Override
//	protected Filter[] getServletFilters() {
//		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
//		characterEncodingFilter.setEncoding("UTF-8");
//		characterEncodingFilter.setForceEncoding(true);
//
//		DelegatingFilterProxy securityFilterChain = new DelegatingFilterProxy("springSecurityFilterChain");
//
//		return new Filter[] {characterEncodingFilter, securityFilterChain};
//	}

	@Override
	protected String[] getServletMappings() {
		return new String[]{"/"};
	}

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] {AppConfig.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] {MvcConfig.class};
	}

	@Override
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();

		characterEncodingFilter.setEncoding("UTF-8");
		characterEncodingFilter.setForceEncoding(true);

		DelegatingFilterProxy securityFilterChain = new DelegatingFilterProxy("springSecurityFilterChain");

		return new Filter[] {characterEncodingFilter, securityFilterChain};
	}
}

