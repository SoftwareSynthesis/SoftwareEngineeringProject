package org.softwaresynthesis.mytalk.server.abook.controller;

import static org.mockito.Mockito.*;
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
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.softwaresynthesis.mytalk.server.IMyTalkObject;
import org.softwaresynthesis.mytalk.server.abook.IAddressBookEntry;
import org.softwaresynthesis.mytalk.server.abook.IGroup;
import org.softwaresynthesis.mytalk.server.abook.IUserData;
import org.softwaresynthesis.mytalk.server.dao.DataPersistanceManager;

/**
 * Verifica della classe {@link DeleteGroupController} che può essere definita
 * finalmente un test di unità degno di questo nome!
 * 
 * @author Diego Beraldin
 * @version 2.0
 */
@RunWith(MockitoJUnitRunner.class)
public class DeleteGroupControllerTest {
	private final String username = "indirizzo5@dominio.it";
	private final Long groupId = 1L;
	private Set<IAddressBookEntry> entrySet;
	private Writer writer;
	private DeleteGroupController tester;
	@Mock
	private HttpServletRequest request;
	@Mock
	private HttpServletResponse response;
	@Mock
	private DataPersistanceManager dao;
	@Mock
	private IGroup group;
	@Mock
	private IUserData user;
	@Mock
	private IAddressBookEntry entry;

	@Before
	public void setUp() throws Exception {
		// comportamento del gestore della persistenza
		when(dao.getUserData(username)).thenReturn(user);
		when(dao.getGroup(groupId)).thenReturn(group);
		// configura il gruppo e il suo contenuto
		entrySet = new HashSet<IAddressBookEntry>();
		entrySet.add(entry);
		when(group.getAddressBook()).thenReturn(entrySet);
		when(group.getOwner()).thenReturn(user);
		// comportamento della richiesta
		when(request.getParameter("groupId")).thenReturn(groupId.toString());
		// comportamento della risposta
		writer = new StringWriter();
		when(response.getWriter()).thenReturn(new PrintWriter(writer));
		// inizializza l'oggetto da testare
		tester = new DeleteGroupController() {
			@Override
			protected String getUserMail() {
				return username;
			}

			@Override
			protected DataPersistanceManager getDAOFactory() {
				return dao;
			}
		};
	}

	/**
	 * Verifica il comportamento del metodo doAction nel momento in cui è
	 * invocato con una richiesta che contiene tutti i parametri corretti
	 * affinché l'operazione di cancellazione del gruppo possa essere portata a
	 * termine con successo. In particolare il test controlla che la stringa del
	 * testo della risposta al termine dell'esecuzione del metodo sia, come
	 * desiderato in caso di successo, 'true'. Inoltre è verificato che il
	 * sistema di gestione della persistenza dei dati sia utilizzato per
	 * estrarre le informazioni corrette relative all'utente da cui proviene la
	 * richiesta e al gruppo di cui è richiesta l'eliminazione. Il test
	 * controlla inoltre che tutte le voci della rubrica che appartenevano a
	 * quel gruppo siano cancellate dalla tabella AddressBookEntries.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testDeleteCorrectGroup() throws Exception {
		// invoca il metodo da testare
		tester.doAction(request, response);

		// verifica l'output
		writer.flush();
		String responseText = writer.toString();
		assertEquals("true", responseText);

		// verifica il corretto utilizzo dei mock
		verify(request).getParameter("groupId");
		verify(response).getWriter();
		verify(dao).getUserData(username);
		verify(dao).getGroup(groupId);
		verify(group).getOwner();
		verify(group).getAddressBook();
		verify(dao).delete(entry);
		verify(dao).delete(group);
	}

	/**
	 * Verifica il comportamento del metodo doAction nel momento in cui il
	 * valore numerico identificativo del gruppo di cui si richiede
	 * l'eliminazione contenuto nella richiesta HTTP non corrisponda ad alcun
	 * gruppo valido nella base di dati. Il test verifica che in questo caso il
	 * testo stampato nella risposta sia la stringa 'false', come desiderato in
	 * caso di fallimento dell'operazione, e che inoltre non sia MAI effettuata
	 * un'operazione di cancellazione di una voce di rubrica dal database.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testDeleteNotExistGroup() throws Exception {
		// impedisce di recuperare il gruppo dal database
		when(dao.getGroup(groupId)).thenReturn(null);

		// invoca il metodo da testare
		tester.doAction(request, response);

		// verifica l'output
		writer.flush();
		String responseText = writer.toString();
		assertEquals("false", responseText);

		// verifica il corretto utilizzo dei mock
		verify(request).getParameter("groupId");
		verify(response).getWriter();
		verify(dao).getUserData(username);
		verify(dao).getGroup(groupId);
		verifyZeroInteractions(group);
		verify(dao, never()).delete(any(IMyTalkObject.class));
	}

	/**
	 * Verifica il comportamento del metodo doAction nel momento in cui
	 * l'identificativo del gruppo contenuto nella richiesta non corrisponda a
	 * un gruppo il cui proprietario non è l'utente che ha inviato la richiesta
	 * al server. Il test verifica che in questo caso sulla risposta sia
	 * stampata la stringa 'false', che denota una condizione di errore per il
	 * client che la riceve, e che non sia MAI effettuata alcuna operazione di
	 * cancellazione dalla base di dati.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testDeleteNotOwnedGroup() throws Exception {
		// il gruppo non è di proprietà dell'utente
		IUserData other = mock(IUserData.class);
		when(group.getOwner()).thenReturn(other);

		// invoca il metodo da testare
		tester.doAction(request, response);

		// verifica l'output
		writer.flush();
		String responseText = writer.toString();
		assertEquals("false", responseText);

		// verifica il corretto utilizzo dei mock
		verify(request).getParameter("groupId");
		verify(response).getWriter();
		verify(dao).getUserData(username);
		verify(dao).getGroup(groupId);
		verify(group).getOwner();
		verify(dao, never()).delete(any(IMyTalkObject.class));
	}

	/**
	 * Verifica il comportamento del metodo doAction nel caso in cui la
	 * richiesta HTTP passata ad esso non contenga tutti i parametri necessari a
	 * portare a termine con successo l'operazione. Oltre a verificare che la
	 * stringa stampata sulla risposta sia, anche in questo caso, 'false', il
	 * test si assicura che non sia mai effettuata ALCUNA operazione sulla base
	 * di dati.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testDeleteWrongData() throws Exception {
		// alla richiesta manca un parametro
		when(request.getParameter("groupId")).thenReturn(null);

		// invoca il metodo da testare
		tester.doAction(request, response);

		// verifica l'output
		writer.flush();
		String responseText = writer.toString();
		assertEquals("false", responseText);

		// verifica il corretto utilizzo dei mock
		verify(request).getParameter("groupId");
		verify(response).getWriter();
		verifyZeroInteractions(dao);
		verifyZeroInteractions(group);
	}
}
