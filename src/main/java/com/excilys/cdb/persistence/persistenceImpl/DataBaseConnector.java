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


	@SuppressWarnings("resource")
	public static Connection connect() {

		Connection connection;

		try {

			HikariConfig hikariConfig = new HikariConfig("/hikari.properties");
			HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);

			connection = hikariDataSource.getConnection();
		} catch (SQLException e) {
			throw new DaoException("Cannot establish database connection " + e.getMessage());
		}

		logger.info("DB connection established");

		return connection;

	}
}
