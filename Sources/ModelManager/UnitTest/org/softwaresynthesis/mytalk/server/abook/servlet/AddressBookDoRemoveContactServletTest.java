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
 * Verifica la possibilità di rimuovere un utente dalla propria rubrica
 * 
 * @author Diego Beraldin
 */
public class AddressBookDoRemoveContactServletTest {
	// oggetto da testare
	private static AddressBookDoRemoveContactServlet tester;
	// stub di richiesta HTTP
	private HttpServletRequest request;
	// stub di risposta HTTP
	private HttpServletResponse response;
	// oggetto per simulare il writer della risposta
	private StringWriter writer;
	// dati per effettuare l'accesso al database
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
		tester = new AddressBookDoRemoveContactServlet();
		DB_URL = "jdbc:mysql://localhost/MyTalk";
		DB_USER = "root";
		DB_PASSWORD = "root";
	}

	/**
	 * Prima di ogni test, crea gli stub necessari alla sua esecuzione e azzera
	 * il buffer in cui verrà memorizzato il testo della risposta
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
	 * Verifica la possibilità di rimuovere un utente dalla rubrica identificato
	 * da dati corretti e coerenti con il contenuto del database
	 * 
	 * @throws IOException
	 * @throws ServletException
	 * @author Diego Beraldin
	 */
	@Test
	public void testRemoveCorrectUser() throws IOException, ServletException {
		int userID = -1;
		int groupID = -1;
		int owner = -1;
		// operazioni preliminari: inserisce un utente di test e lo inserisce
		// nella rubrica dell'utente con indirizzo indirizzo5@dominio.it
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
			result = stmt
					.executeQuery("SELECT ID_group FROM Groups WHERE ID_User = '5' AND Name = 'addrBookEntry';");
			while (result.next()) {
				groupID = result.getInt("ID_group");
			}
			result = stmt
					.executeQuery("SELECT ID_user FROM UserData WHERE E_Mail = 'indirizzo5@dominio.it';");
			while (result.next()) {
				owner = result.getInt("ID_user");
			}
			stmt.executeUpdate(String
					.format("INSERT INTO AddressBookEntries(ID_user, ID_group, Owner, Blocked) VALUES('%d', '%d', '%d', '0');",
							userID, groupID, owner));
			stmt.close();
			conn.close();
		} catch (Exception ex) {
			fail(ex.getMessage());
		}

		// crea una sessione di autenticazione
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
			// verifica che il contatto sia stato effettivamente eliminato
			Connection conn = DriverManager.getConnection(DB_URL, DB_USER,
					DB_PASSWORD);
			Statement stmt = conn.createStatement();
			ResultSet result = stmt
					.executeQuery(String
							.format("SELECT * FROM AddressBookEntries WHERE ID_user = '%d' AND ID_group = '%d';",
									userID, groupID));
			boolean notEmpty = result.next();
			assertFalse(notEmpty);
			stmt.close();
			conn.close();
		} catch (Exception ex) {
			fail(ex.getMessage());
		} finally {
			try {
				// operazioni di clean-up
				Connection conn = DriverManager.getConnection(DB_URL, DB_USER,
						DB_PASSWORD);
				Statement stmt = conn.createStatement();
				stmt.executeUpdate("DELETE FROM UserData WHERE E_Mail = 'dummy@dummy.du';");
				stmt.close();
				conn.close();
			} catch (Exception ex) {
				fail(ex.getMessage());
			}
		}
		// verifica l'output ottenuto dalla servlet
		writer.flush();
		String responseText = writer.toString();
		assertNotNull(responseText);
		assertFalse(responseText.length() == 0);
		assertEquals("true", responseText);
	}

	/**
	 * Verifica l'impossibilità di rimuovere dalla rubrica un contatto che non
	 * esiste nel sistema (ID non valido)
	 * 
	 * @throws IOException
	 * @throws ServletException
	 * @author Diego Beraldin
	 */
	@Test
	public void testRemoveNotExistContact() throws IOException,
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
		assertNotNull(responseText);
		assertFalse(responseText.length() == 0);
		assertEquals("false", responseText);
	}

	/**
	 * Verifica l'impossibilità di rimuovere un contatto che non appartiene alla
	 * propria rubrica
	 * 
	 * @throws IOException
	 * @throws ServletException
	 * @author Diego Beraldin
	 */
	@Test
	public void testRemoveNotFriendContact() throws IOException,
			ServletException {
		int userID = -1;
		// operazioni preliminari: inserisce un nuovo utente nel sistema che non
		// sarà nella rubrica dell'utente identificato da indirizzo5@dominio.it
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(DB_URL, DB_USER,
					DB_PASSWORD);
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("INSERT INTO UserData (E_Mail, Password, Question, Answer) VALUES ('dummy@dummy.du', '*', '*', '*');");
			ResultSet result = stmt
					.executeQuery("SELECT ID_user FROM UserData WHERE E_Mail = 'dummy@dummy.du';");
			while (result.next()) {
				userID = result.getInt("ID_user");
			}
			stmt.close();
			conn.close();
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		// crea una sessione di autenticazione
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

		// clean-up finale (elimina il contatto inserito ai fini del test)
		try {
			Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("DELETE FROM UserData WHERE E_Mail = 'dummy@dummy.du';");
			stmt.close();
			conn.close();
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		// verifica l'output ottenuto dalla servlet
		writer.flush();
		String responseText = writer.toString();
		assertNotNull(responseText);
		assertFalse(responseText.length() == 0);
		assertEquals(responseText, "false");
	}

	/**
	 * Verifica l'impossibilità di rimuovere dalla rubrica un contatto se le
	 * informazioni passate alla servlet non sono complete
	 * 
	 * @throws IOException
	 * @throws ServletException
	 * @author Diego Beraldin
	 */
	@Test
	public void testRemoveWrongData() throws IOException, ServletException {
		// crea una sessione di autenticazione
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
