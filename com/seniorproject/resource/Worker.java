package com.seniorproject.resource;


public class Worker extends Resource {

	private String workerName;
	private String workerType;
	private Float workerWages;
	private Double workerEfficiency;
	private int workerQuantity;
	
	public Worker(String workerName, int workerQuantity, Double workerEfficiency, Float workerWages) {
		super();		
		this.workerName = workerName;
		this.workerQuantity = workerQuantity;
		this.workerEfficiency = workerEfficiency;
		this.workerWages = workerWages;		
		resourceType = "WORKER";		
	}
	
	public String getWorkerName() { return workerName; }
	public int getWorkerQuantity() { return workerQuantity; }
	public Float getWorkerWages() { return workerWages; }
	public Double getWorkerEfficiency() { return workerEfficiency; }

}
