package com.excilys.cdb.persistence.persistenceImpl;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.persistence.MyException;

public class DataBaseConnector {

	private static Properties properties = new Properties();
	private static FileInputStream propertiesFile = null;
	private static Connection connection = null;

	static final Logger logger = LoggerFactory.getLogger(DataBaseConnector.class);

	private DataBaseConnector() {

	}

	public static Connection connect() throws IOException {

		try {
			propertiesFile = new FileInputStream("/home/excilys/workspace_cdb/cdb/src/main/resources/db.properties");
			properties.load(propertiesFile);

			Class.forName(properties.getProperty("DB_DRIVER_CLASS"));

			connection = (Connection) DriverManager.getConnection(properties.getProperty("DB_URL"),
					properties.getProperty("DB_USERNAME"), properties.getProperty("DB_PASSWORD"));

			logger.info("DB connection established");

		} catch (SQLException | ClassNotFoundException e) {
			throw new MyException("Database connection error in connect method");
		}

		return connection;

	}
}
