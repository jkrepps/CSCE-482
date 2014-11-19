package com.seniorproject.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;

import com.seniorproject.resource.Tech;
//import com.seniorproject.resource.TechType;
import com.seniorproject.game.Player;

public class TechDao extends DaoObject {
	
	public TechDao(Connection connection) {
		this.connection = connection;
	}

	public List<Tech> getTechList(Player p) throws DaoException {
		//DaoObject dao = new DaoObject();
		String selectQuery = "SELECT * FROM TechTree;";
		List<Tech> returnList = new ArrayList<Tech>();
		try {
			ResultSet resultSet = this.executeSelect(selectQuery);
			while (resultSet.next()) {
				//System.out.println("hi");
				Tech temp = new Tech(resultSet.getString(2), resultSet.getString(3), resultSet.getFloat(4));
				returnList.add(temp);
			}
		} catch (Exception e) {
			throw new DaoException("Getting TechTree failed with: " + e.getMessage());
			
		}
		
		return returnList;
	}
	
	public Float getTechPrice(String TechName) throws DaoException {
		String selectQuery = "SELECT price FROM TechTree WHERE name = '" + TechName + "';";
		Float resourcePrice = -1.0f;

		try {
			ResultSet resultSet = this.executeSelect(selectQuery);
			if(resultSet.next())
			{
				resourcePrice = resultSet.getFloat(1);
			}
			else
			{
				return null;
			}
			
		} catch (Exception e) {
			throw new DaoException("Getting TechTree resource price failed with: " + e.getMessage());
		}

		return resourcePrice;
	}

	

}

