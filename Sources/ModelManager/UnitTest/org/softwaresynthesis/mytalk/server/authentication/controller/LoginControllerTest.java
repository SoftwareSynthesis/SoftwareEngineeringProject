package org.softwaresynthesis.mytalk.server.authentication.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.softwaresynthesis.mytalk.server.abook.IUserData;
import org.softwaresynthesis.mytalk.server.authentication.CredentialLoader;
import org.softwaresynthesis.mytalk.server.authentication.security.ISecurityStrategy;
import org.softwaresynthesis.mytalk.server.dao.DataPersistanceManager;

/**
 * Verifica della classe {@link LoginController}, che nella nuova versione si è
 * trasformato in un test di unità degno di questa definizione!
 * 
 * @author Diego Beraldin
 * @version 2.0
 */
public class LoginControllerTest {
	// dati fittizi necessari test
	private final String username = "indirizzo5@dominio.it";
	private final String name = "paperino";
	private final String surname = "de paperoni";
	private final String path = "img/contactImg/Default.png";
	private final Long id = 1L;
	private final IUserData user = mock(IUserData.class);
	// mock di CredentialLoader
	private final CredentialLoader loader = mock(CredentialLoader.class);
	// mock del contesto di autenticazione
	private final LoginContext context = mock(LoginContext.class);
	// mock della sessione di autenticazione
	private final HttpSession session = mock(HttpSession.class);
	// mock della gestione della persistenza
	private final DataPersistanceManager dao = mock(DataPersistanceManager.class);
	// mock della strategia di crittografia
	private final ISecurityStrategy strategy = mock(ISecurityStrategy.class);
	// mock della richiesta HTTP
	private final HttpServletRequest request = mock(HttpServletRequest.class);
	// mock della risposta HTTP
	private final HttpServletResponse response = mock(HttpServletResponse.class);
	// writer da associare alla risposta
	private Writer writer;

	/*
	 * Oggetto da testare dove si fa overriding al volo dei metodi 'factory'
	 * facendo in modo che restituiscano i mock (che sono dei campi dati privati
	 * della classe contenitore) invece dei reali oggetti.
	 */
	private final LoginController tester = new LoginController() {
		@Override
		protected ISecurityStrategy getSecurityStrategyFactory() {
			return strategy;
		}

		@Override
		protected DataPersistanceManager getDAOFactory() {
			return dao;
		}

		@Override
		LoginContext contextFactory(String rule, CredentialLoader loader) {
			return context;
		}

		@Override
		CredentialLoader loaderFactory(HttpServletRequest request,
				ISecurityStrategy strategy) {
			return loader;
		}
	};

	/**
	 * Reinizializza il comportamento dei mock prima di ogni test (può
	 * occasionalmente essere sovrascritto nel corpo di un metodo di test per
	 * configurazioni particolari).
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Before
	public void setUp() throws Exception {
		// configura il comportamento dello stub di utente
		when(user.getMail()).thenReturn(username);
		when(user.getName()).thenReturn(name);
		when(user.getSurname()).thenReturn(surname);
		when(user.getId()).thenReturn(id);
		when(user.getPath()).thenReturn(path);

		// configura il comportamento della gestione della persistenza
		when(dao.getUserData(username)).thenReturn(user);

		// configura il comportamento della strategia di crittografia in modo da
		// non crittografare di fatto nulla (non mi interessa testarla)
		when(strategy.encode(anyString())).thenAnswer(new Answer<String>() {
			@Override
			public String answer(InvocationOnMock invocation) {
				return (String) invocation.getArguments()[0];
			}
		});

		// azzera il buffer per il testo della risposta e la configura
		writer = new StringWriter();
		when(response.getWriter()).thenReturn(new PrintWriter(writer));

		// configura il comportamento della richiesta
		when(request.getSession(anyBoolean())).thenReturn(session);
		when(request.getParameter("username")).thenReturn(username);
	}

	/**
	 * Verifica la possibilità di accedere al sistema da parte di un utente
	 * registrato. In questo caso allo username salvato nella sessione di
	 * autenticazione associata alla richiesta si fa corrispondere un'istanza di
	 * un sottotipo di {@link IUserData} valido simulando la gestione della
	 * persistenza. Il test verifica che l'output scritto sulla risposta
	 * corrisponda alla rappresentazione in formato JSON dei dati dell'utente
	 * necessaria al client e si accerta, inoltre, che sia invocato il metodo
	 * login() sul contesto di autenticazione dopo aver inizializzato una
	 * sessione.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testLoginCorrectUser() throws Exception {
		// invoca il metodo da testare
		tester.doAction(request, response);

		// verifica l'output
		writer.flush();
		String responseText = writer.toString();
		String toCompare = String
				.format("{\"name\":\"%s\", \"surname\":\"%s\", \"email\":\"%s\", \"id\":\"%s\", \"picturePath\":\"%s\"}",
						user.getName(), user.getSurname(), user.getMail(),
						user.getId(), user.getPath());
		assertEquals(toCompare, responseText);

		// verifica il corretto utilizzo dei mock
		verify(context).login();
		verify(request).getParameter("username");
		verify(dao).getUserData(username);
		verify(request).getSession(true);
		verify(session).setAttribute("context", context);
		verify(session).setAttribute("username", user.getMail());
		verify(session, never()).invalidate();
	}

	/**
	 * Verifica l'impossibilità di accedere al sistema da parte di un utente che
	 * non si è registrato preventivamente simulando il sollevamento di una
	 * LoginException durante l'esecuzione di login() sul contesto di
	 * autenticazione. Il test, in questo caso, verifica che non sia effettuato
	 * alcun accesso al database da parte del controller e che non sia creata
	 * una sessione di autenticazione.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testLoginWrongUser() throws Exception {
		// simula il fallimento dell'autenticazione
		doThrow(new LoginException()).when(context).login();

		// invoca il metodo da testare
		tester.doAction(request, response);

		// verifica l'output
		writer.flush();
		String responseText = writer.toString();
		assertEquals("null", responseText);

		// verifica il corretto utilizzo dei mock
		verify(context).login();
		verify(request, never()).getParameter("username");
		verify(dao, never()).getUserData(username);
		verify(request, never()).getSession(anyBoolean());
	}

	/**
	 * Verifica il comportamento della classe nel momento in cui avviene un
	 * errore nella manipolazione della sessione associata alla richiesta HTTP
	 * (che però non è <code>null</code>). Oltre ad assicurarsi che il risultato
	 * stampato sulla risposta sia la stringa 'null', come desiderato, il test
	 * verifica che la sessione sia resa non valida.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testLoginInvalidSession() throws Exception {
		// solleva un'eccezione non controllata se si manipola la sessione
		doThrow(new RuntimeException()).when(session).setAttribute(anyString(),
				any());

		// invoca il metodo da testare
		tester.doAction(request, response);

		// verifica l'output
		writer.flush();
		String responseText = writer.toString();
		assertEquals("null", responseText);

		// verifica il corretto utilizzo dei mock
		verify(context).login();
		verify(request).getParameter("username");
		verify(dao).getUserData(username);
		verify(session).invalidate();
		verify(request).getSession(anyBoolean());
		verify(session).setAttribute(anyString(), any());
	}

	/**
	 * Verifica che il metodo check abbia il comportamento corretto, vale a dire
	 * che restituisca sempre <code>true</code> indipendentemente dalla
	 * richiesta HTTP che prende in input, dalla quale non estrae MAI alcuna
	 * sessione di autenticazione (perché si tratta proprio del login e
	 * l'autenticazione non è un prerequisito).
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testCheck() {
		// invoca il metodo da testare
		assertTrue(tester.check(request));
		// verifica il corretto utilizzo dei mock
		verify(request, never()).getSession(anyBoolean());
	}
}
