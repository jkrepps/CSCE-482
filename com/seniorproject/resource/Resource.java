package com.seniorproject.resource;


public class Resource {

	protected int id;
	protected String name;
	protected Float cost;
	protected String resourceType;
	private ResourceClassification resourceClassification;
	
	public Resource() { cost = null; resourceClassification = null;}
	
	public Resource(String name, String resourceType, Float cost) {
		this.id = -1;
		this.name = name;
		this.resourceType = resourceType;
		this.cost = cost;
	}
	
	public Resource(int id,String name, String resourceType, Float cost) {
		this.id = id;
		this.name = name;
		this.resourceType = resourceType;
		this.cost = cost;
	}

	public String getName() { return name; }

	public float getCost() { return cost; }
	
	public String getType() { return resourceType; }
	
	public ResourceClassification getClassificaiton() { return resourceClassification; }
	
}


