package com.seniorproject.resource;

public class Infra extends Resource {

	private String name;
	private int quantity;
	private float size;	
	private double efficiency;
	
	public Infra(String name, int quantity, Float costPrice, Float size, Double efficiency) {
		super();
		this.name = name;
		this.quantity = quantity;
		this.cost = costPrice;
		this.size = size;
		this.efficiency = efficiency;
		resourceType = "INFRA";
	}
	
	public String getName() { return name; }
	public int getQuantity() { return quantity; }
	public Float getCostPrice() { return cost; }
	public Double getEfficiency() { return efficiency;}
	public Float getSize() { return size; }
	
}
