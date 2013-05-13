package org.softwaresynthesis.mytalk.server.call.controller;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
	private final String callerName = "paolino";
	private final String callerSurname = "paperino";
	private final boolean isCaller = true;
	private Writer writer;
	private GetCallsController tester;
	private Date startDate = new Date(1368437034437L);
	@Mock
	private HttpSession session;
	@Mock
	private HttpServletRequest request;
	@Mock
	private HttpServletResponse response;
	@Mock
	private DataPersistanceManager dao;
	@Mock
	private IUserData user;
	@Mock
	private IUserData caller;
	Set<ICallList> callListSet;
	@Mock
	ICallList callList;
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
		// configura il comportamento della CallList
		when(callList.getUser()).thenReturn(caller);
		when(callList.getCall()).thenReturn(call);
		when(callList.getCaller()).thenReturn(isCaller);
		// aggiunge la callList all'insieme di CallList
		callListSet = new HashSet<ICallList>();
		callListSet.add(callList);
		// configura il comportamento dell'utente che richiede la lista
		when(user.getCalls()).thenReturn(callListSet);
		// configura il comportamento del chiamante
		when(caller.getName()).thenReturn(callerName);
		when(caller.getSurname()).thenReturn(callerSurname);
		// configura il comportamento della chiamata
		when(call.getStart()).thenReturn(startDate);
		// configura il comprotamento della sessione
		when(session.getAttribute("username")).thenReturn(username);
		// configura il comportamento della richiesta
		when(request.getSession(anyBoolean())).thenReturn(session);
		// configura il comportamento del gestore di persistenza
		when(dao.getUserData(username)).thenReturn(user);
		// configura il comportamento della risposta
		writer = new StringWriter();
		when(response.getWriter()).thenReturn(new PrintWriter(writer));
		// inizializza l'oggetto da testare
		tester = new GetCallsController() {
			@Override
			protected DataPersistanceManager getDAOFactory() {
				return dao;
			}
		};
	}

	/**
	 * TODO da documentare!
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
		String toCompare = String.format(
				"[{\"name\": \"%s %s\", \"start\":\"%s\", \"caller\":\"%s\"}]",
				callerName, callerSurname, startDate, isCaller);
		assertEquals(toCompare, responseText);

		// verifica il corretto utilizzo dei mock
		verify(request).getSession(false);
		verify(session).getAttribute("username");
		verify(dao.getUserData(username));
		verify(user).getCalls();
		verify(user).getName();
		verify(user).getSurname();
		verify(callList).getUser();
		verify(callList).getCall();
		verify(call).getStart();
		verify(call).getStart();
	}

	/**
	 *  TODO da documentare!
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
		verify(request).getSession(false);
		verify(session).getAttribute("username");
		verify(dao.getUserData(username));
		verifyZeroInteractions(user);
		verifyZeroInteractions(callList);
		verifyZeroInteractions(call);
	}
	
	/**
	 * TODO da documentare!
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testGetCallsEmptyList() throws Exception {
		callListSet = new HashSet<ICallList>();

		// invoca il metodo da testare
		tester.doAction(request, response);

		// verifica l'output
		writer.flush();
		String responseText = writer.toString();
		assertEquals("[]", responseText);

		// verifica il corretto utilizzo dei mock
		verify(request).getSession(false);
		verify(session).getAttribute("username");
		verify(dao.getUserData(username));
		verify(user).getCalls();
		verify(user, never()).getName();
		verify(user, never()).getSurname();
		verifyZeroInteractions(callList);
		verifyZeroInteractions(call);
	}
}
