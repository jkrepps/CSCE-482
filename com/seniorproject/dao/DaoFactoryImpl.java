package com.seniorproject.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DaoFactoryImpl implements DaoFactory {
	
	private Connection connect = null;
	private Statement statement = null;
	
	private String loadConnectionString () {
		// load String from environment variables and return
		// Connection String contains confidential data
		return new String();
	}
	
	public boolean initialize() throws Exception {
		try {
			// Loading MySQL driver
			Class.forName("com.mysql.jdbc.Driver");
			// Setting up connection with the DB
			connect = DriverManager.getConnection(loadConnectionString());
			return true;
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
			throw e;
		}
	}

}
