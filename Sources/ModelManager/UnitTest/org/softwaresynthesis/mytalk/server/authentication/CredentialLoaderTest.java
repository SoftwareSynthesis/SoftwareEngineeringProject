package org.softwaresynthesis.mytalk.server.authentication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test dei metodi della classe {@link CredentialLoader}
 * 
 * @author Andrea Meneghinello
 * @author Diego Beraldin
 * @version %I%, %G%
 */
public class CredentialLoaderTest {
	// oggetto da testare
	private static CredentialLoader tester;
	// dati di autenticazione fittizi
	private static AuthenticationData credential;

	/**
	 * Inizializzazione dei dati ausiliari
	 * 
	 * @author Andrea Meneghinello
	 * @version %I%, %G%
	 */
	@BeforeClass
	public static void setupBeforeClass() {
		credential = mock(AuthenticationData.class);
		when(credential.getUsername()).thenReturn("indirizzo1@dominio.it");
		when(credential.getPassword()).thenReturn("password");
		tester = new CredentialLoader(credential, new AESAlgorithm());
	}

	/**
	 * Test del metodo handle
	 * 
	 * @author Andrea Meneghinello
	 * @version %I%, %G%
	 */
	@Test
	public void testHandle() {
		Callback[] callbacks = new Callback[2];
		callbacks[0] = new NameCallback("username");
		callbacks[1] = new PasswordCallback("password", false);
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
	 * @author Diego Beraldin
	 */
	@Test
	public void testToString() {
		String toCompare = "CredentialLoader";
		String result = tester.toString();
		assertEquals(toCompare, result);
	}
}