package address.model.server;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Encoder;

public class PasswordService {
	
	private static PasswordService instance;
	
	public synchronized String encrypt(String plaintextPass) throws ServerException {
		MessageDigest md = null;
		
		try {
			md = MessageDigest.getInstance("SHA");
		}
		catch (NoSuchAlgorithmException e) {
			throw new ServerException("Encryption of password failed with: "+e.getMessage());  
		}
		
		try {
			md.update(plaintextPass.getBytes("UTF-8"));
		}
		catch (UnsupportedEncodingException e) {
			throw new ServerException("Encryption of password failed with: "+e.getMessage());  
		}
		
		byte raw[] = md.digest();
		String postHash = (new BASE64Encoder().encode(raw));
		
		return postHash;
	}

}
