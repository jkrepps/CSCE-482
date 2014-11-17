package com.seniorproject.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.seniorproject.resource.*;
import com.seniorproject.game.Player;

public class PlayerDao extends DaoObject {
	
	public PlayerDao(Connection connection) {
		this.connection = connection;
	}
	
	// Create
	public int createPlayer(Player player, String username, int gameId) throws DaoException {
		String insertQuery = "INSERT INTO Player VALUES (0,'" + player.getPlayerName() + "','" + username +"',"
			+ gameId + "," + player.getPlayerMoney() + "," + player.getPlayerMarketing() + ");";
		
		System.out.println(insertQuery);
			//TODO ADD PLAYER += 1
			
		int retval = -1;
		
		try {
			retval = this.executeUpdate(insertQuery);
		} catch (Exception e) {
			throw new DaoException("Call to create player failed with: " + e.getMessage());
		}
		
		return retval;
	
	}
	
	public Player checkPlayer(int gameId, String username) throws DaoException {
		
		String selectQuery = "SELECT * FROM Player WHERE user='" + username + "' AND game_id=" + gameId +";";
		Player player = new Player(0, "noname", 100.0f, 0.67);
		GameDao gameDao = new GameDao(this.getConnection());
		
		try {
			ResultSet resultSet = this.executeSelect(selectQuery);
			
			if(!resultSet.next()) { 
				if (gameDao.checkGame(gameId)) {
					if (createPlayer(player, username, gameId) > 0)
						return player;
					else
						return null;
				}
				else
					return null;
			
			}
			else 
				player = new Player(resultSet.getInt(1), resultSet.getString(2), resultSet.getFloat(5), resultSet.getDouble(6));
		} catch ( Exception e) {
			e.printStackTrace();
		}
		return player;
	}

	// Money	
	
	public int updatePlayerMoney(String playername, float money) throws DaoException {
		String updateQuery = "UPDATE Player SET money=money+" + money + " WHERE name='" + playername +"';";
		int retval = -1;
		
		try {
			retval = this.executeUpdate(updateQuery);
		} catch (Exception e) {
			throw new DaoException("Call to update Player money failed with:" + e.getMessage());
		}
		
		return retval; 
		
	}
	
	public int setPlayerMoney(String playername, float money) throws DaoException {
		String updateQuery = "UPDATE Player SET money=" + money + " WHERE name='" + playername +"';";
		int retval = -1;
		
		try {
			retval = this.executeUpdate(updateQuery);
		} catch (Exception e) {
			throw new DaoException("Call to set Player money failed with:" + e.getMessage());
		}
		
		return retval; 
		
	}
	
	public float getPlayerMoney(String playername) throws DaoException {
		String selectQuery = "SELECT money FROM Player where name='" + playername +"';";
		float money = -1;
		
		try { 
			ResultSet resultSet = this.executeSelect(selectQuery);
			
			resultSet.next();
			money = resultSet.getFloat(1);
			
		} catch (Exception e) {
			throw new DaoException("Call to get Player money failed with:" + e.getMessage());
		}
		
		return money;
	}
	
	// returns resourceID
	public int addResource (Resource resource, int playerId) throws DaoException {
		
		int retval = -1;
		String insertQuery ="INSERT INTO PlayerResource VALUES (0, " +  playerId +  ", '" + 
				resource.getResourceName() + "', '" + resource.getResourceClass() + "', " + resource.getResourcePrice() + "," + resource.getResourceIncome() + ");" ;
		
		try {
			this.executeUpdate(insertQuery);
			
			String selectIdQuery = "SELECT LAST_INSERT_ID();";
			ResultSet resultSet = executeSelect(selectIdQuery);			
			resultSet.next();			
			retval = resultSet.getInt(1);
			
		} catch (Exception e1) {
			System.err.println(e1.getMessage());
		}
		
		return retval;
	}

	public int removeResource (Resource resource, int playerId) throws DaoException {

		String deleteQuery ="DELETE FROM PlayerResource WHERE resourceName = '" + resource.getResourceName() + "' AND playerId = " + playerId + ";";
		
		try {
			return this.executeDelete(deleteQuery);			
		} catch (Exception e) {
			throw new DaoException("Call to remove Resource failed with: " + e.getMessage());
		}
	}
	
	public List<Resource> getResources (int playerId) throws DaoException {
		
		String selectQuery = "SELECT * FROM PlayerResource WHERE player_id=" + playerId +";";
		List<Resource> returnList = new ArrayList<Resource>();
		
		try {
			ResultSet resultSet = executeSelect(selectQuery);
			
			while(resultSet.next()) {
				
				Resource temp = new Resource(resultSet.getInt(1), resultSet.getString(3), resultSet.getString(4), resultSet.getFloat(5), resultSet.getFloat(6));
				returnList.add(temp);
			}
		}catch (Exception e) {
			throw new DaoException("Call to get Resource by Player failed with: " + e.getMessage());
		}
		
		return returnList;
	}
	
	public float getIncome (int playerId) throws DaoException {
		
		String selectQuery = "SELECT SUM(income) FROM PlayerResource WHERE player_id=" + playerId + ";";
		
		try {
			ResultSet resultSet = executeSelect(selectQuery);
			
			resultSet.next();
			
			Float netIncome = resultSet.getFloat(1);
			
			return netIncome;
		} catch (Exception e){
			throw new DaoException ("Call to get Net Income failed with: " + e.getMessage());
		}
	}
	
}
