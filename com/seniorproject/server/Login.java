package com.seniorproject.server;

import com.seniorproject.dao.DaoException;
import com.seniorproject.dao.UserDao;

public class Login {
	
	private static PasswordService encryptor = null;
	
	public static String UserLogin (String username, String password) throws ServerException {
		try {
			encryptor = new PasswordService();
			String postHashPassword = encryptor.encrypt(password);
			
			boolean userExists = UserDao.CheckUser(username);
			
			
			if ( userExists) {
				if (UserDao.CheckPassword(username, postHashPassword)) {
					System.out.println("Successfully Logged In");
					return "Relog";
				}
				
				else {
					return "Incorrect log in";
				}
			}
			
			else {
				UserDao.RegisterUser(username, postHashPassword);
				System.out.println("Registered user: " + username);
				return "NewUser";
			}		
		}
		catch (DaoException e) {
			throw new ServerException("LogIn failed with message: "+e.getMessage());
		}
		
	}
	
	public boolean CheckExistence (String username, String password) throws ServerException {
		try {
			String postHashPassword = encryptor.encrypt(password);
		}
		catch (ServerException e) {
			throw new ServerException("Encryption failed during checking existence with: "+e.getMessage());
		}
		
		//TODO: Check db for user
		
		return true;
	}

}
