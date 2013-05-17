package org.softwaresynthesis.mytalk.server.message.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.softwaresynthesis.mytalk.server.dao.DataPersistanceManager;
import org.softwaresynthesis.mytalk.server.message.IMessage;

/**
 * 
 * @author Diego Beraldin
 * @version 2.0
 */
@RunWith(MockitoJUnitRunner.class)
public class DeleteMessageControllerTest {
	private final Long idMessage = 1L;
	private Writer writer;
	private DeleteMessageController tester;
	@Mock
	private HttpServletRequest request;
	@Mock
	private HttpServletResponse response;
	@Mock
	private DataPersistanceManager dao;
	@Mock
	private IMessage message;

	@Before
	public void setUp() throws Exception {
		// comportamento della richiesta
		when(request.getParameter("idMessage"))
				.thenReturn(idMessage.toString());
		// comportamento del gestore di persistenza
		when(dao.getMessage(idMessage)).thenReturn(message);
		// writer e comportamento della risposta
		writer = new StringWriter();
		when(response.getWriter()).thenReturn(new PrintWriter(writer));
		// inizializza l'oggetto da testare
		tester = spy(new DeleteMessageController() {
			@Override
			protected DataPersistanceManager getDAOFactory() {
				return dao;
			}
		});
	}

	/**
	 * TODO da documentare
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testDeleteCorrectly() throws Exception {
		// simula operazione di cancellazione andata a buon fine
		when(tester.deleteFile(anyString())).thenReturn(true);

		// invoca il metodo da testare
		tester.doAction(request, response);

		// verifica l'output
		writer.flush();
		String responseText = writer.toString();
		assertEquals("true", responseText);

		// verifica il corretto utilizzo dei mock
		verify(response).getWriter();
		verify(request).getParameter("idMessage");
		verify(dao).getMessage(idMessage);
		verify(dao).delete(message);
		verify(tester).deleteFile("Secretariat/" + idMessage + ".wav");
	}

	/**
	 * TODO da documentare
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testDeleteWithErasureError() throws Exception {
		// simula operazione di cancellazione non andata a buon fine
		when(tester.deleteFile(anyString())).thenReturn(false);

		// invoca il metodo da testare
		tester.doAction(request, response);

		// verifica l'output
		writer.flush();
		String responseText = writer.toString();
		assertEquals("null", responseText);

		// verifica il corretto utilizzo dei mock
		verify(response).getWriter();
		verify(request).getParameter("idMessage");
		verify(dao).getMessage(idMessage);
		verify(dao, never()).delete(message);
		verify(tester).deleteFile("Secretariat/" + idMessage + ".wav");
	}

	/**
	 * TODO da documentare
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testDeleteNotExistMessage() throws Exception {
		// impedisce il recupero del messaggio dal db
		when(dao.getMessage(idMessage)).thenReturn(null);

		// invoca il metodo da testare
		tester.doAction(request, response);

		// verifica l'output
		writer.flush();
		String responseText = writer.toString();
		assertEquals("null", responseText);

		// verifica il corretto utilizzo dei mock
		verify(response).getWriter();
		verify(request).getParameter("idMessage");
		verify(dao).getMessage(idMessage);
		verify(dao, never()).delete(message);
		verify(tester, never()).deleteFile(anyString());
	}
	
	// TODO da fare testUpdateNotOwnedMessage()
	
	/**
	 * TODO da documentare
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testWrongData() throws Exception {
		// elimina parametro obbligatorio dalla richiesta
		when(request.getParameter("idMessage")).thenReturn(null);

		// invoca il metodo da testare
		tester.doAction(request, response);

		// verifica l'output
		writer.flush();
		String responseText = writer.toString();
		assertEquals("null", responseText);

		// verifica il corretto utilizzo dei mock
		verify(response).getWriter();
		verify(request).getParameter("idMessage");
		verifyZeroInteractions(dao);
		verify(tester, never()).deleteFile(anyString());
	}
}
