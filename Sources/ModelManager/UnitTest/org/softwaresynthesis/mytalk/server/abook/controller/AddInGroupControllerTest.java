package org.softwaresynthesis.mytalk.server.abook.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

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
import org.softwaresynthesis.mytalk.server.abook.IAddressBookEntry;
import org.softwaresynthesis.mytalk.server.abook.IGroup;
import org.softwaresynthesis.mytalk.server.abook.IUserData;
import org.softwaresynthesis.mytalk.server.dao.DataPersistanceManager;

/**
 * Verifica della classe {@link AddInGroupController}
 * 
 * @author Diego Beraldin
 * @version 2.0
 */
@RunWith(MockitoJUnitRunner.class)
public class AddInGroupControllerTest {
	private final String username = "indirizzo5@dominio.it";
	private final Long contactId = 1L;
	private final Long groupId = 1L;
	private Writer writer;
	private AddInGroupController tester;
	@Mock
	private HttpServletRequest request;
	@Mock
	private HttpServletResponse response;
	@Mock
	private DataPersistanceManager dao;
	@Mock
	private IUserData user;
	@Mock
	private IGroup group;
	@Mock
	private IUserData contact;
	@Captor
	private ArgumentCaptor<IAddressBookEntry> argEntry;
	@Captor
	private ArgumentCaptor<IUserData> argUser;

	/**
	 * Configura il comportamento dei mock e reinizializza l'oggetto da testare
	 * prima di ognuna delle verifiche contenute in questo caso di test.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Before
	public void setUp() throws Exception {
		// configura la richiesta
		when(request.getParameter("contactId"))
				.thenReturn(contactId.toString());
		when(request.getParameter("groupId")).thenReturn(groupId.toString());
		// configura la risposta
		writer = new StringWriter();
		when(response.getWriter()).thenReturn(new PrintWriter(writer));
		// configura il gestore della persistenza
		when(dao.getUserData(username)).thenReturn(user);
		when(dao.getUserData(contactId)).thenReturn(contact);
		when(dao.getGroup(groupId)).thenReturn(group);
		// inizializza l'oggetto da testare
		tester = new AddInGroupController() {
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
	 * invocato con una richiesta proveniente da un utente autenticato e che
	 * contiene tutti i parametri necessari per portare a termine con successo
	 * l'operazione. In particolare, il test verifica che la stringa stampata
	 * sulla risposta HTTP sia, come desiderato, 'true', nonché che nel database
	 * siano effettuate le operazioni corrette vale a dire l'inserimento di una
	 * nuova AddressBookEntry (con i campi Owner, Contact e Blocked impostati in
	 * maniera corretta) e che sia effettuata un'operazione di update per
	 * l'utente possessore della rubrica che viene aggiornata.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testAddCorrectContact() throws Exception {
		// invoca il metodo da testare
		tester.doAction(request, response);

		// verifica l'output
		writer.flush();
		String responseText = writer.toString();
		assertEquals("true", responseText);

		// verifica il corretto utilizzo dei mock
		verify(response).getWriter();
		verify(request).getParameter("contactId");
		verify(request).getParameter("groupId");
		verify(dao).getUserData(username);
		verify(dao).getUserData(contactId);
		verify(dao).getGroup(groupId);
		verify(user).addAddressBookEntry(argEntry.capture());
		IAddressBookEntry entry = argEntry.getValue();
		assertFalse(entry.getBlocked());
		assertEquals(user, entry.getOwner());
		assertEquals(contact, entry.getContact());
		assertEquals(group, entry.getGroup());
		verify(dao).insert(entry);
		verify(dao).update(user);
	}

	/**
	 * Verifica il comportamento della classe nel momento in cui il metodo
	 * doAction è invocato con il parametro 'contactId' che non corrisponde ad
	 * alcuno degli utenti che sono presenti nella base di dati. Il test
	 * verifica che la stringa stampata nel testo della risposta HTTP
	 * corrisponda alla stringa 'null', come desiderato. Inoltre, è verificato
	 * che non siano MAI effettuate operazioni di inserimento e di aggiornamento
	 * nella base di dati.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testAddNotExistContact() throws Exception {
		// impedisce di recuperare il contatto dal database
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
		verify(request).getParameter("groupId");
		verify(dao).getUserData(username);
		verify(dao).getUserData(contactId);
		verify(dao).getGroup(groupId);
		verify(dao, never()).insert(any(IMyTalkObject.class));
		verify(dao, never()).update(any(IMyTalkObject.class));
		verifyZeroInteractions(contact);
		verifyZeroInteractions(user);
	}

	/**
	 * Verifica il comportamento della classe nel momento in cui il metodo
	 * doAction è invocato con il parametro 'groupId' che non corrisponde ad
	 * alcuno degli utenti che sono presenti nella base di dati. Il test
	 * verifica che la stringa stampata nel testo della risposta HTTP
	 * corrisponda alla stringa 'null', come desiderato. Inoltre, è verificato
	 * che non siano MAI effettuate operazioni di inserimento e di aggiornamento
	 * nella base di dati.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testAddNotExistGroup() throws Exception {
		// impedisce di recuperare il gruppo dal database
		when(dao.getGroup(groupId)).thenReturn(null);

		// invoca il metodo da testare
		tester.doAction(request, response);

		// verifica l'output
		writer.flush();
		String responseText = writer.toString();
		assertEquals("null", responseText);

		// verifica il corretto utilizzo dei mock
		verify(response).getWriter();
		verify(request).getParameter("contactId");
		verify(request).getParameter("groupId");
		verify(dao).getUserData(username);
		verify(dao).getUserData(contactId);
		verify(dao).getGroup(groupId);
		verifyZeroInteractions(user);
		verify(dao, never()).insert(any(IMyTalkObject.class));
		verify(dao, never()).update(any(IMyTalkObject.class));
		verifyZeroInteractions(contact);
	}

	/**
	 * Verifica il comportamento della classe nel momento in cui l'operazione
	 * richiesta corrisponde all'inserimento di un contatto in un gruppo che non
	 * è di proprietà dell'utente da cui proviene la richiesta. Il test verifica
	 * che in questo caso il testo stampato nella risposta sia, come desiderato,
	 * la stringa 'null' e che non sia effettuata alcuna operazione di
	 * aggiornamento dei dati dell'utente che ha richiesto l'inserimento.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testAddNotOwnedGroup() throws Exception {
		// impedisce l'inserimento (errore determinato dal trigger)
		when(dao.insert(any(IAddressBookEntry.class))).thenReturn(false);

		// invoca il metodo da testare
		tester.doAction(request, response);

		// verifica l'output
		writer.flush();
		String responseText = writer.toString();
		assertEquals("null", responseText);

		// verifica il corretto utilizzo dei mock
		verify(response).getWriter();
		verify(request).getParameter("contactId");
		verify(request).getParameter("groupId");
		verify(dao).getUserData(username);
		verify(dao).getUserData(contactId);
		verify(dao).getGroup(groupId);
		verify(user).addAddressBookEntry(argEntry.capture());
		IAddressBookEntry entry = argEntry.getValue();
		verify(dao).insert(entry);
		verifyZeroInteractions(user);
		verify(dao, never()).update(any(IMyTalkObject.class));
		verifyZeroInteractions(contact);
	}

	/**
	 * Verifica il comportamento della classe nel momento in cui il metodo
	 * doAction è invocato con una richiesta HTTP che non contiene tutti i
	 * parametri necessari per portare a termine con successo l'operazione, in
	 * particolare nel caso in cui non è presente il parametro 'contactId'. Il
	 * test controlla, in particolare, che la il testo stampato nella risposta
	 * corrisponda, come atteso, alla stringa 'null' e che non sia effettuata
	 * ALCUNA operazione sul gestore della persistenza dei dati.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testAddWrongUserData() throws Exception {
		// parametro mancante
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
		verify(request, never()).getParameter("groupId");
		verifyZeroInteractions(dao);
		verifyZeroInteractions(user);
		verifyZeroInteractions(contact);
	}

	/**
	 * Verifica il comportamento della classe nel momento in cui il metodo
	 * doAction è invocato con una richiesta HTTP che non contiene tutti i
	 * parametri necessari per portare a termine con successo l'operazione, in
	 * particolare nel caso in cui non è presente il parametro 'groupId'. Il
	 * test controlla, in particolare, che la il testo stampato nella risposta
	 * corrisponda, come atteso, alla stringa 'null' e che non sia effettuata
	 * ALCUNA operazione sul gestore della persistenza dei dati.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testAddWrongGroup() throws Exception {
		// parametro mancante
		when(request.getParameter("groupId")).thenReturn(null);

		// invoca il metodo da testare
		tester.doAction(request, response);

		// verifica l'output
		writer.flush();
		String responseText = writer.toString();
		assertEquals("null", responseText);

		// verifica il corretto utilizzo dei mock
		verify(response).getWriter();
		verify(request).getParameter("contactId");
		verify(request).getParameter("groupId");
		verifyZeroInteractions(dao);
		verifyZeroInteractions(user);
		verifyZeroInteractions(contact);
	}
}
