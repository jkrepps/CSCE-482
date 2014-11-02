package com.seniorproject.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.seniorproject.resource.*;

public class PlayerDao extends DaoObject {

	// Workers
	public static List<Resource> getWorkers(String playerName) throws DaoException {
		String selectQuery = "SELECT * FROM Worker WHERE player='" + playerName + "';";
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
	
	public static int addWorker(String playerName, Worker worker) throws DaoException {
		String workerName = worker.getWorkerName();
		int workerQuantity = worker.getWorkerQuantity();
		Double workerEfficiency = worker.getWorkerEfficiency();
		Float workerWages = worker.getWorkerWages();
	
		
		String insertQuery = "INSERT INTO Worker VALUES('" + workerName	+ "', " + Integer.toString(workerQuantity) + ", '" + playerName
				+ "', " + Double.toString(workerEfficiency) + ", " + Float.toString(workerWages) + ");";
		
		try {
			return executeUpdate(insertQuery);
		} catch (Exception e) {
			throw new DaoException("Adding Worker has failed with: " + e.getMessage());
		}
	}
	
	public static int updateWorker(String playerName, Worker oldWorker, Worker newWorker) throws DaoException {
		String oldName = oldWorker.getWorkerName();
		
		String newName = newWorker.getWorkerName();
		int workerQuantity = newWorker.getWorkerQuantity();
		Double workerEfficiency = newWorker.getWorkerEfficiency();
		Float workerWages = newWorker.getWorkerWages();
		
		String updateQuery = "UPDATE Worker SET name='" + newName + "', quantity=" + Integer.toString(workerQuantity) + ", player='"
				+ playerName + "', efficiency=" + Double.toString(workerEfficiency) + ", wages=" + Float.toString(workerWages) 
				+ " WHERE name='" + oldName + "' AND player='" + playerName + "';"; 
		
		try {
			return executeUpdate(updateQuery);
		} catch (Exception e) {
			throw new DaoException("Updating Worker has failed with: " + e.getMessage());
		}
	}
	
	public static int removeWorker(String playerName, Worker worker) throws DaoException {
		String workerName = worker.getWorkerName();
		
		String deleteQuery = "DELETE FROM Worker WHERE player='" + playerName + "' AND name='" + workerName + "';";
		
		try {
			return executeDelete(deleteQuery);
		} catch (Exception e) {
			throw new DaoException ("Removing Worker failed with: " + e.getMessage()); 
		}
		
	}
	
	// Assets
	public static List<Asset> getAssets(String playerName) throws DaoException {
		String selectQuery = "SELECT * FROM Asset WHERE player='" + playerName + "';";
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
	
	public static int addAsset(String playerName, Asset asset) throws DaoException {
		String assetName = asset.getAssetName();
		int assetQuantity = asset.getAssetQuantity();
		Float assetPrice = asset.getAssetPrice();
	
		
		String insertQuery = "INSERT INTO Asset VALUES('" + assetName	+ "', " + Integer.toString(assetQuantity) + ", '" + playerName
				+ "', " + Float.toString(assetPrice) + ");";
		
		try {
			return executeUpdate(insertQuery);
		} catch (Exception e) {
			throw new DaoException("Adding Asset has failed with: " + e.getMessage());
		}
	}
	
	public static int updateAsset(String playerName, Asset oldAsset, Asset newAsset) throws DaoException {
		String oldName = oldAsset.getAssetName();
		
		String newName = newAsset.getAssetName();
		int assetQuantity = newAsset.getAssetQuantity();
		Float assetPrice = newAsset.getAssetPrice();
		
		String updateQuery = "UPDATE Asset SET name='" + newName + "', quantity=" + Integer.toString(assetQuantity) + ", player='"
				+ playerName + "', cost_price=" + Float.toString(assetPrice) 
				+ " WHERE name='" + oldName + "' AND player='" + playerName + "';"; 
		
		try {
			return executeUpdate(updateQuery);
		} catch (Exception e) {
			throw new DaoException("Updating Asset has failed with: " + e.getMessage());
		}
	}
	
	public static int removeAsset(String playerName, Asset asset) throws DaoException {
		String assetName = asset.getAssetName();
		
		String deleteQuery = "DELETE FROM Asset WHERE player='" + playerName + "' AND name='" + assetName + "';";
		
		try {
			return executeDelete(deleteQuery);
		} catch (Exception e) {
			throw new DaoException ("Removing Asset failed with: " + e.getMessage()); 
		}
		
	}
	
	// Infra
	public static List<Infra> getInfras(String playerName) throws DaoException {
		String selectQuery = "SELECT * FROM Infra WHERE player='" + playerName + "';";
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
	
	public static int addInfra(String playerName, Infra infra) throws DaoException {
		String infraName = infra.getInfraName();
		int infraQuantity = infra.getInfraQuantity();
		Double infraEfficiency = infra.getInfraEfficiency();
		Float infraPrice = infra.getInfraPrice();
		Float infraSize = infra.getInfraSize();
	
		
		String insertQuery = "INSERT INTO Infra VALUES('" + infraName	+ "', " + Integer.toString(infraQuantity) + ", '" + playerName
				+ "', " + Float.toString() + ", " + Float.toString(infraSize) + ", "  + Double.toString(infraEfficiency) + ");";
		
		try {
			return executeUpdate(insertQuery);
		} catch (Exception e) {
			throw new DaoException("Adding Infra has failed with: " + e.getMessage());
		}
	}
	
	public static int updateInfra(String playerName, Infra oldInfra, Infra newInfra) throws DaoException {
		String oldName = oldInfra.getInfraName();
		
		String newName = newInfra.getInfraName();
		int infraQuantity = newInfra.getInfraQuantity();
		Double = infraEfficiency = newInfra.getInfraEfficiency();
		Float infraPrice = newInfra.getInfraPrice();
		Float infraSize = newInfra.getInfraSize();
		
		String updateQuery = "UPDATE Infra SET name='" + newName + "', quantity=" + Integer.toString(infraQuantity) + ", player='"
				+ playerName + "', efficiency=" + Double.toString(infraEfficiency) + ", cost_price=" + Float.toString(infraPirce) +", size=" + Float.toString(infraSize)
				+ " WHERE name='" + oldName + "' AND player='" + playerName + "';"; 
		
		try {
			return executeUpdate(updateQuery);
		} catch (Exception e) {
			throw new DaoException("Updating Infra has failed with: " + e.getMessage());
		}
	}
	
	public static int removeInfra(String playerName, Infra infra) throws DaoException {
		String infraName = infra.getInfraName();
		
		String deleteQuery = "DELETE FROM Infra WHERE player='" + playerName + "' AND name='" + infraName + "';";
		
		try {
			return executeDelete(deleteQuery);
		} catch (Exception e) {
			throw new DaoException ("Removing Infra failed with: " + e.getMessage()); 
		}
		
	}
	
	
}
