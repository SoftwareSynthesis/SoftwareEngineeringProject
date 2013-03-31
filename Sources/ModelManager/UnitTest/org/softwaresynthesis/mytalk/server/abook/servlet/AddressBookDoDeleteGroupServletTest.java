package org.softwaresynthesis.mytalk.server.abook.servlet;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.StringWriter;
import java.io.PrintWriter;
import java.io.IOException;

/**
 * Verifica la possibilità di cancellare un gruppo dalla propria rubrica
 * 
 * @author diego
 */
public class AddressBookDoDeleteGroupServletTest {
	// oggetto da testare
	private static AddressBookDoDeleteGroupServlet tester;
	// stub di richiesta HTTP
	private HttpServletRequest request;
	// stub di risposta HTTP
	private HttpServletResponse response;
	// contiene lo StringBuffer in cui salvare il testo della risposta
	private StringWriter writer;

	/**
	 * Inizializza l'oggetto da verificare prima di tutti i test
	 * 
	 * @author diego
	 */
	@BeforeClass
	public static void setUpBeforeClass() {
		tester = new AddressBookDoDeleteGroupServlet();
	}

	/**
	 * Ricrea tutti gli stub prima dell'esecuzione di ogni test e azzera il
	 * buffer in cui sarà memorizzato il testo della risposta
	 * 
	 * @author diego
	 */
	@Before
	public void setUp() {
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		writer = new StringWriter();
	}

	/**
	 * Verifica la corretta rimozione di un gruppo dalla rubrica di un utente
	 * 
	 * @throws IOException
	 * @throws ServletException
	 * @author diego
	 */
	@Test
	public void testRemoveCorrectGroup() throws IOException, ServletException {
		// crea sessione di autenticazione
		HttpSession mySession = mock(HttpSession.class);
		when(mySession.getAttribute("username")).thenReturn(
				"indirizzo5@dominio.it");

		// configura il comportamento della richiesta
		when(request.getSession(false)).thenReturn(mySession);
		when(request.getParameter("groupId")).thenReturn("2");

		// configura il comportamento della risposta
		when(response.getWriter()).thenReturn(new PrintWriter(writer));

		// invoca il metodo da testare
		tester.doPost(request, response);

		// verifica l'output
		writer.flush();
		String responseText = writer.toString();
		assertNotNull(responseText);
		assertFalse(responseText.length() == 0);
		assertEquals("true", responseText);
	}

	/**
	 * Verifica la corretta gestione del caso in cui giunga la richiesta di
	 * eliminare un gruppo che non esiste nel database
	 * 
	 * @throws IOException
	 * @throws ServletException
	 * @author diego
	 */
	@Test
	public void testRemoveNotExistGroup() throws IOException, ServletException {
		// crea sessione di autenticazione
		HttpSession mySession = mock(HttpSession.class);
		when(mySession.getAttribute("username")).thenReturn(
				"indirizzo5@dominio.it");

		// configura il comportamento della richiesta
		when(request.getSession(false)).thenReturn(mySession);
		when(request.getParameter("groupId")).thenReturn("-1");

		// invoca il metodo da testare
		tester.doPost(request, response);

		// verifica l'output
		writer.flush();
		String responseText = writer.toString();
		assertNotNull(responseText);
		assertFalse(responseText.length() == 0);
		assertEquals("false", responseText);
	}
	
	/**
	 * Verifica la corretta gestione del caso in cui giunga la richiesta di
	 * eliminare un gruppo che non esiste nella rubrica dell'utente
	 * 
	 * @throws IOException
	 * @throws ServletException
	 */
	@Test
	public void testRemoveNotFriendContact() throws IOException, ServletException {
		fail("Non ancora implementato");
	}
	
	/**
	 * Verifica la corretta gestione del caso in cui giunga la richiesta di
	 * eliminare un gruppo con un numero di parametri insufficiente
	 * 
	 * @throws IOException
	 * @throws ServletException
	 * @author diego
	 */
	@Test
	public void testRemoveWrongData() throws IOException, ServletException {
		// crea sessione di autenticazione
		HttpSession mySession = mock(HttpSession.class);
		when(mySession.getAttribute("username")).thenReturn(
				"indirizzo5@dominio.it");

		// configura il comportamento della richiesta (manca un parametro)
		when(request.getSession(false)).thenReturn(mySession);

		// invoca il metodo da testare
		tester.doPost(request, response);

		// verifica l'output
		writer.flush();
		String responseText = writer.toString();
		assertNotNull(responseText);
		assertFalse(responseText.length() == 0);
		assertEquals("false", responseText);
	}
}
