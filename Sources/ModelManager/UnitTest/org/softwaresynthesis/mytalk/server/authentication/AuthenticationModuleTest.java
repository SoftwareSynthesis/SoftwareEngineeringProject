package org.softwaresynthesis.mytalk.server.authentication;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.security.Principal;
import java.util.Set;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;

import org.junit.BeforeClass;
import org.junit.Test;
import org.softwaresynthesis.mytalk.server.authentication.security.ISecurityStrategy;

/**
 * Verifica la possibilità di effettuare un login al sistema
 * 
 * @author Andrea Meneghinello
 * @author Diego Beraldin
 * @version 2.0
 */
public class AuthenticationModuleTest {
	private static LoginContext tester;
	private static HttpServletRequest request;
	private static ISecurityStrategy strategy;
	private static CredentialLoader loader;
	private static final String username = "indirizzo5@dominio.it";
	private static final String password = "password";

	/**
	 * Inizializza i dati necessari all'esecuzione dei test
	 * 
	 * @author Andrea Meneghinello
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// imposta proprietà di sistema necessaria per l'autenticazione
		String path = System.getenv("MyTalkConfiguration");
		String separator = System.getProperty("file.separator");
		path += separator + "MyTalk" + separator + "Conf" + separator
				+ "LoginConfiguration.conf";
		System.setProperty("java.security.auth.login.config", path);

		// configura il comportamento della richiesta HTTP
		request = mock(HttpServletRequest.class);
		when(request.getParameter("username")).thenReturn(username);
		when(request.getParameter("password")).thenReturn(password);

		// configura il comportamento della strategia di crittografia
		strategy = mock(ISecurityStrategy.class);
		when(strategy.encode(password)).thenReturn("tQJu8lEBkXWy+YuqNKZsqA==");

		// inizializza il loader (non bisognerebbe farlo così!)
		loader = new CredentialLoader(request, strategy);

		// inizializza l'oggetto da testare
		tester = new LoginContext("Configuration", loader);
	}

	/**
	 * @author Andrea Meneghinello
	 * @version 1.0
	 */
	@Test
	public void testLogin() {
		Subject subject = null;
		Set<Principal> principals = null;
		try {
			tester.login();
			subject = tester.getSubject();
			principals = subject.getPrincipals();
			assertNotNull(principals);
		} catch (LoginException ex) {
			fail(ex.getMessage());
		}
	}

	/**
	 * @author Andrea Meneghinello
	 * @version 1.0
	 */
	@Test
	public void testLogout() {
		try {
			tester.logout();
			Subject subject = tester.getSubject();
			Set<Principal> principals = subject.getPrincipals();
			assertTrue(principals.size() == 0);
		} catch (LoginException ex) {
			fail(ex.getMessage());
		}
	}
}
