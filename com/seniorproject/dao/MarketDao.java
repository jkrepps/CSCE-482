package com.seniorproject.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.seniorproject.resource.Resource;

public class MarketDao extends DaoObject {
	
	public static int sellResource(Resource resource, String seller, int quantity, Float pricePerUnit) throws DaoException {
		String resourceName = resource.getName();
		String resourceType = resource.getType();
		
		String insertQuery = "INSERT INTO Market VALUES('" + resourceName + "', '" + resourceType + "', '"
				+ seller + "', " + Integer.toString(quantity) + ", " + Float.toString(pricePerUnit) + ");";
		
		try {
			return executeUpdate(insertQuery);
		} catch (Exception e) {
			throw new DaoException("Adding Resource to Market has failed with: " + e.getMessage());
		}
		
 	}
	
	public static List<Resource> getMarketResources() throws DaoException {
		String selectQuery = "SELECT * FROM Market;";
		List<Resource> returnList = new ArrayList<Resource>();
		
		try {
			ResultSet resultSet = executeSelect(selectQuery);
			
			while(resultSet.next()) {
				Resource temp = new Resource(resultSet.getString(1), resultSet.getString(2), resultSet.getFloat(5));
				returnList.add(temp);
			}
		} catch (Exception e) {
			throw new DaoException("Call to get Market Resources failed with: " + e.getMessage());
		}
		
		return returnList;
	}
	
	
	// TODO
	public static int buyResource(String buyer, Resource resource, int quantity) {
		return 0;
	}
}
