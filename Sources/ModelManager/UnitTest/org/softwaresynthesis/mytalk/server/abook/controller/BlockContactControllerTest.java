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
 * Verifica della classe {@link BlockContactController} che può definirsi un
 * test di unità degno finalmente di questo nome.
 * 
 * @author Diego Beraldin
 * @version 2.0
 */
@RunWith(MockitoJUnitRunner.class)
public class BlockContactControllerTest {
	private final Long contactId = 1L;
	private final String username = "indirizzzo5@dominio.it";
	private Writer writer;
	private BlockContactController tester;
	private Set<IAddressBookEntry> entrySet;
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
	private IAddressBookEntry entry;

	/**
	 * Reinizializza l'oggetto da testare e configura il comportamento dei mock
	 * prima di ogni verifica che è contenuta in questo caso di test.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Before
	public void setUp() throws Exception {
		// comportamento del contatto
		when(entry.getContact()).thenReturn(contact);
		// configura il comportamento dell'utente
		entrySet = new HashSet<IAddressBookEntry>();
		entrySet.add(entry);
		// configura il comportamento dell'utente
		when(user.getAddressBook()).thenReturn(entrySet);
		// configura il comportamento del gestore di persistenza
		when(dao.getUserData(username)).thenReturn(user);
		when(dao.getUserData(contactId)).thenReturn(contact);
		// configura il comportamento della richiesta
		when(request.getParameter("contactId"))
				.thenReturn(contactId.toString());
		// inizializza il comportamento della risposta
		writer = new StringWriter();
		when(response.getWriter()).thenReturn(new PrintWriter(writer));
		// inizializza l'oggetto da testare
		tester = new BlockContactController() {
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
	 * Verifica il comportamento del metodo doAction del controller quando
	 * l'operazione va a buon fine perché invocato con una richiesta che
	 * contiene al suo interno tutti i dati necessari a portare a termine
	 * l'operazione. In particolare il test verifica che il testo stampato sulla
	 * risposta HTTP corrisponda alla stringa 'true' e che sia utilizzato in
	 * maniera corretta il sistema di persistenza per recuperare i dati e per
	 * aggiornare la voce della rubrica impostando il campo 'Blocked' a
	 * <code>true</code> dell'utente che ha inviato la richiesta al controller.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testBlockCorrectContact() throws Exception {
		// invoca il metodo da testare
		tester.doAction(request, response);

		// verifica l'output ottenuto
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
		verify(entry).setBlocked(true);
		verify(dao).update(entry);
	}

	/**
	 * Verifica il comportamento della classe nel momento in cui viene invocato
	 * il metodo doAction con una richiesta contenente un parametro 'contactId'
	 * che non corrisponde al alcuno degli utenti del sistema memorizzati nella
	 * base di dati. In particolare il test verifica che il testo stampato sulla
	 * risposta sia la stringa 'null', come desiderato, e che non sia effettuata
	 * alcuna operazione di aggiornamento delle voci di rubrica presenti nel
	 * database.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testBlockNotExistsContact() throws Exception {
		// impedisce di recuperare dal database il contatto
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
		verify(dao).getUserData(username);
		verify(dao).getUserData(contactId);
		verifyZeroInteractions(user);
		verifyZeroInteractions(entry);
		verify(dao, never()).update(any(IAddressBookEntry.class));
	}

	/**
	 * Verifica il comportamento del metodo doAction nel momento in cui la
	 * richiesta HTTP con cui viene invocato non contiene tutti i dati necessari
	 * per portare a termine con successo l'operazione, in particolare perché
	 * non è possibile individuare univocamente il contatto da bloccare. Il test
	 * verifica che in una simile situazione il testo di risposta sia, come
	 * desiderato, la stringa 'null' e che non sia effettuata alcuna operazione
	 * sul sistema di persistenza né sui mock degli utenti messi a disposizione
	 * di questo caso di test.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testWrongData() throws Exception {
		// impedisce il recupero del parametro
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
		verifyZeroInteractions(dao);
		verifyZeroInteractions(user);
		verifyZeroInteractions(entry);
	}
}
