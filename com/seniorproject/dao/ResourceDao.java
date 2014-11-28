package com.seniorproject.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;

import com.seniorproject.resource.Resource;
import com.seniorproject.resource.*;
import com.seniorproject.resource.ResourceType;
import com.seniorproject.game.Player;

public class ResourceDao extends DaoObject {
	
	public ResourceDao(Connection connection) {
		this.connection = connection;
	}

	public List<Resource> getResourceList(Player p) throws DaoException {
		List<Integer> techIDs = new ArrayList<Integer>();
		//grab string from PlayerTechs corresponding to current research levels
		List<Resource> returnList = new ArrayList<Resource>();
		String selectQuery = "SELECT Researched FROM PlayerTechs WHERE player_id = " + p.getPlayerId() + ";";
		//parse the return string into indexes that can be looped through
		try 
		{
			ResultSet resultSet = executeSelect(selectQuery);
			techIDs.add(0);
			while (resultSet.next())
			{
				techIDs.add(resultSet.getInt(1));
			}
			//create a loop for Selecting from ResourceList based on each ID
			for(int i = 0; i < techIDs.size(); i++)
			{
				selectQuery = "SELECT * FROM ResourceList WHERE tech_restrict = " + techIDs.get(i) + ";";
				resultSet = this.executeSelect(selectQuery);
				while (resultSet.next()) 
				{
					Resource temp = new Resource(resultSet.getString(2), resultSet.getString(3), resultSet.getFloat(4));
					returnList.add(temp);
				}
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
	public Boolean isInTechList(String resourceName) throws DaoException {
		String selectQuery = "SELECT tech_id FROM TechTree WHERE name = '" + resourceName + "';";
		try {
			ResultSet resultSet = this.executeSelect(selectQuery);
			if(resultSet.next()) { return true; }
		} catch (Exception e) {
			throw new DaoException("Getting resource type failed with: " +  e.getMessage());
		}
		return false;
	}
	
	public Boolean isInPlayerItemList(String resourceName, Player p) throws DaoException {
		
		try {
		List<Resource> resources = getResourceList(p);
		for(int i = 0; i < resources.size(); i++) 
		{
			if(resources.get(i).getResourceName().equals(resourceName))
				return true;
		}
		} catch (Exception e) {
			throw new DaoException("Getting resource type failed with: " +  e.getMessage());
		}
		return false;
	}
	
	public Boolean isInPlayerTechList(String resourceName, Player p) throws DaoException 
	{
		TechDao techDao = new TechDao(connection);
		try {
		List<Tech> resources = techDao.getTechList(p);
		for(int i = 0; i < resources.size(); i++) 
		{
			if(resources.get(i).getTechName().equals(resourceName))
				return true;
		}
		} catch (Exception e) {
			throw new DaoException("Getting resource type failed with: " +  e.getMessage());
		}
		return false;
	}
	public Tech getTech(String resourceName) throws DaoException {
		String selectQuery = "SELECT * FROM TechTree WHERE name = '" + resourceName + "';";
		try {
			ResultSet resultSet = this.executeSelect(selectQuery);
			if(resultSet.next()) { return new Tech(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3),resultSet.getFloat(4)); }
		} catch (Exception e) {
			throw new DaoException("Getting resource type failed with: " +  e.getMessage());
		}
		return null;
	}
	
	public Boolean isInPlayerItemList(String resourceName) throws DaoException {
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
	public String getResourceIncome(String ResourceName) throws DaoException {
		String selectQuery = "SELECT income_type FROM ResourceList WHERE name = '" + ResourceName + "';";
		String resourceIncome = "";
		try {
			ResultSet resultSet = this.executeSelect(selectQuery);
			if(resultSet.next())
			{
				resourceIncome = resultSet.getString(1);
			}
			else
			{
				return "";
			}
		} catch (Exception e) {
			throw new DaoException("Getting resource income type failed with: " + e.getMessage());
		}
		return resourceIncome;
	}
	public int getResourceWorkerNum(String ResourceName) throws DaoException {
		String selectQuery = "SELECT num_Workers FROM ResourceList WHERE name = '" + ResourceName + "';";
		int resourcePrice = 0;
		try {
			ResultSet resultSet = this.executeSelect(selectQuery);
			if(resultSet.next())
			{
				resourcePrice = resultSet.getInt(1);
			}
			else
			{
				return 0;
			}
		} catch (Exception e) {
			throw new DaoException("Getting resource price failed with: " + e.getMessage());
		}
		return resourcePrice;
	}
	public String getResourceWorkerName(String ResourceName) throws DaoException {
		String selectQuery = "SELECT workerReq FROM ResourceList WHERE name = '" + ResourceName + "';";
		String resourcePrice = "";
		try {
			ResultSet resultSet = this.executeSelect(selectQuery);
			if(resultSet.next())
			{
				resourcePrice = resultSet.getString(1);
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
	public String getResourceClass(String ResourceName) throws DaoException {
		String selectQuery = "SELECT type FROM ResourceList WHERE name = '" + ResourceName + "';";
		String resourcePrice = "";
		try {
			ResultSet resultSet = this.executeSelect(selectQuery);
			if(resultSet.next())
			{
				resourcePrice = resultSet.getString(1);
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
	public int getResourceLand(String ResourceName) throws DaoException {
		String selectQuery = "SELECT space FROM ResourceList WHERE name = '" + ResourceName + "';";
		int resourcePrice = 0;
		try {
			ResultSet resultSet = this.executeSelect(selectQuery);
			if(resultSet.next())
			{
				resourcePrice = resultSet.getInt(1);
			}
			else
			{
				return 0;
			}
		} catch (Exception e) {
			throw new DaoException("Getting resource land failed with: " + e.getMessage());
		}
		return resourcePrice;
	}
	public int getResourceIncomeAmount(String ResourceName) throws DaoException {
		String selectQuery = "SELECT income FROM ResourceList WHERE name = '" + ResourceName + "';";
		int resourcePrice = 0;
		try {
			ResultSet resultSet = this.executeSelect(selectQuery);
			if(resultSet.next())
			{
				resourcePrice = resultSet.getInt(1);
			}
			else
			{
				return 0;
			}
		} catch (Exception e) {
			throw new DaoException("Getting resource Income Amount failed with: " + e.getMessage());
		}
		return resourcePrice;
	}
}
