package org.softwaresynthesis.mytalk.server.message.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
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
import org.softwaresynthesis.mytalk.server.message.IMessage;

/**
 * Verifica della classe {@link GetMessagesController} che finalmente può essere
 * definita un test di unità degno di questo nome!
 * 
 * @author Deigo Beraldin
 * @version 2.0
 */
@RunWith(MockitoJUnitRunner.class)
public class GetMessagesControllerTest {
	private final String username = "indirizzo5@dominio.it";
	private final Long senderId = 1L;
	private final Long messageId = 1L;
	private final Boolean isNew = true;
	private final Boolean isVideo = true;
	private final Date messageDate = new Date(1368689524L);
	private List<IMessage> messageList;
	private Writer writer;
	private GetMessagesController tester;
	@Mock
	private HttpServletRequest request;
	@Mock
	private HttpServletResponse response;
	@Mock
	private DataPersistanceManager dao;
	@Mock
	private IUserData user;
	@Mock
	private IUserData sender;
	@Mock
	private IMessage message;

	/**
	 * Configura il comportamento dei mock e inizializza l'oggetto da sottoporre
	 * a test prima di ogni verifica contenuta in questo caso di test.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Before
	public void setUp() throws Exception {
		// comportamento del mittente
		when(sender.getId()).thenReturn(senderId);
		// comportamento del messaggio
		when(message.getId()).thenReturn(messageId);
		when(message.getSender()).thenReturn(sender);
		when(message.getNewer()).thenReturn(isNew);
		when(message.getVideo()).thenReturn(isVideo);
		when(message.getDate()).thenReturn(messageDate);
		// comportamento dell'utente e sua segreteria
		messageList = new ArrayList<IMessage>();
		messageList.add(message);
		// when(user.getMessages()).thenReturn(messageList);
		// writer e comportamento della risposta
		writer = new StringWriter();
		when(response.getWriter()).thenReturn(new PrintWriter(writer));
		// comportamento del gestore di persistenza
		when(dao.getUserData(username)).thenReturn(user);
		when(dao.getMessages(user)).thenReturn(messageList);
		// inizializza l'oggetto da testare
		tester = new GetMessagesController() {
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
	 * possibile completare con successo l'operazione di scaricamento dei dati
	 * relativi alla segreteria telefonica dell'utente. In particolare il test
	 * verifica che la stringa stampata sullla risposta HTTP corrisponda alla
	 * rappresentazione in formato JSON dei dati relativi ai messaggi che hanno
	 * come destinatario l'utente da cui proviene la richiesta presenti nel
	 * database, ognuno corredato di tutti i campi che il client si aspetta di
	 * ricevere. Inoltre si verifica il corretto utilizzo dei mock, vale a dire
	 * che i dati siano estratti dalla base di dati nel modo corretto.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testGetMessagesCorrectUser() throws Exception {
		// invoca il metodo da testare
		tester.doAction(request, response);

		// verifica l'output
		writer.flush();
		String responseText = writer.toString();
		String toCompare = String
				.format("[{\"id\":\"%d\", \"sender\":\"%d\", \"status\":\"%s\", \"video\":\"%s\", \"date\":\"%s\", \"src\":\"%s\"}]",
						messageId, senderId, isNew, isVideo, messageDate,
						"Secretariat/" + messageId + ".wav");
		assertEquals(toCompare, responseText);

		// verifica il corretto utilizzo dei mock
		verify(response).getWriter();
		verifyZeroInteractions(request);
		verify(dao).getUserData(username);
		verify(dao).getMessages(user);
		verify(message).getSender();
		verify(message, times(2)).getId();
		verify(message).getNewer();
		verify(message).getVideo();
		verify(message).getDate();
		verify(sender).getId();
	}

	/**
	 * Verifica il comportamento del metodo doAction nel momento in cui l'utente
	 * da cui proviene la richiesta non ha alcun messaggio a lui destinato nella
	 * segreteria telefonica del sistema. Il test verifica che il testo stampato
	 * nella risposta corrisponda alla stringa '[]', che in JSON rappresenta un
	 * array enumerativo privo di elementi. Si verifica inoltre che il sistema
	 * di gestione della persistenza dei dati sia utilizzato in modo corretto
	 * per il recupero dei messaggi e che non sia estratto alcun messaggio.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testGetMessagesEmptyList() throws Exception {
		// l'utente non ha messaggi in segreteria
		messageList = new ArrayList<IMessage>();
		when(dao.getMessages(user)).thenReturn(messageList);

		// invoca il metodo da testare
		tester.doAction(request, response);

		// verifica l'output
		writer.flush();
		String responseText = writer.toString();
		assertEquals("[]", responseText);

		// verifica il corretto utilizzo dei mock
		verify(response).getWriter();
		verifyZeroInteractions(request);
		verify(dao).getUserData(username);
		verify(dao).getMessages(user);
		verifyZeroInteractions(message);
		verifyZeroInteractions(sender);
	}

	/**
	 * Verifica il comportamento del metodo doAction nel momento in cui non è
	 * possibile accedere con successo al database per il recupero dei messaggi
	 * in segreteria. In tal caso il test verifica che il testo stampato sulla
	 * riposta HTTP corrisponda alla stringa 'null' che denota un errore nel
	 * server.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testWrongData() throws Exception {
		// errore nel recupero dei dati
		when(dao.getMessages(user)).thenThrow(new RuntimeException());

		// invoca il metodo da testare
		tester.doAction(request, response);

		// verifica l'output
		writer.flush();
		String responseText = writer.toString();
		assertEquals("null", responseText);

		// verifica il corretto utilizzo dei mock
		verify(response).getWriter();
		verifyZeroInteractions(request);
		verify(dao).getUserData(username);
		verify(dao).getMessages(user);
		verifyZeroInteractions(message);
		verifyZeroInteractions(sender);
	}
}
