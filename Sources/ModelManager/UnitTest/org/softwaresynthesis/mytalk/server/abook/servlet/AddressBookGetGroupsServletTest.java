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
 * Verifica la possibilità di scaricare la lista dei gruppi della propria
 * rubrica, ognuno dei quali corredato dall'array degli id dei contatti che
 * contiene
 * 
 * @author Diego Beraldin
 */
public class AddressBookGetGroupsServletTest {
	// oggetto da testare
	private static AddressBookGetGroupsServlet tester;
	// stub di richiesta HTTP
	private HttpServletRequest request;
	// stub di risposta HTTP
	private HttpServletResponse response;
	// writer su cui sarà scritto il testo della risposta
	private StringWriter writer;

	/**
	 * Inizializza l'oggetto della verifica prima di tutti i test
	 * 
	 * @author Diego Beraldin
	 */
	@BeforeClass
	public static void setUpBeforeClass() {
		tester = new AddressBookGetGroupsServlet();
	}

	/**
	 * Crea gli stub necessari all'esecuzione di tutti i metodi del test case e
	 * azzera il buffer in cui sarà memorizzata la risposta ottenuta
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
	 * Verifica la possibilità di scaricare i gruppi della rubrica memorizzati
	 * nel server, e la lista dei contatti che appartengono a ciascun gruppo
	 * 
	 * @throws IOException
	 * @throws ServletException
	 * @author Diego Beraldin
	 */
	@Test
	public void testGetGroupContact() throws IOException, ServletException {
		// crea una sessione di autenticazione
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
		String toCompare = "{"
				+ "\"1\":{\"name\":\"Gruppo 1\",\"id\":\"1\",\"contacts\":[1]},"
				+ "\"3\":{\"name\":\"Gruppo 3\",\"id\":\"3\",\"contacts\":[]},"
				+ "\"64\":{\"name\":\"addrBookEntry\",\"id\":\"64\",\"contacts\":[]}"
				+ "}";
		assertEquals(toCompare, responseText);

	}
}
