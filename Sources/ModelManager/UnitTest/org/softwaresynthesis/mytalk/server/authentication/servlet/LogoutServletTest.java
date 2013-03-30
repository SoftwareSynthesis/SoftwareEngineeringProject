package org.softwaresynthesis.mytalk.server.authentication.servlet;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.security.auth.login.LoginContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class LogoutServletTest {
	
	private static LogoutServlet tester;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private StringWriter writer;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		tester = new LogoutServlet();
	}

	@Before
	public void setUp() throws Exception {
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		writer = new StringWriter();
	}

	@Test
	public void testLogoutCorrectUser() throws IOException, ServletException {
		// imposta la richiesta per restituire una sessione che ha successo
		HttpSession successSession = mock(HttpSession.class);
		when(successSession.getAttribute("context")).thenReturn(mock(LoginContext.class));
		when(request.getSession(false)).thenReturn(successSession);
		
		// configura il comportamento della risposta
		when(response.getWriter()).thenReturn(new PrintWriter(writer));
		
		// invoca il metodo da testare
		tester.doPost(request, response);
		
		writer.flush();
		String responseText = writer.toString();
		assertFalse(responseText.length() == 0);
		assertEquals(responseText, "true");

	}
	
	@Test
	public void testLogoutWrongUser() throws IOException, ServletException {
		// imposta la richiesta per restituire una sessione che non ha successo
		HttpSession failSession = mock(HttpSession.class);
		when(request.getSession(false)).thenReturn(failSession);
		
		// configura il comportamento della risposta
		when(response.getWriter()).thenReturn(new PrintWriter(writer));
		
		// invoca il metodo da testare
		writer.getBuffer().setLength(0);
		tester.doPost(request, response);
		writer.flush();
		String responseText = writer.toString();
		assertFalse(responseText.length() == 0);
		assertEquals(responseText, "false");
	}

}
