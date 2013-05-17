package org.softwaresynthesis.mytalk.server.message.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
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
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.softwaresynthesis.mytalk.server.abook.IUserData;
import org.softwaresynthesis.mytalk.server.dao.DataPersistanceManager;
import org.softwaresynthesis.mytalk.server.message.IMessage;

/**
 * Verifica della classe {@UpdateMessageController}
 * 
 * @author Diego Beraldin
 * @version 2.0
 */
@RunWith(MockitoJUnitRunner.class)
public class UpdateMessageControllerTest {
	private final String username = "indirizzo5@dominio.it";
	private final Long messageId = 1L;
	private Writer writer;
	private UpdateMessageController tester;
	@Mock
	private HttpServletRequest request;
	@Mock
	private HttpServletResponse response;
	@Mock
	private DataPersistanceManager dao;
	@Mock
	private IMessage message;
	@Mock
	private IUserData user;

	/**
	 * Inizializza l'oggetto da testare e riconfigura il comportamento dei mock
	 * prima di ognuna delle verifiche contenute in questo caso di test.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Before
	public void setUp() throws Exception {
		// comportamento del messaggio
		when(message.getReceiver()).thenReturn(user);
		// comportamento del gestore della persistenza
		when(dao.getMessage(messageId)).thenReturn(message);
		when(dao.getUserData(username)).thenReturn(user);
		// comportamento della risposta
		writer = new StringWriter();
		when(response.getWriter()).thenReturn(new PrintWriter(writer));
		// comportamento della richiesta
		when(request.getParameter("idMessage"))
				.thenReturn(messageId.toString());
		// inizializza l'oggetto da testare
		tester = new UpdateMessageController() {
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
	 * Verifica il comportamento del metodo doAction per aggiornare lo stato di
	 * un messaggio a non letto nella segreteria quando la richiesta con cui
	 * viene invocato contiene tutti i parametri necessari a portare a termine
	 * con successo l'operazione. In particolare, il test verifica che il testo
	 * stampato nella risposta sia, come atteso, la stringa 'true'. Inoltre si
	 * verifica che sia fatto un uso corretto del sistema di persistenza
	 * aggiornando il messaggio dopo averne impostato lo stato a non letto.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testSetNewCorrectMessage() throws Exception {
		// il messaggio è da impostare come non letto
		when(request.getParameter("valueToSet")).thenReturn("true");

		// invoca il metodo da testare
		tester.doAction(request, response);

		// verifica l'output
		writer.flush();
		String responseText = writer.toString();
		assertEquals("true", responseText);

		// verifica il corretto utilizzo dei mock
		verify(response).getWriter();
		verify(request).getParameter("idMessage");
		verify(request).getParameter("valueToSet");
		verify(dao).getUserData(username);
		verify(dao).getMessage(messageId);
		verify(message).setNewer(true);
		verify(dao).update(message);
	}

	/**
	 * Verifica il comportamento del metodo doAction per aggiornare lo stato di
	 * un messaggio a letto nella segreteria quando la richiesta con cui viene
	 * invocato contiene tutti i parametri necessari a portare a termine con
	 * successo l'operazione. In particolare, il test verifica che il testo
	 * stampato nella risposta sia, come atteso, la stringa 'true'. Inoltre si
	 * verifica che sia fatto un uso corretto del sistema di persistenza
	 * aggiornando il messaggio dopo averne impostato lo stato a letto.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testSetNotNewCorrectMessage() throws Exception {
		// il messaggio è da impostare come non letto
		when(request.getParameter("valueToSet")).thenReturn("false");

		// invoca il metodo da testare
		tester.doAction(request, response);

		// verifica l'output
		writer.flush();
		String responseText = writer.toString();
		assertEquals("true", responseText);

		// verifica il corretto utilizzo dei mock
		verify(response).getWriter();
		verify(request).getParameter("idMessage");
		verify(request).getParameter("valueToSet");
		verify(dao).getUserData(username);
		verify(dao).getUserData(username);
		verify(dao).getMessage(messageId);
		verify(message).setNewer(false);
		verify(dao).update(message);
	}

	/**
	 * Verifica il comportamento del metodo doAction quando il destinatario del
	 * messaggio il cui numero identificativo passato nella richiesta non
	 * corrisponde all'utente che ha inviato la richiesta al controller. In
	 * particolare il test verifica che il testo stampato nella risposta
	 * corrisponda alla stringa 'null', come desiderato in caso di errore, e che
	 * nel sistema di gestione della persistenza dei dati non sia effettuato
	 * alcun aggiornamento.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testUpdateNotOwnedMessage() throws Exception {
		when(request.getParameter("valueToSet")).thenReturn("true");
		// il messaggio non appartiene a chi manda la richiesta
		IUserData other = mock(IUserData.class);
		when(message.getReceiver()).thenReturn(other);

		// invoca il metodo da testare
		tester.doAction(request, response);

		// verifica l'output ottenuto
		writer.flush();
		String responseText = writer.toString();
		assertEquals("null", responseText);

		// verifica il corretto utilizzo dei mock
		verify(response).getWriter();
		verify(request).getParameter("idMessage");
		verify(request).getParameter("valueToSet");
		verify(dao).getUserData(username);
		verify(dao).getMessage(messageId);
		verify(message).getReceiver();
		verify(message, never()).setNewer(anyBoolean());
		verify(dao, never()).update(message);
	}

	/**
	 * Verifica il comportamento del metodo doAction nel momento in cui il
	 * valore identificativo passato come parametro nella richiesta non
	 * corrisponde ad alcuno nei messaggi presenti nel database del sistema. Il
	 * test verifica che la stringa stampata sulla risposta sia effettivamente
	 * 'null', come desiderato in caso di errore, e che non siano mai effettuate
	 * operazioni di aggiornamento della tabella Messages nel database.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testUpdateNotExistMessage() throws Exception {
		// il messaggio è da impostare come non letto
		when(request.getParameter("valueToSet")).thenReturn("false");
		// impedisce il recupero del messaggio dal database
		when(dao.getMessage(messageId)).thenReturn(null);

		// invoca il metodo da testare
		tester.doAction(request, response);

		// verifica l'output
		writer.flush();
		String responseText = writer.toString();
		assertEquals("null", responseText);

		// verifica il corretto utilizzo dei mock
		verify(response).getWriter();
		verify(request).getParameter("idMessage");
		verify(request).getParameter("valueToSet");
		verify(dao).getUserData(username);
		verify(dao).getMessage(messageId);
		verify(dao, never()).update(any(IMessage.class));
		verifyZeroInteractions(message);
	}

	/**
	 * Verifica il comportamento del metodo doAction nel momento in cui la
	 * richiesta HTTP non comprende tutti i parametri necessari per portare a
	 * termine con successo l'operazione. Il test verifica che la stringa
	 * stampata sulla risposta sia effettivamente 'null', come desiderato in
	 * caso di errore, e che non siano mai effettuate operazioni di
	 * aggiornamento della tabella Messages nel database.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testWrongData() throws Exception {
		// manca un parametro necessario nella richiesta
		when(request.getParameter("valueToSet")).thenReturn(null);

		// invoca il metodo da testare
		tester.doAction(request, response);

		// verifica l'output
		writer.flush();
		String responseText = writer.toString();
		assertEquals("null", responseText);

		// verifica il corretto utilizzo dei mock
		verify(response).getWriter();
		verify(request).getParameter("idMessage");
		verify(request).getParameter("valueToSet");
		verify(dao, never()).getMessage(messageId);
		verify(dao, never()).update(any(IMessage.class));
		verifyZeroInteractions(message);
	}
}
