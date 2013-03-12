package org.softwaresynthesis.mytalk.server.authentication;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test per l'agoritmo di crittografia AES
 * utilizzato dal sistema mytalk
 * 
 * @author 	Andrea Meneghinello
 * @version	%I%, %G%
 */
public class AESAlgorithmTest
{
	private static AESAlgorithm tester;
	private static String plainText;
	
	/**
	 * Inizializzazione degli oggetti utilizzati
	 * per il test
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 */
	@BeforeClass
	public static void setUpBeforeClass()
	{
		tester = new AESAlgorithm();
		plainText = "testoInChiaro";
	}

	/**
	 * Test di codifica e decodifica di un testo
	 * 
	 * @author	Andrea Meneghinello
	 * @version %I%, %G%
	 */
	@Test
	public void testEncodeAndDecode()
	{
		boolean result = false;
		String encodedText = null;
		String decodedText = null;
		try
		{
			encodedText = tester.encode(plainText);
			decodedText = tester.decode(encodedText);
			result = decodedText.equals(plainText);
			assertTrue(result);
		}
		catch (Exception ex)
		{
			fail(ex.getMessage());
		}
	}
}