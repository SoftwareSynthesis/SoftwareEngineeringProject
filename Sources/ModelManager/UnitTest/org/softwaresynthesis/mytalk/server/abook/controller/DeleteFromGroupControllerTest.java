package org.softwaresynthesis.mytalk.server.abook.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
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

/**
 * Verifica della classe {@link DeleteFromGroupController}
 * 
 * @author Diego Beraldin
 * @version 2.0
 * 
 */
@RunWith(MockitoJUnitRunner.class)
public class DeleteFromGroupControllerTest {
	private final String username = "indirizzo5@dominio.it";
	private final Long contactId = 1L;
	private final Long groupId = 1L;
	private Set<IAddressBookEntry> entrySet = new HashSet<IAddressBookEntry>();
	private Writer writer;
	private DeleteFromGroupController tester;
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
	 * prima di ognuna delle verifica contenuta in questo caso di test.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Before
	public void setUp() throws Exception {
		// configura il comportamento della richiesta
		when(request.getParameter("contactId"))
				.thenReturn(contactId.toString());
		when(request.getParameter("groupId")).thenReturn(groupId.toString());
		// configura il comportamento della risposta
		writer = new StringWriter();
		when(response.getWriter()).thenReturn(new PrintWriter(writer));
		// configura il comportamento del gestore di persistenza
		when(dao.getUserData(username)).thenReturn(user);
		when(dao.getUserData(contactId)).thenReturn(contact);
		when(dao.getGroup(groupId)).thenReturn(group);
		// configura il comportamento della voce
		when(entry.getContact()).thenReturn(contact);
		when(entry.getGroup()).thenReturn(group);
		when(entry.getOwner()).thenReturn(user);
		// configura il comportamento dell'utente e della sua rubrica
		entrySet.add(entry);
		when(user.getAddressBook()).thenReturn(entrySet);
		// inizializza l'oggetto da testare
		tester = new DeleteFromGroupController() {
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
	 * Verifica il comportamento del metodo doAction nel momento in cui viene
	 * invocato con una richiesta contenente tutti i parametri necessari per
	 * portare a termine con successo l'operazione. In particolare il test
	 * verifica che il testo stampato sulla risposta HTTP sia, come desiderato,
	 * la stringa 'true'. Si verifica inoltre che il sistema di persistenza sia
	 * utilizzato per estrarre i dati corrispondenti all'utente, al contatto e
	 * al gruppo contenuti nella richiesta, che sia effettuata un'operazione di
	 * rimozione della entry e che venga effettuato l'aggiornamento dell'utente
	 * la cui rubrica è soggetta a modifica.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testRemoveCorrectContact() throws Exception {
		// invoca il metodo da testare
		tester.doAction(request, response);

		// verifica l'output ottenuto
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
		verify(user).getAddressBook();
		verify(entry).getContact();
		verify(entry).getGroup();
		verify(entry).getOwner();
		verify(user).removeAddressBookEntry(entry);
		verify(dao).delete(entry);
		verify(dao).update(user);
	}

	/**
	 * Verifica il comportamento del metodo doAction nel momento in cui il
	 * contatto di cui si richiede la cancellazione non corrisponde ad alcuno
	 * degli utenti che sono registrati al sistema. Il test verifica che in
	 * questo caso il testo stampato sulla risposta HTTP sia, come desiderato,
	 * la stringa 'null' e che non siano MAI effettuate operazioni di
	 * aggiornamento dei dati dell'utente che ha richiesto la cancellazione e
	 * che non sia cancellata alcuna voce dalla rubrica.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testRemoveNotExistUser() throws Exception {
		// impedisce di recuperare l'utente
		when(dao.getUserData(contactId)).thenReturn(null);

		// invoca il metodo da testare
		tester.doAction(request, response);

		// verifica l'output ottenuto
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
		verifyZeroInteractions(entry);
		verify(dao, never()).delete(any(IAddressBookEntry.class));
		verify(dao, never()).update(any(IUserData.class));
	}

	/**
	 * Verifica il comportamento del metodo doAction nel momento in cui il
	 * gruppo da cui si richiede la cancellazione di un contatto non corrisponde
	 * ad alcuno dei gruppo che sono presenti nel database del sistema. Il test
	 * verifica che in questo caso il testo stampato sulla risposta HTTP sia,
	 * come desiderato, la stringa 'null' e che non siano MAI effettuate
	 * operazioni di aggiornamento dei dati dell'utente che ha richiesto la
	 * cancellazione e che non sia cancellata alcuna voce dalla rubrica.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testRemoveNotExistGroup() throws Exception {
		// impedisce di recuperare l'utente
		when(dao.getGroup(groupId)).thenReturn(null);

		// invoca il metodo da testare
		tester.doAction(request, response);

		// verifica l'output ottenuto
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
		verifyZeroInteractions(entry);
		verify(dao, never()).delete(any(IAddressBookEntry.class));
		verify(dao, never()).update(any(IUserData.class));
	}

	/**
	 * Verifica il comportamento del metodo doAction nel momento in cui esso è
	 * invocato con una richiesta HTTP che non contiene tutti i parametri
	 * necessari per portare a termine con successo l'operazione. In tal caso il
	 * test verifica che il testo stampato sulla risposta sia effettivamente
	 * 'null', come desiderato in questo caso e che non sia effettuata ALCUNA
	 * operazione sul gestore di persistenza dei dati.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testRemoveWrongData() throws Exception {
		// impedisce di recuperare l'utente
		when(request.getParameter("contactId")).thenReturn(null);

		// invoca il metodo da testare
		tester.doAction(request, response);

		// verifica l'output ottenuto
		writer.flush();
		String responseText = writer.toString();
		assertEquals("null", responseText);

		// verifica il corretto utilizzo dei mock
		verify(response).getWriter();
		verify(request).getParameter("contactId");
		verify(request, never()).getParameter("groupId");
		verifyZeroInteractions(dao);
		verifyZeroInteractions(user);
		verifyZeroInteractions(entry);
	}
}
