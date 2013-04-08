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

/**
 * Verifica la possibilità di ottenere una lista di contatti tramite opportuni
 * parametri (criteri) di ricerca sfruttando la servlet addetta alle ricerche,
 * AddressBookDoSearchServlet, interrogata mediante il metodo POST
 * 
 * @author Diego Beraldin
 */
public class AddressBookDoSearchServletTest {
	// oggetto da testare
	private static AddressBookDoSearchServlet tester;
	// stub di richiesta HTTP
	private HttpServletRequest request;
	// stub di risposta HTTP
	private HttpServletResponse response;
	// writer su cui scrivere il testo della risposta
	private StringWriter writer;

	/**
	 * Inizializza l'oggetto da verificare prima di tutti i test
	 * 
	 * @author Diego Beraldin
	 */
	@BeforeClass
	public static void setUpBeforeClass() {
		tester = new AddressBookDoSearchServlet();
	}

	/**
	 * Prima di ogni test, crea gli stub necessari per la sua esecuzione e
	 * azzera il contenuto dello StringBuffer in cui dovrà essere memorizzato il
	 * testo della risposta ottenuta dalla servlet
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
	 * Verifica che sia possibile effettuare una ricerca fra gli utenti
	 * registrati al sistema in base a determinati criteri
	 * 
	 * @throws IOException
	 * @throws ServletException
	 * @author Diego Beraldin
	 */
	@Test
	public void testGetSearchContact() throws IOException, ServletException {
		// configura il comportamento della richiesta
		when(request.getParameter("param")).thenReturn("pippo");

		// configura il comportamento della risposta
		when(response.getWriter()).thenReturn(new PrintWriter(writer));

		// invoca il metodo da testare
		tester.doPost(request, response);

		// verifica l'output
		writer.flush();
		String responseText = writer.toString();
		assertNotNull(responseText);
		assertFalse(responseText.length() == 0);
		System.out.println(responseText);
		String toCompare = "{\"1\":{\"name\":\"pippo\", "
				+ "\"surname\":\"rossi\", "
				+ "\"email\":\"indirizzo1@dominio.it\", \"id\":\"1\", "
				+ "\"picturePath\":\"img/contactImg/Default.png\", "
				+ "\"state\":\"offline\", " + "\"blocked\":\"false\"}}";
		assertEquals(toCompare, responseText);
	}

	/**
	 * Verifica la corretta gestione del caso in cui la richiesta HTTP non
	 * contenga delle informazioni errate che impediscono di portare a termine
	 * con successo l'operazione di ricerca
	 * 
	 * @throws IOException
	 * @throws ServletException
	 * @author Diego Beraldin
	 */
	@Test
	public void testWrongData() throws IOException, ServletException {
		// configura il comportamento della richiesta (parametro errato)
		when(request.getParameter("para")).thenReturn("pippo");

		// configura il comportamento della risposta
		when(response.getWriter()).thenReturn(new PrintWriter(writer));

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
