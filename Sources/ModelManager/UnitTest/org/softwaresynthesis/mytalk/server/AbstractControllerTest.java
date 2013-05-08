package org.softwaresynthesis.mytalk.server;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * I verificatori non sono impazziti! Qui testiamo solo i metodi NON ASTRATTI e
 * che nel loro corpo NON richiamano metodi astratti, vale a dire le uniche cose
 * che ha senso testare in una classe astratta! :) Inoltre, dato che una
 * siffatta classe non può essere istanziata, useremo uno speciale tipo di mock
 * che richiama ove possibile i metodi reali.
 * 
 * @author Diego Beraldin
 * @version 2.0
 */
public class AbstractControllerTest {
	private final String emailAddress = "indirizzo5@dominio.it";
	private final AbstractController tester = mock(AbstractController.class,
			Mockito.CALLS_REAL_METHODS);
	private final HttpServletRequest request = mock(HttpServletRequest.class);
	private final HttpServletResponse response = mock(HttpServletResponse.class);
	private StringWriter writer;

	@Before
	public void setUp() throws Exception {
		// configura il comportamento della richiesta
		when(request.getParameter("username")).thenReturn(emailAddress);
		// configura il comportamento del mock per il metodo astratto
		doNothing().when(tester).doAction(any(HttpServletRequest.class),
				any(HttpServletResponse.class));
		writer = new StringWriter();
		when(response.getWriter()).thenReturn(new PrintWriter(writer));
	}

	@Test
	public void testUserMail() {
		// invoca i metodi da testare
		tester.setUserMail(emailAddress);
		String result = tester.getUserMail();
		// verifica del risultato ottenuto
		assertNotNull(result);
		assertEquals(emailAddress, result);
		// verifica che non siano fatte operazioni non richieste
		try {
			verify(tester, never()).check(any(HttpServletRequest.class));
			verify(tester, never()).doAction(request, response);
			verify(tester, never()).execute(request, response);
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testCheck() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetDAOFactory() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetSecurityStrategyFactory() {
		fail("Not yet implemented");
	}

}
