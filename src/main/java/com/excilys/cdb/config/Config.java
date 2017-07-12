package com.excilys.cdb.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan("com.excilys.cdb")
public class Config extends WebMvcConfigurerAdapter {

	private static final Logger logger = LoggerFactory.getLogger(Config.class);

	@Bean
	public HikariDataSource dataBaseConnector() {
		logger.info("Initializing dataSource");
		return new HikariDataSource(new HikariConfig("/hikari.properties"));
	}

	@Bean
	public ViewResolver internalRessourceViewResolver() {
		logger.info("Initializing view resolver");
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setViewClass(JstlView.class);
		resolver.setSuffix(".jsp");
		return resolver;
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		logger.info("Initializing data source");
		return new DataSourceTransactionManager(dataBaseConnector());
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		logger.info("Initializing resource handlers");
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		logger.info("Initializing view controllers");
		registry.addRedirectViewController("/", "/dashboard");
	}
}
