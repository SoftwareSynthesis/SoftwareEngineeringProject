package org.softwaresynthesis.mytalk.server.abook.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.softwaresynthesis.mytalk.server.abook.IAddressBookEntry;
import org.softwaresynthesis.mytalk.server.abook.IGroup;
import org.softwaresynthesis.mytalk.server.abook.IUserData;
import org.softwaresynthesis.mytalk.server.dao.DataPersistanceManager;

@RunWith(MockitoJUnitRunner.class)
public class GetContactsControllerTest {
	private final String username = "indirizzo5@dominio.it";
	private final String groupname = "addrBookEntry";
	private final Boolean blocked = false;
	private final String contactName = "paolino";
	private final String contactSurname = "paperino";
	private final String contactEmail = "paperino@paperopoli.it";
	private final String contactPath = "img/contactImg/Default.png";
	private final String contactState = "online";
	private final Long contactId = 1L;
	private Set<IAddressBookEntry> entrySet;
	private Writer writer;
	private GetContactsController tester;
	@Mock
	private HttpServletRequest request;
	@Mock
	private HttpServletResponse response;
	@Mock
	private DataPersistanceManager dao;
	@Mock
	private IUserData user;
	@Mock
	private IAddressBookEntry entry;
	@Mock
	private IGroup group;
	@Mock
	private IUserData contact;

	/**
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Before
	public void setUp() throws Exception {
		// comportamento dell'utente
		when(contact.getName()).thenReturn(contactName);
		when(contact.getSurname()).thenReturn(contactSurname);
		when(contact.getMail()).thenReturn(contactEmail);
		when(contact.getId()).thenReturn(contactId);
		when(contact.getPath()).thenReturn(contactPath);
		// comportamento del gruppo
		when(group.getName()).thenReturn(groupname);
		// comportamento della voce della rubrica
		when(entry.getGroup()).thenReturn(group);
		when(entry.getContact()).thenReturn(contact);
		when(entry.getBlocked()).thenReturn(blocked);
		// comportamento dell'utente e sua rubrica
		entrySet = new HashSet<IAddressBookEntry>();
		entrySet.add(entry);
		when(user.getAddressBook()).thenReturn(entrySet);
		// comportamento del gestore di persistenza
		when(dao.getUserData(username)).thenReturn(user);
		// comportamento della risposta
		writer = new StringWriter();
		when(response.getWriter()).thenReturn(new PrintWriter(writer));
		// inizializza l'oggetto da testare
		tester = new GetContactsController() {
			@Override
			protected DataPersistanceManager getDAOFactory() {
				return dao;
			}

			@Override
			protected String getUserMail() {
				return username;
			}

			@Override
			String getContactState(IUserData contact) {
				return contactState;
			}
		};
	}

	/**
	 * Verifica il comportamento del metodo doAction nel momento in cui un
	 * utente richiede di scaricare la propria rubrica e questa contiene almeno
	 * un contatto. Il test verifica che il testo stampato nella risposta HTTP
	 * corrisponda alla rappresentazione in formato JSON della lista dei
	 * contatti (ognuno presente una sola volta) e che sia utilizzato
	 * correttamente il gestore della persistenza per estrarre i dati dal
	 * database.
	 * 
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testGetCorrectContacts() throws Exception {
		// chiama il metodo da testare
		tester.doAction(request, response);

		// verifica l'output
		writer.flush();
		String responseText = writer.toString();
		String toCompare = String
				.format("{\"%d\":{\"name\":\"%s\", \"surname\":\"%s\", \"email\":\"%s\", \"id\":\"%d\", \"picturePath\":\"%s\", \"state\":\"%s\", \"blocked\":%s}}",
						contactId, contactName, contactSurname, contactEmail,
						contactId, contactPath, contactState,
						blocked.toString());
		assertEquals(toCompare, responseText);

		// verifica il corretto utilizzo dei mock
		verifyZeroInteractions(request);
		verify(response).getWriter();
		verify(dao).getUserData(username);
		verify(user).getAddressBook();
		verify(entry).getGroup();
		verify(entry).getBlocked();
		verify(group).getName();
		verify(contact).getName();
		verify(contact).getSurname();
		verify(contact).getMail();
		verify(contact).getPath();
		verify(contact, times(2)).getId();
	}

	/**
	 * Verifica il comportamento del metodo doAction nel momento in cui l'utente
	 * che chiede di scaricare la prorpia rubrica non ha alcuna voce associata a
	 * sé nella tabella AddressBookEntries della base di dati. In particolare il
	 * test verifica che sia restituita la rappresentazione in formato JSON di
	 * un array associativo che non contiene alcuna proprietà, cioè '{}'. Il
	 * test verifica inoltre che sia utilizzato correttamente il gestore della
	 * persistenza per estrarre i dati dal database.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testGetEmptyContacts() throws Exception {
		// azzera la rubrica dell'utente
		entrySet = new HashSet<IAddressBookEntry>();
		when(user.getAddressBook()).thenReturn(entrySet);

		// chiama il metodo da testare
		tester.doAction(request, response);

		// verifica l'output
		writer.flush();
		String responseText = writer.toString();
		String toCompare = String.format("{}", contactId, contactName,
				contactSurname, contactEmail, contactId, contactPath,
				contactState, blocked.toString());
		assertEquals(toCompare, responseText);

		// verifica il corretto utilizzo dei mock
		verifyZeroInteractions(request);
		verify(response).getWriter();
		verify(dao).getUserData(username);
		verify(user).getAddressBook();
		verifyZeroInteractions(entry);
		verifyZeroInteractions(group);
		verifyZeroInteractions(contact);
	}
}
