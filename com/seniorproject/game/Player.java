package com.seniorproject.game;

import com.seniorproject.dao.DaoObject;
import com.seniorproject.resource.Resource;

import java.util.Random;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;


public class Player
{
	private static int NUMITEMS = 500; // for now, no idea what this number will be realistically
	
    private int gold;		//private variables
    private String name;
	private String password;
	private int land;
    private DaoObject dao;

    Random rand = new Random();



    File logFile = new File("logfile.txt"); //not sure if this is the best place to define this! Also, need to be sure file actually exists, if not, do logFile.createNewFile()
    FileWriter fw = new FileWriter(logFile.getAbsoluteFile());
    BufferedWriter bw = new BufferedWriter(fw);

	
    public Player(int g, String playerName, String p) { // initialization function
		
		land = 0;
        gold = g;
		name = playerName;
		password = p;
    }
    
    Resource[] inventory= new Resource[NUMITEMS];//Item[] inventory= new Item[NUMITEMS];
    
												/*The following are all standard object info retrieval functions.*/
    public int getGold() { return gold; }
	public int getLand() { return gold; }
    public String getName() { return name; }
	public String getPass() { return password; }
    
	public void setLand(int l) { land = l; }
	public void buyLand(int l) { land += l; }
	public void useLand(int l) { land -= l; }
    public void recieve(int d) { gold += d; }
    public void pay(int d) { gold -= d; }

    public void addResource(Resource resource)
	{ 
		//add to inventory array
		for (int i=0;i<inventory.length;i++)
			if (inventory[i]==NULL)
			{
				inventory[i]=resource;	
				System.out.println("new purchase");
				break;
			}

		//add to inventory database
		dao.executeUpdate("INSERT INTO ResourceList VALUES (\"" + resource.getResourceName() + "\", \"" + resource.getResourceClass() + "\", \"" + resource.getResourceCost() + "\");");	
			
	}

	public void removeResource(Resource resource) 
	{ 
		for (int i=0;i<inventory.length;i++)
			if (inventory[i]==resource)
			{
				inventory[i]=NULL;	
				System.out.println("inventory slot removed");
				break;
			}

		//remove from inventory database (may need to make this more specific)
		dao.executeDelete("DELETE FROM Inventory WHERE resourceName = " + resource.getResourceName);
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

    public int[] buyResource(Resource resource) //for right now returns int, in future could be different
	{
		//subtract gold (price of resource)
		gold = gold-resource.getResourceCost();

		//add to inventory (add to database!)
		inventory.addResource(resource);
		
		//publish to activity log
		bw.write(this.getName() + " purchased " + resource.getName());
		bw.close();
	}

	//are we doing this?
	public int[] sellResource(Resource resource)
	{
		//add gold (profit from resource, for right now is just the price of resource)
		//figure out how to make market work
		gold = gold + resource.getResourceCost();

		//remove from inventory (remove from database!)
		inventory.removeResource(resource);

		//publish to activity log
		//not logging name of player purchasing because it will say they purchased it
		//should we specify whether purchasing secondhand?
		bw.write(this.getName() + " sold " + resource.getResourceName());
	}
	
}