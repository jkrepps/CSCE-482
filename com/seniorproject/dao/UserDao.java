package com.seniorproject.dao;

import java.sql.ResultSet;

public class UserDao extends DaoObject  {

	
	public static boolean CheckPassword(String username, String postHashPassword) throws DaoException {
		// Establish Connection
		ResultSet resultSet;
		String selectQuery = "SELECT COUNT(*) AS rowcount FROM User where hashed_pass='"
				+ postHashPassword + "' AND username='" + username +"';";
		
		try {
			resultSet = ExecuteSelect(selectQuery);
			if (resultSet.getInt("rowcount")> 0)
				return true;
		} catch (Exception e) {
			throw new DaoException("Checking password failed with: "+ e.getMessage());
		}

		return false;	
	}
	
	public static int RegisterUser (String username, String postHashPassword) throws DaoException {
		
		String insertQuery = "INSERT INTO User (username, hashed_password) VALUES ('"
				+ username + "', '" + postHashPassword + "');";
		try {
			return ExecuteUpdate(insertQuery);
		} catch (Exception e) {
			throw new DaoException("Logging in has failed with: "+ e.getMessage());
		}
	}
}
