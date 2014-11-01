package address.model.resource;

// I couldn't work out how to invoke String enums so I am using a string to store the type for now
// TODO use this enum in place of the String type for the class Resource
public enum ResourceType {
	INFRA("INFRA"),
	WORKER("WORKER"),
	ASSET("ASSET")
	;
	
	private final String name;
	
	private ResourceType(final String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
