package org.softwaresynthesis.mytalk.server.authentication;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Test;

/**
 * Test dei metodi della classe {@link AESAlgorithm}
 * 
 * @author Andrea Meneghinello
 * @version %I%, %G%
 */
public class AESAlgorithmTest {
	// oggetto da testare
	private static AESAlgorithm tester;
	// attributi necessari ai singoli test
	private String plainText;
	private String cipherText;

	/**
	 * Inizializzazione dell'oggetto da testare
	 * 
	 * @author Andrea Meneghinello
	 * @version %I%, %G%
	 */
	@BeforeClass
	public static void setUpBeforeClass() {
		tester = new AESAlgorithm();

	}
	
	/**
	 * Crea gli oggetti necessari all'esecuzione di ogni test
	 * 
	 * @author Diego Beraldin
	 */
	@Before
	public void setUp() {
		plainText = "testoInChiaro";
		cipherText = "uDWIvRHmVrCXVdjtZeYj1g==";
	}

	/**
	 * Verifica che il testo sia correttamente codificato
	 * 
	 * @author Andrea Meneghinello
	 * @version %I%, %G%
	 */
	@Test
	public void testEncode() {
		String result = null;
		try {
			result = tester.encode(plainText);
			assertNotNull(result);
			assertEquals(cipherText, result);
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}
	
	/**
	 * Verifica la corretta decodifica di un testo cifrato
	 * 
	 * @author Diego Beraldin
	 */
	@Test
	public void testDecode() {
		String result = null;
		try {
			result = tester.decode(cipherText);
			assertNotNull(result);
			assertEquals(plainText, result);
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}
	
	/**
	 * Testa la conversione in stringa
	 * 
	 * @author Diego Beraldin
	 */
	@Test
	public void testToString() {
		String toCompare = "Algoritmo di crittografia AES a 128 bit";
		String result = tester.toString();
		assertEquals(toCompare, result);
	}
}