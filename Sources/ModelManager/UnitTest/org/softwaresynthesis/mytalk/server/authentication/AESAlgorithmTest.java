package org.softwaresynthesis.mytalk.server.authentication;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

public class AESAlgorithmTest {
	private static AESAlgorithm tester = new AESAlgorithm();
	private static String plainText;
	
	@BeforeClass
	public static void setUpBeforeClass()
	{
		tester = new AESAlgorithm();
		plainText = "pippo";
	}

	@Test
	public void testEncryptAndDecrypt()
	{
		try 
		{
		String encryptedText = tester.encrypt(plainText);
		assertTrue(plainText.equals(tester.decrypt(encryptedText)));
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
}
