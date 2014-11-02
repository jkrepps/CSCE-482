package address.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DaoObject {

	private static Connection connection = null;
	
	public static boolean initialize (String url, String username, String pass) throws Exception {
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

	
	public static ResultSet executeSelect(String statement) throws DaoException {
		ResultSet resultSet = null;
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(statement);
			resultSet = preparedStatement.executeQuery();
		} catch (SQLException e) {
			throw new DaoException("Executing select failed with: "+e.getMessage());
		}
		
		return resultSet;
	}
	
	public static int executeUpdate (String statement ) throws DaoException {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(statement);
			return preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("Executing update failed with: " + e.getMessage());
		}
	}
	
	public static int executeDelete (String statement ) throws DaoException {
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
