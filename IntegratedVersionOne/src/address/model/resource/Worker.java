package address.model.resource;


public class Worker extends Resource {

	private String workerType;
	private Float wages;
	private Double efficiency;
	private int quantity;
	
	public Worker(String name, int quantity, Double efficiency, Float wages) {
		super();		
		this.name = name;
		this.quantity = quantity;
		this.efficiency = efficiency;
		this.wages = wages;		
		resourceType = "WORKER";		
	}
	
	public String getName() { return name; }
	public int getQuantity() { return quantity; }
	public Float getWages() { return wages; }
	public Double getEfficiency() { return efficiency; }

}
