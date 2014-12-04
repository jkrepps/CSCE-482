package com.seniorproject.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.lang.Boolean;

import com.seniorproject.resource.*;
import com.seniorproject.game.Player;
import com.seniorproject.game.Game;


public class PlayerDao extends DaoObject {

//Connection connection;	

	public PlayerDao(Connection connection) {
		this.connection = connection;
	}
	
	// Create
	public int createPlayer(Player player, String username, int gameId) throws DaoException {
		String insertQuery = "INSERT INTO Player VALUES (0,'" + username + "','" + username +"',"
			+ gameId + "," + player.getPlayerMoney() + "," + player.getPlayerMarketing() + ",0);";
			//TODO ADD PLAYER += 1
		UpdateGamePlayersIncrement(gameId);
			
		int retval = -1;
		
		try {
			retval = this.executeUpdate(insertQuery);
		} catch (Exception e) {
			throw new DaoException("Call to create player failed with: " + e.getMessage());
		}
		
		return retval;
	
	}
	public Player checkPlayer(int gameId, String username, Game game) throws DaoException {
		
		String selectQuery = "SELECT * FROM Player WHERE user='" + username + "' AND game_id=" + gameId +";";
		Player player = new Player(0, username, 100.0f, 0.67, connection);
		GameDao gameDao = new GameDao(this.getConnection());
		
		try {
			ResultSet resultSet = this.executeSelect(selectQuery);
			
			if(!resultSet.next()) 
			{ 
				if (gameDao.checkGame(gameId)) {
					if (createPlayer(player, username, gameId) > 0)
					{
						player = checkPlayer(gameId, username, game);
						game.insertPlayer(player);
						return player;
					}
					else
						return null;
				}
				else
					return null;
			}
			else 
			{
				player = new Player(resultSet.getInt(1), resultSet.getString(2), resultSet.getFloat(5), resultSet.getDouble(6), connection);
			}
		} catch ( Exception e) {
			e.printStackTrace();
		}
		return player;
	}
	public List<Player> getPlayersForGame(Game g)
	{
		String selectQuery = "SELECT * FROM Player WHERE game_id=" + g.getGameId() +";";
		Player player = new Player(0, "noname", 100.0f, 0.67, connection);
		GameDao gameDao = new GameDao(this.getConnection());
		List<Player> playerList = new ArrayList<Player> ();
		
		try {
			ResultSet resultSet = this.executeSelect(selectQuery);
			
			while(resultSet.next()) 
			{ 
				player = new Player(resultSet.getInt(1), resultSet.getString(2), resultSet.getFloat(5), resultSet.getDouble(6), connection);
				playerList.add(player);
			}
		} catch ( Exception e) {
			e.printStackTrace();
		}
		return playerList;
	}
	/*public Player getId(int gameId, String username) throws DaoException {
		
		String selectQuery = "SELECT * FROM Player WHERE user='" + username + "' AND game_id=" + gameId +";";
		GameDao gameDao = new GameDao(this.getConnection());
		
		try {
			ResultSet resultSet = this.executeSelect(selectQuery);
			
			if(!resultSet.next()) { 
				return null;
			}
			else 
				player = new Player(resultSet.getInt(1), resultSet.getString(2), resultSet.getFloat(5), resultSet.getDouble(6), connection);
		} catch ( Exception e) {
			e.printStackTrace();
		}
		return player;
	}*/
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
	
	public int setPlayerMoney(String playername, int playerId, float money) throws DaoException {
		String updateQuery = "UPDATE Player SET money=" + money + " WHERE name='" + playername +"' AND id=" + playerId +";";
		int retval = -1;
		
		try {
			retval = this.executeUpdate(updateQuery);
		} catch (Exception e) {
			throw new DaoException("Call to set Player money failed with:" + e.getMessage());
		}
		
		return retval; 
		
	}
	public int getGameId(int playerId) throws DaoException {
		String selectQuery = "SELECT game_id FROM Player where id=" + playerId +";";
		int ID = -1;
		try { 
			ResultSet resultSet = this.executeSelect(selectQuery);
			
			resultSet.next();
			ID = resultSet.getInt(1);
			
		} catch (Exception e) {
			throw new DaoException("Call to get Player ID failed with:" + e.getMessage());
		}
		
		return ID;
	}
	public float getPlayerMoney(String playername, int gameId) throws DaoException {
		String selectQuery = "SELECT money FROM Player where name='" + playername +"' AND game_id=" + gameId +";";
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
	//game players
	public int UpdateGamePlayersIncrement(int gameId) throws DaoException 
	{
		String updateQuery = "UPDATE Game SET current_players=current_players+" + 1 + " WHERE id=" + gameId +";";
		int retval = -1;
		
		try {
			retval = this.executeUpdate(updateQuery);
		} catch (Exception e) {
			throw new DaoException("Call to update current players in game "+ gameId+ " failed with:" + e.getMessage());
		}
		
		return retval; 
	}
	// returns resourceID
	public int addResource (Resource resource, int playerId, int numUnits) throws DaoException {
		
		int retval = -1;
		String insertQuery ="INSERT INTO PlayerResource VALUES (0, " +  playerId +  ", '" + 
				resource.getResourceName() + "', '" + resource.getResourceClass() + "', " + resource.getResourcePrice() + "," + resource.getResourceIncome() + "," + numUnits + ");" ;
		
		try {
			this.executeUpdate(insertQuery);
			
			String selectIdQuery = "SELECT LAST_INSERT_ID();";
			ResultSet resultSet = executeSelect(selectIdQuery);			
			resultSet.next();			
			retval = resultSet.getInt(1);
			
		} catch (Exception e1) {
			System.out.println("Something broke in the addResource function of PlayerDao");
			System.err.println(e1.getMessage());
		}
		
		return retval;
	}
	
	public int addTech (Tech tech, int playerId) throws DaoException {
		
		int retval = -1;
		String insertQuery ="INSERT INTO PlayerTechs VALUES (" +  playerId +  ", " + 
				tech.getResouceId() + ");" ;
		
		try {
			this.executeUpdate(insertQuery);
			
			String selectIdQuery = "SELECT LAST_INSERT_ID();";
			ResultSet resultSet = executeSelect(selectIdQuery);			
			resultSet.next();			
			retval = resultSet.getInt(1);
			
		} catch (Exception e1) {
			System.out.println("Something broke in the addTech function of PlayerDao");
			System.err.println(e1.getMessage());
		}
		
		return retval;
	}

	public int updateResource (Resource resource, int playerId, int newNumUnits) throws DaoException {
		String updateQuery ="UPDATE PlayerResource SET units = " + newNumUnits + " WHERE player_id = " + playerId + " AND resourceName = '" + resource.getResourceName() +
			 "' AND resourceClass = '" + resource.getResourceClass() + "';";
		try {
		//System.out.println(updateQuery);
			return executeUpdate(updateQuery);			
		} catch (Exception e) {
			throw new DaoException("Call to update number of units of resource failed with: " + e.getMessage());
		}
	}

	public int removeResource (Resource resource, int playerId) throws DaoException {
		String deleteQuery ="DELETE FROM PlayerResource WHERE resourceName = '" + resource.getResourceName() + "' AND player_id = " + playerId + 
					" AND resourceClass = '" + resource.getResourceClass() + "';";
		try {
			//System.out.println(deleteQuery);
			return executeDelete(deleteQuery);			
		} catch (Exception e) {
			throw new DaoException("Call to remove Resource failed with: " + e.getMessage());
		}
	}
	
	public HashMap<String, Integer> getResourceQuantities(int playerId) throws DaoException {
		HashMap<String, Integer> retval  = new HashMap<String, Integer>();
		String selectQuery = "SELECT * FROM PlayerResource WHERE player_id=" + playerId +";";
		
		try {
			ResultSet resultSet = executeSelect(selectQuery);
			
			while(resultSet.next()) {
				retval.put(resultSet.getString(3), resultSet.getInt(7));
			}
		}catch (Exception e) {
			throw new DaoException("Call to get Resource Quantities failed with: " + e.getMessage());
		}
		
		return retval;
	}

	
	public List<Resource> getResources(int playerId) throws DaoException {
		
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
	public int getScience(Player p) throws DaoException {
		
		String selectQuery = "SELECT units FROM PlayerResource WHERE player_id=" + p.getPlayerId() +" AND resourceName = 'Science';";
		int units = 0;
		
		try {
			ResultSet resultSet = executeSelect(selectQuery);
			
			while(resultSet.next()) {
				
				units = resultSet.getInt(1);
			}
		}catch (Exception e) {
			throw new DaoException("Call to get Science by Player failed with: " + e.getMessage());
		}
		
		return units;
	}
	public int getMarketing(Player p) throws DaoException {
		
		String selectQuery = "SELECT units FROM PlayerResource WHERE player_id=" + p.getPlayerId() +" AND resourceName = 'Marketing';";
		int units = 0;
		
		try {
			ResultSet resultSet = executeSelect(selectQuery);
			
			while(resultSet.next()) {
				
				units = resultSet.getInt(1);
			}
		}catch (Exception e) {
			throw new DaoException("Call to get Marketing by Player failed with: " + e.getMessage());
		}
		
		return units;
	}
	/*
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
	}*/
	public float getIncome (int playerId) throws DaoException {
		
		String selectQuery = "SELECT * FROM PlayerResource WHERE player_id=" + playerId + ";";
		List<Resource> resourceList = new ArrayList<Resource> ();
		Float netIncome = 0.0f;
		Float rincome = 0.0f;
		int units = 0;
		try {
			ResultSet resultSet = executeSelect(selectQuery);
			//get list of resources player owns
			while(resultSet.next())
			{
				resourceList.add(new Resource(resultSet.getString(3),resultSet.getString(4),resultSet.getFloat(5)));
			}
			//for each resource, get the income for that resource and multiply it by the number of units the person has. append to net income.
			for(int i = 0; i < resourceList.size(); i++)
			{
				rincome = getIncomeOfResource(resourceList.get(i).getResourceName());
				units = getResourceNumUnits(playerId, resourceList.get(i).getResourceName(),resourceList.get(i).getResourceClass());
				netIncome += rincome * units;
			}

			return netIncome;
		} catch (Exception e){
			throw new DaoException ("Call to get Net Income failed with: " + e.getMessage());
		}
	}
	public float updateAssets (Player p) throws DaoException {
		String selectQuery = "SELECT * FROM PlayerResource WHERE player_id=" + p.getPlayerId() + " AND resourceClass = 'INFRA';";
		List<Resource> resourceList = new ArrayList<Resource> ();
		Float netIncome = 0.0f;
		Float overallNet = 0.0f;
		Float rincome = 0.0f;
		int units = 0;
		try {
			ResultSet resultSet = executeSelect(selectQuery);
			//get list of resources player owns
			while(resultSet.next())
			{
				resourceList.add(new Resource(resultSet.getString(3),resultSet.getString(4),resultSet.getFloat(5)));
			}
			//for each resource, get the income for that resource and multiply it by the number of units the person has. append to net income.
			for(int i = 0; i < resourceList.size(); i++)
			{
			//System.out.println(" 1 " + resourceList.get(i).getResourceName() + " " + resourceList.get(i).getResourceClass()+ " " +resourceList.get(i).getResourcePrice());
				rincome = getIncomeOfResource(resourceList.get(i).getResourceName());
				units = getResourceNumUnits(p.getPlayerId(), resourceList.get(i).getResourceName(),resourceList.get(i).getResourceClass());
				//System.out.println("2");
				netIncome = rincome * units;
				String incomeType = getResourceIncomeType(resourceList.get(i).getResourceName());
				if(incomeType.equals("Money"))
				{	//then simply increment money by netincome
					setPlayerMoney(p.getPlayerName(), p.getPlayerId(), getPlayerMoney(p.getPlayerName(), getGameId(p.getPlayerId())) + netIncome);
					overallNet += netIncome;
				}
				else
				{ //add the resource to the players resourcelist
					Resource newResource = getResource(incomeType);
					if(isPlayerResource(p.getPlayerId(), newResource.getResourceName(), newResource.getResourceClass())) {
						int numAvailableUnits = getResourceNumUnits(p.getPlayerId(), newResource.getResourceName(),newResource.getResourceClass());
						int newNumUnits = numAvailableUnits + (int)Math.round(netIncome);
						updateResource(newResource, p.getPlayerId(), newNumUnits);
						overallNet += netIncome*newResource.getResourcePrice();
					}

					else 
					{ 
						if(addResource(newResource, p.getPlayerId(), (int)Math.round(netIncome)) == -1){
							System.out.println("Adding resource is broken");
							return -2.0f;
						} 
					}
				}
			}
			return overallNet;
		} catch (Exception e){
			throw new DaoException ("Call to get Net Income failed with: " + e.getMessage());
		}
	}
	public float getIncome (Player p) throws DaoException {
		String selectQuery = "SELECT * FROM PlayerResource WHERE player_id=" + p.getPlayerId() + " AND resourceClass = 'INFRA';";
		List<Resource> resourceList = new ArrayList<Resource> ();
		Float netIncome = 0.0f;
		Float overallNet = 0.0f;
		Float rincome = 0.0f;
		int units = 0;
		try {
			ResultSet resultSet = executeSelect(selectQuery);
			//get list of resources player owns
			while(resultSet.next())
			{
				resourceList.add(new Resource(resultSet.getString(3),resultSet.getString(4),resultSet.getFloat(5)));
			}
			//for each resource, get the income for that resource and multiply it by the number of units the person has. append to net income.
			for(int i = 0; i < resourceList.size(); i++)
			{
				rincome = getIncomeOfResource(resourceList.get(i).getResourceName());
				units = getResourceNumUnits(p.getPlayerId(), resourceList.get(i).getResourceName(),resourceList.get(i).getResourceClass());
				netIncome = rincome * units;
				String incomeType = getResourceIncomeType(resourceList.get(i).getResourceName());
				if(incomeType.equals("Money"))
				{	
					//System.out.println("item "+resourceList.get(i).getResourceName()+" for player " + p.getPlayerName() + " = " + netIncome);
					overallNet += netIncome;
				}
			}
			return overallNet;
		} catch (Exception e){
			throw new DaoException ("Call to get Net Income failed with: " + e.getMessage());
		}
	}
	public String getResourceIncomeType(String resourceName)throws DaoException 
	{
		String selectQuery = "SELECT income_type FROM ResourceList WHERE name='" + resourceName + "';";
		List<Resource> resourceList = new ArrayList<Resource> ();
		String IncomeType;
		try {
			ResultSet resultSet = executeSelect(selectQuery);
			
			resultSet.next();
			IncomeType = resultSet.getString(1);
			
			return IncomeType;
		} catch (Exception e){
			throw new DaoException ("Call to get Resource IncomeType failed with: " + e.getMessage());
		}
		
	}
	public float getIncomeOfResource(String resourceName) throws DaoException 
	{
	//chance price to income later
		String selectQuery = "SELECT income FROM ResourceList WHERE name='" + resourceName + "';";
		List<Resource> resourceList = new ArrayList<Resource> ();
		Float Income;
		try {
			ResultSet resultSet = executeSelect(selectQuery);
			
			resultSet.next();
			Income = resultSet.getFloat(1);
			
			return Income;
		} catch (Exception e){
			throw new DaoException ("Call to get Resource Income failed with: " + e.getMessage());
		}
	
	}
	
	public boolean isPlayerResource(int playerId, String resourceName, String Classification) throws DaoException {
		
		String selectQuery = "SELECT * FROM PlayerResource WHERE player_id=" + playerId +" AND resourceName=\"" + resourceName + "\" AND resourceClass = '" + Classification + "';";
		List<Resource> returnList = new ArrayList<Resource>();
		
		try {
			ResultSet resultSet = executeSelect(selectQuery);
			
			if(resultSet.next())
			{
				return true;
			}
		
		}catch (Exception e) {
			throw new DaoException("Call to get check if player owns resource failed with: " + e.getMessage());
		}
		
		return false;
	}
	public boolean isPlayerTech(int playerId, int techId) throws DaoException {
		
		String selectQuery = "SELECT * FROM PlayerTechs WHERE player_id=" + playerId +" AND Researched=" + techId + ";";
		List<Resource> returnList = new ArrayList<Resource>();
		
		try {
			ResultSet resultSet = executeSelect(selectQuery);
			
			if(resultSet.next())
			{
				return true;
			}
		
		}catch (Exception e) {
			throw new DaoException("Call to get check if player owns tech failed with: " + e.getMessage());
		}
		
		return false;
	}
	public Resource getResource(String name)throws DaoException
	{
		String selectQuery = "SELECT * FROM ResourceList WHERE name='" + name + "';";
		Resource resource;
		try {
			ResultSet resultSet = executeSelect(selectQuery);
			
			resultSet.next();
			resource = (new Resource(resultSet.getString(2),resultSet.getString(3),resultSet.getFloat(4)));
			
			return resource;
		} catch (Exception e){
			throw new DaoException ("Call to get Resource IncomeType failed with: " + e.getMessage());
		}
	}
	public int getResourceNumUnits(int playerId, String resourceName, String Classification) throws DaoException {
		String selectQuery = "SELECT units FROM PlayerResource WHERE player_id='" + playerId +"' AND resourceName=\"" + resourceName +"\"AND resourceClass = '" + Classification + "';";

		int numUnits = -1;
		
		try { 
			ResultSet resultSet = this.executeSelect(selectQuery);
			
			if (resultSet.next()) numUnits = resultSet.getInt(1);
			else numUnits = 0;
			
		} catch (Exception e) {
			throw new DaoException("Call to get number of resource units failed with:" + e.getMessage());
		}
		
		return numUnits;
	}
	public List<String> getPlayerList(int gameId) throws DaoException {
		String selectQuery = "SELECT * FROM Player WHERE game_id=" + gameId +";";

		List<String> namelist = new ArrayList<String>();
		
		try { 
			ResultSet resultSet = this.executeSelect(selectQuery);
			
			while (resultSet.next())
			{
				namelist.add(resultSet.getString(2));
			}
			while(namelist.size() < getGameSize(gameId))
			{
				namelist.add("empty slot");
			}
			
		} catch (Exception e) {
			throw new DaoException("Call to get player list failed with:" + e.getMessage());
		}
		
		return namelist;
	}
	public int getGameSize(int gameId) throws DaoException {
		String selectQuery = "SELECT max_players FROM Game WHERE id=" + gameId + ";";

		int numPlayers = -1;
		
		try { 
			ResultSet resultSet = this.executeSelect(selectQuery);
			
			if (resultSet.next()) numPlayers = resultSet.getInt(1);
			else numPlayers = 0;
			
		} catch (Exception e) {
			throw new DaoException("Call to get game size failed with:" + e.getMessage());
		}
		
		return numPlayers;
	}
	public boolean hasRequiredWorkers(Player p, String workertype,int numworkers) throws DaoException
	{
		int units = getResourceNumUnits(p.getPlayerId(), workertype, "WORKER");
		//System.out.println("have = "+ units);
		//System.out.println("needed = "+numworkers);
		if(units >= numworkers)
			return true;
		else
			return false;
	}
	
	public int setPlayerStatus(int playerId, int status) throws DaoException
	{
		String updateQuery ="UPDATE Player SET status = " + status + " WHERE id = " + playerId + ";";

		int retval = -1;
		
		try { 
			retval = this.executeUpdate(updateQuery);
			
		} catch (Exception e) {
			throw new DaoException("Call to set player status failed with:" + e.getMessage());
		}
		
		return retval;
	}
	public int getPlayerStatus(int playerId) throws DaoException {
		String selectQuery = "SELECT status FROM Player WHERE id=" + playerId + ";";

		int status = -1;
		
		try { 
			ResultSet resultSet = this.executeSelect(selectQuery);
			
			if (resultSet.next()) status = resultSet.getInt(1);
			else status = -1;
			
		} catch (Exception e) {
			throw new DaoException("Call to get player status failed with:" + e.getMessage());
		}
		
		return status;
	}
	
}
