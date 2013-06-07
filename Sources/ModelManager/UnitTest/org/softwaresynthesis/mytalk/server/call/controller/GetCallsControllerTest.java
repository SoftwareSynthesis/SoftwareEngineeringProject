package org.softwaresynthesis.mytalk.server.call.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.softwaresynthesis.mytalk.server.abook.IUserData;
import org.softwaresynthesis.mytalk.server.call.ICall;
import org.softwaresynthesis.mytalk.server.call.ICallList;
import org.softwaresynthesis.mytalk.server.dao.DataPersistanceManager;

/**
 * Verifica della classe {@link GetCallsController}
 * 
 * @author Diego Beraldin
 * @version 2.0
 */
@RunWith(MockitoJUnitRunner.class)
public class GetCallsControllerTest {
	private final String username = "indirizzo5@dominio.it";
	private final String calleeUsername = "indirizzo4@dominioit";
	private final Long userId = 1L;
	private final Long calleeId = 5L;
	private final Long callId = 1L;
	private Writer writer;
	private GetCallsController tester;
	private Date startDate = new Date(1368437034437L);
	Set<ICallList> callListSet;
	Set<ICallList> otherCallListSet;
	Set<ICallList> callCallListSet;
	@Mock
	private HttpServletRequest request;
	@Mock
	private HttpServletResponse response;
	@Mock
	private DataPersistanceManager dao;
	@Mock
	private IUserData user;
	@Mock
	private IUserData callee;
	@Mock
	private IUserData caller;
	@Mock
	ICallList callList;
	@Mock
	ICallList otherCallList;
	@Mock
	ICall call;

	/**
	 * Reinizializza l'oggetto da testare e configura il comportamento dei mock
	 * prima di ognuna delle verifiche contenute in questo caso di test.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Before
	public void setUp() throws Exception {
		// configura il comportamento delle CallList
		when(callList.getUser()).thenReturn(caller);
		when(callList.getCall()).thenReturn(call);
		when(callList.getCaller()).thenReturn(true);
		when(otherCallList.getUser()).thenReturn(callee);
		when(otherCallList.getCall()).thenReturn(call);
		when(otherCallList.getCaller()).thenReturn(false);
		// aggiunge la callList all'insieme di CallList del chiamante
		callListSet = new HashSet<ICallList>();
		callListSet.add(callList);
		// configura il comportamento dell'utente che richiede la lista
		when(user.getId()).thenReturn(userId);
		when(user.getCalls()).thenReturn(callListSet);
		// aggiunge l'altra callList all'insieme del chiamato
		otherCallListSet = new HashSet<ICallList>();
		otherCallListSet.add(otherCallList);
		// configura il comportamento dell'utente chiamato
		when(callee.getId()).thenReturn(calleeId);
		when(callee.getCalls()).thenReturn(otherCallListSet);
		when(callee.getMail()).thenReturn(calleeUsername);
		// configura il comportamento della chiamata
		when(call.getStart()).thenReturn(startDate);
		when(call.getId()).thenReturn(callId);
		callCallListSet = new HashSet<ICallList>();
		callCallListSet.add(otherCallList);
		when(call.getCalls()).thenReturn(callCallListSet);
		// configura il comportamento del gestore di persistenza
		when(dao.getUserData(username)).thenReturn(user);
		when(dao.getCall(callId)).thenReturn(call);
		// configura il comportamento della risposta
		writer = new StringWriter();
		when(response.getWriter()).thenReturn(new PrintWriter(writer));
		// inizializza l'oggetto da testare
		tester = new GetCallsController() {
			@Override
			protected DataPersistanceManager getDAOFactory() {
				return dao;
			}

			@Override
			protected String getUserMail() {
				return username;
			}
		};
	}

	/**
	 * Verifica il comportamento del metodo doAction quando viene invocato da
	 * parte di un utente registrato al sistema e a cui è asscoiata una lista di
	 * chiamate non vuota nel database del sistema. Il test verifica che
	 * l'output ottenuto corrisponda alla rappresentazione in formato JSON di un
	 * array enumerativo di oggetti che corrispondono alle chiamate, ognuna
	 * caratterizzata dal nome dell'altro utente coinvolto nella chiamata, dalla
	 * data di avvio e da un flag booleano che permettere di distinguere le
	 * chiamate in ingresso dalle chiamate in uscita. Durante la verifica è
	 * inoltre verificato il corretto utilizzo dei mock, in particolare
	 * l'estrazione dei dati dal sistema di persistenza
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testGetCallsCorrectUser() throws Exception {
		// invoca il metodo da testare
		tester.doAction(request, response);

		// verifica l'output
		writer.flush();
		String responseText = writer.toString();
		String toCompare = String
				.format("[{\"id\":\"%d\", \"email\":\"%s\", \"start\":\"%s\", \"caller\":%s}]",
						calleeId, calleeUsername, startDate, true);
		assertEquals(toCompare, responseText);

		// verifica il corretto utilizzo dei mock
		verify(response).getWriter();
		verify(dao).getUserData(username);
		verify(dao).getCall(callId);
		verify(user).getCalls();
		verify(callee).getId();
		verify(callee).getMail();
		verify(callList).getCall();
		verify(callList).getCaller();
		verify(call).getStart();
		verify(call).getId();
	}

	/**
	 * Verifica il comportamento della metodo doAction nel momento in cui
	 * l'utente da cui proviene la richiesta non è presente nel database. Un
	 * simile avvenimento viene simulato facendo in modo che il riferimento
	 * ottenuto dal gestore di persistenza non sia valido. In questo caso il
	 * test verifica che la stringa restituita sia effettivamente 'null' come
	 * desiderato e che non avvenga alcuna interazione con i mock che
	 * corrispondono all'utente, all'altro utente coinvolto nella chiamata, alla
	 * CallList e alla chiamata stessa.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testGetCallsWrongUser() throws Exception {
		// simula il fatto che si tratti di un utente non registrato
		when(dao.getUserData(anyString())).thenReturn(null);

		// invoca il metodo da testare
		tester.doAction(request, response);

		// verifica l'output
		writer.flush();
		String responseText = writer.toString();
		assertEquals("null", responseText);

		// verifica il corretto utilizzo dei mock
		verify(response).getWriter();
		verify(dao).getUserData(username);
		verifyZeroInteractions(callee);
		verifyZeroInteractions(user);
		verifyZeroInteractions(callList);
		verifyZeroInteractions(call);
	}

	/**
	 * Verifica il comportamento del metodo doAction nel momento in cui viene
	 * invocato con una richiesta proveniente da un utente il cui storico delle
	 * chiamate relativo è vuoto. Il test in questo caso verifica che la stringa
	 * stampata sulla risposta HTTP sia '[]', che corrisponde alla
	 * rappresentazione in formato JSON di un array vuoto. È inoltre verificato
	 * il corretto utilizzo dei mock, verificando che sia estratto l'utente dal
	 * sistema di persistenza dei dati e che sia recuperata la lista (vuota) di
	 * chiamate ad esso associata.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testGetCallsEmptyList() throws Exception {
		// sovrascrive quanto fatto nel setUp()
		callListSet = new HashSet<ICallList>();
		when(user.getCalls()).thenReturn(callListSet);

		// invoca il metodo da testare
		tester.doAction(request, response);

		// verifica l'output
		writer.flush();
		String responseText = writer.toString();
		assertEquals("[]", responseText);

		// verifica il corretto utilizzo dei mock
		verify(response).getWriter();
		verify(dao).getUserData(username);
		verify(user).getCalls();
		verifyZeroInteractions(callee);
		verifyZeroInteractions(callList);
		verifyZeroInteractions(call);
	}

	/**
	 * Verifica il comportamento del metodo doAction nel momento in cui si
	 * verifica un errore nel recupero dello storico delle chiamate dell'utente
	 * da cui proviene la richiesta e il riferimento restituito dal sistema di
	 * gestione della persistenza non è valido. Il test verifica che in questo
	 * caso sulla pagina di risposta sia stampata la stringa '[]', che
	 * corrisponde alla rappresentazione in formato JSON di un array enumerativo
	 * vuoto, che è quanto il client si aspetta in questo caso.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testNoCalls() throws Exception {
		// situazione poco probabile in realtà
		when(user.getCalls()).thenReturn(null);

		// invoca il metodo da testare
		tester.doAction(request, response);

		// verifica l'output
		writer.flush();
		String responseText = writer.toString();
		assertEquals("[]", responseText);

		// verifica il corretto utilizzo dei mock
		verify(response).getWriter();
		verify(dao).getUserData(username);
		verify(user).getCalls();
		verifyZeroInteractions(callee);
		verifyZeroInteractions(callList);
		verifyZeroInteractions(call);
	}
}
