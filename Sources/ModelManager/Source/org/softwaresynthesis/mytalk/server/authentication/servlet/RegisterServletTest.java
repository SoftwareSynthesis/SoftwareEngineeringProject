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

public class RegisterServletTest {

	private static RegisterServlet tester;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private StringWriter writer;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		tester = new RegisterServlet();
	}

	@Before
	public void setUp() throws Exception {
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		writer = new StringWriter();
	}

	@Test
	public void testDoPost() throws IOException, ServletException {
		// configura il comportamento della richiesta
		when(request.getParameter("username")).thenReturn("flabacco@gmail.com");
		when(request.getParameter("password")).thenReturn("farfalla");
		when(request.getParameter("question")).thenReturn("Come mi chiamo?");
		when(request.getParameter("answer")).thenReturn("flavia");

		// configura il comportamento della risposta
		when(response.getWriter()).thenReturn(new PrintWriter(writer));

		// invoca il metodo da testare
		tester.doPost(request, response);

		writer.flush();
		String responseText = writer.toString();
		assertTrue(responseText.length() == 0);
		//FIXME il test non passa
	}

}
