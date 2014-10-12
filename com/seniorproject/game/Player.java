package com.seniorproject.game;
import java.util.Random;


public class Player
{
	private static int NUMITEMS = 500; // for now, no idea what this number will be realistically
	
    private int gold;		//private variables
    private String name;
	private String password;
	private int land;
    Random rand = new Random();

	
    public Player(int g, String playerName, String p) { // initialization function
		
		land = 0;
        gold = g;
		name = playerName;
		password = p;
    }
    
    int[] inventory= new int[NUMITEMS];//Item[] inventory= new Item[NUMITEMS];
    
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

    public void addItem(int item) //(Item item)
	{ 
		for (int i=0;i<inventory.length;i++)
			if (inventory[i]==0)
			{
				inventory[i]=item;	
				System.out.println("new purchase");
				break;
			}
			
			
	}
	public void removeItem(int item)//(Item item) 
	{ 
		for (int i=0;i<inventory.length;i++)
			if (inventory[i]==item)
			{
				inventory[i]=0;	
				System.out.println("inventory slot removed");
				break;
			}
			
	}
    public int[] getInventory() //Item[] getInventory() 
    { 
    		return inventory;
    }
	
    public boolean isInInventory(int item)//(Item item)
    {
		for (int i=0;i<inventory.length;i++)
			if (inventory[i]==item)	return true;
		return false;	
    } 
	
   
}