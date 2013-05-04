package org.softwaresynthesis.mytalk.server.authentication.security;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test dei metodi della classe {@link AESAlgorithm}. In realtà la correttezza
 * di questo test deriva direttamente dalla correttezza delle classi Encode e
 * Decode nonché della classe astratta che estendono (CryptoTemplate)
 * 
 * @author Andrea Meneghinello
 * @author Diego Beraldin
 * @version 2.0
 */
public class AESAlgorithmTest {
	// oggetto da testare
	private static AESAlgorithm tester;
	// attributi necessari ai singoli test
	private static String plainText;
	private static String cipherText;

	/**
	 * Inizializzazione dell'oggetto da testare e degli oggetti necessari
	 * all'esecuzione di tutti i test
	 * 
	 * @author Andrea Meneghinello
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@BeforeClass
	public static void setUpBeforeClass() {
		// oggetto da testare
		tester = new AESAlgorithm();
		// dati di test
		plainText = "testoInChiaro";
		cipherText = "uDWIvRHmVrCXVdjtZeYj1g==";
	}

	/**
	 * Verifica che una stringa di esempio sia correttamente codificata mediante
	 * il confronto con la sua versione cifrata (nota a priori)
	 * 
	 * @author Andrea Meneghinello
	 * @author Diego Beraldin
	 * @version 1.0
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
	 * Verifica la corretta decodifica di un testo cifrato la cui controparte in
	 * chiaro è nota
	 * 
	 * @author Diego Beraldin
	 * @version 1.0
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
}