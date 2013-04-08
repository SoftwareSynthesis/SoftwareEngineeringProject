package org.softwaresynthesis.mytalk.server.authentication.servlet;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;

/**
 * Verifica la possibilità di registrarsi al sistema mediante la servlet
 * dedicata, che viene interrogata mediante richieste HTTP POST.
 * 
 * @author Diego Beraldin
 */
public class RegisterServletTest {
	// oggetto da testare
	private static RegisterServlet tester;
	// stub di richiesta HTTP
	private HttpServletRequest request;
	// stub di risposta HTTP
	private HttpServletResponse response;
	// base per recuperare il testo della risposta
	private StringWriter writer;

	/**
	 * Inizializza l'oggetto da verificare prima di tutti i test
	 * 
	 * @author Diego Beraldin
	 */
	@BeforeClass
	public static void setUpBeforeClass() {
		tester = new RegisterServlet();
	}

	/**
	 * Prima di ogni test, ricrea gli stub necessari alla sua esecuzione e
	 * azzera il buffer in cui verrà memorizzato il testo della risposta
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
	 * Verifica la possibilità di accedere al sistema da parte di un utente che
	 * ha inserito tutti i dati obbligatori per la procedura di registrazione
	 * 
	 * @throws IOException
	 * @throws ServletException
	 * @author Diego Beraldin
	 */
	@Test
	public void testRegisterCorrectUser() throws IOException, ServletException {
		// configura il comportamento della richiesta
		when(request.getParameter("username")).thenReturn("flabacco@gmail.com");
		when(request.getParameter("password")).thenReturn("farfalla");
		when(request.getParameter("question")).thenReturn("Come mi chiamo?");
		when(request.getParameter("answer")).thenReturn("flavia");

		// configura il comportamento della risposta
		when(response.getWriter()).thenReturn(new PrintWriter(writer));

		// invoca il metodo da testare
		tester.doPost(request, response);

		// verifica l'output
		writer.flush();
		String responseText = writer.toString();
		try {
			assertNotNull(responseText);
			assertFalse(responseText.length() == 0);
			// FIXME il client ha bisogno di user.toString() e non di 'true'!
			assertEquals("true", responseText);
		} catch (AssertionError err) {
			fail(err.getMessage());
		} finally {
			// operazioni di clean-up al termine del test
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection conn = DriverManager.getConnection(
						"jdbc:mysql://localhost/MyTalk", "root", "root");
				Statement stmt = conn.createStatement();
				stmt.execute("DELETE FROM Groups WHERE ID_user = (SELECT ID_user FROM UserData WHERE E_Mail = 'flabacco@gmail.com');");
				stmt.execute("DELETE FROM UserData WHERE E_Mail = 'flabacco@gmail.com';");
				conn.close();
			} catch (Exception ex) {
				fail(ex.getMessage());
			}
		}
	}

	/**
	 * Verifica l'impossibilità di registrarsi al sistema nel caso in cui non
	 * siano inseriti tutti i dati obbligatori per la creazione di un account
	 * 
	 * @throws IOException
	 * @throws ServletException
	 * @author Diego Beraldin
	 */
	@Test
	public void testRegisterWrongUser() throws IOException, ServletException {
		// configura il comportamento della richiesta (manca un dato
		// obbligatorio)
		when(request.getParameter("username")).thenReturn("flabacco@gmail.com");
		when(request.getParameter("question")).thenReturn("Come mi chiamo?");
		when(request.getParameter("answer")).thenReturn("flavia");

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