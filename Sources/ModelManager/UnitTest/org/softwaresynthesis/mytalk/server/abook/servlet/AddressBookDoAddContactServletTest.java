package org.softwaresynthesis.mytalk.server.abook.servlet;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
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

public class AddressBookDoAddContactServletTest {
	
	private static AddressBookDoAddContactServlet tester;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private StringWriter writer;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		tester = new AddressBookDoAddContactServlet();
	}

	@Before
	public void setUp() throws Exception {
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		writer = new StringWriter();
		when(response.getWriter()).thenReturn(new PrintWriter(writer));
	}

	@Test
	public void testAddCorrectContact() throws IOException, ServletException {
		// crea sessione identificativa per indirizzo5@dominio.it
		HttpSession mySession = mock(HttpSession.class);
		when(mySession.getAttribute("username")).thenReturn("indirizzo5@dominio.it");
		
		// configura il comportamento della richiesta
		when(request.getSession(false)).thenReturn(mySession);
		when(request.getParameter("contactId")).thenReturn("2");

		// invoca il metodo da testare
		tester.doPost(request, response);
		
		// verifica l'output
		writer.flush();
		String responseText = writer.toString();
		assertNotNull(responseText);
		assertEquals("true", responseText);
	}

}
