package org.softwaresynthesis.mytalk.server.authentication;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.security.Principal;
import java.util.Set;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.softwaresynthesis.mytalk.server.authentication.security.ISecurityStrategy;

/**
 * Verifica la possibilità di effettuare un login al sistema
 * 
 * @author Andrea Meneghinello
 * @author Diego Beraldin
 * @version 2.0
 */
@RunWith(MockitoJUnitRunner.class)
public class AuthenticationModuleTest {
	private final String username = "indirizzo5@dominio.it";
	private final String password = "password";
	@Mock
	private HttpServletRequest request;
	@Mock
	private ISecurityStrategy strategy;
	@Mock
	private CredentialLoader loader;
	private LoginContext tester;

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
	}

	/**
	 * Reinizializza il comportamento dei mock e crea l'oggetto da testare prima
	 * di ogni verifica contenuta in questo caso di test
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
		when(strategy.encode(password)).thenReturn("tQJu8lEBkXWy+YuqNKZsqA==");

		// inizializza il loader (non bisognerebbe farlo così!)
		loader = new CredentialLoader(request, strategy);

		// inizializza l'oggetto da testare
		tester = new LoginContext("Configuration", loader);
	}

	/**
	 * TODO questa documentazione manca!
	 * 
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
	 * TODO questa documentazione manca!
	 * 
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
