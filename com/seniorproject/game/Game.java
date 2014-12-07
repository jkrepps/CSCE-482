package com.seniorproject.game;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.lang.Math;

import org.joda.time.DateTime;
import org.joda.time.Duration;

import java.sql.Connection;

import com.seniorproject.dao.DaoException;
import com.seniorproject.dao.DaoObject;
import com.seniorproject.dao.PlayerDao;
import com.seniorproject.dao.ResourceDao;
import com.seniorproject.resource.Resource;

public class Game {

	private int id;
	private String weather; 
	private String daytime; 
	private Date startTime;
	private Date endTime;
	private int gameYears;
	private int maxPlayers;
	private List<Player> currentPlayers;
	private List<Integer> playerStatus; 
	private GameThread gameThread;
	private DaoObject dao;
	private PlayerDao playerDao;
	private ResourceDao resourceDao;
	private Duration timeUnit;
	private HashMap<String, Float> priceList;
	
	public Game(int maxPlayers, int days, int gameYears, DaoObject dao) {
		this.id = -1;
		this.currentPlayers = new ArrayList<Player>();
		this.playerStatus = new ArrayList<Integer>();
		this.maxPlayers = maxPlayers;
		Calendar cal = Calendar.getInstance();
		this.startTime = cal.getTime();
		cal.add(Calendar.DATE, days);
		this.endTime = cal.getTime();
		this.gameYears = gameYears;
		setTimeUnit();
		resourceDao = new ResourceDao(dao.getConnection());
		File logFile =  new File("com/seniorproject/" + Integer.toString(id) + ".txt");
		try{
			BufferedWriter bw = new BufferedWriter(new FileWriter(logFile.getAbsoluteFile(), true));
   			bw.write("");
   			bw.close();
   		} catch (Exception e1) {
			System.err.println(e1.getMessage());
		} 

		try {
			this.priceList = resourceDao.getResourcePrice();
		} catch (DaoException e) {
			e.printStackTrace();
			priceList = null;
		}
	
	}
	
	public Game(int id, int maxPlayers, Date startTime, Date endTime, int gameYears, String weather, Connection connection) {
		System.out.println("This is the long one: " + id);
		this.id = id;
		this.maxPlayers = maxPlayers;
		this.startTime = startTime;
		this.endTime = endTime;
		this.gameYears = gameYears;
		this.weather = weather; // placeholder
		this.currentPlayers = new ArrayList<Player>();
		this.playerStatus = new ArrayList<Integer>();
		setTimeUnit();
		File logFile =  new File("com/seniorproject/" + Integer.toString(id) + ".txt");
		try{
			BufferedWriter bw = new BufferedWriter(new FileWriter(logFile.getAbsoluteFile(), true));
	   		bw.write("");
	   		bw.close();
	   	} catch (Exception e1) {
			System.err.println(e1.getMessage());
		} 

		resourceDao = new ResourceDao(connection);
		
		try {
			this.priceList = resourceDao.getResourcePrice();
		} catch (DaoException e) {
			e.printStackTrace();
			priceList = null;
		}
		
	}
	
	private void setTimeUnit() {
		DateTime oldTime = new DateTime(startTime);
		DateTime newTime = new DateTime(endTime);
		Duration gameTime = new Duration(startTime.getTime(), endTime.getTime());
		timeUnit = new Duration(gameTime.getStandardSeconds()*1000/(gameYears*12));
		
	}
	
	public int getGameId () { return id; }
	public List<Player> getCurrentPlayers() { return currentPlayers; }
	public int getMaxPlayers() { return maxPlayers; }
	public Date getStartTime() { return startTime; }
	public Date getEndTime() { return endTime; }
	public int getGameYears() { return gameYears; }
	public String getWeather() { return weather; }
	public String getDaytime() { return daytime; }
	public GameThread getGameThread() { return gameThread; }
	
	public void setWeather(String weather) { this.weather = weather; }
	
	/**
	 * returns the price of the resource within the scope of the game
	 * @param id The id of the resource to be price checked
	 * @return price for the given resource, identified by resourceId
	 */
	public Float getPrice(String resourceName, boolean buy) {
		Float retval = priceList.get(resourceName);
		if (buy == false)
			retval *= 0.85f;
		return retval;
	}
	
	/**
	 * Function to adjust price of the resource(by id), to account for supply and demand
	 * <p>
	 * After the purchase of a given number of units, the price will be inflated to account for the demand
	 * <p>
	 * After the sale of a given number of units, the price will be deflated to account for the excess supply
	 * 
	 * @param id The Resource id of the resource in the transaction
	 * @param quantity Number of units of the resource in the transaction
	 * @param buy Will be set to true if it is being bought, false if it is being sold
	 * @return An integer flag to denote success or failure
	 */
	public int adjustPrice(String resourceName, int quantity, boolean buy) {
		Float tempPrice = priceList.get(resourceName);
		int sign = (buy) ? 1 : -1;
		tempPrice = (float) (tempPrice + sign*((0.001 + (0.01*(Math.log(quantity))))*tempPrice));
		priceList.put(resourceName, tempPrice);
		return 1;
		
	}

	
	public void startGameThread(int gameId, DaoObject dao) throws Exception {
	
		playerDao = new PlayerDao(dao.getConnection());
		gameThread = new GameThread(gameId, dao, this.timeUnit, gameYears*12);
		new Thread(gameThread).start();
	}
	public HashMap<Integer, Integer> calculatePlayerStatus(int gameId){
	int peopleInGame = 0;
	HashMap<Integer,Integer > debtStatus = new HashMap<Integer, Integer>();
	try
	{
		
		for(int i = 0; i < currentPlayers.size(); i++)
		{
			if(playerDao.getPlayerStatus(currentPlayers.get(i).getPlayerId()) == 0) 
			{
				if(playerDao.getPlayerMoney(currentPlayers.get(i).getPlayerName(), gameId) >= 0 )
				{
					playerStatus.set(i,0);
					peopleInGame++;
					debtStatus.put(currentPlayers.get(i).getPlayerId(), 0);
				}
				else if(playerDao.getPlayerMoney(currentPlayers.get(i).getPlayerName(), gameId) <= 0 )
					playerStatus.set(i,playerStatus.get(i) + 1);
				if(playerStatus.get(i) == 4 )
					playerDao.setPlayerStatus(currentPlayers.get(i).getPlayerId(), -1);
				if(currentPlayers.size() == maxPlayers && peopleInGame == 1)
				{
					for(int j = 0; j < currentPlayers.size(); j++)
					{
						if(playerDao.getPlayerStatus(currentPlayers.get(i).getPlayerId()) == 0);
						{
							playerDao.setPlayerStatus(currentPlayers.get(i).getPlayerId(), 1);
							System.out.println(currentPlayers.get(i).getPlayerName() + " WON GAME " + gameId);
						}
					}
				}
			}
			
		}
		
	} catch(Exception e) {
	System.out.println(e);
	}
	return debtStatus;
	}
	
	
	public boolean insertPlayer(Player p) {
	try
	{
		if (currentPlayers.size() == maxPlayers)
			return false;
		else {
			currentPlayers.add(p);
			if(playerDao.getPlayerStatus(p.getPlayerId()) == -1) 
				playerStatus.add(5);
			else
				playerStatus.add(0);
			return true;
		}
	} catch(Exception e) {
	System.out.println(e);
	}
	return false;
	}
	
	
	public class GameThread implements Runnable {  // client thread, each client goes through this process individually and simultaneously. 

		int gameId;
		DaoObject dao;
		Duration timeUnit;
		PlayerDao playerDao;
		World world;
		HashMap<Integer, Integer> debtStatus;
		int numTimeUnits;
		Player winner;
		
        public GameThread(int gameId, DaoObject dao, Duration timeUnit, int numTimeUnits) throws Exception {// accept client socket connection. // int id as an argument
        	this.gameId = gameId;
        	this.dao = dao;
        	this.timeUnit = timeUnit;
        	this.numTimeUnits = numTimeUnits;
        	playerDao = new PlayerDao(dao.getConnection());
        	world = new World();
        }

        @Override
        public void run() {	//start thread
        	
        	int count = 0;
			while(true)
			{	
				debtStatus = calculatePlayerStatus(gameId);
				world.SetDaytime();
				world.SetWeather();
				weather = world.GetWeather();
				daytime = world.GetDaytime();
				System.out.println("Weather for game "+gameId+" is : " + weather);
				System.out.println("Time of day for game "+gameId+" is : " + daytime);
				try
				{
				TimeUnit.SECONDS.sleep(this.timeUnit.getStandardSeconds());
				float maxIncome = 0;
				for ( Iterator<Player> iterator = currentPlayers.iterator(); iterator.hasNext(); ) {
					Player p = iterator.next();
					float netIncome = playerDao.updateAssets(p);
					
					if (netIncome > maxIncome){
						maxIncome = netIncome;
						winner = p;
					}
					
					if (playerDao.getPlayerMoney(p.getPlayerName(), gameId) < 0.0) {
						if (debtStatus.get(p.getPlayerId()) == null)
							debtStatus.put(p.getPlayerId(), 1);
						else
							debtStatus.put(p.getPlayerId(), debtStatus.get(p.getPlayerId()) + 1);
					}
					else
						debtStatus.put(p.getPlayerId(), 0);
					
					if (debtStatus.get(p.getPlayerId()) > 5) {
						playerDao.setPlayerStatus(p.getPlayerId(), -1);
						iterator.remove();
					}
					//playerDao.updatePlayerMoney(p.getPlayerName(), netIncome);
					// Player.refresh()
				}
				count++;
				if (count == numTimeUnits - 1) {
					playerDao.setPlayerStatus(winner.getPlayerId(), 1);
					break;
				}
				} catch (InterruptedException | DaoException e) {
                    e.printStackTrace();
                }
			}
        }
    }
	
	
}
