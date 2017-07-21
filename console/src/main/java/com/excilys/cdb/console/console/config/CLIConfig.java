package com.excilys.cdb.console.console.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.excilys.cdb.service.config.ServiceConfig;


@Configuration
@EnableTransactionManagement
@Import(ServiceConfig.class)
public class CLIConfig {
	
}
