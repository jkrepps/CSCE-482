package com.seniorproject.game;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import com.seniorproject.dao.DaoException;
import com.seniorproject.dao.DaoObject;
import com.seniorproject.dao.PlayerDao;

public class Game {

	private int id;
	private String weather; 
	private Date startTime;
	private Date endTime;
	private int gameYears;
	private int maxPlayers;
	private List<Player> currentPlayers;
	private GameThread gameThread;
	private DaoObject dao;
	
	public Game(int maxPlayers, int days, int gameYears) {
		this.id = -1;
		this.currentPlayers = new ArrayList<Player>();
		this.maxPlayers = maxPlayers;
		Calendar cal = Calendar.getInstance();
		this.startTime = cal.getTime();
		cal.add(Calendar.DATE, days);
		this.endTime = cal.getTime();
		this.gameYears = gameYears;
	
	}
	
	public Game(int id, int maxPlayers, Date startTime, Date endTime, int gameYears, String weather) {
		this.id = id;
		this.maxPlayers = maxPlayers;
		this.startTime = startTime;
		this.endTime = endTime;
		this.gameYears = gameYears;
		this.weather = weather; // placeholder
		this.currentPlayers = new ArrayList<Player>();
		
	}
	
	public int getGameId () { return id; }
	public List<Player> getCurrentPlayers() { return currentPlayers; }
	public int getMaxPlayers() { return maxPlayers; }
	public Date getStartTime() { return startTime; }
	public Date getEndTime() { return endTime; }
	public int getGameYears() { return gameYears; }
	public String getWeather() { return weather; }
	public GameThread getGameThread() { return gameThread; }
	
	public void setWeather(String weather) { this.weather = weather; }
	
	public void startGameThread(int gameId, DaoObject dao) throws Exception {

		gameThread = new GameThread(gameId, dao);
		new Thread(gameThread).start();
	}
	
	
	
	public boolean insertPlayer(Player p) {
	System.out.println("player p being inserted = "+ p);
		if (currentPlayers.size() == maxPlayers)
			return false;
		else {
			currentPlayers.add(p);
			return true;
		}
		
	}
	
	
	public class GameThread implements Runnable {  // client thread, each client goes through this process individually and simultaneously. 

		int gameId;
		DaoObject dao;
		PlayerDao playerDao;
		World world;
		
        public GameThread(int gameId, DaoObject dao) throws Exception {// accept client socket connection. // int id as an argument
        	this.gameId = gameId;
        	this.dao = dao;
        	playerDao = new PlayerDao(dao.getConnection());
        	world = new World();
        }

        @Override
        public void run() {	//start thread
        	
        	
			while(true)
			{
				world.SetDaytime();
				world.SetWeather();
				System.out.println("Weather is : " + world.GetWeather());
				System.out.println("Time of day is : " + world.GetDaytime());
				try
				{
				TimeUnit.SECONDS.sleep(10);
				for ( Player p : currentPlayers ) {
					float netIncome = playerDao.updateAssets(p);
					//playerDao.updatePlayerMoney(p.getPlayerName(), netIncome);
					// Player.refresh()
				}
				} catch (InterruptedException | DaoException e) {
                    e.printStackTrace();
                }
			}
        }
    }
	
	
}
