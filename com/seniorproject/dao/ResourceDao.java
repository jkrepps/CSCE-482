package com.seniorproject.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.seniorproject.resource.Resource;

public class ResourceDao extends DaoObject {
	
	public static List<Resource> getResourceList() throws DaoException {
		String selectQuery = "SELECT * FROM ResourceList;";
		List<Resource> returnList = new ArrayList<Resource>();
		
		try {
			ResultSet resultSet = executeSelect(selectQuery);
			
			while (resultSet.next()) {
				Resource temp = new Resource(resultSet.getString(2), resultSet.getString(3), resultSet.getFloat(4));
				returnList.add(temp);
			}
		} catch (Exception e) {
			throw new DaoException("Getting Resourcelist failed with: " + e.getMessage());
			
		}
		
		return returnList;
	}

}

