package kr.co.leem.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by Administrator on 2015-04-08.
 */
@Configuration
@ComponentScan(basePackages = "kr.co.leem")
@PropertySources(value = {@PropertySource("classpath:properties/db.properties")})
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "kr.co.leem.repositories.jpa")
public class AppConfig {
	@Autowired
	private Environment environment;

	@Bean(name = "messageSource")
	public ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();

		messageSource.setBasenames("classpath:properties/env");
		messageSource.setDefaultEncoding("UTF-8");

		return messageSource;
	}

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();

		dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
		dataSource.setUrl(environment.getRequiredProperty("jdbc.url"));
		dataSource.setUsername(environment.getRequiredProperty("jdbc.username"));
		dataSource.setPassword(environment.getRequiredProperty("jdbc.password"));

		return dataSource;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();

		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();

		vendorAdapter.setDatabase(Database.MYSQL);
		vendorAdapter.setGenerateDdl(true);
		vendorAdapter.setShowSql(true);

		entityManagerFactoryBean.setDataSource(dataSource());
		entityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);
		entityManagerFactoryBean.setPersistenceUnitName("DefaultGenesis");

		entityManagerFactoryBean.setJpaProperties(hibProperties());

		return entityManagerFactoryBean;
	}

	@Bean
	public JpaTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

		return transactionManager;
	}

	private Properties hibProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
		properties.put("database.mysql.engine", "InnoDB");

		return properties;
	}
}
