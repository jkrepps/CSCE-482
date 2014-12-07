package com.seniorproject.dao;

public class DaoException extends Exception {

	private String message = null;
	
	public DaoException() {
		super();
	}
	
	public DaoException(String message) {
		super(message);
		this.message = message;
	}
	
	public DaoException (Throwable cause) {
		super(cause);
	}
	
	@Override
	public String toString() {
		return message;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
}
