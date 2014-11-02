package address.model.resource;

public class Infrastructure extends Resource {

	public Infrastructure(int resourceId, String fileName) {
		super(resourceId, fileName);
	}

	private int availableLand;
	
	private double efficiency;
}
