package com.seniorproject.resource;

import com.seniorproject.resource.ResourceClassification;
import com.seniorproject.resource.ResourceType;

import java.util.Random;
import java.util.*;
import java.util.regex.Pattern;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;


public class Resource {

	private float resourcePrice;
	private ResourceType resourceType;
	private String resourceClass;
	private String resourceName;
	private int resourceId;
	private int resourceIcon;
	private float resourceIncome;

	Random rand = new Random();

	public Resource() {

	}
	
	//this is used in MarketDao, problem - should resourceClass be of type ResourceClass
	public Resource (int resourceId, String resourceName, String resourceClass, Float resourcePrice, float resourceIncome) {
		this.resourceId = resourceId;
		this.resourceName = resourceName;
		this.resourceClass = resourceClass;
		this.resourcePrice = resourcePrice;
		this.resourceIncome = resourceIncome;
	}
	
	//this is used in MarketDao, problem - should resourceClass be of type ResourceClass
	public Resource (int resourceId, String resourceName, String resourceClass, Float resourcePrice) {
		this.resourceId = resourceId;
		this.resourceName = resourceName;
		this.resourceClass = resourceClass;
		this.resourcePrice = resourcePrice;
	}

	//this is used in ResourceDao
	public Resource (String resourceName, String resourceClass, Float resourcePrice) {
		this.resourceName = resourceName;
		this.resourceClass = resourceClass;
		this.resourcePrice = resourcePrice;
	}

	
	/* Getters and Setters */
	public float getResourcePrice() { return resourcePrice; }
	public ResourceType getResourceType() { return resourceType; }
	public String getResourceClass() { return resourceClass; }
	public String getResourceName() { return resourceName; }
	public int getResouceId() { return resourceId; }
	public int getResourceIcon() { return resourceIcon; }
	public float getResourceIncome() { return resourceIncome; }

	public void setResourcePrice(float resourcePrice) { this.resourcePrice = resourcePrice; }
	public void setResourceType(ResourceType resourceType) { this.resourceType = resourceType; }
	public void setResourceClass(String resourceClass) { this.resourceClass = resourceClass; }
	public void setResourceName(String resourceName) { this.resourceName = resourceName; }
	public void setResourceId(int resourceId) { this.resourceId = resourceId; }
	public void setResourceIcon(int resourceIcon) { this.resourceIcon = resourceIcon; }

	boolean isAsset() {
		if (resourceType == ResourceType.ASSET) {
			return true;
		}

		else return false;
	}

	boolean isInfra() {
		if (resourceType == ResourceType.INFRA) {
			return true;
		}

		else return false;
	}

	boolean isWorker() {
		if (resourceType == ResourceType.WORKER) {
			return true;
		}

		else return false;
	}


	/*Pretty complicated parsing function, shouldnt have much use when we move data storage to a more appropriate medium, this shouldn't cause a problem and I can explain if needed.*/
	public String[][] readFileToArray(String fileName, String delimiter) {
		String dataStr = "";
		
		String[] lineArray;
		
		int numLines = 0;
		int numCols = 0;
		
		//delimiter = "\t";
		
		try {
			InputStream is = getClass().getClassLoader().getResourceAsStream(fileName);
		
			Scanner s = new Scanner(is).useDelimiter("\\A");
			dataStr = s.hasNext() ? s.next() : "";
			
			s.close();
			is.close();
			
		} catch (Exception e) {
			System.out.println("couldn't get " + fileName); 
			e.printStackTrace();
		}
		
		
		
		dataStr += "\n";
		
		dataStr.replace("\r","");
				
			numLines = dataStr.split("\n").length;
			String[] colCountArray = dataStr.split("\n");
			
			for(int i = 0; i < colCountArray.length; i++) {
				int cols = colCountArray[i].split(delimiter).length;
				
				if(cols > numCols) numCols = cols;
				
			}


		String[][] lines = new String[numLines][numCols];
		String temp = "";
		int nline = 0;
		for (int i=0; i < dataStr.length(); i++) {
			char c = dataStr.charAt(i);
			if(c == '\n') {
				Pattern pattern = Pattern.compile(Pattern.quote(delimiter));
				temp = temp.replace("\n", "");
				lineArray = pattern.split(temp);
				
				if(!(temp.length() < 1 || lineArray.length == 1 || temp.length() == 0 || temp.charAt(0) == '/')) {
					lines[nline] = lineArray;
					nline++;
				}
				temp = "";
			}
			temp += c;
		}
		//System.out.println("nline " + nline + " numCols " + numCols);
		//System.out.println(Arrays.deepToString(lines));
		String[][] linesClean = new String[nline][numCols];
		
		int y = 0;
		for(int j = 0; j < lines.length; j++) {
			//System.out.println("lines[j][0] " + lines[j][0]);
				if(lines[j][0] != null) {
					linesClean[y] = lines[j];
					y++;
				}
		}	
		//System.out.println("y " + y);
		//System.out.println(Arrays.deepToString(linesClean));
		return linesClean;
	}
	
}


