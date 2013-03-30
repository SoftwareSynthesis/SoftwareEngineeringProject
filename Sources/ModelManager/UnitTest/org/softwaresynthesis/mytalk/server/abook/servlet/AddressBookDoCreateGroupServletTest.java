package org.softwaresynthesis.mytalk.server.abook.servlet;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
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

public class AddressBookDoCreateGroupServletTest {
	private static AddressBookDoCreateGroupServlet tester;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private StringWriter writer;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		tester = new AddressBookDoCreateGroupServlet();
	}

	@Before
	public void setUp() throws Exception {
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		writer = new StringWriter();
		when(response.getWriter()).thenReturn(new PrintWriter(writer));
	}

	@Test
	public void testAddCorrectGroup() throws IOException, ServletException {
		// crea sessione di autenticazione
		HttpSession mySession = mock(HttpSession.class);
		when(mySession.getAttribute("username")).thenReturn("indirizzo5@dominio.it");
		// configura il comportamento della richiesta
		when(request.getSession(false)).thenReturn(mySession);
		when(request.getParameter("groupName")).thenReturn("gruppo0");
		// invoca il metodo da testare
		tester.doPost(request, response);
		// verifica l'output
		writer.flush();
		String responseText = writer.toString();
		assertNotNull(responseText);
		assertFalse(responseText.length() == 0);
		assertEquals("true", responseText);
	}

}
