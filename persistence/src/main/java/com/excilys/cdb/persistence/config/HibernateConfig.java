package com.excilys.cdb.persistence.config;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.core.Company;
import com.excilys.cdb.core.Computer;

public class HibernateConfig {

	private static final Logger logger = LoggerFactory.getLogger(HibernateConfig.class);
	
	private static SessionFactory sessionFactory;

	public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = buildSessionFactory();
        }
        return sessionFactory;
    }

	private static SessionFactory buildSessionFactory() {
		try {
			Configuration config = new Configuration();
			config.configure();
			config.addAnnotatedClass(Company.class);
			config.addAnnotatedClass(Computer.class);
			
			
			ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();
			
			SessionFactory sessionFactory = config.buildSessionFactory(serviceRegistry);

			return sessionFactory;

		} catch (Throwable e) {
			logger.info("Could not create SessionFactory " + e);
			throw new ExceptionInInitializerError(e);
		}
	}

	public static void shutdown() {
		getSessionFactory().close();
	}
}