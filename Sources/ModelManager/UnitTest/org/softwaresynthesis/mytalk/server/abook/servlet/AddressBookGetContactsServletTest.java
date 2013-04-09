package org.softwaresynthesis.mytalk.server.abook.servlet;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Verifica la possibilità di scaricare la rubrica di un utente autenticato al
 * sistema tramite la servlet AddressBookGetContacts
 * 
 * @author Diego Beraldin
 */
public class AddressBookGetContactsServletTest {
	// oggetto da testare
	private static AddressBookGetContactsServlet tester;
	// stub di richiesta HTTP
	private HttpServletRequest request;
	// stub di risposta HTTP
	private HttpServletResponse response;
	// writer in cui è contenuto lo StringBuffer su cui sarà memorizzato il
	// testo della risposta ottenuta dalla servlet
	private StringWriter writer;

	/**
	 * Inizializza l'oggetto da verificare prima di tutti i test
	 * 
	 * @author Diego Beraldin
	 */
	@BeforeClass
	public static void setUpBeforeClass() {
		tester = new AddressBookGetContactsServlet();
	}

	/**
	 * Crea prima di ogni test gli stub necessari per la sua esecuzione e azzera
	 * il buffer in cui sarà memorizzato il testo della risposta
	 * 
	 * @author Diego Beraldin
	 */
	@Before
	public void setUp() {
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		writer = new StringWriter();
	}

	/**
	 * Verifica che sia correttamente scaricata la rubrica di un utente
	 * registrato nel sistema
	 * 
	 * @throws ServletException
	 * @throws IOException
	 * @author Diego Beraldin
	 */
	@Test
	public void testGetCorrectContacts() throws ServletException, IOException {
		// crea una sessione che identifica l'utente 'indirizzo5@dominio.it'
		HttpSession mySession = mock(HttpSession.class);
		when(mySession.getAttribute("username")).thenReturn(
				"indirizzo5@dominio.it");

		// configura il comportamento della richiesta
		when(request.getSession(false)).thenReturn(mySession);

		// configura il comportamento della risposta
		when(response.getWriter()).thenReturn(new PrintWriter(writer));

		// invoca il metodo da testare
		tester.doPost(request, response);

		// verifica l'output
		writer.flush();
		String responseText = writer.toString();
		assertNotNull(responseText);
		assertFalse(responseText.length() == 0);

		String toCompare = "{\"2\":{"
				+ "\"name\":\"marco\", \"surname\":\"verdi\", \"email\":\"indirizzo2@dominio.it\", "
				+ "\"id\":\"2\", \"picturePath\":\"img/contactImg/Default.png\", \"state\":\"offline\", "
				+ "\"blocked\":false}}";
		assertEquals(toCompare, responseText);
	}

	/**
	 * Verifica la corretta gestione del caso in cui la richiesta provenga da un
	 * utente non registrato al sistema (sessione di autenticazione non valida)
	 * 
	 * @throws ServletException
	 * @throws IOException
	 * @author Diego Beraldin
	 */
	@Test
	public void testWrongData() throws ServletException, IOException {
		// crea una sessione non valida
		HttpSession failSession = mock(HttpSession.class);

		// configura il comportamento della richiesta
		when(request.getSession(false)).thenReturn(failSession);

		// configura il comportamento della risposta
		when(response.getWriter()).thenReturn(new PrintWriter(writer));

		// invoca il metodo da testare
		tester.doPost(request, response);

		// verifica l'output
		writer.flush();
		String responseText = writer.toString();
		assertNotNull(responseText);
		assertFalse(responseText.length() == 0);

		String toCompare = "null";
		assertEquals(toCompare, responseText);
	}
}
