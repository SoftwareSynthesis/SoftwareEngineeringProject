package org.softwaresysnthesis.mytalk.server.authentication;

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Encoder;
import sun.misc.BASE64Decoder;

/**
 * Implementazione della strategia di codifica/decodifica
 * con l'uso dell'algoritmo AES a 128bit
 * 
 * @author 	Andrea Meneghinello
 * @version	%I%, %G%
 */
public class AESAlgorithm implements ISecurityStrategy 
{
	private static final byte[] keyValue = new byte[] {'C', 'p', '2', 'Q', 'j', 'w', 'M', 'F', '7', 'e', 'j', 'N', 't', 'd', 'b', '1'};
	private static final String algorithm = "AES";
	
	@Override
	public String encrypt(String plainText) throws Exception
	{
		byte[] encryptedByteArray = null;
		Cipher cipher = Cipher.getInstance(AESAlgorithm.algorithm);
		Key key = this.generateKey();
		String encryptedValue = null;
		cipher.init(Cipher.ENCRYPT_MODE, key);
		encryptedByteArray = cipher.doFinal(plainText.getBytes());
		encryptedValue = new BASE64Encoder().encode(encryptedByteArray);
		return encryptedValue;
	}

	@Override
	public String decrypt(String encryptedText) throws Exception
	{
		byte[] decordedValue = null;
		byte[] decodedValue = null;
		Cipher cipher = Cipher.getInstance(AESAlgorithm.algorithm);
		Key key = this.generateKey();
		String decryptedValue = null;
		cipher.init(Cipher.DECRYPT_MODE, key);
		decordedValue = new BASE64Decoder().decodeBuffer(encryptedText);
		decodedValue = cipher.doFinal(decordedValue);
		decryptedValue = new String(decodedValue);
		return decryptedValue;
	}
	
	private Key generateKey() throws Exception
	{
		return new SecretKeySpec(AESAlgorithm.keyValue, AESAlgorithm.algorithm);
	}
}