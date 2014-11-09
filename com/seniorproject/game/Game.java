package com.seniorproject.game;

import java.util.Date;

public class Game {

	private int id;
	private String weather; 
	private Date startTime;
	private Date endTime;
	private int gameYears;
	private int maxPlayers;
	private int currentPlayers;
	
	public Game(int id, int currentPlayers, int maxPlayers, Date startTime, Date endTime, int gameYears, String weather) {
		this.id = id;
		this.currentPlayers = currentPlayers;
		this.maxPlayers = maxPlayers;
		this.startTime = startTime;
		this.endTime = endTime;
		this.gameYears = gameYears;
		this.weather = weather; // placeholder
	}
	
	public int getId () { return id; }
	public int getCurrentPlayers() { return currentPlayers; }
	public int getMaxPlayers() { return maxPlayers; }
	public Date getStartTime() { return startTime; }
	public Date getEndTime() { return endTime; }
	public int getGameYears() { return gameYears; }
	public String getWeather() { return weather; }
	
	public void setWeather(String weather) { this.weather = weather; }
	
	
}
