package com.seniorproject.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;

import com.seniorproject.resource.Resource;

public class ResourceDao extends DaoObject {
	
	public ResourceDao(Connection connection) {
		this.connection = connection;
	}
	public List<Resource> getResourceList() throws DaoException {
		//DaoObject dao = new DaoObject();
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

}

