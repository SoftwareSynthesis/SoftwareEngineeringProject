package org.softwaresynthesis.mytalk.server.abook.servlet;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import java.io.StringWriter;
import java.io.PrintWriter;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Verifica la possibilità di sboccare un contatto all'interno della rubrica
 * 
 * @author Diego Beraldin
 */
public class AddressBookDoUnblockServletTest {
	// oggetto da testare
	private static AddressBookDoUnblockServlet tester;
	// stub di richiesta HTTP
	private HttpServletRequest request;
	// stub di risposta HTTP
	private HttpServletResponse response;
	// contiene il buffer su cui è scritto il testo della risposta
	private StringWriter writer;

	/**
	 * Inizializza l'oggetto da verificare prima di tutti i test
	 * 
	 * @author Diego Beraldin
	 */
	@BeforeClass
	public static void setUpBeforeClass() {
		tester = new AddressBookDoUnblockServlet();
	}

	/**
	 * Prima dell'esecuzione di ogni test, crea gli stub necessari alla sua
	 * esecuzione e azzera il buffer in cui verrà memorizzato il testo della
	 * risposta proveniente dalla servlet
	 * 
	 * @author Diego Beraldin
	 */
	@Before
	public void setUp() throws Exception {
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		writer = new StringWriter();
	}

	/**
	 * Verifica la possibilità di bloccare un contatto presente all'interno
	 * della rubrica dell'utente da cui proviene la richiesta
	 * 
	 * @throws IOException
	 * @throws ServletException
	 * @author Diego Beraldin
	 */
	@Test
	public void testUnblockCorrectContact() throws IOException,
			ServletException {
		// crea una sessione di autenticazione
		HttpSession mySession = mock(HttpSession.class);
		when(mySession.getAttribute("username")).thenReturn(
				"indirizzo5@dominio.it");

		// configura il comportamento della richiesta
		when(request.getSession(false)).thenReturn(mySession);
		when(request.getParameter("contactId")).thenReturn("3");

		// configura il comportamento della risposta
		when(response.getWriter()).thenReturn(new PrintWriter(writer));

		// invoca il metodo da testare
		tester.doPost(request, response);

		// verifica l'output
		writer.flush();
		String responseText = writer.toString();
		System.err.println(responseText);
		assertNotNull(responseText);
		assertFalse(responseText.length() == 0);
		assertEquals("true", responseText);
	}

	/**
	 * Verifica la corretta gestione di una richiesta di sblocco per un contatto
	 * che non corrisponde ad alcun utente registrato nel sistema
	 * 
	 * @throws IOException
	 * @throws ServletException
	 * @author Diego Beraldin
	 */
	@Test
	public void testUnblockNotExistContact() throws IOException,
			ServletException {
		// crea una sessione di autenticazione
		HttpSession mySession = mock(HttpSession.class);
		when(mySession.getAttribute("username")).thenReturn(
				"indirizzo5@dominio.it");

		// configura il comportamento della richiesta
		when(request.getSession(false)).thenReturn(mySession);
		when(request.getParameter("contactId")).thenReturn("-1");

		// invoca il metodo da testare
		tester.doPost(request, response);

		// verifica l'output
		writer.flush();
		String responseText = writer.toString();
		System.err.println(responseText);
		assertNotNull(responseText);
		assertFalse(responseText.length() == 0);
		assertEquals("false", responseText);
	}

	/**
	 * Verifica la corretta gestione di una richiesta di sbloccare un contatto
	 * nel momento in cui non vengano forniti tutti i parametri necessari per
	 * portare a termine l'operazione
	 * 
	 * @throws IOException
	 * @throws ServletException
	 * @author Diego Beraldin
	 */
	@Test
	public void testWrongData() throws IOException, ServletException {
		// crea una sessione di autenticazione
		HttpSession mySession = mock(HttpSession.class);
		when(mySession.getAttribute("username")).thenReturn(
				"indirizzo5@dominio.it");

		// configura il comportamento della richiesta (manca il parametro)
		when(request.getSession(false)).thenReturn(mySession);

		// invoca il metodo da testare
		tester.doPost(request, response);

		// verifica l'output
		writer.flush();
		String responseText = writer.toString();
		System.err.println(responseText);
		assertNotNull(responseText);
		assertFalse(responseText.length() == 0);
		assertEquals("false", responseText);
	}
}
