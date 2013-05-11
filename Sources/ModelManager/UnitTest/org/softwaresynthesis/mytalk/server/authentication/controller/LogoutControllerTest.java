package org.softwaresynthesis.mytalk.server.authentication.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import javax.security.auth.login.LoginContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;

/**
 * Verifica della classe {@link LogoutController} che in questa nuova versione
 * può finalmente definirsi un test di unità degno di questo nome!
 * 
 * @author Diego Beraldin
 * @version 2.0
 */
public class LogoutControllerTest {
	// mock di contesto di autenticazione
	private final LoginContext context = mock(LoginContext.class);
	// mock di sessione di autenticazione
	private final HttpSession session = mock(HttpSession.class);
	// mock di richiesta HTTP
	private final HttpServletRequest request = mock(HttpServletRequest.class);
	// mock di risposta HTTP
	private final HttpServletResponse response = mock(HttpServletResponse.class);
	// writer contenente lo StringBuffer associato alla risposta
	private Writer writer;
	// qui l'oggetto da testare può essere una vera istanza di LogoutController
	private final LogoutController tester = new LogoutController();

	/**
	 * Reinizializza il comportamento di tutti i mock prima dell'esecuzione di
	 * ciascuna delle verifiche contenute in questo caso di test.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Before
	public void setUp() throws Exception {
		// azzera il contenuto del writer
		writer = new StringWriter();
		// configura il comportamento della risposta
		when(response.getWriter()).thenReturn(new PrintWriter(writer));
		// configura il comportamento della richiesta
		when(request.getSession(anyBoolean())).thenReturn(session);
		// configura il comportamento della sessione
		when(session.getAttribute("context")).thenReturn(context);
	}

	/**
	 * Verifica la possibilità di effettuare logout da parte di un utente che ha
	 * superato la fase di autenticazione. Il test verifica che la richiesta sia
	 * utilizzata correttamente (con il parametro <code>false</code>) per
	 * ottenere la sessione e che dalla sessione sia recuperato il contesto di
	 * autenticazione. È inoltre verificato che sia invocato il logout() sul
	 * contesto, che in seguito la sessione invalidata e che la stringa stampata
	 * sulla risposta sia, come richiesto, 'true'.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testLogoutCorrectUser() throws Exception {
		// invoca il metodo da testare
		tester.doAction(request, response);

		// verifica l'output
		writer.flush();
		String responseText = writer.toString();
		assertEquals("true", responseText);

		// verifica il corretto utilizzo dei mock
		verify(request).getSession(false);
		verify(request, never()).getSession(true);
		verify(session).getAttribute("context");
		verify(session).invalidate();
		verify(context).logout();
		verify(response).getWriter();
	}

	/**
	 * Verifica il comportamento della classe nel caso in cui la richiesta di
	 * logout provenga da un utente che non ha effettuato previamente
	 * l'autenticazione al sistema, situazione che è simulata impedendo di
	 * recuperare la sessione associata alla richiesta HTTP. Il test verifica
	 * che in questo caso sia stampata sulla risposta la stringa 'null' e che
	 * non sia mai manipolata la sessione per recuperare il contesto (su cui non
	 * può quindi essere invocato il metodo logout()).
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testLogoutWrongUser() throws Exception {
		// impedisce di recuperare la sessione
		when(request.getSession(anyBoolean())).thenReturn(null);

		// invoca il metodo da testare
		tester.doAction(request, response);

		// verifica l'output ottenuto
		writer.flush();
		String responseText = writer.toString();
		assertEquals("null", responseText);

		// verifica il corretto utilizzo dei mock
		verify(request).getSession(false);
		verify(session, never()).getAttribute(anyString());
		verify(session, never()).invalidate();
		verify(context, never()).logout();
		verify(response).getWriter();
	}
}
