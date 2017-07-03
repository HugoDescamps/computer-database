package com.excilys.cdb.persistence.persistenceImpl;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.persistence.DaoException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public enum DataBaseConnector {
	INSTANCE;

	private static final Logger logger = LoggerFactory.getLogger(DataBaseConnector.class);
	private HikariDataSource hikariDataSource;
	private static ThreadLocal<Connection> connectionThread = new ThreadLocal<>();

	private DataBaseConnector() {
		HikariConfig hikariConfig = new HikariConfig("/hikari.properties");
		hikariDataSource = new HikariDataSource(hikariConfig);
	}

	public Connection connect() {
		Connection connection = null;

		try {
			connection = hikariDataSource.getConnection();
			connectionThread.set(connection);
		} catch (SQLException e) {
			throw new DaoException("Cannot establish database connection thread " + e.getMessage());
		}

		logger.info("DB connection thread established");

		return connectionThread.get();

	}

	public void close() {

		if (connectionThread.get() != null) {
			try {
				if (!connectionThread.get().isClosed()) {
					connectionThread.get().close();
					connectionThread.remove();
				}
			} catch (SQLException e) {
				throw new DaoException("Cannot close database connection thread " + e.getMessage());
			}
		}
		logger.info("DB connection thread closed");
	}
}
