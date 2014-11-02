package com.seniorproject.resource;

public class Asset extends Resource{

	privat String assetName;
	private int assetQuantity;
	private Float assetPrice;
	
	public Asset(String assetName, int assetQuantity, Float assetPrice) {
		super();
		this.assetName = assetName;
		this.assetQuantity = assetQuantity;
		this.assetPrice = assetPrice;
		resourceType = "ASSET";
	}
	
	public String getAssetName() { return assetName; }
	public int getAssetQuantity() { return assetQuantity; }
	public Float getAssetPrice() { return assetPrice; }

}
