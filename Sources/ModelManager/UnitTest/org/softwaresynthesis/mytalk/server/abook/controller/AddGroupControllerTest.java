package org.softwaresynthesis.mytalk.server.abook.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
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
import org.softwaresynthesis.mytalk.server.abook.IGroup;
import org.softwaresynthesis.mytalk.server.abook.IUserData;
import org.softwaresynthesis.mytalk.server.dao.DataPersistanceManager;

/**
 * Verifica della classe {@link AddGroupController} che finalmente può definirsi
 * un test di unità degno di questa denominazione!
 * 
 * @author Diego Beraldin
 * @version 2.0
 */
@RunWith(MockitoJUnitRunner.class)
public class AddGroupControllerTest {
	private final String username = "indirizzo5@dominio.it";
	private final String groupName = "ThisIsNotAGroup";
	private Writer writer;
	private AddGroupController tester;
	@Mock
	private DataPersistanceManager dao;
	@Mock
	private HttpServletRequest request;
	@Mock
	private HttpServletResponse response;
	@Mock
	private IUserData user;
	@Captor
	private ArgumentCaptor<IGroup> argument;

	/**
	 * Configura il comportamento dei mock e reinizializza l'oggetto da testare
	 * prima di ognuna delle verifiche contenute in questo caso di test.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Before
	public void setUp() throws Exception {
		// comportamento del gestore della persistenza dei dati
		when(dao.getUserData(username)).thenReturn(user);
		// configura il comportamento della richiesta
		when(request.getParameter("groupName")).thenReturn(groupName);
		// configura il comportamento della risposta
		writer = new StringWriter();
		when(response.getWriter()).thenReturn(new PrintWriter(writer));

		// inizializza l'oggetto da testare
		tester = new AddGroupController() {
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
	 * Verifica il comportamento della classe se il metodo doAction è invocato
	 * per aggiungere un gruppo e l'operazione può essere portata a termine con
	 * successo. In particolare, il test verifica che l'output stampato sulla
	 * riposta corrisponda alla stringa 'true' e che sul database venga
	 * richiamato il metodo per effettuare l'inserimento di un gruppo che
	 * corrisponde per nome e proprietario rispettivamente all'utente la cui
	 * sessione di autenticazione è associata alla richiesta pervenuta al
	 * controller e al parametro 'groupName' passato attraverso la richiesta.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testAddCorrectGroup() throws Exception {
		// invoca il metodo da testare
		tester.doAction(request, response);

		// verifica l'output
		writer.flush();
		String responseText = writer.toString();
		assertEquals("true", responseText);

		// verifica il corretto utilizzo dei mock
		verify(response).getWriter();
		verify(request).getSession(false);
		verify(request).getParameter("groupName");
		verify(dao).getUserData(username);
		verify(dao).insert(argument.capture());
		IGroup group = argument.getValue();
		assertEquals(groupName, group.getName());
		assertEquals(user, group.getOwner());
	}

	/**
	 * Verifica l'impossibilità di portar a termine l'operazione nel momento in
	 * cui i dati passati alla richiesta non sono corretti e pregiudicano
	 * l'operazione di inserimento nel database. Il test verifica che in un caso
	 * del genere l'output stampato sulla risposta HTTP sia la stringa di errore
	 * 'null', come il client si aspetta.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testAddWrongData() throws Exception {
		// impedisce l'inserimento del gruppo
		when(dao.insert(any(IGroup.class))).thenThrow(new RuntimeException());

		// invoca il metodo da testare
		tester.doAction(request, response);

		// verifica il corretto utilizzo dei mock
		verify(request).getSession(false);
		verify(request).getParameter("groupName");
		verify(response).getWriter();
		verify(dao).getUserData(username);
		verify(dao).insert(any(IGroup.class));
	}

	/**
	 * Verifica il comportamento della classe se il metodo doAction è invocato
	 * con una richiesta che non racchiude tutti i parametri necessari per
	 * portare a compimento l'operazione. In particolare il test verifica che
	 * sia stampata sulla riposta la stringa 'null' che segnala al client il
	 * verificarsi di un errore, e si assicura che non venga effettuata alcuna
	 * operazione sul sistema di gestione della persistenza dei dati.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testAddMissingData() throws Exception {
		// alla richiesta manca un parametro necessario
		when(request.getParameter("groupName")).thenReturn(null);

		// invoca il metodo da testare
		tester.doAction(request, response);

		// verifica il corretto utilizzo dei mock
		verify(request).getSession(false);
		verify(request).getParameter("groupName");
		verify(response).getWriter();
		verifyZeroInteractions(dao);
	}
}
