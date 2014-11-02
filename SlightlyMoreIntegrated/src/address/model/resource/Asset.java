package address.model.resource;

public class Asset extends Resource{
	private int quantity;
	private Float costPrice;
	
	public Asset(String name, int quantity, Float costPrice) {
		super();
		this.name = name;
		this.quantity = quantity;
		this.costPrice = costPrice;
		resourceType = "ASSET";
	}
	
	public String getName() { return name; }
	public int getQuantity() { return quantity; }
	public Float getCostPrice() { return costPrice; }

}
