package org.softwaresynthesis.mytalk.server.authentication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.security.auth.callback.NameCallback;
import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Verifica della classe {@link NameLoader}
 * 
 * @author Diego Beraldin
 * @version 2.0
 */
@RunWith(MockitoJUnitRunner.class)
public class NameLoaderTest {
	private final String username = "indirizzo5@dominio.it";
	@Mock
	private HttpServletRequest request;
	private Loader tester;

	/**
	 * Reinizializza prima di ogni test il mock di richiesta HTTP (necessario
	 * farlo per contare il numero di invocazioni di metodi sul mock con verify)
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Before
	public void setUp() {
		request = mock(HttpServletRequest.class);
		tester = new NameLoader();
	}

	/**
	 * Lo scopo di tale test è verificare il comportamento della classe nel caso
	 * in cui il metodo load sia invocato con una richiesta HTTP che non
	 * contiene tutti i parametri necessari allo svolgimento dell'operazione, in
	 * particolare senza lo username. Il test controlla che la richiesta sia
	 * gestita nel modo corretto (invocando solo una volta il metodo
	 * getParameter) e ha successo solo se viene sollevata un'eccezione di tipo
	 * IOException, come ci si aspetta nel caso non tutti i parametri necessari
	 * siano forniti al metodo.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test(expected = IOException.class)
	public void testLoadMissingUsername() throws IOException {
		// invoca il metodo da testare
		tester.load(request);

		// verifica che il mock sia stato usato nel modo giusto
		verify(request).getParameter("username");
	}

	/**
	 * Lo scopo di tale test è verificare il corretto comportamento del metodo
	 * load nel momento in cui la richiesta HTTP contiene tutti i parametri
	 * necessari. In particolare è verificato che si invochi una ed una sola
	 * volta il metodo getParameter sulla richiesta per estrarre lo username, e
	 * che il callback interno al Loader sia effettivamente un NameCallback cui
	 * è associato il nome utente che era stato passato tramite la richiesta.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testLoadCorrectUsername() throws IOException {
		// configura il comportamento della richiesta
		when(request.getParameter("username")).thenReturn(username);

		// invoca il metodo da testare
		tester.load(request);

		// verifica che il mock sia stato usato nel modo giusto
		verify(request).getParameter("username");

		// verifica che sia stato impostato il nome
		NameCallback callback = (NameCallback) tester.getCallback();
		assertNotNull(callback);
		String retrievedUsername = callback.getName();
		assertNotNull(retrievedUsername);
		assertEquals(username, retrievedUsername);
	}

	/**
	 * Lo scopo di questo test è verificare, indipendentemente dal fatto che il
	 * NameLoader sia servendo una richiesta HTTP, che siano estratti in modo
	 * corretto i dati dal callback interno, in particolare che il nome utente
	 * restituito sia lo stesso associato al callback.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testGetData() throws IOException {
		// imposta il callback del NameLoader
		NameCallback callback = (NameCallback) tester.getCallback();
		callback.setName(username);

		// invoca il metodo da testare
		String result = tester.getData();

		// verifica l'output
		assertEquals(username, result);
	}

}
