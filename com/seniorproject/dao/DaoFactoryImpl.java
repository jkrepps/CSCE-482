package com.seniorproject.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DaoFactoryImpl implements DaoFactory {
	
	private static Connection connect = null;
	private Statement statement = null;
	
	public static boolean initialize(String url, String username, String pass) throws Exception {
		try {
			String jdbcUrl = "jdbc:mysql://" + url + "?user=" + username + "&password=" + pass;
			// Loading MySQL driver
			Class.forName("com.mysql.jdbc.Driver");
			// Setting up connection with the DB
			connect = DriverManager.getConnection(jdbcUrl);
			return true;
		}
		catch (Exception e) {
			System.err.println("Connection to database failed with error message: " + e.getMessage());
			throw e;
		}
	}

}
