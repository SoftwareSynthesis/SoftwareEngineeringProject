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
import javax.sql.DataSource;
import java.io.StringWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

/**
 * Verifica la possibilità di cancellare un gruppo dalla propria rubrica
 * 
 * @author Diego Beraldin
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
	// dati per la connessione al database
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
		tester = new AddressBookDoDeleteGroupServlet();
		DB_URL = "jdbc:mysql://localhost/MyTalk";
		DB_USER = "root";
		DB_PASSWORD = "root";
	}

	/**
	 * Ricrea tutti gli stub prima dell'esecuzione di ogni test e azzera il
	 * buffer in cui sarà memorizzato il testo della risposta
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
	 * Verifica la corretta rimozione di un gruppo dalla rubrica di un utente
	 * 
	 * @throws IOException
	 * @throws ServletException
	 * @author Diego Beraldin
	 */
	@Test
	public void testRemoveCorrectGroup() throws IOException, ServletException {
		int groupID = -1;

		// inserisce un gruppo per il test
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(DB_URL, DB_USER,
					DB_PASSWORD);
			Statement stmt = conn.createStatement();
			// inserisce il gruppo nel database
			stmt.executeUpdate("INSERT INTO Groups(Name, ID_user) VALUES ('dummygroup', '5');");
			// recupera l'identificativo del gruppo che è appena stato inserito
			ResultSet result = stmt
					.executeQuery("SELECT ID_group FROM Groups WHERE Name = 'dummygroup' AND ID_user = '5';");
			result.next();
			groupID = result.getInt("ID_group");
			stmt.close();
			conn.close();
		} catch (Exception ex) {
			fail(ex.getMessage());
		}

		// crea sessione di autenticazione
		HttpSession mySession = mock(HttpSession.class);
		when(mySession.getAttribute("username")).thenReturn(
				"indirizzo5@dominio.it");

		// configura il comportamento della richiesta
		when(request.getSession(false)).thenReturn(mySession);
		when(request.getParameter("groupId")).thenReturn(
				Integer.toString(groupID));

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

		// verifica che il gruppo sia stato effettivamente cancellato
		try {
			Connection conn = DriverManager.getConnection(DB_URL, DB_USER,
					DB_PASSWORD);
			Statement stmt = conn.createStatement();
			ResultSet result = stmt
					.executeQuery("SELECT * FROM Groups WHERE ID_user = '5' AND Name = 'dummygroup';");
			boolean notEmpty = result.next();
			assertFalse(notEmpty);
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}

	/**
	 * Verifica la corretta gestione del caso in cui giunga la richiesta di
	 * eliminare un gruppo che non esiste nel database
	 * 
	 * @throws IOException
	 * @throws ServletException
	 * @author Diego Beraldin
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
	 * Verifica la corretta gestione del caso in cui giunga la richiesta di
	 * eliminare un gruppo che non esiste nella rubrica dell'utente ma esiste
	 * nel sistema (e appartiene ad un altro utente)
	 * 
	 * @throws IOException
	 * @throws ServletException
	 * @author Diego Beraldin
	 */
	@Test
	public void testRemoveNotFriendContact() throws IOException,
			ServletException {
		// fase preliminare: inserisce un utente e il suo gruppo addrBookEntry
		int groupID = -1;
		try {
			int userID = -1;
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(DB_URL, DB_USER,
					DB_PASSWORD);
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("INSERT INTO UserData(E_Mail, Password, Question, Answer) VALUES('dummy@dummy.du', '*', '*', '*');");
			ResultSet result = stmt
					.executeQuery("SELECT ID_user FROM UserData WHERE E_Mail = 'dummy@dummy.du';");
			while (result.next()) {
				userID = result.getInt("ID_user");
			}
			stmt.executeUpdate(String
					.format("INSERT INTO Groups(Name, ID_user) VALUES ('dummygroup', '%d');",
							userID));
			result = stmt
					.executeQuery(String
							.format("SELECT ID_group FROM Groups WHERE Name = 'dummygroup' AND ID_user = '%d';",
									userID));
			while (result.next()) {
				groupID = result.getInt("ID_group");
			}
			stmt.close();
			conn.close();
		} catch (Exception ex) {
			fail(ex.getMessage());
		}

		// crea sessione di autenticazione
		HttpSession mySession = mock(HttpSession.class);
		when(mySession.getAttribute("username")).thenReturn(
				"indirizzo5@dominio.it");

		// configura il comportamento della richiesta
		when(request.getSession(false)).thenReturn(mySession);
		when(request.getParameter("groupId")).thenReturn(
				Integer.toString(groupID));

		// configura il comportamento della risposta
		when(response.getWriter()).thenReturn(new PrintWriter(writer));

		// invoca il metodo da testare
		tester.doPost(request, response);

		// verifiche finali
		try {
			// verifica l'output ottenuto dalla servlet
			writer.flush();
			String responseText = writer.toString();
			assertNotNull(responseText);
			assertFalse(responseText.length() == 0);
			assertEquals("false", responseText);
			
			// verifica che il gruppo non viene eliminato
			Connection conn = DriverManager.getConnection(DB_URL, DB_USER,
					DB_PASSWORD);
			Statement stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery(String.format(
					"SELECT * FROM Groups WHERE ID_group = '%d';", groupID));
			boolean empty = !result.next();
			stmt.close();
			conn.close();
			assertFalse(empty);
		} catch (Exception ex) {
			fail(ex.getMessage());
		} finally {
			// clean-up finale: elimina l'utente finto e il gruppo finto
			try {
				Connection conn = DriverManager.getConnection(DB_URL, DB_USER,
						DB_PASSWORD);
				Statement stmt = conn.createStatement();
				stmt.executeUpdate(String.format(
						"DELETE FROM Groups WHERE ID_group = '%d';", groupID));
				stmt.executeUpdate("DELETE FROM UserData WHERE E_Mail = 'dummy@dummy.du';");
				stmt.close();
				conn.close();
			} catch (Exception ex) {
				fail(ex.getMessage());
			}
		}
	}

	/**
	 * Verifica la corretta gestione del caso in cui giunga la richiesta di
	 * eliminare un gruppo con un numero di parametri insufficiente
	 * 
	 * @throws IOException
	 * @throws ServletException
	 * @author Diego Beraldin
	 */
	@Test
	public void testRemoveWrongData() throws IOException, ServletException {
		// crea sessione di autenticazione
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
