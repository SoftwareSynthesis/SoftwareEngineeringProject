package org.softwaresynthesis.mytalk.server.abook.controller;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
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
	private HttpSession session;
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
		// configura la sessione
		when(session.getAttribute("username")).thenReturn(username);
		// configura la richiesta
		when(request.getSession(anyBoolean())).thenReturn(session);
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
		};
	}

	/**
	 * TODO da terminare!
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
	}

	/**
	 * TODO da terminare!
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testAddNotExistContact() throws Exception {
		// invoca il metodo da testare
		tester.doAction(request, response);

		// verifica l'output
		writer.flush();
		String responseText = writer.toString();
		assertEquals("null", responseText);

		// verifica il corretto utilizzo dei mock
	}

	/**
	 * TODO da terminare!
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testAddNotExistGroup() throws Exception {
		// invoca il metodo da testare
		tester.doAction(request, response);

		// verifica l'output
		writer.flush();
		String responseText = writer.toString();
		assertEquals("null", responseText);

		// verifica il corretto utilizzo dei mock
	}

	/**
	 * TODO da terminare!
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testAddNotOwnedGroup() throws Exception {
		// invoca il metodo da testare
		tester.doAction(request, response);

		// verifica l'output
		writer.flush();
		String responseText = writer.toString();
		assertEquals("null", responseText);

		// verifica il corretto utilizzo dei mock
	}

	/**
	 * TODO da terminare!
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
	}

	/**
	 * TODO da terminare!
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testAddWrongGroup() throws Exception {
		// parametro mancante
		when(request.getParameter("contactId")).thenReturn(null);

		// invoca il metodo da testare
		tester.doAction(request, response);

		// verifica l'output
		writer.flush();
		String responseText = writer.toString();
		assertEquals("null", responseText);

		// verifica il corretto utilizzo dei mock
	}
}
