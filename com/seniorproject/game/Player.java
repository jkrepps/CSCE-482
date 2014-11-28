package com.seniorproject.game;

import java.sql.Connection;
import com.seniorproject.dao.DaoException;
import com.seniorproject.dao.DaoObject;
import com.seniorproject.dao.PlayerDao;
import com.seniorproject.dao.ResourceDao;
import com.seniorproject.resource.*;
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
	private float playerIncome = 0.0f;

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
    public Float getPlayerMoney() 
	{ 
		try{
		playerMoney = playerDao.getPlayerMoney(playerName, playerDao.getGameId(playerId));
		}catch(Exception e){ System.out.println(e);}
		
		return playerMoney; 
	}
    public String getPlayerName() { return playerName; }
	public Double getPlayerMarketing() { return playerMarketing; }
	public String getPass() { return password; }
	public int getPlayerId() { return playerId; }
	public float getPlayerIncome() { return playerIncome; }
    
    public void setPlayerName(String playerName) {this.playerName = playerName; }
    public void setPlayerMoney(Float playerMoney) {this.playerMoney = playerMoney; }
	public void setPlayerMarketing (Double playerMarketing) { this.playerMarketing = playerMarketing; }
	public void setPlayerId(int playerId) {this.playerId = playerId; }
	public void setPlayerIncome(float playerIncome) {this.playerIncome = playerIncome; }
	
 
	//need to add a playerId

    public int buyResource(String resourceName, int numUnits) throws DaoException //for right now returns int, in future could be different
	{
		//can't go further unless in itemlist
		if(resourceDao.isInItemList(resourceName) && resourceDao.isInPlayerItemList(resourceName,this) == false) return -1;
		String resourceType = resourceDao.getResourceType(resourceName).toString();
		Float resourcePrice = resourceDao.getResourcePrice(resourceName);
		Resource resource = new Resource(resourceName, resourceType, resourcePrice);
		int numAvailableUnits = playerDao.getResourceNumUnits(playerId, resourceName,resourceType);
		int newNumUnits = numAvailableUnits + numUnits;
		//only purchase if have the correct number of workers for the building
		int numworkers = resourceDao.getResourceWorkerNum(resourceName);
		String workertype = resourceDao.getResourceWorkerName(resourceName);
		if(!playerDao.hasRequiredWorkers(this, workertype, numworkers * numUnits))
			return -3;
		//only purchase if can afford it
		if(playerMoney - (resourcePrice * numUnits) < 0) return 0; // if you dont have enough money then dont do anything else
	
		//add to database
		if(playerDao == null) System.out.println("dao is null in add Resource - player\n");
		if(numworkers > 0)
		{
			Resource worker = new Resource(workertype, "WORKER", resourceDao.getResourcePrice(workertype));
			int currentworkers = playerDao.getResourceNumUnits(playerId, workertype, "WORKER");
			int currentusedworkers = playerDao.getResourceNumUnits(playerId, workertype, "INFRA");
			//subtract workers
			if (currentworkers - numworkers * numUnits == 0)
			{
				playerDao.removeResource(worker, playerId);
			}
			else
			{
				playerDao.updateResource(worker, playerId, currentworkers - numworkers * numUnits);
			}

			//add used workers
			worker = new Resource(workertype, "INFRA", resourceDao.getResourcePrice(workertype));
			if(playerDao.isPlayerResource(playerId, workertype, "INFRA")) {
				playerDao.updateResource(worker, playerId, currentusedworkers + numworkers * numUnits);
			}
			else 
			{ 
				//System.out.println("making new entry");
				if(playerDao.addResource(worker, playerId, numworkers * numUnits) == -1){
					System.out.println("Adding resource is broken");
					return -2;
				} 
			}
		}
		//only add resource if it isn't already there
		if(playerDao.isPlayerResource(playerId, resourceName, resourceDao.getResourceClass(resourceName))) {
			playerDao.updateResource(resource, playerId, newNumUnits);
		}

		else 
		{ 
			if(playerDao.addResource(resource, playerId, numUnits) == -1){
				System.out.println("Adding resource is broken");
				return -2;
			} 
		}

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

		return 1;
	}
	
	public int buyTech(String techName) throws DaoException //for right now returns int, in future could be different
	{
		//can't go further unless in itemlist
		if(resourceDao.isInTechList(techName) && resourceDao.isInPlayerTechList(techName,this) == false) return -1;


		Tech tech = resourceDao.getTech(techName);
		int playerScience = playerDao.getScience(this);
		
		//only purchase if can afford it
		if(playerScience - (tech.getTechPrice()) < 0) return 0; // if you dont have enough money then dont do anything else
	
		//add to database
		if(playerDao == null) System.out.println("dao is null in add Resource - player\n");
	
		//System.out.println(playerId);

		//only add if it isn't already there
		if(playerDao.isPlayerTech(playerId, tech.getResouceId())) {
			return -2;
		}

		else 
		{ 
			if(playerDao.addTech(tech, playerId) == -1){
				System.out.println("Adding tech is broken");
				return -2;
			} 
		}
		/*
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
		*/
		return 1;
	}

	//are we doing this?
	//need to add a playerId table

	public int sellResource(String resourceName, int numUnits) throws DaoException
	{

		//can only sell if it exists in itemlist
		if(resourceDao.isInItemList(resourceName) == false) return -1;

		String resourceType = resourceDao.getResourceType(resourceName).toString();
		Float resourcePrice = resourceDao.getResourcePrice(resourceName);
		int numAvailableUnits = playerDao.getResourceNumUnits(playerId, resourceName, resourceDao.getResourceType(resourceName).toString());
		int newNumUnits = numAvailableUnits - numUnits;

		Resource resource = new Resource(resourceName, resourceType, resourcePrice);

		//add gold (profit from resource, for right now is just the price of resource)
		//figure out how to make market work
		if (playerDao.isPlayerResource(playerId, resourceName, resourceDao.getResourceClass(resourceName))) {
			
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
				return 1;
			}

			else if (newNumUnits < 0)
			{
				System.out.println("This can't be done, not enough units available");
				return 0;
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
				return 1;
			}
			
			else return 0;
		}
		else return 0;
	}
	
	// TODO: Implement refresh()
	public boolean refresh () { return true; }

	
}