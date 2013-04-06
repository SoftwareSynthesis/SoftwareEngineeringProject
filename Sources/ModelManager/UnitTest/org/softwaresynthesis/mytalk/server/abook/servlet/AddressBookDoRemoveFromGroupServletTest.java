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
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Verifica la possibilità di rimuovere un contatto da un gruppo della rubrica
 * del richiedente tramite la servlet AddressBokDoRemoveFromGroupServlet
 * 
 * @author Diego Beraldin
 */
public class AddressBookDoRemoveFromGroupServletTest {
	// oggetto da testare
	private static AddressBookDoRemoveFromGroupServlet tester;
	// stub di richiesta HTTP
	private HttpServletRequest request;
	// stub di risposta HTTP
	private HttpServletResponse response;
	// contiene il buffer in cui memorizzare il testo della risposta
	private StringWriter writer;
	// dati per l'accesso al database
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
		tester = new AddressBookDoRemoveFromGroupServlet();
		DB_URL = "jdbc:mysql://localhost/MyTalk";
		DB_USER = "root";
		DB_PASSWORD = "root";
	}

	/**
	 * Crea gli stub di richiesta e risposta HTTP e azzera il contenuto del
	 * buffer in cui sarà memorizzato il testo della riposta prima di ogni test
	 * 
	 * @author Diego Beraldin
	 */
	@Before
	public void setUp() {
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		writer = new StringWriter();

	}

	private int createDummyGroup(String name, int owner) {
		int groupID = -1;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(DB_URL, DB_USER,
					DB_PASSWORD);
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(String.format(
					"INSERT INTO Groups (Name, ID_user) VALUES ('%s', '%d');",
					name, owner));
			ResultSet result = stmt
					.executeQuery(String
							.format("SELECT ID_group FROM Groups WHERE Name = '%s' AND ID_user = '%d';",
									name, owner));
			result.next();
			groupID = result.getInt("ID_group");
			stmt.close();
			conn.close();
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		return groupID;
	}

	private int createDummyUser(String email) {
		int userID = -1;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(DB_URL, DB_USER,
					DB_PASSWORD);
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(String
					.format("INSERT INTO UserData (e_Mail, Password, Question, Answer) VALUES ('%s', '*', '*', '*');",
							email));
			ResultSet result = stmt
					.executeQuery(String
							.format("SELECT ID_user FROM UserData WHERE E_Mail = '%s';",
									email));
			result.next();
			userID = result.getInt("ID_user");
			stmt.close();
			conn.close();
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		return userID;
	}

	private int getTestOwner() {
		int userID = -1;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(DB_URL, DB_USER,
					DB_PASSWORD);
			Statement stmt = conn.createStatement();
			ResultSet result = stmt
					.executeQuery("SELECT ID_user FROM UserData WHERE E_Mail = 'indirizzo5@dominio.it';");
			result.next();
			userID = result.getInt("ID_user");
			stmt.close();
			conn.close();
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		return userID;
	}

	/**
	 * Verifica la corretta rimozione di un contatto da un gruppo, che ha luogo
	 * quando sia l'utente che il gruppo appartengono alla rubrica associata
	 * all'utente da cui proviene la richiesta
	 * 
	 * @throws IOException
	 * @throws ServletException
	 * @author Diego Beraldin
	 */
	@Test
	public void testRemoveCorrectContact() throws IOException, ServletException {
		// fasi preliminari: crea l'ambiente necessario all'esecuzione del test
		// creando un utente di prova, un gruppo in cui inserirlo e provvedendo
		// in seguito all'inserimento nel gruppo (da cui verrà rimosso)
		int owner = getTestOwner();
		int userID = createDummyUser("dummy@dummy.du");
		int groupID = createDummyGroup("dummygroup", owner);
		try {
			Connection conn = DriverManager.getConnection(DB_URL, DB_USER,
					DB_PASSWORD);
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(String
					.format("INSERT INTO AddressBookEntries(ID_user, ID_group, Owner, Blocked) VALUES ('%d', '%d', '%d', '0');",
							userID, groupID, owner));
			stmt.close();
			conn.close();
		} catch (Exception ex) {
			fail(ex.getMessage());
		}

		// crea una sessione di autenticazione che identifica il richiedente
		HttpSession mySession = mock(HttpSession.class);
		when(mySession.getAttribute("username")).thenReturn(
				"indirizzo5@dominio.it");

		// configura il comportamento della richiesta
		when(request.getSession(false)).thenReturn(mySession);
		when(request.getParameter("groupId")).thenReturn(
				Integer.toString(groupID));
		when(request.getParameter("contactId")).thenReturn(
				Integer.toString(userID));

		// configura il comportamento della risposta
		when(response.getWriter()).thenReturn(new PrintWriter(writer));

		// invoca il metodo da testare
		tester.doPost(request, response);

		writer.flush();
		String responseText = writer.toString();

		// verifica l'effettiva rimozione del contatto dal gruppo
		try {
			Connection conn = DriverManager.getConnection(DB_URL, DB_USER,
					DB_PASSWORD);
			Statement stmt = conn.createStatement();
			ResultSet result = stmt
					.executeQuery(String
							.format("SELECT * FROM AddressBookEntries WHERE ID_user = '%d' AND Owner = '%d' AND ID_group = '%d';",
									userID, owner, groupID));
			boolean empty = !result.next();
			stmt.close();
			conn.close();
			assertTrue(empty);
		} catch (Exception ex) {
			fail(ex.getMessage());
		} finally {
			try {
				// operazioni di clean-up
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
			assertNotNull(responseText);
			assertFalse(responseText.length() == 0);
			assertEquals("true", responseText);
		}
	}

	/**
	 * Verifica l'impossibilità di rimuovere un contatto da un gruppo nel caso
	 * in cui il contatto sia presente nel sistema e appartenga alla rubrica del
	 * richiedente, ma non faccia parte del gruppo da cui ne viene chiesta la
	 * rimozione
	 * 
	 * @throws IOException
	 * @throws ServletException
	 */
	@Test
	public void testRemoveNotExistUser() throws IOException, ServletException {
		// fase preliminare: crea gli oggetti per il test
		int userID = createDummyUser("dummy@dummy.du");
		int owner = getTestOwner();
		int groupID = createDummyGroup("dummygroup", owner);
		// l'utente 'dummy@dummy.du' non appartiene al gruppo 'dummygroup'
		
		// crea una sessione di autenticazione
		HttpSession mySession = mock(HttpSession.class);
		
		// configura il comportamento della richiesta
		when(request.getSession(false)).thenReturn(mySession);
		when(request.getParameter("groupId")).thenReturn(Integer.toString(groupID));
		when(request.getParameter("contactId")).thenReturn(Integer.toString(userID));
		
		// configura il comportamento della risposta
		when(response.getWriter()).thenReturn(new PrintWriter(writer));
		
		// invoca il metodo da testare
		tester.doPost(request, response);
		writer.flush();
		String responseText = writer.toString();
		
		// clean-up
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
		assertNotNull(responseText);
		assertFalse(responseText.length() == 0);
		assertEquals("false", responseText);
	}

	/**
	 * Verifica l'impossibilità di rimuovere un contatto da un gruppo nel
	 * momento in cui il contatto appartenga alla rubrica dell'utente da cui
	 * proviene la richiesta ma quest'ultima non contenga alcun gruppo con l'ID
	 * passato nella richiesta
	 * 
	 * @throws IOException
	 * @throws ServletException
	 */
	@Test
	public void testNotExistGroup() throws IOException, ServletException {
		fail("Non ho voglia di farlo");
	}

	/**
	 * Verifica l'impossibilità della rimozione nel momento in cui la richiesta
	 * non contiene tutti i dati necessari al suo completamento
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

}
