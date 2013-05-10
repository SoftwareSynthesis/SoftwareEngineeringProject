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
import org.softwaresynthesis.mytalk.server.abook.IUserData;
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
	 * della classe contenitore).
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
		protected LoginContext contextFactory() {
			return context;
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
		ArgumentCaptor<String> arg = ArgumentCaptor.forClass(String.class);
		when(strategy.encode(arg.capture())).thenReturn(arg.getValue());
		// azzera il buffer per il testo della risposta
		writer = new StringWriter();
		// configura il comportamento della risposta
		when(response.getWriter()).thenReturn(new PrintWriter(writer));
		// configura il comportamento della richiesta
		when(request.getSession(anyBoolean())).thenReturn(session);
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
