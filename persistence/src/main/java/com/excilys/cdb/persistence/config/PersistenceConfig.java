package com.excilys.cdb.persistence.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * Scanning necessary package & initializing hikari for Spring Configuration
 * @author Hugo Descamps
 *
 */

@Configuration
@EnableTransactionManagement
@ComponentScan("com.excilys.cdb.persistence")
public class PersistenceConfig {

	private static final Logger logger = LoggerFactory.getLogger(PersistenceConfig.class);

	/**
	 * Initializes Hikari database connector from hikari.properties files (resources folder)
	 * @return
	 */
	
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
