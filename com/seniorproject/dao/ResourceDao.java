package com.seniorproject.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;

import com.seniorproject.resource.Resource;
import com.seniorproject.resource.ResourceType;
import com.seniorproject.game.Player;

public class ResourceDao extends DaoObject {
	
	public ResourceDao(Connection connection) {
		this.connection = connection;
	}

	public List<Resource> getResourceList(Player p) throws DaoException {
		//grab string from PlayerTechs corresponding to current research levels
			///String selectQuery = "SELECT currentResearch FROM PlayerTechs WHERE player_id = " + p.getPlayerId() + ";";
		//parse the return string into indexes that can be looped through
			///String PossibleIDs = executeSelect(selectQuery);
			///String Delim = ","
			///String IDS[] = PossibleIDs.split(Delim);
		//create a loop for Selecting from ResourceList based on each ID
			/**for(int i = 0; i < IDS.length(); i++)
			{
				String selectQuery = "SELECT * FROM ResourceList WHERE RequiredTech = " + IDS[i] + ";";
			}
			*/
		
		String selectQuery = "SELECT * FROM ResourceList;";
		List<Resource> returnList = new ArrayList<Resource>();
		try {
			ResultSet resultSet = this.executeSelect(selectQuery);
			while (resultSet.next()) {
				//System.out.println("hi");
				Resource temp = new Resource(resultSet.getString(2), resultSet.getString(3), resultSet.getFloat(4));
				returnList.add(temp);
			}
		} catch (Exception e) {
			throw new DaoException("Getting Resourcelist failed with: " + e.getMessage());
			
		}
		
		return returnList;
	}

	public Float getResourcePrice(String ResourceName) throws DaoException {
		String selectQuery = "SELECT price FROM ResourceList WHERE name = '" + ResourceName + "';";
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
			throw new DaoException("Getting resource price failed with: " + e.getMessage());
		}

		return resourcePrice;
	}

	public Boolean isInItemList(String resourceName) throws DaoException {
		String selectQuery = "SELECT id FROM ResourceList WHERE name = '" + resourceName + "';";
		try {
			ResultSet resultSet = this.executeSelect(selectQuery);
			if(resultSet.next()) { return true; }
		} catch (Exception e) {
			throw new DaoException("Getting resource type failed with: " +  e.getMessage());
		}
		return false;
	}

	public ResourceType getResourceType(String ResourceName) throws DaoException {
		String selectQuery = "SELECT type FROM ResourceList WHERE name = '" + ResourceName + "';";
		ResourceType resourceType = null;
		String tempResourceType = null;
		try {
			ResultSet resultSet = this.executeSelect(selectQuery);
			if(resultSet.next())
			{
				tempResourceType = resultSet.getString(1);
			}
			
			else
			{
				return null;
			}
			
			if (tempResourceType.equals("ASSET")) {resourceType = ResourceType.ASSET;}
			
			else if (tempResourceType.equals("INFRA")) {resourceType = ResourceType.INFRA;}
			
			else if (tempResourceType.equals("WORKER")) {resourceType = ResourceType.WORKER;}
		
		} catch (Exception e) {
			throw new DaoException("Getting resource type failed with: " +  e.getMessage());
		}
		return resourceType;
	}

}

