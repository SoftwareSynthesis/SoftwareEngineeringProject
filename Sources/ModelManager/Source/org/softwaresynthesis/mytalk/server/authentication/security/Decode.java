package org.softwaresynthesis.mytalk.server.authentication.security;

import javax.crypto.Cipher;
import sun.misc.BASE64Decoder;

/**
 * Rappresenta il passo finale dell'algoritmo
 * di crittografia AES a 128 bit. Questo passo
 * decodifica una stringa codificata in AES
 * 
 * @author 	Andrea Meneghinello
 * @version 3.0
 */
final class Decode extends CryptoTemplate 
{	
	@Override
	String completeAlgorithm(String text) throws Exception 
	{
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] buffer = decoder.decodeBuffer(text);
		Cipher cipher = super.getCipher();
		String result = null;
		cipher.init(Cipher.DECRYPT_MODE, super.getGeneratedKey());
		buffer = cipher.doFinal(buffer);
		result = new String(buffer);
		return result;
	}
}