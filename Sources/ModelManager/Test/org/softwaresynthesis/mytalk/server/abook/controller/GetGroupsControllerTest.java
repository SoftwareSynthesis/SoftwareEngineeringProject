package org.softwaresynthesis.mytalk.server.abook.controller;

import static org.junit.Assert.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GetGroupsControllerTest {
	private final String username = "indirizzo5@dominio.it";
	private final String groupname = "addrBookEntry";
	private final Long groupId = 1L;
	private final Long contactId = 1L;
	private List<IGroup> groupList;
	private Set<IAddressBookEntry> entrySet;
	private Writer writer;
	private GetGroupsController tester;
	@Mock
	private HttpServletRequest request;
	@Mock
	private HttpServletResponse response;
	@Mock
	private DataPersistanceManager dao;
	@Mock
	private IUserData user;
	@Mock
	private IUserData contact;
	@Mock
	private IGroup group;
	@Mock
	private IAddressBookEntry entry;

	/**
	 * Riconfigura il comportamento dei mock e inizializza l'oggetto da testare
	 * prima di ognuna delle verifiche contenute in questo caso di test.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Before
	public void setUp() throws Exception {
		// contatto
		when(contact.getId()).thenReturn(contactId);
		// gruppo e rubrica associata
		when(entry.getContact()).thenReturn(contact);
		entrySet = new HashSet<IAddressBookEntry>();
		entrySet.add(entry);
		when(group.getAddressBook()).thenReturn(entrySet);
		when(group.getId()).thenReturn(groupId);
		when(group.getName()).thenReturn(groupname);
		// utente e rubrica associata
		groupList = new ArrayList<IGroup>();
		groupList.add(group);
		// configura il comportamento della risposta
		writer = new StringWriter();
		when(response.getWriter()).thenReturn(new PrintWriter(writer));
		// configura il comportamento del gestore di persistenza
		when(dao.getUserData(username)).thenReturn(user);
		when(dao.getGroup(user)).thenReturn(groupList);
		// inizializza l'oggetto da testare
		tester = new GetGroupsController() {
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
	 * Verifica il comportamento del metodo doAction nel momento in cui è
	 * invocato con una richiesta che contiene tutti i parametri necessari a
	 * portare a termine l'operazione con successo e la lista degli utenti
	 * contenuti nel gruppo non è vuota. In particolare, il test verifica che il
	 * testo stampato sulla risposta HTTP corrisponda, come desiderato, alla
	 * rappresentazione in formato JSON della lista dei gruppi come si attende
	 * il client. Inoltre, si controlla che siano estratte dal database le
	 * informazioni necessarie alla costruzione della stringa JSON.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testGetGroupContact() throws Exception {
		// invoca il metodo da testare
		tester.doAction(request, response);

		// verifica l'output
		writer.flush();
		String responseText = writer.toString();
		String toCompare = String.format(
				"{\"%d\":{\"name\":\"%s\",\"id\":\"%d\",\"contacts\":[%d]}}",
				groupId, groupname, groupId, contactId);
		assertEquals(toCompare, responseText);

		// verifica il corretto utilizzo dei mock
		verify(response).getWriter();
		verify(dao).getUserData(username);
		verify(dao).getGroup(user);
		verify(group).getAddressBook();
		verify(group).getName();
		verify(group, times(2)).getId();
		verify(entry).getContact();
		verify(contact).getId();
	}

	/**
	 * Verifica il comportamento del metodo doAction nel momento in cui è
	 * invocato con una richiesta che contiene tutti i parametri necessari a
	 * portare a termine l'operazione con successo e la lista degli utenti
	 * contenuti nel gruppo è vuota. In particolare, il test verifica che il
	 * testo stampato sulla risposta HTTP corrisponda, come desiderato, alla
	 * rappresentazione in formato JSON della lista dei gruppi come si attende
	 * il client. Inoltre, si controlla che siano estratte dal database le
	 * informazioni necessarie alla costruzione della stringa JSON e che non
	 * siano recuperate informazioni non necessarie.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testGetEmptyGroup() throws Exception {
		// fa in modo che il gruppo sia vuoto
		when(group.getAddressBook()).thenReturn(
				new HashSet<IAddressBookEntry>());

		// invoca il metodo da testare
		tester.doAction(request, response);

		// verifica l'output
		writer.flush();
		String responseText = writer.toString();
		String toCompare = String.format(
				"{\"%d\":{\"name\":\"%s\",\"id\":\"%d\",\"contacts\":[]}}",
				groupId, groupname, groupId);
		assertEquals(toCompare, responseText);

		// verifica il corretto utilizzo dei mock
		verify(response).getWriter();
		verify(dao).getUserData(username);
		verify(dao).getGroup(user);
		verify(group).getAddressBook();
		verify(group).getName();
		verify(group, times(2)).getId();
		verifyZeroInteractions(entry);
		verifyZeroInteractions(contact);
	}
}
