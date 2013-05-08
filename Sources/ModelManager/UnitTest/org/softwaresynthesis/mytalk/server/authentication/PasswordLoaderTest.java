package org.softwaresynthesis.mytalk.server.authentication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.security.auth.callback.PasswordCallback;
import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.softwaresynthesis.mytalk.server.authentication.security.ISecurityStrategy;

/**
 * Verifica della classe {@link PasswordLoader}
 * 
 * @author Diego Beraldin
 * @version 2.0
 */
public class PasswordLoaderTest {
	// stringa che simula la password da caricare
	private static final String password = "password";
	// strategia di autenticazione dittizia
	private final ISecurityStrategy strategy = mock(ISecurityStrategy.class);
	// mock della richiesta HTTP
	private final HttpServletRequest request = mock(HttpServletRequest.class);
	// oggetto da testare
	private Loader tester = new PasswordLoader();

	/**
	 * Reinizializza prima di ogni test il mock di richiesta HTTP (necessario
	 * farlo per contare il numero di invocazioni di metodi sul mock con verify)
	 * e la strategia di crittografia. Configura inoltre l'oggetto da sottoporre
	 * a verifica in modo da mettere a disposizione di quest'ultimo la strategia
	 * di crittografia fittizia.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Before
	public void setUp() throws Exception {
		// configura il comportamento della strategia di crittografia
		when(strategy.encode(password)).thenReturn(password);
		// configura la richiesta HTTP
		when(request.getParameter("password")).thenReturn(password);
		// configura l'oggetto da tester
		tester.setSecurityStrategy(strategy);
	}

	/**
	 * Verifica il comportamento di PasswordLoader nel momento in cui viene
	 * gestita una richiesta che non contiene tutti i parametri sufficienti per
	 * portare a termine l'esecuzione. Il test ha successo solo se viene
	 * sollevata una IOException, come ci si attende dalla classe.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test(expected = IOException.class)
	public void testLoadMissingPassword() throws IOException {
		// impedisce l'estrazione del parametro
		when(request.getParameter("password")).thenReturn(null);
		
		// invoca il metodo da testare
		tester.load(request);

		// verifica che il mock sia stato usato nel modo giusto
		verify(request).getParameter("password");
	}

	/**
	 * Verifica il comportamento di PasswordLoader nel momento in cui è chiamato
	 * a servire una richiesta contenente tutti i parametri necessari. In
	 * particolare, si controlla che venga invocato una e una sola volta il
	 * metodo getParameter sulla richiesta per recuperare il valore della
	 * password e che la strategia di codifica sia utilizzata una sola volta per
	 * effettuare la crittografia della password. È inoltre verificato che al
	 * termine dell'operazione il callback interno al PasswordLoader sia un
	 * PasswordCallBack e che la password ad esso associata corriponda alla
	 * versione crittografata (in via simulata tramite il mock della strategia)
	 * di quella impostata tramite la richiesta.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testLoadCorrectPassword() throws IOException {
		// configura il comportamento della richiesta
		when(request.getParameter("password")).thenReturn(password);

		// invoca il metodo da testare
		tester.load(request);

		// verifica che il mock della richiesta sia usato correttamente
		verify(request).getParameter("password");
		// verifica che il mock della strategia sia usato correttamente
		try {
			verify(strategy).encode(password);
		} catch (Exception e) {
			fail(e.getMessage());
		}

		// verifica che sia stata impostata la password
		PasswordCallback callback = (PasswordCallback) tester.getCallback();
		assertNotNull(callback);
		String retrievedPassword = new String(callback.getPassword());
		assertNotNull(retrievedPassword);
		assertEquals(password, retrievedPassword);
	}

	/**
	 * Verifica che sia possibile recuperare correttamente i dati associati al
	 * PasswordCallBack interno al PasswordLoader.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testGetData() throws IOException {
		// imposta i dati nel callback del PasswordLoader
		PasswordCallback callback = (PasswordCallback) tester.getCallback();
		callback.setPassword(password.toCharArray());

		// invoca il metodo da testare
		String result = tester.getData();

		// verifica l'output
		assertEquals(password, result);
	}

}
