package org.softwaresynthesis.mytalk.server.authentication.security;

import javax.crypto.Cipher;
import sun.misc.BASE64Encoder;

/**
 * Rappresenta il passo finale dell'algoritmo
 * di crittografia AES a 128 bit. Questo passo
 * codifica utilizzando AES a 128 bit
 * 
 * @author 	Andrea Meneghinello
 * @version 3.0
 */
final class AESEncode extends AESTemplate 
{	
	@Override
	protected final String completeAlgorithm(String text) throws Exception 
	{
		BASE64Encoder encoder = new BASE64Encoder();
		byte[] buffer = text.getBytes();
		Cipher cipher = super.getCipher();
		String result = null;
		cipher.init(Cipher.ENCRYPT_MODE, super.getGeneratedKey());
		buffer = cipher.doFinal(buffer);
		result = encoder.encode(buffer);
		return result;
	}
}