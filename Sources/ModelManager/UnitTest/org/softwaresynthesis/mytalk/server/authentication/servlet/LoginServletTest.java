package org.softwaresynthesis.mytalk.server.authentication.servlet;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//

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
	public void testLoginCorrectUser() throws Exception {
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
		String responseText = writer.toString();
		assertFalse(responseText.length() == 0);
		
		// FIXME se 'responseText' non fosse 'null' si potrebbe proseguire con il test!
	}

}
