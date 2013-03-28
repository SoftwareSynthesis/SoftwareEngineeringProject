package org.softwaresynthesis.mytalk.server.authentication;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test dei metodi della classe {@link CredentialLoader}
 * 
 * @author Andrea Meneghinello
 * @version %I%, %G%
 */
public class CredentialLoaderTest {
	private static CredentialLoader tester;
	private static AuthenticationData credential;

	/**
	 * Inizializzazione dei dati ausiliari
	 * 
	 * @author Andrea Meneghinello
	 * @version %I%, %G%
	 */
	@BeforeClass
	public static void setupBeforeClass() {
		credential = new AuthenticationData("indirizzo1@dominio.it", "password");
	}

	/**
	 * Test del metodo handle
	 * 
	 * @author Andrea Meneghinello
	 * @version %I%, %G%
	 */
	@Test
	public void testLoader() {
		Callback[] callbacks = new Callback[2];
		callbacks[0] = new NameCallback("username");
		callbacks[1] = new PasswordCallback("password", false);
		tester = new CredentialLoader(credential, new AESAlgorithm());
		NameCallback nc = null;
		PasswordCallback pc = null;
		String username = null;
		char[] password = null;
		try {
			tester.handle(callbacks);
			nc = (NameCallback) callbacks[0];
			pc = (PasswordCallback) callbacks[1];
			username = nc.getName();
			password = pc.getPassword();
			assertNotNull(username);
			assertNotNull(password);
			assertEquals(username, "indirizzo1@dominio.it");
			assertFalse(new String(password).equals(""));
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}
	
	/**
	 * Testa conversione in stringa
	 * 
	 * @author diego
	 */
	@Test
	public void testToString() {
		String toCompare = "CredentialLoader";
		assertTrue(toCompare.equals(tester.toString()));
	}
}