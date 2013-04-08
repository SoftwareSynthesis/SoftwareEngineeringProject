package org.softwaresynthesis.mytalk.server.abook.servlet;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
import java.sql.Statement;
import java.sql.ResultSet;

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
	// dati per accedere al database
	private static String DB_URL;
	private static String DB_USER;
	private static String DB_PASSWORD;

	/**
	 * Inizializza l'oggetto da verificare prima di tutti i test
	 * 
	 * @author Diego Beraldin
	 */
	@BeforeClass
	public static void setUpBeforeClass() {
		tester = new AddressBookDoUnblockServlet();
		DB_URL = "jdbc:mysql://localhost/MyTalk";
		DB_USER = "root";
		DB_PASSWORD = "root";
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
		when(request.getParameter("contactId")).thenReturn("2");

		// configura il comportamento della risposta
		when(response.getWriter()).thenReturn(new PrintWriter(writer));

		// per prima cosa assicuriamoci che l'utente 1 nella rubrica di 5 sia
		// davvero bloccato
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(DB_URL, DB_USER,
					DB_PASSWORD);
			Statement stmt = conn.createStatement();
			stmt.execute("UPDATE AddressBookEntries SET Blocked = '1' WHERE Owner = '5' AND ID_User = '2'");
			stmt.close();
			conn.close();
		} catch (Exception ex) {
			fail(ex.getMessage());
		}

		// invoca il metodo da testare
		tester.doPost(request, response);

		try {
			// verifica l'output
			writer.flush();
			String responseText = writer.toString();
			System.err.println(responseText);
			assertNotNull(responseText);
			assertFalse(responseText.length() == 0);
			assertEquals("true", responseText);

			// controllo che sia stato effettivamente sbloccato
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost/MyTalk", "root", "root");
			Statement stmt = conn.createStatement();
			ResultSet result = stmt
					.executeQuery("SELECT Blocked FROM AddressBookEntries WHERE Owner = '5' AND ID_user = '2'");
			while (result.next()) {
				Boolean blocked = result.getBoolean("Blocked");
				System.err.println(blocked);
				assertFalse(blocked);
			}
			stmt.close();
			conn.close();
		} catch (Throwable error) {
			fail(error.getMessage());
		} finally {
			try {
				// ripristina lo status quo ante del test
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
		assertEquals("false", responseText);
	}
}
