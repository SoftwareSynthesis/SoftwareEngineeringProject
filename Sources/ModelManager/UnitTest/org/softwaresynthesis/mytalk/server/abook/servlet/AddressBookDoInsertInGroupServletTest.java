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
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

/**
 * Verifica la possibilità di aggiungere un contatto all'interno di un gruppo
 * della rubrica mediante la servlet dedicata a questo scopo, che viene invocata
 * tramite una richiesta HTTP con il metodo POST
 * 
 * @author Diego Beraldin
 */
public class AddressBookDoInsertInGroupServletTest {
	// oggetto da testare
	private static AddressBookDoInsertInGroupServlet tester;
	// stub di richiesta HTTP
	private HttpServletRequest request;
	// stub di risposta HTTP
	private HttpServletResponse response;
	// contiene il buffer in cui salvare il testo della risposta
	private StringWriter writer;
	// dati per effettuare l'accesso al database
	private static String DB_URL;
	private static String DB_USER;
	private static String DB_PASSWORD;

	/**
	 * Inizializza l'oggetto da testare, prima di tutte le verifiche contenute
	 * in questo test case
	 * 
	 * @author Diego Beraldin
	 */
	@BeforeClass
	public static void setUpBeforeClass() {
		tester = new AddressBookDoInsertInGroupServlet();
		DB_URL = "jdbc:mysql://localhost/MyTalk";
		DB_USER = "root";
		DB_PASSWORD = "root";
	}

	/**
	 * Prima di ogni test, crea gli stub necessari alla sua esecuzione e azzera
	 * il contenuto del buffer in cui sarà memorizzato il testo della risposta
	 * 
	 * @author Diego Beraldin
	 */
	@Before
	public void setUp() {
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		writer = new StringWriter();
	}

	private int createDummyUser(String email) {
		int user = -1;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(DB_URL, DB_USER,
					DB_PASSWORD);
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(String
					.format("INSERT INTO UserData (E_Mail, Password, Question, Answer) VALUES ('%s', '*', '*', '*');",
							email));
			ResultSet result = stmt
					.executeQuery(String
							.format("SELECT ID_user FROM UserData WHERE E_Mail = '%s';",
									email));
			while (result.next()) {
				user = result.getInt("ID_user");
			}
			stmt.close();
			conn.close();
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		return user;
	}

	private int getTestOwner() {
		int owner = -1;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(DB_URL, DB_USER,
					DB_PASSWORD);
			Statement stmt = conn.createStatement();
			ResultSet result = stmt
					.executeQuery("SELECT ID_user FROM UserData WHERE E_Mail = 'indirizzo5@dominio.it';");
			while (result.next()) {
				owner = result.getInt("ID_user");
			}
			stmt.close();
			conn.close();
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		return owner;
	}

	private int createDummyGroup(String name, int owner) {
		int group = -1;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(DB_URL, DB_USER,
					DB_PASSWORD);
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(String.format(
					"INSERT INTO Groups(Name, ID_user) VALUES ('%s', '%d');",
					name, owner));
			ResultSet result = stmt
					.executeQuery(String
							.format("SELECT ID_group FROM Groups WHERE Name = '%s' AND ID_user = '%d';",
									name, owner));
			while (result.next()) {
				group = result.getInt("ID_group");
			}
			stmt.close();
			conn.close();
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		return group;
	}

	/**
	 * Verifica il corretto inserimento dell'utente passato come parametro
	 * tramite la richiesta a un gruppo, anch'esso passato tramite la richiesta.
	 * Tanto l'utente quanto il gruppo sono già presenti nella rubrica
	 * dell'utente da cui proviene la richiesta
	 * 
	 * @throws IOException
	 * @throws ServletException
	 * @author Diego Beraldin
	 */
	@Test
	public void testAddCorrectContact() throws IOException, ServletException {
		// operazioni preliminari: inserisce un utente di test nel sistema e
		// crea un gruppo 'dummygroup' nella rubrica dell'utente identificato
		// dall'indirizzo indirizzo5@dominio.it, che dovrà ospitare il nuovo
		// utente
		int userID = createDummyUser("dummy@dummy.du");
		int owner = getTestOwner();
		int groupID = createDummyGroup("dummygroup", owner);

		// crea una sessione di autenticazione valida per
		// l'utente associato a 'indirizzo5@dominio.it'
		HttpSession mySession = mock(HttpSession.class);
		when(mySession.getAttribute("username")).thenReturn(
				"indirizzo5@dominio.it");

		// configura il comportamento della richiesta
		when(request.getSession(false)).thenReturn(mySession);
		when(request.getParameter("contactId")).thenReturn(
				Integer.toString(userID));
		when(request.getParameter("groupId")).thenReturn(
				Integer.toString(groupID));

		// configura il comportamento della risposta
		when(response.getWriter()).thenReturn(new PrintWriter(writer));

		// invoca il metodo da testare
		tester.doPost(request, response);

		try {
			// verifica l'output ottenuto dalla servlet
			writer.flush();
			String responseText = writer.toString();
			assertNotNull(responseText);
			assertFalse(responseText.length() == 0);
			assertEquals(responseText, "true");

			// verifica l'effettivo inserimento del contatto
			Connection conn = DriverManager.getConnection(DB_URL, DB_USER,
					DB_PASSWORD);
			Statement stmt = conn.createStatement();
			ResultSet result = stmt
					.executeQuery(String
							.format("SELECT * FROM AddressBookEntries WHERE ID_user = '%d' AND Owner = '%d';",
									userID, owner));
			while (result.next()) {
				int id = result.getInt("ID_group");
				assertEquals(groupID, id);
			}
			stmt.close();
			conn.close();
		} catch (Throwable error) {
			fail(error.getMessage());
		} finally {
			// operazioni di clean-up
			try {
				Connection conn = DriverManager.getConnection(DB_URL, DB_USER,
						DB_PASSWORD);
				Statement stmt = conn.createStatement();
				stmt.executeUpdate(String.format(
						"DELETE FROM Groups WHERE ID_group = '%d';", groupID));
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
	 * Verifica il fallimento dell'operazione di inserimento di un contatto in
	 * un gruppo nel momento in cui l'ID del contatto in questione non
	 * corrisponde ad alcuno degli utenti registrati nel sistema
	 * 
	 * @throws IOException
	 * @throws ServletException
	 * @author Diego Beraldin
	 */
	@Test
	public void testAddNotExistContact() throws IOException, ServletException {
		// crea una sessione di autenticazione
		HttpSession mySession = mock(HttpSession.class);
		when(mySession.getAttribute("username")).thenReturn(
				"indirizzo5@dominio.it");

		// configura il comportamento della richiesta
		when(request.getSession(false)).thenReturn(mySession);
		when(request.getParameter("contactId")).thenReturn("-1");
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
		assertEquals("false", responseText);
	}

	/**
	 * Verifica la corretta gestione del fallimento dell'operazione di
	 * inserimento di un utente in un gruppo nel momento in cui il gruppo
	 * passato tramite la richiesta non corrisponde a nessuno dei gruppi
	 * presenti nel database
	 * 
	 * @throws IOException
	 * @throws ServletException
	 * @author Diego Beraldin
	 */
	@Test
	public void testAddNotExistGroup() throws IOException, ServletException {
		// crea una sessione di autenticazione
		HttpSession mySession = mock(HttpSession.class);
		when(mySession.getAttribute("username")).thenReturn(
				"indirizzo5@dominio.it");

		// configura il comportamento della richiesta
		when(request.getSession(false)).thenReturn(mySession);
		when(request.getParameter("contactId")).thenReturn("1");
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
	 * Verifica il comportamento della servlet nel momento in cui si tenta di
	 * aggiungere un utente in un gruppo che non appartiene all'utente da cui
	 * proviene la richiesta di aggiunta
	 * 
	 * @throws IOException
	 * @throws ServletException
	 * @author Diego Beraldin
	 */
	@Test
	public void testAddInNotOwnedGroup() throws IOException, ServletException {
		int otherUserID = createDummyUser("dummy2@dummy.du");
		// il gruppo appartiene ad OtherUser
		int groupID = createDummyGroup("dummygroup", otherUserID);
		int userID = createDummyUser("dummy@dummy.du");

		// crea sessione di autenticazione
		HttpSession mySession = mock(HttpSession.class);
		when(mySession.getAttribute("username")).thenReturn(
				"indirizzo5@dominio.it");

		// configura il comportamento della risposta
		when(request.getSession(false)).thenReturn(mySession);
		when(request.getParameter("groupId")).thenReturn(
				Integer.toString(groupID));
		when(request.getParameter("contactId")).thenReturn(
				Integer.toString(userID));

		// configura il comportamento della risposta
		when(response.getWriter()).thenReturn(new PrintWriter(writer));

		// invoca il metodo da testare
		tester.doPost(request, response);

		try {
			// verifica l'output ricevuto dalla servlet
			writer.flush();
			String responseText = writer.toString();
			assertNotNull(responseText);
			assertFalse(responseText.length() == 0);
			assertEquals("false", responseText);
		} catch (Throwable error) {
			fail(error.getMessage());
		} finally {
			// operazioni di clean-up
			try {
				Connection conn = DriverManager.getConnection(DB_URL, DB_USER,
						DB_PASSWORD);
				Statement stmt = conn.createStatement();
				stmt.executeUpdate(String.format(
						"DELETE FROM Groups WHERE ID_group = '%d';", groupID));
				stmt.executeUpdate(String
						.format("DELETE FROM UserData WHERE ID_user = '%d' OR ID_User = '%d';",
								userID, otherUserID));
				stmt.close();
				conn.close();
			} catch (Exception ex) {
				fail(ex.getMessage());
			}
		}
	}

	/**
	 * Verifica il fallimento dell'operazione di inserimento di un contatto in
	 * un gruppo nel caso in cui non siano passati correttamente i dati
	 * dell'utente che deve essere aggiunto al gruppo
	 * 
	 * @throws IOException
	 * @throws ServletException
	 * @author Diego Beraldin
	 */
	@Test
	public void testAddWrongUserData() throws IOException, ServletException {
		// crea una sessione di autenticazione
		HttpSession mySession = mock(HttpSession.class);
		when(mySession.getAttribute("username")).thenReturn(
				"indirizzo5@dominio.it");

		// configura il comportamento della richiesta (manca un parametro)
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
		assertEquals("false", responseText);
	}

	/**
	 * Verifica il fallimento dell'operazione di inserimento di un contatto in
	 * un gruppo nel caso in cui non siano passati correttamente i dati del
	 * gruppo della rubrica che deve essere modificato
	 * 
	 * @throws IOException
	 * @throws ServletException
	 * @author Diego Beraldin
	 */
	@Test
	public void testAddWrongGroupData() throws IOException, ServletException {
		// crea una sessione di autenticazione
		HttpSession mySession = mock(HttpSession.class);
		when(mySession.getAttribute("username")).thenReturn(
				"indirizzo5@dominio.it");

		// configura il comportamento della richiesta (manca un parametro)
		when(request.getSession(false)).thenReturn(mySession);
		when(request.getParameter("contactId")).thenReturn("1");

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
