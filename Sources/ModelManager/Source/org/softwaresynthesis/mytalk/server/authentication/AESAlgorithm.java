package org.softwaresynthesis.mytalk.server.authentication;

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * Strategia di cripting adottando l'algoritmo AES a
 * 128 bit
 * 
 * @author 	Andrea Meneghinello
 * @version	%I%, %G%
 */
public class AESAlgorithm implements ISecurityStrategy 
{
	private static final byte[] key = {'C', 'p', '2', 'Q', 'j', 'w', 'M', 'F', '7', 'e', 'j', 'N', 't', 'd', 'b', '1'};
	private static final String algorithm = "AES";
	
	/**
	 * Codifica un stringa attraverso l'algoritmo
	 * AES a 128 bit
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @param 	plainText	{@link String} testo in chiaro
	 * 						da codificare con AES
	 * @return	{@link String} con il testo codificato
	 * @throws	{@link Exception} in caso di fallimento della codifica
	 */
	@Override
	public String encode(String plainText) throws Exception
	{
		BASE64Encoder encoder = null;
		byte[] encryptedByteArray = null;
		byte[] plainTextByteArray = plainText.getBytes();
		Cipher cipher = Cipher.getInstance(AESAlgorithm.algorithm);
		Key key = this.generateKey();
		String encryptedValue = null;
		cipher.init(Cipher.ENCRYPT_MODE, key);
		encryptedByteArray = cipher.doFinal(plainTextByteArray);
		encoder = new BASE64Encoder();
		encryptedValue = encoder.encode(encryptedByteArray);
		return encryptedValue;
	}

	/**
	 * Decodifica una stringa attraverso l'algoritmo AES
	 * a 128 bit
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @param	encodedTest	{@link String} testo codificato
	 * 						da decodificare con AEAS
	 * @return 	{@link String} con il testo decodificato
	 * @throws	{@link Exception} in caso di fallimento della decodifica
	 */
	@Override
	public String decode(String encodedTest) throws Exception
	{
		BASE64Decoder decoder = null;
		byte[] decodedByteArray = null;
		byte[] decodedValue = null;
		Cipher cipher = Cipher.getInstance(AESAlgorithm.algorithm);
		Key key = this.generateKey();
		String decryptedValue = null;
		cipher.init(Cipher.DECRYPT_MODE, key);
		decoder = new BASE64Decoder();
		decodedByteArray = decoder.decodeBuffer(encodedTest);
		decodedValue = cipher.doFinal(decodedByteArray);
		decryptedValue = new String(decodedValue);
		return decryptedValue;
	}

	private Key generateKey() throws Exception
	{
		Key key = new SecretKeySpec(AESAlgorithm.key, AESAlgorithm.algorithm);
		return key;
	}
}
