package com.seniorproject.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;

import com.seniorproject.game.Game;
import com.seniorproject.game.Player;

public class GameDao extends DaoObject{
	
	public GameDao(Connection connection) {
		this.connection = connection;
	}
	
	public List<Game> getGameList(String username) throws DaoException {
		String selectQuery = "SELECT * FROM Game WHERE id in "
				+ "(SELECT game_id from Player where user='" + username +"');";
		List<Game> returnList = new ArrayList<Game>();
		
		try {
			ResultSet resultSet = this.executeSelect(selectQuery);
			
			while(resultSet.next()) {
				Game temp = new Game(resultSet.getInt(1), resultSet.getInt(3), resultSet.getTimestamp("start_time"), resultSet.getTimestamp("end_time"), resultSet.getInt(6), resultSet.getString(7), this.getConnection());
				returnList.add(temp);
			}
		} catch (Exception e) {
			throw new DaoException("Getting list of games by user failed with:" + e.getMessage());
		}
		
		return returnList;
	}
	
	
	public List<Game> getGameList() throws DaoException {
		String selectQuery = "SELECT * FROM Game WHERE current_players<max_players;";
		List<Game> returnList = new ArrayList<Game>();
		
		try {
			ResultSet resultSet = this.executeSelect(selectQuery);
			
			while(resultSet.next()) {
				Game temp = new Game(resultSet.getInt(1), resultSet.getInt(3), resultSet.getTimestamp("start_time"), resultSet.getTimestamp("end_time"), resultSet.getInt(6), resultSet.getString(7), this.getConnection());
				returnList.add(temp);
			}
		} catch (Exception e) {
			throw new DaoException("Getting list of games by user failed with:" + e.getMessage());
		}
		
		return returnList;
	}
	
	public List<Game> getFullGameList() throws DaoException {
		String selectQuery = "SELECT * FROM Game;";
		List<Game> returnList = new ArrayList<Game>();
		
		try {
			ResultSet resultSet = this.executeSelect(selectQuery);
			
			while(resultSet.next()) {
				Game temp = new Game(resultSet.getInt(1), resultSet.getInt(3), resultSet.getTimestamp("start_time"), resultSet.getTimestamp("end_time"), resultSet.getInt(6), resultSet.getString(7), this.getConnection());
				returnList.add(temp);
			}
		} catch (Exception e) {
			throw new DaoException("Getting list of games by user failed with:" + e.getMessage());
		}
		
		return returnList;
	}
	
	
	
	public int createGame(Game game) throws DaoException {
		
		int retval = -1;
		try {
			PreparedStatement insertStatement = this.connection.prepareStatement("INSERT INTO Game VALUES (0, ?, ?, ?, ?, ?, ?);");
			insertStatement.setInt(1,game.getCurrentPlayers().size());
			insertStatement.setInt(2,game.getMaxPlayers());
			insertStatement.setTimestamp(3, new Timestamp(game.getStartTime().getTime()));
			insertStatement.setTimestamp(4, new Timestamp(game.getEndTime().getTime()));
			insertStatement.setInt(5, game.getGameYears());
			insertStatement.setString(6, game.getWeather());
			System.out.println(insertStatement);
			
			insertStatement.executeUpdate();
			String selectIdQuery = "SELECT LAST_INSERT_ID();";
			ResultSet resultSet = executeSelect(selectIdQuery);
			
			resultSet.next();
			retval = resultSet.getInt(1);
			
		} catch (SQLException e) {
			throw new DaoException("Creating Game failed with: " + e.getMessage());
		}
		
		return retval;
	}
	
	public boolean checkGame(int gameId) throws DaoException {
		String selectQuery = "SELECT current_players, max_players FROM Game WHERE id=" + gameId + ";";
		
		try {
			ResultSet resultSet = this.executeSelect(selectQuery);
			
			resultSet.next();
			
			return (resultSet.getInt(1) < resultSet.getInt(2));
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	

	
	// TODO Add and remove players from game 
	
	
}
