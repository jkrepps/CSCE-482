package com.seniorproject.resource;

import com.seniorproject.resource.ResourceType;

public class Infra extends Resource {

	private String infraName;
	private int infraQuantity;
	private float infraSize;	
	private double infraEfficiency;
	private Float infraPrice;
	private ResourceType resourceType;
	
	public Infra(String infraName, int infraQuantity, Float infraPrice, Float infraSize, Double infraEfficiency) {
		super();
		this.infraName = infraName;
		this.infraQuantity = infraQuantity;
		this.infraPrice = infraPrice;
		this.infraSize = infraSize;
		this.infraEfficiency = infraEfficiency;
		resourceType = resourceType.INFRA;
	}
	
	public String getInfraName() { return infraName; }
	public int getInfraQuantity() { return infraQuantity; }
	public Float getInfraPrice() { return infraPrice; }
	public Double getInfraEfficiency() { return infraEfficiency;}
	public Float getInfraSize() { return infraSize; }
	
}
