package com.seniorproject.resource;

import com.seniorproject.resource.ResourceType;

public class Worker extends Resource {

	private Float workerWages;
	private Double workerEfficiency;
	private int workerQuantity;
	private ResourceType resourceType;
	private String workerName;
	
	public Worker(String workerName, int workerQuantity, Double workerEfficiency, Float workerWages) {
		super();		
		this.workerName = workerName;
		this.workerQuantity = workerQuantity;
		this.workerEfficiency = workerEfficiency;
		this.workerWages = workerWages;		
		resourceType = ResourceType.WORKER;		
	}
	
	public String getWorkerName() { return workerName; }
	public int getWorkerQuantity() { return workerQuantity; }
	public Float getWorkerWages() { return workerWages; }
	public Double getWorkerEfficiency() { return workerEfficiency; }

}
