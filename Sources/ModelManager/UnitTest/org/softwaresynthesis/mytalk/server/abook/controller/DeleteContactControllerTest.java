package org.softwaresynthesis.mytalk.server.abook.controller;

import static org.junit.Assert.*;

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
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.softwaresynthesis.mytalk.server.abook.IAddressBookEntry;
import org.softwaresynthesis.mytalk.server.abook.IUserData;
import org.softwaresynthesis.mytalk.server.dao.DataPersistanceManager;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DeleteContactControllerTest {
	private final Long contactId = 1L;
	private final String username = "indrizzo5@dominio.it";
	private Set<IAddressBookEntry> userEntrySet;
	private Set<IAddressBookEntry> contactEntrySet;
	private Writer writer;
	private DeleteContactController tester;
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
	private IAddressBookEntry userEntry;
	@Mock
	private IAddressBookEntry contactEntry;
	@Captor
	private ArgumentCaptor<IAddressBookEntry> argEntry;
	@Captor
	private ArgumentCaptor<IUserData> argUser;

	@Before
	public void setUp() throws Exception {
		// comportamento del contatto e rubrica
		when(userEntry.getContact()).thenReturn(user);
		contactEntrySet = new HashSet<IAddressBookEntry>();
		contactEntrySet.add(userEntry);
		when(contact.getAddressBook()).thenReturn(contactEntrySet);
		// comportamento dell'utente e rubrica
		when(contactEntry.getContact()).thenReturn(contact);
		userEntrySet = new HashSet<IAddressBookEntry>();
		userEntrySet.add(contactEntry);
		when(user.getAddressBook()).thenReturn(userEntrySet);
		// comportamento della richiesta
		when(request.getParameter("contactId"))
				.thenReturn(contactId.toString());
		// comportamento della risposta
		writer = new StringWriter();
		when(response.getWriter()).thenReturn(new PrintWriter(writer));
		// comportamento del gestore di persistenza
		when(dao.getUserData(username)).thenReturn(user);
		when(dao.getUserData(contactId)).thenReturn(contact);
		// inizializza l'oggetto da testare
		tester = new DeleteContactController() {
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
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testDeleteCorrectUser() throws Exception {
		// invoca il metodo da testare
		tester.doAction(request, response);
		
		// verifica l'output
		writer.flush();
		String responseText = writer.toString();
		assertEquals("true", responseText);
		
		// verifica il corretto utilizzo dei mock
		verify(response).getWriter();
		verify(request).getParameter("contactId");
		verify(dao).getUserData(username);
		verify(dao).getUserData(contactId);
		verify(user).getAddressBook();
		verify(contact).getAddressBook();
		verify(userEntry).getContact();
		verify(contactEntry).getContact();
		verify(dao).delete(contactEntry);
		verify(dao).delete(userEntry);
		verify(user).removeAddressBookEntry(contactEntry);
		verify(contact).removeAddressBookEntry(userEntry);
	}

	/**
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testDeleteNotExistContact() throws Exception {
		// impedisce il recupero
		when(dao.getUserData(contactId)).thenReturn(null);
		
		// invoca il metodo da testare
		tester.doAction(request, response);
		
		// verifica l'output
		writer.flush();
		String responseText = writer.toString();
		assertEquals("null", responseText);
		
		// verifica il corretto utilizzo dei mock
		verify(response).getWriter();
		verify(request).getParameter("contactId");
		verify(dao).getUserData(username);
		verify(dao).getUserData(contactId);
		verifyZeroInteractions(user);
		verifyZeroInteractions(contact);
		verifyZeroInteractions(contactEntry);
		verifyZeroInteractions(userEntry);
		verify(dao, never()).delete(any(IAddressBookEntry.class));
	}
	
	/**
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testDeleteNotFriendContact() throws Exception {
		// invoca il metodo da testare
		tester.doAction(request, response);
		
		// verifica l'output
		writer.flush();
		String responseText = writer.toString();
		assertEquals("null", responseText);
		
		// verifica il corretto utilizzo dei mock
		fail("Non ho tempo di farlo!");
	}
	
	/**
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testDeleteWrongData() throws Exception {
		// impedice recupero del parametro dalla richiesta
		when(request.getParameter("contactId")).thenReturn(null);
		
		// invoca il metodo da testare
		tester.doAction(request, response);
		
		// verifica l'output
		writer.flush();
		String responseText = writer.toString();
		assertEquals("null", responseText);
		
		// verifica il corretto utilizzo dei mock
		verify(response).getWriter();
		verify(request).getParameter("contactId");
		verifyZeroInteractions(dao);
		verifyZeroInteractions(user);
		verifyZeroInteractions(contact);
		verifyZeroInteractions(contactEntry);
		verifyZeroInteractions(userEntry);
	}
}
