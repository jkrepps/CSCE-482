package address.model.server;

@SuppressWarnings("serial")
public class ServerException extends Exception {
	
	private String message = null;
	
	public ServerException() {
		super();
	}
	
	public ServerException(String message) {
		super(message);
		this.message = message;
	}
	
	public ServerException (Throwable cause) {
		super(cause);
	}
	
	@Override
	public String toString() {
		return message;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
	

}
