package com.excilys.cdb.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement
@ComponentScan({"com.excilys.cdb.service", "com.excilys.cdb.persistence"})
public class CLIConfig extends WebMvcConfigurerAdapter {

	private static final Logger logger = LoggerFactory.getLogger(CLIConfig.class);

	@Bean
	public HikariDataSource dataBaseConnector() {
		logger.info("Initializing dataSource");
		return new HikariDataSource(new HikariConfig("/hikari.properties"));
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		logger.info("Initializing data source");
		return new DataSourceTransactionManager(dataBaseConnector());
	}
}
