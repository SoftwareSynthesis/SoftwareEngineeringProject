package org.softwaresynthesis.mytalk.server.authentication.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.softwaresynthesis.mytalk.server.abook.IUserData;
import org.softwaresynthesis.mytalk.server.dao.DataPersistanceManager;

/**
 * Verifica della classe {@link QuestionController} che in questa nuova verione
 * può finalmente essere definita un test di unità come si deve.
 * 
 * @author Diego Beraldin
 * @version 2.0
 */
public class QuestionControllerTest {
	// dati di test
	private final String username = "indirizzo5@dominio.it";
	private final String question = "Come si chiama la mia gatta?";
	// mock di un utente del sistema
	private final IUserData user = mock(IUserData.class);
	// mock di sessione di autenticazione
	private final HttpSession session = mock(HttpSession.class);
	// mock della richiesta HTTP
	private final HttpServletRequest request = mock(HttpServletRequest.class);
	// mock della risposta HTTP
	private final HttpServletResponse response = mock(HttpServletResponse.class);
	// writer su cui scrivere la risposta
	private Writer writer;
	// mock del gestore della persistenza
	private final DataPersistanceManager dao = mock(DataPersistanceManager.class);
	/*
	 * Oggetto da testare in cui viene fatto al volo l'overriding di quel che
	 * non mi interessa testare (perché non contiene logica) e sostituisco
	 * l'istanza di ritorno con un opportuno mock necessario ai test.
	 */
	private final QuestionController tester = new QuestionController() {
		@Override
		protected DataPersistanceManager getDAOFactory() {
			return dao;
		}
	};

	/**
	 * Reinizializza il comportamento dei mock e azzera il contenuto del buffer
	 * in cui sarà salvato il testo della riposta prima di ogni verifica del
	 * caso di test.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Before
	public void setUp() throws Exception {
		// configura il comportamento dell'utente
		when(user.getMail()).thenReturn(username);
		when(user.getQuestion()).thenReturn(question);
		// configura il comportamento del gestore del database
		when(dao.getUserData(username)).thenReturn(user);
		// azzera il contenuto dello StringBuffer interno al writer
		writer = new StringWriter();
		// configura il comportamento della risposta
		when(response.getWriter()).thenReturn(new PrintWriter(writer));
		// configura il comportamento della sessione
		when(session.getAttribute("username")).thenReturn(username);
		// configura il comportamento della richiesta
		when(request.getSession(anyBoolean())).thenReturn(session);
	}

	/**
	 * Verifica che il metodo di controllo della superclasse sia sovrascritto in
	 * modo corretto, tale cioè da restituire <code>true</code> in ogni caso,
	 * indipendentemente dalla richiesta che viene passata ad esso come
	 * parametro. Il test si assicura di questo verificando il risultato e
	 * assciurandosi che la richiesta HTTP non sia utilizzata per estrarre
	 * parametri o informazioni di sessione che potrebbero influenzare il
	 * risultato.
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
	 * Verifica la possibilità di recuperare la domanda segreta per il recupero
	 * della password associata a un determinato utente in fase di
	 * registrazione. In particolare il test verifica che il testo stampato
	 * sulla risposta HTTP passata come parametro al metodo doAction corriponda
	 * alla domanda dell'utente che ha inoltrato la richiesta. È inoltre
	 * assicurato grazie all'utilizzo dei mock che sia interrogata la base di
	 * dati per ottenere l'oggetto di tipo {@link IUserData} contenente i dati
	 * di registrazione e che fra questi sia recuperata la domanda segreta.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testGetQuestion() throws IOException {
		// invoca il metodo da testare
		tester.doAction(request, response);

		// verifica l'output ottenuto
		writer.flush();
		String responseText = writer.toString();
		assertEquals(question, responseText);

		// verifica il corretto utilizzo dei mock
		verify(response).getWriter();
		verify(dao).getUserData(username);
		verify(user).getQuestion();
	}
}
