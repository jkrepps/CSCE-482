package address.model.game;

import address.model.dao.DaoObject;
import address.model.resource.Resource;

import java.util.Random;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.net.*;
import java.io.*;


public class Player
{
	private static int NUMITEMS = 500; // for now, no idea what this number will be realistically
	
    private Float gold;		//private variables
    private String playerName;
    private int playerId;
	private String password;
	private int land;
    private DaoObject dao;

    private int resourceID = 0;

    Random rand = new Random();

    //right now not closing the file - needs to be closed!!!!
    File logFile;
    FileWriter fw;
    BufferedWriter bw;

    public Player(int playerId, Float gold, String playerName, String password) { // initialization function
		land = 0;
		this.playerId = playerId;
        this.gold = gold;
		this.playerName = playerName;
		this.password = password;

		try {
   			logFile = new File("logfile.txt"); //not sure if this is the best place to define this! Also, need to be sure file actually exists, if not, do logFile.createNewFile()
		} catch (Exception e1) {
			System.err.println(e1.getMessage());
		} 
    }
    
    Resource[] inventory= new Resource[NUMITEMS];//Item[] inventory= new Item[NUMITEMS];
    
												/*The following are all standard object info retrieval functions.*/
    public Float getGold() { return gold; }
	public int getLand() { return land; }
    public String getPlayerName() { return playerName; }
	public String getPass() { return password; }
    
	public void setLand(int l) { land = l; }
	public void buyLand(int l) { land += l; }
	public void useLand(int l) { land -= l; }
    public void recieve(Float d) { gold += d; }
    public void pay(Float d) { gold -= d; }

    public void setId(int id) {playerId = id; }

    public void addResource(Resource resource)
	{ 
		//add to inventory array
		for (int i=0;i<inventory.length;i++)
			if (inventory[i]==null)
			{
				inventory[i]=resource;	
				System.out.println("new purchase");
				break;
			}

		//add to inventory database
		try {
			dao.executeUpdate("INSERT INTO PlayerResource VALUES (\"" + playerId + "\", \"" + resourceID + "\", \"" + resource.getResourceName() + "\", \"" + resource.getResourceClass() + "\", \"" + resource.getResourceCost() + "\");");	
		} catch (Exception e1) {
			System.err.println(e1.getMessage());
		} 

		//automatically generate
		resourceID ++;
	}

	public void removeResource(Resource resource) 
	{ 
		for (int i=0;i<inventory.length;i++)
			if (inventory[i]==resource)
			{
				inventory[i]=null;	
				System.out.println("inventory slot removed");
				break;
			}

		//remove from inventory database (may need to make this more specific)
		try {
			dao.executeDelete("DELETE FROM PlayerResource WHERE resourceName = \"" + resource.getResourceName() + "\" AND playerId = " + this.playerId + ";");
		} catch (Exception e1) {
			System.err.println(e1.getMessage());
		} 
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


    //buy resource?
	// array of three types of resource
	// buy resource, then figure out which kind of resource, then what to do based on 
	// could just take in the string of a 

	//need to add a playerId

    public boolean buyResource(String resourceName, String resourceClass, Float resourceCost) //for right now returns int, in future could be different
	{
		Resource resource = new Resource(resourceName, resourceClass, resourceCost);
		//subtract gold (price of resource)
		if(gold - resource.getResourceCost() > 0)
			gold = gold - resource.getResourceCost();
		else return false; // if you dont have enough money then dont do anything else

		//add to inventory (add to database!)
		this.addResource(resource);
		
		//publish to activity log
		try {
			fw = new FileWriter(logFile.getAbsoluteFile());
   			bw = new BufferedWriter(fw);
			bw.write(this.getPlayerName() + " purchased " + resource.getResourceName());
			bw.close();
		} catch (Exception e1) {
			System.err.println(e1.getMessage());
		} 
		return true;
	}

	//are we doing this?
	//need to add a playerId table

	public boolean sellResource(String resourceName, String resourceClass, Float resourceCost)
	{
		Resource resource = new Resource(resourceName, resourceClass, resourceCost);
		//add gold (profit from resource, for right now is just the price of resource)
		//figure out how to make market work
		gold = gold + resource.getResourceCost();

		//remove from inventory (remove from database!)
		this.removeResource(resource);

		//publish to activity log
		//not logging name of player purchasing because it will say they purchased it
		//should we specify whether purchasing secondhand?
		try {
			fw = new FileWriter(logFile.getAbsoluteFile());
   			bw = new BufferedWriter(fw);
			bw.write(this.getPlayerName() + " sold " + resource.getResourceName());
			bw.close();
		} catch (Exception e1) {
			System.err.println(e1.getMessage());
		} 
		return true;
	}
	
}