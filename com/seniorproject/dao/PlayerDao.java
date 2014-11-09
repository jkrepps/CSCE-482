package com.seniorproject.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.seniorproject.resource.*;
import com.seniorproject.game.Player;

public class PlayerDao extends DaoObject {
	
	// Create
	public static int  createPlayer(Player player, String username, int gameId) throws DaoException {
		String insertQuery = "INSERT INTO Player VALUES (0,'" + player.getPlayerName() + "','" + username +"','',"
			+ gameId + "," + player.getPlayerMoney() + "," + player.getPlayerMarketing() + ");";
			
		int retval = -1;
		
		try {
			retval = executeUpdate(insertQuery);
		} catch (Exception e) {
			throw new DaoException("Call to create player failed with: " + e.getMessage());
		}
		
		return retval;
	
	}
	
	// Money	
	public static int setPlayerMoney(String playername, float money) throws DaoException {
		String updateQuery = "UPDATE Player SET money=" + money + " WHERE name='" + playername +"';";
		int retval = -1;
		
		try {
			retval = executeUpdate(updateQuery);
		} catch (Exception e) {
			throw new DaoException("Call to set Player money failed with:" + e.getMessage());
		}
		
		return retval; 
		
	}
	
	public static float getPlayerMoney(String playername) throws DaoException {
		String selectQuery = "SELECT money FROM Player where name='" + playername +"';";
		float money = -1;
		
		try { 
			ResultSet resultSet = executeSelect(selectQuery);
			
			resultSet.next();
			money = resultSet.getFloat(1);
			
		} catch (Exception e) {
			throw new DaoException("Call to get Player money failed with:" + e.getMessage());
		}
		
		return money;
	}

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
		String oldWorkerName = oldWorker.getWorkerName();
		
		String newWorkerName = newWorker.getWorkerName();
		int newWorkerQuantity = newWorker.getWorkerQuantity();
		Double newWorkerEfficiency = newWorker.getWorkerEfficiency();
		Float newWorkerWages = newWorker.getWorkerWages();
		
		String updateQuery = "UPDATE Worker SET name='" + newWorkerName + "', quantity=" + Integer.toString(newWorkerQuantity) + ", player='"
				+ playerName + "', efficiency=" + Double.toString(newWorkerEfficiency) + ", wages=" + Float.toString(newWorkerWages) 
				+ " WHERE name='" + oldWorkerName + "' AND player='" + playerName + "';"; 
		
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
		String oldAssetName = oldAsset.getAssetName();
		
		String newAssetName = newAsset.getAssetName();
		int newAssetQuantity = newAsset.getAssetQuantity();
		Float newAssetPrice = newAsset.getAssetPrice();
		
		String updateQuery = "UPDATE Asset SET name='" + newAssetName + "', quantity=" + Integer.toString(newAssetQuantity) + ", player='"
				+ playerName + "', cost_price=" + Float.toString(newAssetPrice) 
				+ " WHERE name='" + oldAssetName + "' AND player='" + playerName + "';"; 
		
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
	
		
		String insertQuery = "INSERT INTO Infra VALUES('" + infraName + "', " + Integer.toString(infraQuantity) + ", '" + playerName
				+ "', " + Float.toString(infraPrice) + ", " + Float.toString(infraSize) + ", "  + Double.toString(infraEfficiency) + ");";
		
		try {
			return executeUpdate(insertQuery);
		} catch (Exception e) {
			throw new DaoException("Adding Infra has failed with: " + e.getMessage());
		}
	}
	
	public static int updateInfra(String playerName, Infra oldInfra, Infra newInfra) throws DaoException {
		String oldInfraName = oldInfra.getInfraName();
		
		String newInfraName = newInfra.getInfraName();
		int newInfraQuantity = newInfra.getInfraQuantity();
		Double newInfraEfficiency = newInfra.getInfraEfficiency();
		Float newInfraPrice = newInfra.getInfraPrice();
		Float newInfraSize = newInfra.getInfraSize();
		
		String updateQuery = "UPDATE Infra SET name='" + newInfraName + "', quantity=" + Integer.toString(newInfraQuantity) + ", player='"
				+ playerName + "', efficiency=" + Double.toString(newInfraEfficiency) + ", cost_price=" + Float.toString(newInfraPrice) +", size=" + Float.toString(newInfraSize)
				+ " WHERE name='" + oldInfraName + "' AND player='" + playerName + "';"; 
		
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
