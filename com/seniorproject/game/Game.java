package com.seniorproject.game;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import com.seniorproject.dao.DaoException;
import com.seniorproject.dao.DaoObject;
import com.seniorproject.dao.*;

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
	
	public Game(int maxPlayers, int days, int gameYears) {
		this.id = -1;
		this.currentPlayers = new ArrayList<Player>();
		this.playerStatus = new ArrayList<Integer>();
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
		this.playerStatus = new ArrayList<Integer>();
		
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
	
	public void startGameThread(int gameId, DaoObject dao) throws Exception {
	
		playerDao = new PlayerDao(dao.getConnection());
		gameThread = new GameThread(gameId, dao);
		new Thread(gameThread).start();
	}
	public void calculatePlayerStatus(int gameId){
	int peopleInGame = 0;
	try
	{
		
		for(int i = 0; i < currentPlayers.size(); i++)
		{
			if(playerDao.getPlayerMoney(currentPlayers.get(i).getPlayerName(), gameId) >= 0 )
			{
				playerStatus.set(i,0);
				peopleInGame++;
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
	} catch(Exception e) {
	System.out.println(e);
	}
	}
	
	
	public boolean insertPlayer(Player p) {
	try
	{
		if (currentPlayers.size() == maxPlayers)
			return false;
		else {
			currentPlayers.add(p);
			System.out.println("here");
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
				calculatePlayerStatus(gameId);
				world.SetDaytime();
				world.SetWeather();
				weather = world.GetWeather();
				daytime = world.GetDaytime();
				System.out.println("Weather for game "+gameId+" is : " + weather);
				System.out.println("Time of day for game "+gameId+" is : " + daytime);
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
