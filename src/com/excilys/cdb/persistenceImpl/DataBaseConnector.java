package com.excilys.cdb.persistenceImpl;

import java.sql.DriverManager;

import com.mysql.jdbc.Connection;

public class DataBaseConnector {

	private static String url = "jdbc:mysql://localhost:3306/computer-database-db?zeroDateTimeBehavior=convertToNull";
	private static String user = "root";
	private static String password = "";
	private static Connection connection = null;

	public static Connection connect() {

		try {
			Class.forName("com.mysql.jdbc.Driver");

			connection = (Connection) DriverManager.getConnection(url, user, password);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("DB connection could not be established");
		}

		System.out.println("DB connection established");
		
		return connection;

	}
}
