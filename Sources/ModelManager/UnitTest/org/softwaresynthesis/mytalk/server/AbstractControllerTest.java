package org.softwaresynthesis.mytalk.server;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

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
	// dati di test
	private final String emailAddress = "indirizzo5@dominio.it";
	// finta richiesta HTTP
	private final HttpServletRequest request = mock(HttpServletRequest.class);
	// finta risposta HTTP
	private final HttpServletResponse response = mock(HttpServletResponse.class);
	// finta sessione di autenticazione
	private final HttpSession session = mock(HttpSession.class);
	// contiene lo StringBuffer su cui è scritta la risposta
	private StringWriter writer;
	// oggetto da testare
	private final AbstractController tester = mock(AbstractController.class,
			Mockito.CALLS_REAL_METHODS);

	/**
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Before
	public void setUp() throws Exception {
		// configura il comportamento della sessione
		when(session.getAttribute("username")).thenReturn(emailAddress);
		// configura il comportamento della richiesta
		when(request.getParameter("username")).thenReturn(emailAddress);
		when(request.getSession(false)).thenReturn(session);
		// reinizializza il writer e configura il comportamento della risposta
		writer = new StringWriter();
		when(response.getWriter()).thenReturn(new PrintWriter(writer));
		// configura il comportamento del mock per il metodo astratto
		doNothing().when(tester).doAction(request, response);
	}

	/**
	 * Verifica che sia possibile impostare e recuperare l'indirizzo email
	 * dell'utente che richiede l'utilizzo del controller.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
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
			verify(tester, never()).check(request);
			verify(tester, never()).doAction(request, response);
			verify(tester, never()).execute(request, response);
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Verifica il comportamento del controller nel caso in cui si tenti di
	 * chiamare il metodo execute ma non sia possibile superare il controllo di
	 * autenticazione. In tale caso si verifica che la stringa stampata sulla
	 * risposta sia 'null' come richiesto e che siano effettuate le operazioni
	 * corrette, in particolare, si controlla che non sia MAI eseguito il
	 * doAction (dato che il controllo di autenticazione non è superato).
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 * @throws IOException
	 */
	@Test
	public void testExecuteWithNegativeCheck() throws IOException {
		// fa in modo che il controllo di autenticazione NON sia superato
		when(session.getAttribute("username")).thenReturn("");

		// invoca il metodo da testare
		try {
			tester.execute(request, response);
		} catch (IOException e) {
			fail(e.getMessage());
		}

		// verifica l'output ottenuto
		String result = writer.toString();
		assertNotNull(result);
		assertEquals("null", result);

		// verifica il corretto svolgimento delle operazioni
		verify(tester, never()).doAction(request, response);
	}

	/**
	 * FIXME
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testExecuteWithPositiveCheck() {
		// riconfigura il doAction in modo che scriva qualcosa sulla risposta
		try {
			doAnswer(new Answer<Object>() {
				@Override
				public Object answer(InvocationOnMock invocation) {
					Object[] args = invocation.getArguments();
					HttpServletResponse responseMock = (HttpServletResponse) args[1];
					try {
						responseMock.getWriter().write("true");
					} catch (IOException e) {
						e.printStackTrace();
					}

					return null;
				}
			}).when(tester).doAction(request, response);
		} catch (Exception e) {
			fail(e.getMessage());
		}

		// invoca il metodo da testare
		try {
			tester.execute(request, response);
		} catch (IOException e) {
			fail(e.getMessage());
		}

		// verifica l'output ottenuto
		String result = writer.toString();
		assertNotNull(result);
		assertEquals("true", result);
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
