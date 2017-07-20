package com.excilys.cdb.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan("com.excilys.cdb.service")
@Import(PersistenceConfig.class)
public class ServiceConfig {

}