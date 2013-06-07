package org.softwaresynthesis.mytalk.server.message.controller;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.softwaresynthesis.mytalk.server.abook.IUserData;
import org.softwaresynthesis.mytalk.server.dao.DataPersistanceManager;
import org.softwaresynthesis.mytalk.server.message.IMessage;

import static org.mockito.Mockito.*;

/**
 * Verifica della classe {@link AddMessageController}
 * 
 * @author Diego Beraldin
 * @version 2.0
 */
@RunWith(MockitoJUnitRunner.class)
public class AddMessageControllerTest {
	private final String username = "indirizzo5@dominio.it";
	private final String sep = System.getProperty("file.separator");
	private final Long messageId = 1L;
	private final Long receiverId = 1L;
	private final String path = System.getenv("MyTalkConfiguration");
	private final String messagePath = String.format(path + "%sMyTalk%sSecretariat%s%d.wav",
			sep, sep, sep, messageId);
	private final Date messageDate = new Date(1368775249L);
	private Writer writer;
	private AddMessageController tester;
	@Mock
	private HttpServletRequest request;
	@Mock
	private HttpServletResponse response;
	@Mock
	private Part messagePart;
	@Mock
	private Part receiverPart;
	@Mock
	private IUserData sender;
	@Mock
	private IUserData receiver;
	@Mock
	private IMessage message;
	@Mock
	private DataPersistanceManager dao;
	@Mock
	private InputStream istream;

	/**
	 * Inizializza l'oggetto da testare e configura il comportamento dei mock
	 * prima di tutte le verifiche che sono contenuti in questo caso di test.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Before
	public void setUp() throws Exception {
		// comportamento della part del messaggio
		when(messagePart.getInputStream()).thenReturn(istream);
		// comportamento della richiesta
		when(request.getPart("contactId")).thenReturn(receiverPart);
		when(request.getPart("msg")).thenReturn(messagePart);
		// comportamento della risposta
		writer = new StringWriter();
		when(response.getWriter()).thenReturn(new PrintWriter(writer));
		// comportamento del gestore di persistenza
		when(dao.getMessageNewKey()).thenReturn(messageId);
		when(dao.getUserData(username)).thenReturn(sender);
		when(dao.getUserData(receiverId)).thenReturn(receiver);

		// inizializza l'oggetto da testare ;)
		tester = spy(new AddMessageController() {
			@Override
			protected DataPersistanceManager getDAOFactory() {
				return dao;
			}

			@Override
			protected String getUserMail() {
				return username;
			}

			@Override
			String getValue(Part part) {
				return receiverId.toString();
			}

			@Override
			void writeFile(String path, InputStream istream) {
			}

			@Override
			IMessage createMessage() {
				return message;
			}

			@Override
			Date getCurrentDate() {
				return messageDate;
			}
		});
	}

	/**
	 * Verifica il comportamento del metodo doAction per effettuare l'aggiunta
	 * di un messaggio nella segreteria telefonica. In particolare il test
	 * verifica che il testo stampato sulla risposta HTTP sia, come desiderato
	 * in caso di successo, la stringa 'true'. Il test controlla inoltre che i
	 * mock siano utilizzati in modo corretto e, in particolare, che sia salvato
	 * un nuovo file con il percorso corretto e che sia effettuata nel database
	 * un'operazione di inserimento per il nuovo messaggio (dopo aver impostato
	 * i suoi campi dati ai valori corretti).
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testAddCorrectMessage() throws Exception {
		// invoca il metodo da testare
		tester.doAction(request, response);

		// verifica l'output
		writer.flush();
		String responseText = writer.toString();
		assertEquals("true", responseText);

		// verifica il corretto utilizzo dei mock e degli spy
		verify(response).getWriter();
		verify(request).getPart("msg");
		verify(request).getPart("contactId");
		verify(tester).getValue(receiverPart);
		verify(messagePart).getInputStream();
		verify(tester).writeFile(messagePath, istream);
		verify(tester).createMessage();
		verify(dao).getMessageNewKey();
		verify(message).setId(messageId);
		verify(message).setSender(sender);
		verify(message).setReceiver(receiver);
		verify(message).setDate(messageDate);
		verify(dao).insert(message);
	}

	/**
	 * Verifica il comportamento del metodo doAction nel momento in cui non è
	 * possibile portare a termine l'operazione con successo perché si verifica
	 * un errore nel recupero del flusso di input associato al messaggio. Il
	 * test verifica che in questo caso il testo stampato nella riposta HTTP sia
	 * la stringa 'null' che denota per il client un fallimento dell'operazione.
	 * Inoltre si controlla che non vengano effettuate operazioni di creazione
	 * di un nuovo messaggio e di inserimento di nuovi messaggi nella base di
	 * dati del sistema.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testErrorWithInput() throws Exception {
		// solleva eccezione
		when(messagePart.getInputStream()).thenThrow(new RuntimeException());
		// invoca il metodo da testare
		tester.doAction(request, response);

		// verifica l'output
		writer.flush();
		String responseText = writer.toString();
		assertEquals("null", responseText);

		// verifica il corretto utilizzo dei mock e degli spy
		verify(response).getWriter();
		verify(request).getPart("msg");
		verify(request).getPart("contactId");
		verify(tester).getValue(receiverPart);
		verify(messagePart).getInputStream();
		verify(tester, never()).writeFile(anyString(), any(InputStream.class));
		verify(tester, never()).createMessage();
		verify(dao).getMessageNewKey();
		verifyZeroInteractions(message);
		verify(dao, never()).insert(any(IMessage.class));
	}

	/**
	 * Verifica il comportamento del metodo doAction nel momento in cui la
	 * richiesta HTTP con cui viene invocato non contiene tutti i dati necessari
	 * a portare a termine con successo l'operazione di salvataggio di un nuovo
	 * messaggio nella segreteria telefonica di un determinato utente, in
	 * particolare perché non è presente il contenuto binario del messaggio
	 * stesso. Il test verifica che il testo stampato sulla risposta sia, come
	 * desiderato, la stringa 'null', che non sia creato alcun nuovo transfer
	 * object sottotipo di IMessage e che non sia effettuata alcuna operazione
	 * sul sistema di gestione della persistenza (né per calcolare il nome del
	 * messaggio né per l'inserimento vero e proprio).
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testMissingPart() throws Exception {
		// la richiesta non contiene alcun dato binario
		when(request.getPart(anyString())).thenReturn(null);

		// invoca il metodo da testare
		tester.doAction(request, response);

		// verifica l'output
		writer.flush();
		String responseText = writer.toString();
		assertEquals("null", responseText);

		// verifica il corretto utilizzo dei mock
		verify(response).getWriter();
		verify(request).getPart("msg");
		verify(request).getPart("contactId");
		verify(dao).getMessageNewKey();
		verify(tester, never()).createMessage();
		verifyZeroInteractions(istream);
	}
}
