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

/**
 * Verifica la possibilità di aggiungere un contatto all'interno di un gruppo
 * della rubrica mediante la servlet dedicata a questo scopo, che viene invocata
 * tramite una richiesta HTTP con il metodo POST
 * 
 * @author diego
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

	/**
	 * Inizializza l'oggetto da testare, prima di tutte le verifiche contenute
	 * in questo test case
	 * 
	 * @author diego
	 */
	@BeforeClass
	public static void setUpBeforeClass() {
		tester = new AddressBookDoInsertInGroupServlet();
	}

	/**
	 * Prima di ogni test, crea gli stub necessari alla sua esecuzione e azzera
	 * il contenuto del buffer in cui sarà memorizzato il testo della risposta
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
	 * Verifica il corretto inserimento dell'utente passato come parametro
	 * tramite la richiesta a un gruppo, anch'esso passato tramite la richiesta.
	 * Tanto l'utente quanto il gruppo sono già presenti nella rubrica
	 * dell'utente da cui proviene la richiesta
	 * 
	 * @throws IOException
	 * @throws ServletException
	 */
	@Test
	public void testAddCorrectContact() throws IOException, ServletException {
		fail("Non ancora implementato!");
	}

	/**
	 * Verifica il fallimento dell'operazione di inserimento di un contatto in
	 * un gruppo nel momento in cui l'ID del contatto in questione non
	 * corrisponde ad alcuno degli utenti registrati nel sistema
	 * 
	 * @throws IOException
	 * @throws ServletException
	 * @author diego
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
	 * passato tramite la richiesta non è di proprietà dell'utente che la invia
	 * alla servlet oggetto di test
	 * 
	 * @throws IOException
	 * @throws ServletException
	 */
	public void testAddNotExistGroup() throws IOException, ServletException {
		fail("Non ancora implementato!");
	}

	/**
	 * Verifica il fallimento dell'operazione di inserimento di un contatto in
	 * un gruppo nel caso in cui non siano passati correttamente i dati
	 * dell'utente che deve essere aggiunto al gruppo
	 * 
	 * @throws IOException
	 * @throws ServletException
	 * @author diego
	 */
	@Test
	public void testAddNotWrongUserData() throws IOException, ServletException {
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
	 * @author diego
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
