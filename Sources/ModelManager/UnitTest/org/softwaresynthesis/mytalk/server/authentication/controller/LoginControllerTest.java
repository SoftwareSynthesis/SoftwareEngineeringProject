package org.softwaresynthesis.mytalk.server.authentication.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import javax.security.auth.login.LoginContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.softwaresynthesis.mytalk.server.abook.IUserData;
import org.softwaresynthesis.mytalk.server.authentication.CredentialLoader;
import org.softwaresynthesis.mytalk.server.authentication.security.ISecurityStrategy;
import org.softwaresynthesis.mytalk.server.dao.DataPersistanceManager;

public class LoginControllerTest {
	// dati di test
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
	/**
	 * Oggetto da testare dove si fa overriding al volo dei metodi 'factory'
	 * facendo in modo che restituiscano i mock (che sono dei campi dati privati
	 * della classe contenitore) invece dei reali oggetti
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
	 * TODO da terminare
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testDoActionSuccessfully() {
	
		try {
			// invoca il metodo da testare
			tester.doAction(request, response);
			
			// verifica l'output
			writer.flush();
			String responseText = writer.toString();
			assertNotNull(responseText);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

}
