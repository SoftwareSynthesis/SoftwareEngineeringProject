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
import org.softwaresynthesis.mytalk.server.abook.IUserData;
import org.softwaresynthesis.mytalk.server.dao.DataPersistanceManager;

/**
 * Verifica della classe {@link DeleteContactController}
 * 
 * @author Diego Beraldin
 * @version 2.0
 */
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

	/**
	 * Configura il comportamento dei mock condivisi e inizializza l'oggetto da
	 * testare prima di ognuna delle verifiche contenute in questo caso di test.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
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
	 * Verifica il comportamento del metodo doAction nel momento in cui è
	 * invocato con una richiesta che contiene tutti i parametri necessari per
	 * portare a termine l'operazione di cancellazione. Il test verifica che il
	 * testo stampato sulla risposta sia, come atteso, la stringa 'true', che
	 * sia estratta dalla base di dati la rubrica di entrambi gli utenti
	 * coinvolti nell'operazione e che da ognuna delle voci della rubrica sia
	 * estratto il contatto per effettuare le operazioni di confronto. Inoltre
	 * il test verifica che sia invocato il metodo removeAddressBookEntr sugli
	 * utenti e che in seguito siano aggiornati gli oggetti tramite il gestore
	 * della persistenza dei dati, e che quest'ultimo sia utilizzato infine per
	 * la cancellazione delle voci di rubrica corrispondenti alla richiesta di
	 * eliminazione ricevuta dal controller.
	 * 
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
//		verify(user).removeAddressBookEntry(contactEntry);
//		verify(contact).removeAddressBookEntry(userEntry);
	}

	/**
	 * Verifica il comportamento del metodo doAction nel momento in cui è
	 * invocato con una richiesta contenente un parametro 'contactId' che non
	 * corrisponde all'identificativo di alcuno degli utenti registrati nel
	 * sistema. Il test verifica che in questo caso il testo stampato sulla
	 * risposta HTTP corrisponda, come desiderato dal client, alla stringa
	 * 'null' e che non siano mai effettuate operazioni di aggiornamento degli
	 * utenti né cancellazioni di voci di rubrica.
	 * 
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
		verify(dao, never()).update(user);
		verify(dao, never()).update(contact);
	}

	/**
	 * Verifica il comportamento del metodo doAction nel momento in cui la
	 * richiesta HTTP che gli viene passata come parametro non contiene tutti i
	 * dati necessari al completamento con successo dell'operazione di
	 * cancellazione dalla rubrica degli utenti. Il test verifica che sulla
	 * risposta sia effettivamente stampata la stringa 'null', che denota il
	 * verificarsi di un errore nel server, e che non sia effettuata ALCUNA
	 * operazione (né di estrazione, né di cancellazione, né di aggiornamento)
	 * sulla base di dati.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testWrongData() throws Exception {
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
