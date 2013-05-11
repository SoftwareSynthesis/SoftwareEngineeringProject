package org.softwaresynthesis.mytalk.server.authentication.security;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

/**
 * Verifica della classe {@link Decode} che attua la decodifica di stringhe
 * cifrate secondo l'algoritmo AES a 128 bit (con la chiave prestabilita). Il
 * test avviene mediante una stringa la cui versione in chiaro è nota,
 * confrontando quest'ultima con il risultato dell'applicazione dell'algoritmo.
 * 
 * @author Diego Beraldin
 * @version 2.0
 */
public class AESDecodeTest {
	private final String cipherText = "Ub92LcoN/pLMLZnz6Bg4CA==";
	private final String plainText = "paperino";
	private AESTemplate tester;

	/**
	 * Inizializza l'oggetto da testare
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Before
	public void setUp() {
		tester = new AESDecode();
	}

	/**
	 * Verifica la corretta applicazione dell'algoritmo di decodifica
	 * confrontando il testo in chiaro ottenuto dall'esecuzione del metodo
	 * execute() della classe Decode a partire da una stringa di test la cui
	 * versione in chiaro è nota a priori.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testExecute() {
		String result;
		try {
			result = tester.execute(cipherText);
			assertNotNull(result);
			assertEquals(plainText, result);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
}
