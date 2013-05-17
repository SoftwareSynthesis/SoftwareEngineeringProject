package org.softwaresynthesis.mytalk.server.message.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
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
 * Verifica della classe {@link DeleteMessageController} che è finalmente degna
 * di essere definita un vero test di unità.
 * 
 * @author Diego Beraldin
 * @version 2.0
 */
@RunWith(MockitoJUnitRunner.class)
public class DeleteMessageControllerTest {
	private final String username = "indirizzo5@dominio.it";
	private final String sep = System.getProperty("file.separator");
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
	@Mock
	private IUserData user;

	/**
	 * Configura il comportamento di tutti i mock e reinizializza l'oggetto da
	 * testare prima di tutte le verifiche contenute in questo caso di test.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Before
	public void setUp() throws Exception {
		// comportamento della richiesta
		when(request.getParameter("idMessage"))
				.thenReturn(idMessage.toString());
		// comportamento del messaggio
		when(message.getReceiver()).thenReturn(user);
		// comportamento del gestore di persistenza
		when(dao.getUserData(username)).thenReturn(user);
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

			@Override
			protected String getUserMail() {
				return username;
			}
		});
	}

	/**
	 * Verifica il comportamento del metodo doAction nel controller per la
	 * cancellazione di un messaggio nel momento in cui la richiesta che viene
	 * passata ad esso come parametro contiene tutti i parametri per portare a
	 * termine con successo la richiesta. In particolare, il test verifica che
	 * il testo stampato nella risposta HTTP sia effettivamente 'true', come
	 * desiderato. Inoltre si verifica che sia cancellata la voce del messaggio
	 * corrispondente nel sistema di gestione della persistenza dei dati e che
	 * sia effettivamente rimosso il file dal disco rigido del server tramite il
	 * metodo deleteFile.
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
		verify(tester).deleteFile("Secretariat" + sep + idMessage + ".wav");
		verify(message).getReceiver();
	}

	/**
	 * Verifica il comportamento del metodo doAction nel momento in cui in fase
	 * di cancellazione del messaggio si verifica un errore di I/O. Il test
	 * verifica che in tal caso il testo stampato nella risposta corrisponda
	 * alla stringa 'null', che denota un errore nel server. Inoltre, si
	 * controlla che non sia MAI effettuata alcuna operazione di cancellazione
	 * sul sistema di gestione della persistenza dei dati.
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
		verify(dao, never()).delete(any(IMessage.class));
		verify(tester).deleteFile("Secretariat" + sep + idMessage + ".wav");
		verify(message).getReceiver();
	}

	/**
	 * Verifica il comportamento del metodo doAction nel momento in cui il
	 * valore numerico passato tramite la richiesta HTTP con cui è invocato non
	 * corrisponde ad alcuno dei messaggi che sono presenti nel sistema di
	 * persistenza dei dati. In particolare, il test verifica che il testo
	 * stampato nella risposta sia la stringa 'null', come desiderato in questo
	 * caso, che non sia mai effettuata un'operazione di cancellazione nel
	 * sistema di persistenza dei dati e che non sia mai cancellato alcun file.
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
		verifyZeroInteractions(message);
	}

	/**
	 * Verifica il comportamento del metodo doAction nel momento in cui il
	 * messaggio di cui si richiede la cancellazione non appartiene all'utente
	 * da cui proviene la richiesta al controller. In particolare, il test
	 * verifica che il testo stampato sulla risposta corrisponda alla stringa
	 * 'null', come desiderato in questo caso. Inoltre si verifica che non venga
	 * mai effettuata alcuna operazione di cancellazione dal sistema di
	 * persistenza dei dati e che non sia rimosso dal disco alcun file.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testDeleteNotOwnedMessage() throws Exception {
		// il messaggio appartiene a un altro utente
		IUserData other = mock(IUserData.class);
		when(message.getReceiver()).thenReturn(other);

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
		verify(message).getReceiver();
		verify(dao, never()).delete(any(IMessage.class));
		verify(tester, never()).deleteFile(anyString());
	}

	/**
	 * Verifica il comportamento del metodo doAction per la cancellazione di un
	 * messaggio nella segreteria nel momento in cui la richiesta che gli viene
	 * passata come parametro non contiene tutti i parametri necessari per
	 * potrare a termine con successo l'operazione. In particolare si verifica
	 * che il testo stampato nella risposta HTTP sia effettivamente la stringa
	 * 'null', che denota per il client il verificarsi di un errore. Inoltre si
	 * verifica che non avvenga alcuna interazione con il sistema di gestion
	 * della persistenza dei dati e che non sia MAI cancellato alcun file dal
	 * server tramite il metodo deleteFile.
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
		verifyZeroInteractions(message);
	}
}
