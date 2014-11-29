package com.seniorproject.game;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.joda.time.DateTime;
import org.joda.time.Duration;

import com.seniorproject.dao.DaoException;
import com.seniorproject.dao.DaoObject;
import com.seniorproject.dao.PlayerDao;

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
	private Duration timeUnit;
	
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
		setTimeUnit();
	
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
		setTimeUnit();
		
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
					
					if (playerDao.getPlayerMoney(p.getPlayerName(), gameId) < 0.0)
						debtStatus.put(p.getPlayerId(), debtStatus.get(p.getPlayerId()) + 1);
					else
						debtStatus.put(p.getPlayerId(), 0);
					
					for (Map.Entry<Integer, Integer> entry: debtStatus.entrySet()){
						if (entry.getValue() > 6) {
							//Player has lost
							playerDao.setPlayerStatus(p.getPlayerId(), -1);
							iterator.remove();
							// TODO maybe a message here?
						}
						
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
