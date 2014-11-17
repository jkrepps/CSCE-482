package com.seniorproject.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DaoObject {

	public DaoObject() { connection = null; }
	public DaoObject(DaoObject dao) { connection = dao.getConnection(); }
	
	protected Connection connection = null;
	
	public Connection getConnection () { return connection; }
	
	public boolean initialize (String url, String username, String pass) throws Exception {
		try {
			String jdbcUrl = "jdbc:mysql://" + url + "?user=" + username + "&password=" + pass;
			// Loading MySQL driver
			Class.forName("com.mysql.jdbc.Driver");
			// Setting up connection with the DB
			connection = DriverManager.getConnection(jdbcUrl);
			return true;
		}
		catch (Exception e) {
			System.err.println("Connection to database failed with error message: " + e.getMessage());
			throw e;
		}
	}
	
	public ResultSet executeSelect(String statement) throws DaoException {
		ResultSet resultSet = null;
		if (connection == null)
		System.out.println("Execute Select connection is null\n");
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(statement);
			resultSet = preparedStatement.executeQuery();
		} catch (SQLException e) {
			throw new DaoException("Executing select failed with: "+e.getMessage());
		}
		
		return resultSet;
	}
	
	public int executeUpdate (String statement ) throws DaoException {
		if (connection == null)
		System.out.println("Execute Update connection is null\n");
		try {
			System.out.println(statement);
			PreparedStatement preparedStatement = connection.prepareStatement(statement);
			return preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("Executing update failed with: " + e.getMessage());
		}
	}
	
	public int executeUpdate (PreparedStatement preparedStatement) throws DaoException {
		try {
			return preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("Executing update failed with: " + e.getMessage());
		}
	}
	
	public int executeDelete (String statement ) throws DaoException {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(statement);
			return preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("Executing delete failed with: " + e.getMessage());
		}
	}
	
	public void close() throws DaoException {
		try {
			connection.close();
		} catch (SQLException e) {
			throw new DaoException ("Closing database connection failed with: " + e.getMessage());
		}
		
	}
	
}
