package com.seniorproject.resource;

import com.seniorproject.resource.ResourceType;

public class Asset extends Resource{

	private String assetName;
	private int assetQuantity;
	private Float assetPrice;
	private ResourceType resourceType;
	
	public Asset(String assetName, int assetQuantity, Float assetPrice) {
		super();
		this.assetName = assetName;
		this.assetQuantity = assetQuantity;
		this.assetPrice = assetPrice;
		resourceType = resourceType.ASSET;
	}
	
	public String getAssetName() { return assetName; }
	public int getAssetQuantity() { return assetQuantity; }
	public Float getAssetPrice() { return assetPrice; }

}
