package org.softwaresynthesis.mytalk.server.call.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.softwaresynthesis.mytalk.server.IMyTalkObject;
import org.softwaresynthesis.mytalk.server.abook.IUserData;
import org.softwaresynthesis.mytalk.server.call.ICall;
import org.softwaresynthesis.mytalk.server.call.ICallList;
import org.softwaresynthesis.mytalk.server.dao.DataPersistanceManager;

/**
 * Verifica della classe {@link AddCallController}
 * 
 * @author Diego Beraldin
 * @version 2.0
 */
@RunWith(MockitoJUnitRunner.class)
public class AddCallControllerTest {
	private final String username = "indirizzo5@dominio.it";
	private final Long contactId = 1L;
	private final Long callId = 2L;
	private Writer writer;
	private AddCallController tester;
	@Mock
	private ICall call;
	@Mock
	private IUserData user;
	@Mock
	private IUserData contact;
	@Mock
	private HttpServletRequest request;
	@Mock
	private HttpServletResponse response;
	@Mock
	private DataPersistanceManager dao;
	@Mock
	private Date date;
	@Captor
	private ArgumentCaptor<IMyTalkObject> argument;

	/**
	 * Configura il comportamento dei mock e reinizializza l'oggetto da testare
	 * prima di tutte le verifiche contenute all'interno di questo caso di test.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Before
	public void setUp() throws Exception {
		// comportamento della chiamata
		when(call.getId()).thenReturn(callId);
		// comportamento del gestore di persistenza
		when(dao.getUserData(username)).thenReturn(user);
		when(dao.getUserData(contactId)).thenReturn(contact);
		// comportamento della richiesta
		when(request.getParameter("contactId"))
				.thenReturn(contactId.toString());
		// comportamento della risposta
		writer = new StringWriter();
		when(response.getWriter()).thenReturn(new PrintWriter(writer));
		// inizializza l'oggetto da testare
		tester = new AddCallController() {
			@Override
			protected String getUserMail() {
				return username;
			}

			@Override
			protected DataPersistanceManager getDAOFactory() {
				return dao;
			}

			@Override
			Date createCurrentDate() {
				return date;
			}

			@Override
			ICall createCall() {
				return call;
			}
		};
	}

	/**
	 * Verifica il comportamento del metodo doAction quando la richiesta può
	 * essere portata a termine con successo. In particolare il test verifica
	 * che sulla risposta sia stampata la stringa 'true', che il sistema di
	 * gestione della persistenza sia utilizzato correttamente per recuperare i
	 * dati del mittente e del destinatario della chiamata, che sia inserita una
	 * nuova chiamata con la data corrente e che nella tabella CallLists siano
	 * inserite due nuove voci con i corretti riferimenti alla chiamata, agli
	 * utenti e con il flag booleano caller a <code>true</code> nel caso del
	 * chiamante e <code>false</code> nel caso del chiamato.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testAddCorrectCall() throws Exception {
		// invoca il metodo da testare
		tester.doAction(request, response);

		// verifica l'output
		writer.flush();
		String responseText = writer.toString();
		assertEquals("true", responseText);

		// verifica il corretto utilizzo dei mock
		verify(request).getParameter("contactId");
		verify(response).getWriter();
		verify(dao).getUserData(username);
		verify(dao).getUserData(contactId);
		verify(call).setStart(date);
		verify(dao).insert(call);
		verify(dao, times(3)).insert(argument.capture());
		ICall first = (ICall) argument.getAllValues().get(0);
		assertEquals(call, first);
		ICallList second = (ICallList) argument.getAllValues().get(1);
		ICallList third = (ICallList) argument.getAllValues().get(2);
		assertEquals(user, second.getUser());
		assertEquals(contact, third.getUser());
		assertEquals(call, second.getCall());
		assertEquals(call, third.getCall());
		assertTrue(second.getCaller());
		assertFalse(third.getCaller());
	}

	/**
	 * Verifica il comportamento del metodo doAction nel momento in cui nella
	 * richiesta HTTP manca uno dei parametri obbligatori per portare a termine
	 * la richiesta con successo. Il test verifica che il testo stampato sulla
	 * pagina di risposta sia, come atteso in caso di errore, la stringa 'null'
	 * e che non sia effettuata alcuna operazione né di recupero dati né di
	 * scrittura sul database del sistema.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void addMissingData() throws Exception {
		// manca parametro obbligatorio nella richiesta
		when(request.getParameter("contactId")).thenReturn(null);

		// invoca il metodo da testare
		tester.doAction(request, response);

		// verifica l'output
		writer.flush();
		String responseText = writer.toString();
		assertEquals("null", responseText);

		// verifica il corretto utilizzo dei mock
		verify(request).getParameter("contactId");
		verify(response).getWriter();
		verifyZeroInteractions(dao);
		verifyZeroInteractions(call);
	}
}
