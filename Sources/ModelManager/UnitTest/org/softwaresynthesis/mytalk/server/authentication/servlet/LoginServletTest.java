package org.softwaresynthesis.mytalk.server.authentication.servlet;

import java.io.PrintWriter;
import java.io.StringWriter;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static org.mockito.Mockito.*;

public class LoginServletTest {

	private static LoginServlet tester;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private StringWriter writer;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		tester = new LoginServlet();
	}
	
	@Before
	public void setUp() {
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		writer = new StringWriter();
	}

	@Test
	public void testDoPost() throws Exception {
		// configura il comportamento della richiesta
		when(request.getParameter("username")).thenReturn(
				"indirizzo5@dominio.it");
		when(request.getParameter("password")).thenReturn("password");
		// configura il comportamento della risposta
		when(response.getWriter()).thenReturn(new PrintWriter(writer));

		// invoca il metodo da testare
		tester.doPost(request, response);
		
		// verifica l'output della servlet
		writer.flush();
		String responseText = writer.getBuffer().toString();
		assertFalse(responseText.length() == 0);
		
		//TODO approfondire questo test!
	}

}
