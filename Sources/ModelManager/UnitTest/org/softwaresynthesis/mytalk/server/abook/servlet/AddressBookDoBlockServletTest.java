package org.softwaresynthesis.mytalk.server.abook.servlet;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
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
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Verifica la possibilità di bloccare un contatto all'interno di una rubrica
 * 
 * @author diego
 */
public class AddressBookDoBlockServletTest {
	// oggetto da testare
	private static AddressBookDoBlockServlet tester;
	// stub di richiesta HTTP
	private HttpServletRequest request;
	// stub di riposta HTTP
	private HttpServletResponse response;
	// contiene lo StringBuffer in cui sarà memorizzato il testo della risposta
	private StringWriter writer;
	// nome del database
	private static String DB_URL;
	private static String DB_USER;
	private static String DB_PASSWORD;

	/**
	 * Inizializza l'oggetto da verificare prima di tutti i test
	 * 
	 * @author diego
	 */
	@BeforeClass
	public static void setUpBeforeClass() {
		tester = new AddressBookDoBlockServlet();
		DB_URL = "jdbc:mysql://localhost/MyTalk";
		DB_USER = "root";
		DB_PASSWORD = "root";
	}

	/**
	 * Ricrea prima di ogni test gli stub necessari alla sua esecuzione e azzera
	 * il buffer in cui verrà memorizzato il testo della risposta
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
	 * Verifica l'avvenuto blocco del contatto passato come parametro, che
	 * appartiene effettivamente alla rubrica dell'utente da cui proviene la
	 * richiesta
	 * 
	 * @throws IOException
	 * @throws ServletException
	 * @author diego
	 */
	@Test
	public void testBlockCorrectContact() throws IOException, ServletException {
		// crea una sessione di autenticazione
		HttpSession mySession = mock(HttpSession.class);
		when(mySession.getAttribute("username")).thenReturn(
				"indirizzo5@dominio.it");

		// configura il comportamento della richiesta
		when(request.getSession(false)).thenReturn(mySession);
		when(request.getParameter("contactId")).thenReturn("2");

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

		// verifica che il blocco sia stato effettivo e su TUTTE le entries
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(DB_URL, DB_USER,
					DB_PASSWORD);
			Statement stmt = conn.createStatement();
			ResultSet result = stmt
					.executeQuery("SELECT Blocked FROM AddressBookEntries WHERE Owner = '5' AND ID_user = '2'");
			while (result.next()) {
				Boolean blocked = result.getBoolean("Blocked");
				assertTrue(blocked);
			}
			stmt.close();
			conn.close();

		} catch (Exception ex) {
			fail(ex.getMessage());
		}

		// effettua un po' di operazioni di clean-up finali
		try {
			Connection conn = DriverManager.getConnection(DB_URL, DB_USER,
					DB_PASSWORD);
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("UPDATE AddressBookEntries SET Blocked = '0' WHERE Owner = '5' AND ID_user = '2'");
			stmt.close();
			conn.close();
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}

	/**
	 * Verifica l'impossibilità di bloccare un contatto che non appartiene alla
	 * rubrica dell'utente da cui proviene la richiesta
	 * 
	 * @throws IOException
	 * @throws ServletException
	 * @author diego
	 */
	@Test
	public void testBlockNotExistContact() throws IOException, ServletException {
		// crea una sessione di autenticazione
		HttpSession mySession = mock(HttpSession.class);
		when(mySession.getAttribute("username")).thenReturn(
				"indirizzo5@dominio.it");

		// configura il comportamento della richiesta
		when(request.getSession(false)).thenReturn(mySession);
		when(request.getParameter("contactId")).thenReturn("-1");

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

	/**
	 * Verifica l'impossibilità di bloccare un contatto nel momento in cui la
	 * richiesta che giunge alla servlet non contiene tutti i parametri
	 * necessari per portare a termine l'operazione
	 * 
	 * @throws IOException
	 * @throws ServletException
	 * @author diego
	 */
	@Test
	public void testWrongData() throws IOException, ServletException {
		// crea una sessione di autenticazione
		HttpSession mySession = mock(HttpSession.class);
		when(mySession.getAttribute("username")).thenReturn(
				"indirizzo5@dominio.it");

		// configura il comportamento della richiesta (manca il parametro)
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
		assertEquals("false", responseText);
	}
}
