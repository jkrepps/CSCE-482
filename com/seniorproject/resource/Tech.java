package com.seniorproject.resource;


import java.util.Random;
import java.util.*;
import java.util.regex.Pattern;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;


public class Tech {

	private float resourcePrice;
	//private TechType resourceType;
	private String resourceTrace;
	private String resourceName;
	private int resourceId;
	private int resourceIcon;
	private float resourceIncome;

	Random rand = new Random();

	public Tech() {

	}
	
	//this is used in MarketDao, problem - should resourceClass be of type TechClass
	public Tech (int resourceId, String resourceName, String resourceTrace, Float resourcePrice, float resourceIncome) {
		this.resourceId = resourceId;
		this.resourceName = resourceName;
		this.resourceTrace = resourceTrace;
		this.resourcePrice = resourcePrice;
		this.resourceIncome = resourceIncome;
	}
	
	//this is used in MarketDao, problem - should resourceClass be of type TechClass
	public Tech (int resourceId, String resourceName, String resourceTrace, Float resourcePrice) {
		this.resourceId = resourceId;
		this.resourceName = resourceName;
		this.resourceTrace = resourceTrace;
		this.resourcePrice = resourcePrice;
	}

	//this is used in TechDao
	public Tech (String resourceName, String resourceTrace, Float resourcePrice) {
		this.resourceName = resourceName;
		this.resourceTrace = resourceTrace;
		this.resourcePrice = resourcePrice;
	}

	
	/* Getters and Setters */
	public float getTechPrice() { return resourcePrice; }
	//public TechType getTechType() { return resourceType; }
	public String getTechTrace() { return resourceTrace; }
	public String getTechName() { return resourceName; }
	public int getResouceId() { return resourceId; }
	public int getTechIcon() { return resourceIcon; }
	public float getTechIncome() { return resourceIncome; }

	public void setTechPrice(float resourcePrice) { this.resourcePrice = resourcePrice; }
	//public void setTechType(TechType resourceType) { this.resourceType = resourceType; }
	public void setTechTrace(String resourceTrace) { this.resourceTrace = resourceTrace; }
	public void setTechName(String resourceName) { this.resourceName = resourceName; }
	public void setTechId(int resourceId) { this.resourceId = resourceId; }
	public void setTechIcon(int resourceIcon) { this.resourceIcon = resourceIcon; }



	
	
}


