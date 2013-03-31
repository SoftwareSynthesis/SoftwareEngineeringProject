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

/**
 * Verifica la possibilità di rimuovere un contatto da un gruppo della rubrica
 * del richiedente tramite la servlet AddressBokDoRemoveFromGroupServlet
 * 
 * @author diego
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

	/**
	 * Inizializza l'oggetto da verificare prima di tutti i test
	 * 
	 * @author diego
	 */
	@BeforeClass
	public static void setUpBeforeClass() {
		tester = new AddressBookDoRemoveFromGroupServlet();
	}

	/**
	 * Crea gli stub di richiesta e risposta HTTP e azzera il contenuto del
	 * buffer in cui sarà memorizzato il testo della riposta prima di ogni test
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
	 * Verifica la corretta rimozione di un contatto da un gruppo, che ha luogo
	 * quando sia l'utente che il gruppo appartengono alla rubrica associata
	 * all'utente da cui proviene la richiesta
	 * 
	 * @throws IOException
	 * @throws ServletException
	 */
	@Test
	public void testRemoveCorrectContact() throws IOException, ServletException {
		fail("Non ancora implementato");
	}

	/**
	 * Verifica l'impossibilità di rimuovere un contatto da un gruppo nel caso
	 * in cui il contatto sia presente nel sisteme e appartenga alla rubrica del
	 * richiedente, ma non faccia parte del gruppo da cui ne viene chiesta la
	 * rimozione
	 * 
	 * @throws IOException
	 * @throws ServletException
	 */
	@Test
	public void testRemoveNotExistUser() throws IOException, ServletException {
		fail("Non ancora implementato");
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
		fail("Non ancora implementato");
	}

	/**
	 * Verifica l'impossibilità della rimozione nel momento in cui la richiesta
	 * non contiene tutti i dati necessari al suo completamento
	 * 
	 * @throws IOException
	 * @throws ServletException
	 * @author diego
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
