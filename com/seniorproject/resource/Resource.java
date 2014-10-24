package com.seniorproject.resource;


public class Resource {

	protected String name;
	private Float cost;
	protected String resourceType;
	private ResourceClassification resourceClassification;
	
	public Resource() { cost = null; resourceClassification = null;}


	public float getCost() { return cost; }
	
	public String getType() { return resourceType; }
	
	public ResourceClassification getClassificaiton() { return resourceClassification; }
	
}


