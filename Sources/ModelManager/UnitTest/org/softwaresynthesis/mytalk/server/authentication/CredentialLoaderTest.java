package org.softwaresynthesis.mytalk.server.authentication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.servlet.http.HttpServletRequest;

import org.junit.BeforeClass;
import org.junit.Test;
import org.softwaresynthesis.mytalk.server.authentication.security.ISecurityStrategy;

/**
 * Test dei metodi della classe {@link CredentialLoader}
 * 
 * @author Andrea Meneghinello
 * @author Diego Beraldin
 * @version 2.0
 */
public class CredentialLoaderTest {
	private static CredentialLoader tester;
	private static HttpServletRequest request;
	private static ISecurityStrategy strategy;
	private static final String password = "password";
	private static final String username = "indirizzo5@dominio.it";

	/**
	 * Inizializzazione dei dati ausiliari e dell'oggetto da testare
	 * 
	 * @author Andrea Meneghinello
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@BeforeClass
	public static void setupBeforeClass() throws Exception {
		// configura il comportamento della richiesta HTTP
		request = mock(HttpServletRequest.class);
		when(request.getParameter("username")).thenReturn(username);
		when(request.getParameter("password")).thenReturn(password);
		// configura il comportamento della strategia di crittografia
		strategy = mock(ISecurityStrategy.class);
		when(strategy.encode(password)).thenReturn(password);
		// crea l'oggetto da testare
		tester = new CredentialLoader(request, strategy);
	}

	/**
	 * Verifica il comportamento del metodo handle, controllando che le
	 * credenziali (username e password) contenute nella richiesta HTTP siano
	 * caricate in modo corretto.
	 * 
	 * @author Andrea Meneghinello
	 * @author Diego Beraldin
	 * @version 1.1
	 */
	@Test
	public void testHandle() {
		Callback[] callbacks = new Callback[2];
		callbacks[0] = new NameCallback(username);
		callbacks[1] = new PasswordCallback(password, false);
		try {
			tester.handle(callbacks);
			NameCallback nc = (NameCallback) callbacks[0];
			PasswordCallback pc = (PasswordCallback) callbacks[1];
			String retrievedUsername = nc.getName();
			String retrievedPassword = new String(pc.getPassword());
			assertNotNull(retrievedUsername);
			assertNotNull(retrievedPassword);
			assertEquals(username, retrievedUsername);
			assertEquals(password, retrievedPassword);
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}

	/**
	 * Verifica che sia possibile ottenere la strategia di crittografia in uso
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testSecurityStrategy() {
		ISecurityStrategy result = tester.getSecurityStrategy();
		assertNotNull(result);
		assertEquals(strategy, result);
	}
}