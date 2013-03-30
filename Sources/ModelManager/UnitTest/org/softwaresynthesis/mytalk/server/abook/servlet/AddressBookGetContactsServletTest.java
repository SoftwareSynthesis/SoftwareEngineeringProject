package org.softwaresynthesis.mytalk.server.abook.servlet;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
import javax.servlet.http.HttpSession;

public class AddressBookGetContactsServletTest {

	private static AddressBookGetContactsServlet tester;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private StringWriter writer;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		tester = new AddressBookGetContactsServlet();
	}

	@Before
	public void setUp() throws Exception {
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		writer = new StringWriter();
	}

	@Test
	public void testDoPost() throws ServletException, IOException {
		// crea una sessione che identifica l'utente 'indirizzo5@dominio.it'
		HttpSession mySession = mock(HttpSession.class);
		when(mySession.getAttribute("username")).thenReturn("indirizzo5@dominio.it");
		
		// configura il comportamento della richiesta
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
		
		// FIXME da me il test non passa a causa di ChannelServlet.getState a riga 101 di AddressBookGetContactsServlet
		String toCompare = "{" +
		"\"3\":{\"name\":\"luigi\", \"surname\":\"gialli\", \"email\":\"indirizzo3@dominio.it\", " +
		        "\"id\":\"3\", \"picturePath\":\"img/contactImg/Default.png\", \"state\":\"offline\", \"blocked\":\"false\"}," + 
		"\"4\":{\"name\":\"piero\", \"surname\":\"pelu\", \"email\":\"indirizzo4@dominio.it\", " +
		        "\"id\":\"4\", \"picturePath\":\"img/contactImg/Default.png\", \"state\":\"offline\", \"blocked\":\"false\"}," + 
		"\"2\":{\"name\":\"marco\", \"surname\":\"verdi\", \"email\":\"indirizzo2@dominio.it\", " +
		        "\"id\":\"2\", \"picturePath\":\"img/contactImg/Default.png\", \"state\":\"offline\", \"blocked\":\"false\"}" +
		"}";
		assertEquals(toCompare, responseText);
	}
}
