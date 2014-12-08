package com.seniorproject.resource;

public class MarketResource {
	private int id;
	private Resource resource;
	private int quantity;
	private String seller;
	private int sellerId;
	private int gameId;
	
	public MarketResource(Resource resource, int id, int quantity, String seller, int sellerId, int gameId) {
		this.resource = resource;
		this.id = id;
		this.quantity = quantity;
		this.seller = seller;
		this.sellerId = sellerId;
		this.gameId = gameId;
	}
	
	public MarketResource() {
		this.id=-1;
		this.quantity = -1;
		
	}
	
	public int getId() { return id;}
	public Resource getResource() {return resource;}
	public int getQuantity () { return quantity; }
	public String getSellerName() { return seller; }
	public int getSellerId() { return sellerId; }
	public int getGameId() {return gameId; }
	
	

}
