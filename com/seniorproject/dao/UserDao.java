package com.seniorproject.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.seniorproject.game.Game;

public class UserDao extends DaoObject  {

	public UserDao(Connection connection) {
		this.connection = connection;
	}
	
	public boolean CheckPassword(String username, String postHashPassword) throws DaoException {
		// Establish Connection
		ResultSet resultSet;
		String selectQuery = "SELECT COUNT(*) as rowcount FROM User where hashed_password='"
				+ postHashPassword + "' AND username='" + username +"';";
		
		try {
			resultSet = this.executeSelect(selectQuery);
			// Counting Results
			resultSet.next();
			
			if (resultSet.getInt(1)> 0)
				return true;
		} catch (Exception e) {
			throw new DaoException("Checking password failed with: "+ e.getMessage());
		}

		return false;	
	}
	
	public boolean CheckUser(String username) throws DaoException {
		// Establish Connection
		ResultSet resultSet;
		String selectQuery = "SELECT COUNT(*) as rowcount FROM User where username='" + username +"';";
		
		try {
			resultSet = this.executeSelect(selectQuery);
			// Counting Results
			resultSet.next();
			
			if (resultSet.getInt(1)> 0)
				return true;
		} catch (Exception e) {
			throw new DaoException("Checking password failed with: "+ e.getMessage());
		}

		return false;	
	}
	
	public int RegisterUser (String username, String postHashPassword) throws DaoException {
		
		String insertQuery = "INSERT INTO User (username, hashed_password) VALUES ('"
				+ username + "', '" + postHashPassword + "');";
		try {
			return this.executeUpdate(insertQuery);
		} catch (Exception e) {
			throw new DaoException("Logging in has failed with: "+ e.getMessage());
		}
	}
	


}
