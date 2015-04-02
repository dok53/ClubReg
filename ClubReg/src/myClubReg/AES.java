package myClubReg;
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.*;

public class AES {

	private static final String ALGO = "AES";
	//Get key from database and split it into bytes
	private static final byte[] keyValue = 
			new byte[] {'C', 'l', 'u', 'b', 'R', 'e', 'g','D', 'e', 'c', 'E','n', 'c', 'K', 'e', 'y'};

	public static String encrypt(String data) throws Exception {
		Key key = generateKey();
		Cipher c = Cipher.getInstance(ALGO);
		c.init(Cipher.ENCRYPT_MODE, key);
		byte[] encVal = c.doFinal(data.getBytes("UTF-8"));
		String encryptedValue = new BASE64Encoder().encode(encVal);
		return encryptedValue;
	}

	public static String decrypt(String encryptedData) throws Exception {
		Key key = generateKey();
		Cipher c = Cipher.getInstance(ALGO);
		c.init(Cipher.DECRYPT_MODE, key);
		byte[] decordedValue = new BASE64Decoder().decodeBuffer(encryptedData);
		byte[] decValue = c.doFinal(decordedValue);
		String decryptedValue = new String(decValue);
		return decryptedValue;
	}
	private static Key generateKey() throws Exception {
		Key key = new SecretKeySpec(keyValue, ALGO);
		return key;
	}

}
