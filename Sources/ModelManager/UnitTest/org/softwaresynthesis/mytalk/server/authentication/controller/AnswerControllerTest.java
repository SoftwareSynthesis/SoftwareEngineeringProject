package org.softwaresynthesis.mytalk.server.authentication.controller;

import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.softwaresynthesis.mytalk.server.abook.IUserData;
import org.softwaresynthesis.mytalk.server.authentication.security.ISecurityStrategy;
import org.softwaresynthesis.mytalk.server.dao.DataPersistanceManager;

/**
 * Verifica della classe {@link AnswerController}
 * 
 * @author Diego Beraldin
 * @version 2.0
 */
@RunWith(MockitoJUnitRunner.class)
public class AnswerControllerTest {
	private final String username = "indirizzo5@dominio.it";
	private final String answer = "Antonella";
	private final String encryptedAnswer = "LZKEMNtyFhmiVzqBi2LLNA==";
	private final String password = "panettone";
	private final String encryptedPassword = "fvNCqhL7tO4pZiJIwTp25A==";
	private AnswerController tester;
	private Writer writer;
	@Mock
	private ISecurityStrategy strategy;
	@Mock
	private DataPersistanceManager dao;
	@Mock
	private IUserData user;
	@Mock
	private HttpServletRequest request;
	@Mock
	private HttpServletResponse response;

	/**
	 * Reinizializza l'oggetto da testare e configura il comportamento dei mock
	 * prima dell'esecuzione di ogni verifica contenuta in questo caso di test
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Before
	public void setUp() throws Exception {
		// configura il mock della strategia
		when(strategy.encode(answer)).thenReturn(encryptedAnswer);
		when(strategy.decode(encryptedPassword)).thenReturn(password);
		// configura il gestore di persistenza
		when(dao.getUserData(username)).thenReturn(user);
		// configura il comportamento dell'utente
		when(user.getAnswer()).thenReturn(encryptedAnswer);
		when(user.getPassword()).thenReturn(encryptedPassword);
		// configura il comportamento della richiesta
		when(request.getParameter("username")).thenReturn(username);
		when(request.getParameter("answer")).thenReturn(answer);
		// azzera il buffer per la risposta
		writer = new StringWriter();
		// configura il comportamento della risposta
		when(response.getWriter()).thenReturn(new PrintWriter(writer));
		// inizializza l'oggetto da testare (come di consueto)
		tester = new AnswerController() {
			@Override
			protected DataPersistanceManager getDAOFactory() {
				return dao;
			}

			@Override
			protected ISecurityStrategy getSecurityStrategyFactory() {
				return strategy;
			}
		};
	}

	/**
	 * Verifica che il metodo di controllo della superclasse sia sovrascritto in
	 * modo corretto, tale cioè da restituire <code>true</code> in ogni caso,
	 * indipendentemente dalla richiesta che viene passata ad esso come
	 * parametro. Il test si assicura di questo verificando il risultato e
	 * sincerandosi del fatto che la richiesta HTTP non sia utilizzata per
	 * estrarre parametri o informazioni di sessione che potrebbero influenzare
	 * il risultato.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testCheck() {
		// invoca il metodo da testare
		Boolean result = tester.check(request);
		// verifica il risultato ottenuto
		assertTrue(result);
		// verifica il corretto uso dei mock
		verify(request, never()).getParameter(anyString());
		verify(request, never()).getSession(anyBoolean());
	}

	/**
	 * Verifica la possibilità di recuperare la password da parte di un utente
	 * che è registrato al sistema e che fornisce pertanto uno username valido.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testRecoverPasswordSuccesfully() throws Exception {
		// invoca il metodo da testare
		tester.doAction(request, response);

		// verifica l'output
		writer.flush();
		String responseText = writer.toString();
		assertEquals(answer, responseText);

		// verifica il corretto utilizzo dei mock
		// TODO da completare
	}
}
