package com.excilys.cdb.persistence.persistenceImpl;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.persistence.DaoException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DataBaseConnector {

	static final Logger logger = LoggerFactory.getLogger(DataBaseConnector.class);
	private static HikariDataSource hikariDataSource;
	private static HikariConfig hikariConfig;

	public static Connection connect() {

		Connection connection;

		try {
			hikariConfig = new HikariConfig("/hikari.properties");
			hikariDataSource = new HikariDataSource(hikariConfig);

			connection = hikariDataSource.getConnection();
		} catch (SQLException e) {
			throw new DaoException("Cannot establish database connection " + e.getMessage());
		} finally {
			
		}

		logger.info("DB connection established");

		return connection;

	}
}
