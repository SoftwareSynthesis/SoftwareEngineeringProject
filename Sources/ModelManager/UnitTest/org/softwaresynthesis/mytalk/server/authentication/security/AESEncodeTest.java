package org.softwaresynthesis.mytalk.server.authentication.security;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test della classe {@link Encode} avente il fine di verificare che la codifica
 * dei dati avvenga come stabilito dall'algoritmo di crittografia AES a 128 bit
 * con la chiave prescelta.
 * 
 * @author Diego Beraldin
 * @version 2.0
 */
public class AESEncodeTest {
	private static AESTemplate tester;
	private static String plainText;
	private static String cipherText;

	/**
	 * Inizializza l'oggetto da sottoporre a verifica e crea i dati necessari
	 * all'esecuzione di tutti i test
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@BeforeClass
	public static void setUpBeforeClass() {
		// oggetto da testare
		tester = new AESEncode();
		// dati di test
		plainText = "paperino";
		cipherText = "Ub92LcoN/pLMLZnz6Bg4CA==";
	}

	/**
	 * Verifica la correttezza dell'algoritmo di crittografia codificando una
	 * stringa la cui versione cifrata è nota per la chiave prescelta e
	 * confrontando la corrispondenza delle due stringhe.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testExecute() {
		try {
			String result = tester.execute(plainText);
			assertNotNull(result);
			assertEquals(cipherText, result);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

}
