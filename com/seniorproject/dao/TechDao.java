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

	public List<Tech> getTechList(Player p) throws DaoException 
	{
		List<String> techIDs = new ArrayList<String>();
		//grab string from PlayerTechs corresponding to current research levels
		List<Tech> returnList = new ArrayList<Tech>();
		String selectQuery = "SELECT Researched FROM PlayerTechs WHERE player_id = " + p.getPlayerId() + ";";
		//parse the return string into indexes that can be looped through
		try 
		{
			ResultSet resultSet = executeSelect(selectQuery);
			techIDs.add("0");
			while (resultSet.next())
			{
				techIDs.add(Integer.toString(resultSet.getInt(1)));
			}
			//create a loop for Selecting from ResourceList based on each ID
			selectQuery = "SELECT * FROM TechTree;";
			resultSet = this.executeSelect(selectQuery);
			while (resultSet.next()) 
			{
				Tech temp = new Tech(resultSet.getInt(1),resultSet.getString(2), resultSet.getString(3), resultSet.getFloat(4));
				//get trace
				String trace = temp.getTechTrace();
				//delimit
				String delim = ",";
				String[] tokens = trace.split(delim);
				boolean hasReqTech = true;
				boolean forcheck = false;
				for(int i = 0; i < tokens.length; i++)
				{
					for(String str: techIDs) 
					{
						if(str.trim().contains(tokens[i]))
							forcheck = true;
					}
					if(forcheck == false)
						hasReqTech = false;
				}
				if(hasReqTech)
				{	//IF doesnt already own
					boolean owns = false;
					for(String str: techIDs) 
					{
						if(str.trim().contains(Integer.toString(temp.getResouceId())))
							owns = true;
					}
					if(!owns)
						returnList.add(temp);
				}
			}
			
		} catch (Exception e) {
			throw new DaoException("Getting Resourcelist failed with: " + e.getMessage());
			
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

