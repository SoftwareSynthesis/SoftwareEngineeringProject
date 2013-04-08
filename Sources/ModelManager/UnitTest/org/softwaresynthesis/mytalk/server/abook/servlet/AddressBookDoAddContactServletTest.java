package org.softwaresynthesis.mytalk.server.abook.servlet;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;
import java.io.StringWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

/**
 * Verifica la possibilità di aggiungere un contatto alla propria rubrica
 * 
 * @author Diego Beraldin
 */
public class AddressBookDoAddContactServletTest {
	// oggetto da testare
	private static AddressBookDoAddContactServlet tester;
	// stub di richiesta HTTP
	private HttpServletRequest request;
	// stub di risposta HTTP
	private HttpServletResponse response;
	// buffer che memorizza il testo della risposta
	private StringWriter writer;
	// dati necessari per accedere al database
	private static String DB_URL;
	private static String DB_USER;
	private static String DB_PASSWORD;

	/**
	 * Inizializza l'oggetto da testare prima di tutti i test
	 * 
	 * @author Diego Beraldin
	 */
	@BeforeClass
	public static void setUpBeforeClass() {
		tester = new AddressBookDoAddContactServlet();
		DB_URL = "jdbc:mysql://localhost/MyTalk";
		DB_USER = "root";
		DB_PASSWORD = "root";
	}

	/**
	 * Prima di ogni test, ricrea gli stub necessari alla sua esecuzione e
	 * azzera la stringa in cui è rappresentato il testo della risposta
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
	 * Verifica la possibilità di aggiungere correttamente un contatto
	 * 
	 * @throws IOException
	 * @throws ServletException
	 * @author Diego Beraldin
	 */
	@Test
	public void testAddCorrectContact() throws IOException, ServletException {
		int userID = -1;
		int owner = -1;
		// setup iniziale: inserisce un nuovo utente nel sistema (che sarà in
		// seguito aggiunto alla propria rubrica da 'indirizzo5@dominio.it'
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(DB_URL, DB_USER,
					DB_PASSWORD);
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("INSERT INTO UserData(E_Mail, Password, Question, Answer) VALUES ('dummy@dummy.du', '*', '*', '*');");
			ResultSet result = stmt
					.executeQuery("SELECT ID_user FROM UserData WHERE E_Mail = 'dummy@dummy.du';");
			while (result.next()) {
				userID = result.getInt("ID_user");
			}
			stmt.executeUpdate(String.format("INSERT INTO Groups(Name, ID_user) VALUES ('addrBookEntry', '%d');", userID));
			stmt.close();
			conn.close();
		} catch (Exception ex) {
			fail(ex.getMessage());
		}

		// crea sessione identificativa per indirizzo5@dominio.it
		HttpSession mySession = mock(HttpSession.class);
		when(mySession.getAttribute("username")).thenReturn(
				"indirizzo5@dominio.it");

		// configura il comportamento della richiesta
		when(request.getSession(false)).thenReturn(mySession);
		when(request.getParameter("contactId")).thenReturn(
				Integer.toString(userID));

		// configura il comportamento della risposta
		when(response.getWriter()).thenReturn(new PrintWriter(writer));

		// invoca il metodo da testare
		tester.doPost(request, response);

		try {
			// verifica l'output
			writer.flush();
			String responseText = writer.toString();
			assertNotNull(responseText);
			assertFalse(responseText.length() == 0);
			assertEquals("true", responseText);
			
			// verifica l'effettivo inserimento della voce di rubrica
			Connection conn = DriverManager.getConnection(DB_URL, DB_USER,
					DB_PASSWORD);
			Statement stmt = conn.createStatement();
			ResultSet result = stmt
					.executeQuery("SELECT ID_user FROM UserData WHERE E_Mail = 'indirizzo5@dominio.it';");
			result.next();
			owner = result.getInt("ID_user");
			result = stmt
					.executeQuery(String
							.format("SELECT * FROM AddressBookEntries WHERE ID_user = '%d' AND Owner = '%d'",
									userID, owner));
			int count = 0;
			while (result.next()) {
				count++;
			}
			assertEquals(1, count);
			stmt.close();
			conn.close();
		} catch (Throwable error) {
			fail(error.getMessage());
		} finally {
			// clean-up finale
			try {
				Connection conn = DriverManager.getConnection(DB_URL, DB_USER,
						DB_PASSWORD);
				Statement stmt = conn.createStatement();
				stmt.executeUpdate(String
						.format("DELETE FROM AddressBookEntries WHERE ID_user = '%d' AND Owner = '%d';",
								userID, owner));
				stmt.executeUpdate(String
						.format("DELETE FROM AddressBookEntries WHERE ID_user = '%d' AND Owner = '%d';",
								owner, userID));
				stmt.executeUpdate(String.format("DELETE FROM Groups WHERE ID_user = '%d';", userID));
				stmt.executeUpdate(String.format(
						"DELETE FROM UserData WHERE ID_user = '%d';", userID));
				stmt.close();
				conn.close();
			} catch (Exception ex) {
				fail(ex.getMessage());
			}
		}
	}

	/**
	 * Verifica l'impossibilità di aggiungere un contatto inesistente alla
	 * rubrica (ID del contatto non valido)
	 * 
	 * @throws IOException
	 * @throws ServletException
	 * @author Diego Beraldin
	 */
	@Test
	public void testAddNotExistContact() throws IOException, ServletException {
		// crea sessione identificativa per indirizzo5@dominio.it
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
	 * Verifica l'impossibilità di portare a termine l'operazione nel caso in
	 * cui non siano forniti tutti i dati necessari all'aggiunta di un nuovo
	 * contatto alla rubrica
	 * 
	 * @throws IOException
	 * @throws ServletException
	 * @author Diego Beraldin
	 */
	@Test
	public void testAddWrongData() throws IOException, ServletException {
		// crea sessione identificativa per indirizzo5@dominio.it
		HttpSession mySession = mock(HttpSession.class);
		when(mySession.getAttribute("username")).thenReturn(
				"indirizzo5@dominio.it");

		// configura il comportamento della richiesta (manca un parametro)
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
