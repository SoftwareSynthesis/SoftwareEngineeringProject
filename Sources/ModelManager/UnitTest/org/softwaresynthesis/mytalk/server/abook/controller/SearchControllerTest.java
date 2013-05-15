package org.softwaresynthesis.mytalk.server.abook.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.softwaresynthesis.mytalk.server.abook.IUserData;
import org.softwaresynthesis.mytalk.server.dao.DataPersistanceManager;

/**
 * Verifica della classe {@link SearchController}
 * 
 * @author Diego Beraldin
 * @version 2.0
 */
@RunWith(MockitoJUnitRunner.class)
public class SearchControllerTest {
	private final String searchCriterion = "paperino";
	private final String someUserName = "paolino";
	private final String someUserEmail = "paperino@paperopoli.it";
	private final String someUserPath = "img/contactImg/Default.png";
	private final Long someUserId = 1L;
	private List<IUserData> userList;
	private SearchController tester;
	private Writer writer;
	@Mock
	private HttpServletRequest request;
	@Mock
	private HttpServletResponse response;
	@Mock
	private DataPersistanceManager dao;
	@Mock
	private IUserData someUser;

	@Before
	public void setUp() throws Exception {
		// utente trovato e lista di utenti
		when(someUser.getName()).thenReturn(someUserName);
		when(someUser.getSurname()).thenReturn(searchCriterion);
		when(someUser.getMail()).thenReturn(someUserEmail);
		when(someUser.getPath()).thenReturn(someUserPath);
		when(someUser.getId()).thenReturn(someUserId);
		userList = new ArrayList<IUserData>();
		userList.add(someUser);
		// comportamento del gestore di persistenza
		when(
				dao.getUserDatas(searchCriterion, searchCriterion,
						searchCriterion)).thenReturn(userList);
		// comportamento della richiesta
		when(request.getParameter("param")).thenReturn(searchCriterion);
		// writer e comportamento della risposta
		writer = new StringWriter();
		when(response.getWriter()).thenReturn(new PrintWriter(writer));
		// inizializza l'oggetto da testare
		tester = new SearchController() {
			@Override
			protected DataPersistanceManager getDAOFactory() {
				return dao;
			}
		};
	}

	/**
	 * Verifica il comportamento del metodo doAction nel momento in cui la
	 * richiesta contiene tutti i parametri per portare a termine con successo
	 * l'operazione di ricerca. In particolare il test verifica che la stringa
	 * stampata sulla risposta corrisponda alla rappresentazione in formato JSON
	 * di un array associativo indirizzato per identificativo degli utenti che
	 * corrispondono al criterio di ricerca specificato nella richiesta. Il test
	 * verifica che sia utilizzato correttamente il sistema di gestione della
	 * persistenza per l'estrazione degli utenti e che da questi ultimi siano
	 * estratte solo le informazioni rilevanti per la codifica JSON dei
	 * risultati.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testGetSearchContact() throws Exception {
		// invoca il metodo da testare
		tester.doAction(request, response);

		// verifica l'output
		writer.flush();
		String responseText = writer.toString();
		String toCompare = String
				.format("{\"%d\":{\"name\":\"%s\", \"surname\":\"%s\", \"email\":\"%s\", \"id\":\"%d\", \"picturePath\":\"%s\", \"blocked\":false}}",
						someUserId, someUserName, searchCriterion,
						someUserEmail, someUserId, someUserPath);
		assertEquals(toCompare, responseText);

		// verifica il corretto utilizzo dei mock
		verify(response).getWriter();
		verify(request).getParameter("param");
		verify(dao).getUserDatas(searchCriterion, searchCriterion,
				searchCriterion);
		verify(someUser).getName();
		verify(someUser).getSurname();
		verify(someUser, times(2)).getId();
		verify(someUser).getMail();
		verify(someUser).getPath();
		verify(someUser, never()).getAnswer();
		verify(someUser, never()).getQuestion();
		verify(someUser, never()).getPassword();
	}

	/**
	 * Verifica il comportamento del metodo doAction nel caso in cui la
	 * richiesta HTTP con cui viene invocato non contiene tutti i parametri
	 * necessari a portare a termine con successo l'operazione di ricerca. Il
	 * test verifica che in questo caso sia stampata sulla risposta la stringa
	 * '{}' che rappresenta un array associativo vuoto.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testWrongData() throws Exception {
		// impedisce di recuperare il parametro dalla richiesta
		when(request.getParameter("param")).thenReturn(null);

		// invoca il metodo da testare
		tester.doAction(request, response);

		// verifica l'output
		writer.flush();
		String responseText = writer.toString();
		assertEquals("{}", responseText);

		// verifica il corretto utilizzo dei mock
		verify(response).getWriter();
		verify(request).getParameter("param");
		verify(dao).getUserDatas(anyString(), anyString(), anyString());
		verifyZeroInteractions(someUser);
	}
}
