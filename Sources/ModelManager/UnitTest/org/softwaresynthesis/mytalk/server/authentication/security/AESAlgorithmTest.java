package org.softwaresynthesis.mytalk.server.authentication.security;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
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
	private final String plainText = "testoInChiaro";
	private final String cipherText = "uDWIvRHmVrCXVdjtZeYj1g==";
	private AESAlgorithm tester;

	/**
	 * Inizializza l'oggetto da testare
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Before
	public void setUp() {
		tester = new AESAlgorithm();
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
	public void testEncode() throws Exception {
		String result = null;
		result = tester.encode(plainText);
		assertNotNull(result);
		assertEquals(cipherText, result);
	}

	/**
	 * Verifica la corretta decodifica di un testo cifrato la cui controparte in
	 * chiaro è nota
	 * 
	 * @author Diego Beraldin
	 * @version 1.0
	 */
	@Test
	public void testDecode() throws Exception {
		String result = null;
		result = tester.decode(cipherText);
		assertNotNull(result);
		assertEquals(plainText, result);
	}
}