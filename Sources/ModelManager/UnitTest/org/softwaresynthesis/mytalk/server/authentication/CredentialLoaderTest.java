package org.softwaresynthesis.mytalk.server.authentication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import javax.security.auth.callback.Callback;
import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.softwaresynthesis.mytalk.server.authentication.security.ISecurityStrategy;

/**
 * Test dei metodi della classe {@link CredentialLoader}
 * 
 * @author Andrea Meneghinello
 * @author Diego Beraldin
 * @version 2.0
 */
@RunWith(MockitoJUnitRunner.class)
public class CredentialLoaderTest {
	private final String password = "password";
	private final String username = "indirizzo5@dominio.it";
	@Mock
	private HttpServletRequest request;
	@Mock
	private ISecurityStrategy strategy;
	private CredentialLoader tester;

	/**
	 * Inizializzazione dei dati ausiliari e dell'oggetto da testare
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Before
	public void setUp() throws Exception {
		// configura il comportamento della richiesta HTTP
		when(request.getParameter("username")).thenReturn(username);
		when(request.getParameter("password")).thenReturn(password);
		// configura il comportamento della strategia di crittografia
		when(strategy.encode(password)).thenReturn(password);
		// inizializza l'oggetto da testare
		tester = new CredentialLoader(request, strategy);
	}

	/**
	 * Verifica il comportamento del metodo handle, controllando che le
	 * credenziali (username e password) contenute nella richiesta HTTP siano
	 * caricate in modo corretto.
	 * 
	 * @author Andrea Meneghinello
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testHandle() throws Exception {
		Callback[] callbacks = new Callback[2];
		callbacks[0] = new NameLoader();
		callbacks[1] = new PasswordLoader();
		// invoca il metodo da testare
		tester.handle(callbacks);
		String retrievedUsername = ((Loader) callbacks[0]).getData();
		String retrievedPassword = new String(((Loader) callbacks[1]).getData());
		assertNotNull(retrievedUsername);
		assertNotNull(retrievedPassword);
		assertEquals(username, retrievedUsername);
		assertEquals(password, retrievedPassword);
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