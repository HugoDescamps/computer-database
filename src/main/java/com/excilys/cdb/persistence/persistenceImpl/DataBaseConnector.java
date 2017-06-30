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
	public static Connection connection;
	private static HikariDataSource hikariDataSource;
	private static HikariConfig hikariConfig;
	private static ThreadLocal<Connection> connectionThread = new ThreadLocal<>();

	public static Connection connect() {

		try {
			hikariConfig = new HikariConfig("/hikari.properties");
			hikariDataSource = new HikariDataSource(hikariConfig);

			connection = hikariDataSource.getConnection();
			connectionThread.set(connection);
		} catch (SQLException e) {
			throw new DaoException("Cannot establish database connection thread " + e.getMessage());
		}

		logger.info("DB connection thread established");

		return connectionThread.get();

	}

	public static void close() {

		if (connectionThread.get() != null) {
			try {
				connectionThread.get().close();
				connectionThread.remove();
			} catch (SQLException e) {
				throw new DaoException("Cannot close database connection thread " + e.getMessage());
			}
		}
		logger.info("DB connection thread closed");
	}
}
