package com.excilys.cdb.service.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.excilys.cdb.persistence.config.PersistenceConfig;

/**
 * Scanning necessary package for Spring Configuration
 * @author Hugo Descamps
 *
 */

@Configuration
@ComponentScan("com.excilys.cdb.service")
@Import(PersistenceConfig.class)
public class ServiceConfig {

}