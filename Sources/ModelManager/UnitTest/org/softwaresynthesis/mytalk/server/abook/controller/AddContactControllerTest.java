package org.softwaresynthesis.mytalk.server.abook.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.softwaresynthesis.mytalk.server.abook.IAddressBookEntry;
import org.softwaresynthesis.mytalk.server.abook.IUserData;
import org.softwaresynthesis.mytalk.server.dao.DataPersistanceManager;

/**
 * 
 * @author Diego Beraldin
 * @version 2.0
 */
@RunWith(MockitoJUnitRunner.class)
public class AddContactControllerTest {
	private final String username = "indirizzo5@dominio.it";
	private final Long friendId = 1L;
	private Writer writer;
	private AddContactController tester;
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
	private IUserData friend;
	@Captor
	private ArgumentCaptor<IAddressBookEntry> argument;

	/**
	 * Configura il comportamento dei mock e reinizializza l'oggetto da testare
	 * prima di ciascuna verifica contenuta in questo caso di test.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Before
	public void setUp() throws Exception {
		// configura il comportamento della risposta
		writer = new StringWriter();
		when(response.getWriter()).thenReturn(new PrintWriter(writer));
		// configura il comportamento della sessione
		when(session.getAttribute("username")).thenReturn(username);
		// configura il comportamento della richiesta
		when(request.getSession(anyBoolean())).thenReturn(session);
		when(request.getParameter("contactId")).thenReturn(friendId.toString());
		// configura il comportamento del gestore di persistenza
		when(dao.getUserData(username)).thenReturn(user);
		when(dao.getUserData(friendId)).thenReturn(friend);
		// inizializza l'oggetto da testare
		tester = new AddContactController() {
			@Override
			protected DataPersistanceManager getDAOFactory() {
				return dao;
			}
		};
	}

	/**
	 * Verifica che l'operazione di aggiunta di un nuovo contatto vada a buon
	 * fine a partire da una richiesta contenente i parametri corretti. Il test
	 * verifica che la stringa stampata sulla risposta HTTP corrisponda alla
	 * stringa 'true' (che denota il successo dell'operazione), e verifica che
	 * nel database siano effettuale operazioni di inserimento corretto, vale a
	 * dire che siano inserite due nuove voci nella tabella AddressBookEntries
	 * con i dati corretti per le colonne Contact, Owner e Blocked.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testAddCorrectContact() throws Exception {
		// invoca il metodo da testare
		tester.doAction(request, response);

		// verifica l'output ottenuto
		writer.flush();
		String responseText = writer.toString();
		assertEquals("true", responseText);

		// verifica il corretto utilizzo dei mock
		verify(dao).getUserData(friendId);
		verify(dao).getUserData(username);
		verify(dao, times(2)).insert(argument.capture());
		IAddressBookEntry friendEntry = argument.getAllValues().get(0);
		IAddressBookEntry userEntry = argument.getAllValues().get(1);
		assertFalse(friendEntry.getBlocked());
		assertFalse(userEntry.getBlocked());
		assertEquals(user, friendEntry.getContact());
		assertEquals(friend, userEntry.getContact());
		assertEquals(user, userEntry.getOwner());
		assertEquals(friend, friendEntry.getOwner());
	}

	/**
	 * Verifica la rilevazione dell'errore dovuto al fatto che non è possibile
	 * recuperare dal database un contatto corrispondente al parametro
	 * identificativo contenuto nella richiesta HTTP. Il test verifica che il
	 * risultato stampato dal controller sulla risposta sia, come desiderato in
	 * questo caso, la stringa 'null' e che non sia effettuata ALCUNA operazione
	 * di inserimento nella base di dati.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testAddWrongData() throws Exception {
		// impedisce di recuperare l'amico da aggiungere dal database
		when(dao.getUserData(friendId)).thenThrow(new RuntimeException());

		// invoca il metodo da testare
		tester.doAction(request, response);

		// verifica l'output ottenuto
		writer.flush();
		String responseText = writer.toString();
		assertEquals("null", responseText);

		// verifica il corretto utilizzo dei mock
		verify(dao).getUserData(friendId);
		verify(dao, never()).getUserData(username);
		verifyZeroInteractions(friend);
		verify(dao, never()).insert(any(IAddressBookEntry.class));
	}
}
