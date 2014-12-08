package com.seniorproject.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.seniorproject.resource.MarketResource;
import com.seniorproject.resource.Resource;

public class MarketDao extends DaoObject {
	
	public MarketDao(Connection connection) {
		this.connection = connection;
	}
	
	public MarketResource createListing(Resource resource, int quantity, String playername, int sellerId, int gameId) throws DaoException {
		String insertQuery = "INSERT INTO Market VALUES(0," + resource.getResouceId() +", '" + resource.getResourceName() + "', '"
				+ resource.getResourceClass() + "'," +  resource.getResourcePrice() +", '" + playername +"', " + quantity + "," + sellerId + "," + gameId + ";";

		String selectQuery = "SELECT max(id) from Market;";
		MarketResource retval = new MarketResource();
		
		try {
			this.executeUpdate(insertQuery);
			ResultSet rs = this.executeSelect(selectQuery);
			
			if(rs.next())
				retval = new MarketResource(resource, rs.getInt(1), quantity, playername, sellerId, gameId);
			
		}	catch (Exception e) {
			throw new DaoException("Creating market listing has failed with: " + e.getMessage());
		}
		
		return retval;
		
		
	}
	
	public List<MarketResource> getAllMarketResources(int gameId) throws DaoException {
		String selectQuery = "SELECT * FROM Market where game_id=" + gameId + ";";
		List<MarketResource> retval = new ArrayList<MarketResource>();
		
		try{
			ResultSet rs = this.executeSelect(selectQuery);
			
			while(rs.next()) {
				retval.add(new MarketResource(new Resource(rs.getInt(2), rs.getString(3), rs.getString(4), rs.getFloat(5)), rs.getInt(1), rs.getInt(8), rs.getString(6), rs.getInt(7), rs.getInt(9)));
				
			}
			
		} catch (SQLException e) {
			throw new DaoException("Call to get all items on the market failed with: " + e.getMessage());
		}
		
		return retval;
	}
	
	public MarketResource getResource(int id) throws DaoException {
		String selectQuery = "SELECT * from Market where id=" + id +";";
		MarketResource retval = new MarketResource();
		
		try {
			ResultSet rs = this.executeSelect(selectQuery);
			if(rs.next())
				return new MarketResource(new Resource(rs.getInt(2), rs.getString(3), rs.getString(4), rs.getFloat(5)), rs.getInt(1), rs.getInt(8), rs.getString(6), rs.getInt(7), rs.getInt(9));
		} catch(SQLException e) {
			throw new DaoException("Call to get item quantities on the market failed with: " + e.getMessage());
		}
		
		return retval;
	}
	
	public int updateQuantity (int id, int quantity) throws DaoException {
		String updateQuery = "UPDATE Market SET quantity=" + quantity +" WHERE id=" + id +";";
		int retval = -1;
		
		try {
			retval = this.executeUpdate(updateQuery);
		} catch (Exception e) {
			throw new DaoException("Updating quantites on Market has failed with: " + e.getMessage());
		}
		return retval;
	}
	
	
	public int removeListing (int id) throws DaoException {
		String deleteQuery = "DELETE FROM Market WHERE id=" + id +";";
		int retval = -1;
		
		try {
			retval = this.executeUpdate(deleteQuery);
		} catch (Exception e) {
			throw new DaoException("Updating quantites on Market has failed with: " + e.getMessage());
		}
		return retval;
		
	}
	
}
