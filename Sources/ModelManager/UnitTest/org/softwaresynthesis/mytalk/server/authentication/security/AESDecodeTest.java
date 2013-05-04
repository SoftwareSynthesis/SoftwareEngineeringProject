package org.softwaresynthesis.mytalk.server.authentication.security;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.BeforeClass;
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
	private static AESTemplate tester;
	private static final String cipherText = "Ub92LcoN/pLMLZnz6Bg4CA==";
	private static final String plainText = "paperino";

	/**
	 * Inizializza l'oggetto da sottoporre a verifica e i dati necessari
	 * all'esecuzione di tutte le verifiche di questo test cases
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@BeforeClass
	public static void setUpBeforeClass() {
		// oggetto da testare
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
