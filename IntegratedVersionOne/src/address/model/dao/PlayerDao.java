package address.model.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import address.model.resource.*;

public class PlayerDao extends DaoObject {

	// Workers
	public static List<Resource> getWorkers(String playername) throws DaoException {
		String selectQuery = "SELECT * FROM Worker WHERE player='" + playername + "';";
		List<Resource> returnList = new ArrayList<Resource>();
		
		try {
			ResultSet resultSet = executeSelect(selectQuery);
			
			while (resultSet.next()) {
				Worker temp = new Worker(resultSet.getString(1), resultSet.getInt(2), resultSet.getDouble(4), resultSet.getFloat(5));
				returnList.add(temp);
			}
		} catch (Exception e) {
			throw new DaoException("Call to get Worker failed with:" + e.getMessage());
		}
		
		return returnList;
		
	}
	
	public static int addWorker(String playername, Worker worker) throws DaoException {
		String name = worker.getName();
		int quantity = worker.getQuantity();
		Double efficiency = worker.getEfficiency();
		Float wages = worker.getWages();
	
		
		String insertQuery = "INSERT INTO Worker VALUES('" + name	+ "', " + Integer.toString(quantity) + ", '" + playername
				+ "', " + Double.toString(efficiency) + ", " + Float.toString(wages) + ");";
		
		try {
			return executeUpdate(insertQuery);
		} catch (Exception e) {
			throw new DaoException("Adding Worker has failed with: " + e.getMessage());
		}
	}
	
	public static int updateWorker(String playername, Worker oldWorker, Worker newWorker) throws DaoException {
		String oldName = oldWorker.getName();
		
		String newName = newWorker.getName();
		int quantity = newWorker.getQuantity();
		Double efficiency = newWorker.getEfficiency();
		Float wages = newWorker.getWages();
		
		String updateQuery = "UPDATE Worker SET name='" + newName + "', quantity=" + Integer.toString(quantity) + ", player='"
				+ playername + "', efficiency=" + Double.toString(efficiency) + ", wages=" + Float.toString(wages) 
				+ " WHERE name='" + oldName + "' AND player='" + playername + "';"; 
		
		try {
			return executeUpdate(updateQuery);
		} catch (Exception e) {
			throw new DaoException("Updating Worker has failed with: " + e.getMessage());
		}
	}
	
	public static int removeWorker(String playername, Worker worker) throws DaoException {
		String name = worker.getName();
		
		String deleteQuery = "DELETE FROM Worker WHERE player='" + playername + "' AND name='" + name + "';";
		
		try {
			return executeDelete(deleteQuery);
		} catch (Exception e) {
			throw new DaoException ("Removing Worker failed with: " + e.getMessage()); 
		}
		
	}
	
	// Assets
	public static List<Asset> getAssets(String playername) throws DaoException {
		String selectQuery = "SELECT * FROM Asset WHERE player='" + playername + "';";
		List<Asset> returnList = new ArrayList<Asset>();
		
		try {
			ResultSet resultSet = executeSelect(selectQuery);
			
			while (resultSet.next()) {
				Asset temp = new Asset(resultSet.getString(1), resultSet.getInt(2), resultSet.getFloat(4));
				returnList.add(temp);
			}
		} catch (Exception e) {
			throw new DaoException("Call to get Asset failed with:" + e.getMessage());
		}
		
		return returnList;
		
	}
	
	public static int addAsset(String playername, Asset asset) throws DaoException {
		String name = asset.getName();
		int quantity = asset.getQuantity();
		Float costPrice = asset.getCostPrice();
	
		
		String insertQuery = "INSERT INTO Asset VALUES('" + name	+ "', " + Integer.toString(quantity) + ", '" + playername
				+ "', " + Float.toString(costPrice) + ");";
		
		try {
			return executeUpdate(insertQuery);
		} catch (Exception e) {
			throw new DaoException("Adding Asset has failed with: " + e.getMessage());
		}
	}
	
	public static int updateAsset(String playername, Asset oldAsset, Asset newAsset) throws DaoException {
		String oldName = oldAsset.getName();
		
		String newName = newAsset.getName();
		int quantity = newAsset.getQuantity();
		Float costPrice = newAsset.getCostPrice();
		
		String updateQuery = "UPDATE Asset SET name='" + newName + "', quantity=" + Integer.toString(quantity) + ", player='"
				+ playername + "', cost_price=" + Float.toString(costPrice) 
				+ " WHERE name='" + oldName + "' AND player='" + playername + "';"; 
		
		try {
			return executeUpdate(updateQuery);
		} catch (Exception e) {
			throw new DaoException("Updating Asset has failed with: " + e.getMessage());
		}
	}
	
	public static int removeAsset(String playername, Asset asset) throws DaoException {
		String name = asset.getName();
		
		String deleteQuery = "DELETE FROM Asset WHERE player='" + playername + "' AND name='" + name + "';";
		
		try {
			return executeDelete(deleteQuery);
		} catch (Exception e) {
			throw new DaoException ("Removing Asset failed with: " + e.getMessage()); 
		}
		
	}
	
	// Infra
	public static List<Infra> getInfras(String playername) throws DaoException {
		String selectQuery = "SELECT * FROM Infra WHERE player='" + playername + "';";
		List<Infra> returnList = new ArrayList<Infra>();
		
		try {
			ResultSet resultSet = executeSelect(selectQuery);
			
			while (resultSet.next()) {
				Infra temp = new Infra(resultSet.getString(1), resultSet.getInt(2), resultSet.getFloat(4), resultSet.getFloat(5), resultSet.getDouble(6));
				returnList.add(temp);
			}
		} catch (Exception e) {
			throw new DaoException("Call to get Infra failed with:" + e.getMessage());
		}
		
		return returnList;
		
	}
	
	public static int addInfra(String playername, Infra infra) throws DaoException {
		String name = infra.getName();
		int quantity = infra.getQuantity();
		Double efficiency = infra.getEfficiency();
		Float costPrice = infra.getCostPrice();
		Float size = infra.getSize();
	
		
		String insertQuery = "INSERT INTO Infra VALUES('" + name	+ "', " + Integer.toString(quantity) + ", '" + playername
				+ "', " + Float.toString(costPrice) + ", " + Float.toString(size) + ", "  + Double.toString(efficiency) + ");";
		
		try {
			return executeUpdate(insertQuery);
		} catch (Exception e) {
			throw new DaoException("Adding Infra has failed with: " + e.getMessage());
		}
	}
	
	public static int updateInfra(String playername, Infra oldInfra, Infra newInfra) throws DaoException {
		String oldName = oldInfra.getName();
		
		String newName = newInfra.getName();
		int quantity = newInfra.getQuantity();
		Double efficiency = newInfra.getEfficiency();
		Float costPrice = newInfra.getCostPrice();
		Float size = newInfra.getSize();
		
		String updateQuery = "UPDATE Infra SET name='" + newName + "', quantity=" + Integer.toString(quantity) + ", player='"
				+ playername + "', efficiency=" + Double.toString(efficiency) + ", cost_price=" + Float.toString(costPrice) +", size=" + Float.toString(size)
				+ " WHERE name='" + oldName + "' AND player='" + playername + "';"; 
		
		try {
			return executeUpdate(updateQuery);
		} catch (Exception e) {
			throw new DaoException("Updating Infra has failed with: " + e.getMessage());
		}
	}
	
	public static int removeInfra(String playername, Infra infra) throws DaoException {
		String name = infra.getName();
		
		String deleteQuery = "DELETE FROM Infra WHERE player='" + playername + "' AND name='" + name + "';";
		
		try {
			return executeDelete(deleteQuery);
		} catch (Exception e) {
			throw new DaoException ("Removing Infra failed with: " + e.getMessage()); 
		}
		
	}
	
	
}