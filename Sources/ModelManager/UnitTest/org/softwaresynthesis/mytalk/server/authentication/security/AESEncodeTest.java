package org.softwaresynthesis.mytalk.server.authentication.security;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

/**
 * Test della classe {@link AESEncode} avente il fine di verificare che la codifica
 * dei dati avvenga come stabilito dall'algoritmo di crittografia AES a 128 bit
 * con la chiave prescelta.
 * 
 * @author Diego Beraldin
 * @version 2.0
 */
public class AESEncodeTest {
	private final String plainText = "paperino";
	private final String cipherText = "Ub92LcoN/pLMLZnz6Bg4CA==";
	private AESTemplate tester;

	/**
	 * Inizializza l'oggetto da testare
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Before
	public void setUp() {
		tester = new AESEncode();
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
	public void testExecute() throws Exception {
		String result = tester.execute(plainText);
		assertNotNull(result);
		assertEquals(cipherText, result);
	}
}
