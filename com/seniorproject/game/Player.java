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
	
 
	//need to add a playerId

    public boolean buyResource(String resourceName, int numUnits) throws DaoException //for right now returns int, in future could be different
	{
		String resourceType = resourceDao.getResourceType(resourceName).toString();
		Float resourcePrice = resourceDao.getResourcePrice(resourceName);
		Resource resource = new Resource(resourceName, resourceType, resourcePrice);
		int numAvailableUnits = playerDao.getResourceNumUnits(playerId, resourceName);
		int newNumUnits = numAvailableUnits + numUnits;

		
		//subtract gold (price of resource)
		if(playerMoney - (resourcePrice * numUnits) < 0) return false; // if you dont have enough money then dont do anything else
	
		//add to database
		if(playerDao == null) System.out.println("dao is null in add Resource - player\n");
	
		System.out.println(playerId);

		//only add if it isn't already there
		if(playerDao.isPlayerResource(playerId, resourceName)) {
			playerDao.updateResource(resource, playerId, newNumUnits);
		}

		else { playerDao.addResource(resource, playerId, numUnits); }

		//update money (only happens if transaction is successful)
		playerMoney = playerMoney - (resourcePrice * numUnits);
			
		try{
			playerDao.setPlayerMoney(playerName,playerId,playerMoney);
		}catch (Exception e) {
			System.err.println(e.getMessage());
		}

		//publish to activity log
		try {
			log = playerName + " purchased " + numUnits + " units of " + resource.getResourceName();
			logger.writeToLog(log);
		} catch (Exception e1) {
			System.err.println(e1.getMessage());
		}

		//automatically generate
		resourceID ++;

		return true;
	}

	//are we doing this?
	//need to add a playerId table

	public boolean sellResource(String resourceName, int numUnits) throws DaoException
	{
		String resourceType = resourceDao.getResourceType(resourceName).toString();
		Float resourcePrice = resourceDao.getResourcePrice(resourceName);
		int numAvailableUnits = playerDao.getResourceNumUnits(playerId, resourceName);
		int newNumUnits = numAvailableUnits - numUnits;

		Resource resource = new Resource(resourceName, resourceType, resourcePrice);

		//add gold (profit from resource, for right now is just the price of resource)
		//figure out how to make market work
		if (playerDao.isPlayerResource(playerId, resourceName)) {
			
			//remove from database!
			if (newNumUnits == 0)
			{
				playerDao.removeResource(resource, playerId);

				//add money from sale
				playerMoney = playerMoney + (resource.getResourcePrice() * numUnits);
				
				try{
					playerDao.setPlayerMoney(playerName, playerId, playerMoney);
				}catch (Exception e) {
					System.err.println(e.getMessage());
				}

				//publish to activity log
				//not logging name of player purchasing because it will say they purchased it
				//should we specify whether purchasing secondhand?
				try {
					log = playerName + " sold " + numUnits + " units of " + resource.getResourceName();
					logger.writeToLog(log);
				} catch (Exception e1) {
					System.err.println(e1.getMessage());
				} 
				return true;
			}

			else if (newNumUnits < 0)
			{
				System.out.println("This can't be done, not enough units available");
				return false;
			}

			else if (newNumUnits > 0)
			{
				playerDao.updateResource(resource, playerId, newNumUnits);

				//add money from sale
				playerMoney = playerMoney + (resource.getResourcePrice() * numUnits);
				
				try{
					playerDao.setPlayerMoney(playerName, playerId, playerMoney);
				}catch (Exception e) {
					System.err.println(e.getMessage());
				}

				//publish to activity log
				//not logging name of player purchasing because it will say they purchased it
				//should we specify whether purchasing secondhand?
				try {
					log = playerName + " sold " + numUnits + " units of " + resource.getResourceName();
					logger.writeToLog(log);
				} catch (Exception e1) {
					System.err.println(e1.getMessage());
				} 
				return true;
			}
			
			else return false;
		}
		else return false;
	}
	
	// TODO: Implement refresh()
	public boolean refresh () { return true; }

	
}