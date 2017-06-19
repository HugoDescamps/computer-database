package com.excilys.cdb.persistence.persistenceImpl;

import java.io.FileInputStream;
import java.sql.DriverManager;
import java.util.Properties;

import com.excilys.cdb.persistence.MyException;
import com.mysql.jdbc.Connection;

public class DataBaseConnector {

	private static Properties properties = new Properties();
	private static FileInputStream propertiesFile = null;
	private static Connection connection = null;
	
	private DataBaseConnector() {
		
	}

	public static Connection connect() {

		try {
			propertiesFile = new FileInputStream("src/resources/db.properties");
			properties.load(propertiesFile);

			Class.forName(properties.getProperty("DB_DRIVER_CLASS"));

			connection = (Connection) DriverManager.getConnection(properties.getProperty("DB_URL"),
					properties.getProperty("DB_USERNAME"), properties.getProperty("DB_PASSWORD"));
			
			System.out.println("DB connection established");

		} catch (Exception e) {
			e.printStackTrace();
			throw new MyException("Database connection error in connect method");
		}

		return connection;

	}
}
