package org.softwaresynthesis.mytalk.server.abook.servlet;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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

public class AddressBookGetGroupsServletTest {
	
	private static AddressBookGetGroupsServlet tester;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private StringWriter writer;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		tester = new AddressBookGetGroupsServlet();
	}

	@Before
	public void setUp() throws Exception {
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		writer = new StringWriter();
		when(response.getWriter()).thenReturn(new PrintWriter(writer));
	}

	@Test
	public void testBlockCorrectContact() throws IOException, ServletException {
		// crea una sessione di autenticazione
		HttpSession mySession = mock(HttpSession.class);
		when(mySession.getAttribute("username")).thenReturn("indirizzo5@dominio.it");
		
		// configura il comportamento della richiesta
		when(request.getSession(false)).thenReturn(mySession);
		
		// invoca il metodo da testare
		tester.doPost(request, response);
		
		// verifica l'output
		writer.flush();
		String responseText = writer.toString();
		assertFalse(responseText.length() == 0);
		String toCompare = "{" + 
		"\"1\":{\"name\":\"Gruppo 1\",\"id\":\"1\",\"contacts\":[1]}," + 
				"\"3\":{\"name\":\"Gruppo 3\",\"id\":\"3\",\"contacts\":[]}," +
		"\"10\":{\"name\":\"addrBookEntry\",\"id\":\"10\",\"contacts\":[23,29,26,41,15,35,32,17,9,11,13,20,38,44]}" +
		"}";
		assertEquals(toCompare, responseText);
		
	}
}
