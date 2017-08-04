package com.excilys.cdb.console.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.excilys.cdb.service.config.ServiceConfig;

/**
 * Scanning necessary package & importing necessary configuration for Spring Configuration
 * @author Hugo Descamps
 *
 */

@Configuration
@EnableTransactionManagement
@Import(ServiceConfig.class)
public class CLIConfig {
	
}
