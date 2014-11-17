package com.seniorproject.game;

import java.sql.Connection;
import com.seniorproject.dao.DaoException;
import com.seniorproject.dao.DaoObject;
import com.seniorproject.dao.PlayerDao;
import com.seniorproject.dao.ResourceDao;
import com.seniorproject.resource.Resource;
import com.seniorproject.logger.Logger;

import java.util.Random;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.net.*;
import java.io.*;


public class Player
{
	private static int NUMITEMS = 500; // for now, no idea what this number will be realistically
	
    private Float playerMoney;		//private variables
	private Double playerMarketing;
    private String playerName;
    private int playerId;
	private String password;
    private PlayerDao playerDao;
	private ResourceDao resourceDao;
    private String log;
    private Logger logger = new Logger();
	private int resourceID = 0;

	//ResourceDao resourceDao = new ResourceDao();
 	Resource[] inventory= new Resource[NUMITEMS];//Item[] inventory= new Item[NUMITEMS];
    Random rand = new Random();

    public Player(int playerId, String playerName, float playerMoney, Double playerMarketing, Connection connection) { // initialization function
		this.playerId = playerId;
        this.playerMoney = playerMoney;
		this.playerName = playerName;
		this.playerMarketing = playerMarketing;
		playerDao = new PlayerDao(connection);
		resourceDao = new ResourceDao(connection);
    }
   
	public void copyPlayer(Player p) { // initialization function
		this.playerId = p.playerId;
        this.playerMoney = p.playerMoney;
		this.playerName = p.playerName;
		this.playerMarketing = p.playerMarketing;
    }


	/* Getters and Setters */
    public Float getPlayerMoney() { return playerMoney; }
    public String getPlayerName() { return playerName; }
	public Double getPlayerMarketing() { return playerMarketing; }
	public String getPass() { return password; }
	public int getPlayerId() { return playerId; }
    
    public void setPlayerName(String playerName) {this.playerName = playerName; }
    public void setPlayerMoney(Float playerMoney) {this.playerMoney = playerMoney; }
	public void setPlayerMarketing (Double playerMarketing) { this.playerMarketing = playerMarketing; }
	public void setPlayerId(int playerId) {this.playerId = playerId; }
	
    public void addResource(Resource resource) throws DaoException
	{ 
		//add to inventory array
		for (int i=0;i<inventory.length;i++)
			if (inventory[i]==null)
			{
				inventory[i]=resource;	
				System.out.println("new purchase");
				break;
			}
		if(playerDao == null)
		System.out.println("dao is null in add Resource - player\n");
		//add to inventory database
		System.out.println(playerId);
		playerDao.addResource(resource, playerId);

		//automatically generate
		resourceID ++;
	}

	public void removeResource(Resource resource) throws DaoException 
	{ 
		for (int i=0;i<inventory.length;i++)
			if (inventory[i]==resource)
			{
				inventory[i]=null;	
				System.out.println("inventory slot removed");
				break;
			}

		//remove from inventory database (may need to make this more specific)
		playerDao.removeResource(resource, playerId);
	}

    public Resource[] getInventory()
    { 
    	return inventory;
    }
	
    public boolean isInInventory(Resource resource)    
    {
		for (int i=0;i<inventory.length;i++)
			if (inventory[i]==resource)	return true;
		return false;	
    }

	//need to add a playerId

    public boolean buyResource( String resourceName) throws DaoException //for right now returns int, in future could be different
	{
		String resourceType = resourceDao.getResourceType(resourceName).toString();
		Float resourcePrice = resourceDao.getResourcePrice(resourceName);
		Resource resource = new Resource(resourceName, resourceType, resourcePrice);
		//subtract gold (price of resource)
		if(playerMoney - resourcePrice >= 0) {
			playerMoney = playerMoney - resourcePrice;
		if(playerDao == null)
		System.out.println("dao is null in player\n");
			try{
				playerDao.setPlayerMoney(playerName,playerId,playerMoney);
			}catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
		else return false; // if you dont have enough money then dont do anything else
	
		//add to inventory (add to database!)
		addResource(resource);
		//publish to activity log
		try {
			log = this.getPlayerName() + " purchased " + resource.getResourceName();
			logger.writeToLog(log);
		} catch (Exception e1) {
			System.err.println(e1.getMessage());
		}
		return true;
	}

	//are we doing this?
	//need to add a playerId table

	public boolean sellResource( String resourceName) throws DaoException
	{
		String resourceType = resourceDao.getResourceType(resourceName).toString();
		Float resourcePrice = resourceDao.getResourcePrice(resourceName);

		Resource resource = new Resource(resourceName, resourceType, resourcePrice);
		//add gold (profit from resource, for right now is just the price of resource)
		//figure out how to make market work
		if (playerDao.isPlayerResource(playerId, resourceName)) {

			playerMoney = playerMoney + resourcePrice;
			try{
				playerDao.setPlayerMoney(playerName, playerId, playerMoney);
			}catch (Exception e) {
				System.err.println(e.getMessage());
			}
			//remove from inventory (remove from database!)
			this.removeResource(resource);

			//publish to activity log
			//not logging name of player purchasing because it will say they purchased it
			//should we specify whether purchasing secondhand?
			try {
				log = this.getPlayerName() + " sold " + resourceName;
				logger.writeToLog(log);
			} catch (Exception e1) {
				System.err.println(e1.getMessage());
			} 
			return true;
		}
		else return false;
	}
	
	// TODO: Implement refresh()
	public boolean refresh () { return true; }

	
}