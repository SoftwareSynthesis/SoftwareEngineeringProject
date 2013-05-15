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
 * Verifica della classe {@link UnblockContactController}
 * 
 * @author Diego Beraldin
 * @version 2.0
 */
@RunWith(MockitoJUnitRunner.class)
public class UnblockContactControllerTest {
	private final String username = "indirizzo5@dominio.it";
	private final Long contactId = 1L;
	private Set<IAddressBookEntry> entrySet;
	private Writer writer;
	private UnblockContactController tester;
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
	private IUserData contact;

	/**
	 * Configura il comportamento dei mock ed inizializza l'oggetto da testare
	 * prima di tutte le verifiche contenute all'interno di questo caso di test.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Before
	public void setUp() throws Exception {
		// voce di rubrica
		when(entry.getContact()).thenReturn(contact);
		// utente e sua rubrica
		entrySet = new HashSet<IAddressBookEntry>();
		entrySet.add(entry);
		when(user.getAddressBook()).thenReturn(entrySet);
		// comportamento del gestore di persistenza
		when(dao.getUserData(username)).thenReturn(user);
		when(dao.getUserData(contactId)).thenReturn(contact);
		// comportamento della richiesta
		when(request.getParameter("contactId"))
				.thenReturn(contactId.toString());
		// writer e comportamento della risposta
		writer = new StringWriter();
		when(response.getWriter()).thenReturn(new PrintWriter(writer));
		// inizializza l'oggetto da testare
		tester = new UnblockContactController() {
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
	 * invocato a partire da una richiesta che contiene tutti i parametri di
	 * valore corretto per portare a termine con successo l'operazione di blocco
	 * del contatto. In particolare, il test verifica che il testo stampato
	 * sulla risposta sia, come desiderato in caso di successo, la stringa
	 * 'true' e che il sistema di persistenza dei dati sua utilizzato
	 * correttamente per le operazioni di recupero (della rubrica dell'utente
	 * che richiede l'operazione e dei contatti coinvolti) e di aggiornamento
	 * (di tutte le voci di rubrica che devono essere bloccate).
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testUnblockCorrectContact() throws Exception {
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
		verify(entry).getContact();
		verify(entry).setBlocked(false);
		verify(dao).update(entry);
	}

	/**
	 * Verifica il comportamento del metodo doAction nel momento in cui il
	 * parametro identificativo del contatto da bloccare contenuto nella
	 * richiesta non corrisponde ad alcuno degli utenti registrati nel sistema.
	 * Il test verifica che in questo caso la stringa stampata sulla risposta
	 * HTTP sia 'null', che denota l'avvenuto errore nel server. È infine
	 * effettuato un controllo sul sistema di persistenza, che garantisce che
	 * non sia effettuata ALCUNA operazione di aggiornamento di voci di rubrica.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testUnblockNotExistContact() throws Exception {
		// impedisce il recupero del contatto dalla base di dati
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
		verifyZeroInteractions(entry);
		verify(dao, never()).update(any(IAddressBookEntry.class));
	}

	/**
	 * Verifica il comportamento del metodo doAction nel momento in cui la
	 * richiesta HTTP con cui viene invocato non contiene tutti i paramtri
	 * necessari per portare a termine l'operazione di blocco di un contatto. In
	 * particolare, il test verifica che il testo stampato sulla risposta sia la
	 * stringa 'null', che denota un errore nel server, e che non sia effettuata
	 * ALCUNA operazione attraverso il gestore della persistenza dei dati.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testWrongData() throws Exception {
		// alla richiesta manca il parametro necessario
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
		verifyZeroInteractions(entry);
	}
}
