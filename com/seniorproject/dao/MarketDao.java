package com.seniorproject.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.seniorproject.resource.Resource;

public class MarketDao extends DaoObject {
	
	public static int sellResource(Resource resource, String seller, int quantity, Float pricePerUnit) throws DaoException {
		String resourceName = resource.getName();
		String resourceType = resource.getType();
		
		String insertQuery = "INSERT INTO Market(resource_name, resource_type, seller, quantity, price_per_unit) VALUES('" + resourceName + "', '" + resourceType + "', '"
				+ seller + "', " + Integer.toString(quantity) + ", " + Float.toString(pricePerUnit) + ");";
		
		try {
			return executeUpdate(insertQuery);
		} catch (Exception e) {
			throw new DaoException("Adding Resource to Market has failed with: " + e.getMessage());
		}
		
 	}
	
	// List of all resources on the market
	public static List<Resource> getMarketResources() throws DaoException {
		String selectQuery = "SELECT * FROM Market;";
		List<Resource> returnList = new ArrayList<Resource>();
		
		try {
			ResultSet resultSet = executeSelect(selectQuery);
			
			while(resultSet.next()) {
				Resource temp = new Resource(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getFloat(6));
				returnList.add(temp);
			}
		} catch (Exception e) {
			throw new DaoException("Call to get Market Resources failed with: " + e.getMessage());
		}
		
		return returnList;
	}
	
	
	// Buying resource (subtracting values from market) by resource_id as returned by getMarketResources()
	public static int buyResource(String buyer, int resourceId, int buyingQuantity) throws DaoException {
		
		String updateQuery = "UPDATE Market SET quantity = quantity-"+Integer.toString(buyingQuantity)
				+ " WHERE resource_id = " + Integer.toString(resourceId) + ";"; 
		
		try {
			return executeUpdate(updateQuery);
		} catch (Exception e) {
			throw new DaoException("Buying Resources has failed with: " + e.getMessage());
		} 
	}
}
