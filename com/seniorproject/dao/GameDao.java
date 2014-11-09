package com.seniorproject.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;

import com.seniorproject.game.Game;

public class GameDao extends DaoObject{
	
	public static List<Game> getGameList(String username) throws DaoException {
		String selectQuery = "SELECT * FROM Game WHERE id in "
				+ "(SELECT game_id from Player where user='" + username +"');";
		List<Game> returnList = new ArrayList<Game>();
		
		try {
			ResultSet resultSet = executeSelect(selectQuery);
			
			while(resultSet.next()) {
				Game temp = new Game(resultSet.getInt(1),resultSet.getInt(2), resultSet.getInt(3), resultSet.getTimestamp("start_time"), resultSet.getTimestamp("end_time"), resultSet.getInt(6), resultSet.getString(7));
				returnList.add(temp);
			}
		} catch (Exception e) {
			throw new DaoException("Getting list of games by user failed with:" + e.getMessage());
		}
		
		return returnList;
	}
	
	public static int createGame(Game game) throws DaoException {
		
		int retval = -1;
		try {
			PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO Game VALUES (0, ?, ?, ?, ?, ?");
			insertStatement.setInt(1,game.getCurrentPlayers());
			insertStatement.setInt(2,game.getMaxPlayers());
			insertStatement.setTimestamp(3, new Timestamp(game.getStartTime().getTime()));
			insertStatement.setTimestamp(4, new Timestamp(game.getEndTime().getTime()));
			insertStatement.setInt(5, game.getGameYears());
			insertStatement.setString(6, game.getWeather());
			
			retval = insertStatement.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return retval;
	}
	
	// TODO Add and remove players from game 
	
	
}
