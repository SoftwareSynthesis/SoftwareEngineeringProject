package org.softwaresynthesis.mytalk.server.connection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.softwaresynthesis.mytalk.server.ControllerManagerTest.getClients;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.catalina.websocket.WsOutbound;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.softwaresynthesis.mytalk.server.abook.IAddressBookEntry;
import org.softwaresynthesis.mytalk.server.abook.IUserData;
import org.softwaresynthesis.mytalk.server.connection.PushInbound.State;
import org.softwaresynthesis.mytalk.server.dao.DataPersistanceManager;

/**
 * Verifica della classe {@link PushInbound}. Dinanzi a me non fuor cose create
 * / se non etterne, e io etterno duro. / Lasciate ogne speranza, voi
 * ch'intrate!
 * 
 * @author Diego Beraldin
 * @version 2.0
 */
@RunWith(MockitoJUnitRunner.class)
public class PushInboundTest {
	private final Long id = 1L;
	private final Long otherId = 2L;
	private PushInbound tester;
	Map<Long, PushInbound> clients;
	Set<IAddressBookEntry> entrySet;
	private StringWriter writer;
	@Mock
	private ByteBuffer charBuffer;
	@Mock
	private CharBuffer buffer;
	@Mock
	private PushInbound other;
	@Mock
	private WsOutbound outbound;
	@Mock
	DataPersistanceManager dao;
	@Mock
	IUserData user;
	@Mock
	IUserData contact;
	@Mock
	IAddressBookEntry entry;

	/**
	 * Reinizializza tutti gli elementi comuni a tutti i test e l'oggetto da
	 * testare stesso, prima di tutte le verifiche qui contenute.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Before
	public void setUp() throws Exception {
		// è necessario azzerare il contenuto il buffer in cui viene memorizzato
		// il testo stampato sul WsOutbound
		writer = new StringWriter();
		// configura il comportamento del WsOutbound
		doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) {
				try {
					CharBuffer buf = (CharBuffer) invocation.getArguments()[0];
					writer.write(buf.toString());
				} catch (Exception e) {
				}
				return null;
			}
		}).when(outbound).writeTextMessage(any(CharBuffer.class));
		// inizializza la mappa dei client noti al server
		clients = getClients();
		// configura il comportamento del contatto
		when(contact.getId()).thenReturn(otherId);
		// comportamento della voce di rubrica
		when(entry.getContact()).thenReturn(contact);
		// configura il comportamento dell'utente e la sua rubrica
		entrySet = new HashSet<IAddressBookEntry>();
		entrySet.add(entry);
		when(user.getAddressBook()).thenReturn(entrySet);
		// configura il comportamento del gestore di persistenza
		when(dao.getUserData(id)).thenReturn(user);
		// inizializza l'oggetto da sottoporre a verifica ;)
		tester = new PushInbound() {
			@Override
			WsOutbound getWsOutbound(PushInbound inbound) {
				if (inbound != this) {
					return outbound;
				} else {
					throw new RuntimeException("Notifichi a te stesso?!");
				}
			}

			@Override
			DataPersistanceManager getDAOFactory() {
				return dao;
			}
		};
	}

	/**
	 * Operazioni di clean-up necessarie dopo ogni test
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@After
	public void tearDown() {
		clients.clear();
	}

	/**
	 * Verifica che sia possibile impostare e recuperare correttamente il numero
	 * identificativo associato al canale di connessione fra server e client.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testId() {
		tester.setId(id);
		Long result = tester.getId();
		assertNotNull(result);
		assertEquals(id, result);
	}

	/**
	 * Verifica che sia possibile impostare e recuperare correttamente lo stato
	 * del client associato alla connessione client-server.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testState() {
		State state = State.OCCUPIED;
		tester.setState(state);
		assertEquals(state, tester.getState());
		state = State.AVAILABLE;
		tester.setState(state);
		assertEquals(state, tester.getState());
	}

	/**
	 * Verifica il comportamento della classe nel momento in cui si tenta di
	 * trasmettere un messaggio binario tramite il canale di comunicazione con
	 * il server. In particolare il test ha successo solo se viene sollevata una
	 * IOException e se il messaggio associato all'eccezione corrisponde a
	 * quanto atteso.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test(expected = IOException.class)
	public void testOnBinaryMessageByteBuffer() throws Exception {
		try {
			tester.onBinaryMessage(charBuffer);
		} catch (IOException e) {
			assertEquals("Metodo non implementato", e.getMessage());
			throw e;
		}
	}

	/**
	 * Verifica il comportamento della classe nel momento in cui viene
	 * utilizzata per inviare al server un messaggio testuale che segnala la
	 * connessione di un nuovo client. In particolare il test verifica che sia
	 * aggiunta una nuova voce alla lista delle connessioni note al server,
	 * indicizzata con il campo identificativo del nuovo client.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testConnectClient() throws Exception {
		// messaggio di connessione
		when(buffer.toString())
				.thenReturn(String.format("[\"1\", \"%d\"]", id));
		// invoca il metodo da testare
		tester.onTextMessage(buffer);
		// controlla il risultato
		assertFalse(clients.isEmpty());
		assertEquals(1, clients.size());
		assertTrue(clients.containsKey(id));
	}

	/**
	 * Verifica il comportamento del client nel momento in cui il messaggio
	 * ricevuto dal server tramite il canale serve ad inviare a un secondo
	 * client una determinata stringa che contiene la descrizione del primo
	 * client. Il test verifica che il testo effettivamente trasmetto sul
	 * secondo canale di comunicazione sia conforme alle aspettative e contenga
	 * cioè il carattere 2, seguito dal carattere pipe, seguito dalla stringa
	 * ricevuta inizialmente tramite il primo canale.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testExchangeData() throws Exception {
		// prepara il messaggio e il finto 'peer' del client
		clients.put(otherId, other);
		when(buffer.toString()).thenReturn(
				String.format("[\"2\", \"%d\", \"dummy\"]", id));
		// invoca il metodo da testare
		tester.onTextMessage(buffer);
		// verifica l'output
		writer.flush();
		String message = writer.toString();
		assertEquals("2|dummy", message);
	}

	/**
	 * Verifica il comportamento della classe nel momento in cui il canale è
	 * utilizzato da un primo client per chiedere al server di trasmettere a un
	 * secondo client il suo numero identificativo. In particolare, il test
	 * verifica che il messaggio di testo trasmesso al secondo client
	 * corrisponda alle aspettative e sia cioè formato dal carattere 3, seguito
	 * dal carattere pipe e quindi dal numero identificativo del primo client.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testSendIdToCallee() throws Exception {
		// prepara il messaggio e il finto 'peer' del client
		clients.put(otherId, other);
		when(buffer.toString())
				.thenReturn(String.format("[\"3\", \"%d\"]", id));
		// invoca il metodo da testare
		tester.setId(id);
		tester.onTextMessage(buffer);
		// verifica l'output
		writer.flush();
		String message = writer.toString();
		assertEquals("3|" + id, message);
	}

	/**
	 * Verifica il comportamento della classe nel momento in cui il messaggio
	 * trasmesso dal client al server segnala la disconnessione del client. In
	 * particolare il test verifica che dalla lista delle connessioni note al
	 * server sia rimosso il canale di comunicazione associato al client che
	 * richiede la disconnessione.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testDisconnectClient() throws Exception {
		// messaggio di disconnessione
		when(buffer.toString())
				.thenReturn(String.format("[\"4\", \"%d\"]", id));
		// invoca il metodo da testare
		tester.onTextMessage(buffer);
		// controlla il risultato
		assertFalse(clients.containsKey(id));
		assertTrue(clients.isEmpty());
	}

	/**
	 * Verifica il comportamento della classe nel momento in cui il canale di
	 * comunicazione è utilizzato da un client per notificare al server un
	 * cambiamento di stato. In particolare il test verifica che a tutti i
	 * contatti che sono presenti nella rubrica dell'utente che cambia stato e
	 * che sono effettivamente connessi sia inviato un messaggio contenente il
	 * carattere 5, il carattere pipe e la corretta rappresentazione in formato
	 * stringa dello stato assunto dal mittente iniziale del messaggio.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testNotifyStatusChange() throws Exception {
		// preparazione dei dati per il test
		tester.setId(id);
		clients.put(otherId, other);
		when(buffer.toString()).thenReturn("[5, \"occupied\"]");
		// invoca il metodo da testare
		tester.onTextMessage(buffer);
		// verifica l'output
		writer.flush();
		String message = writer.toString();
		assertEquals("5|" + id + "|occupied", message);
		// verifica il corretto utilizzo dei mock
		verify(dao).getUserData(id);
		verify(user).getAddressBook();
		verify(entry).getContact();
		verify(contact).getId();
	}

	/**
	 * Verifica il comportamento della classe nel momento in cui il messaggio
	 * ricevuto dal server sia la notifica, da parte di un client, dell'avvenuto
	 * rifiuto di una chiamata entrante. Il test verifica, in particolare, che
	 * al client sia trasmesso un messaggio contenente il carattere 6, seguito
	 * dal carattere pipe.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testCallRefusal() throws Exception {
		// prepara i dati per il test
		clients.put(otherId, other);
		when(buffer.toString()).thenReturn("[6, " + otherId + "]");
		// invoca il metodo da testare
		tester.onTextMessage(buffer);
		// verifica l'output
		writer.flush();
		String message = writer.toString();
		assertEquals("6|", message);
	}

	/**
	 * Verifica il comportamento della classe nel momento in cui il messaggio
	 * ricevuto dal server indica la volontà da parte di un client di
	 * trasmettere un messaggio testuale ad un altro client. Il test verifica
	 * che sul canale di comunicazione che corrisponde al client destinatario
	 * sia effettivamente mandato un messaggio contenente il carattere 6,
	 * seguito dal carattere pipe, seguito dall'identificativo del client
	 * mittente, quindi nuovamente da un pipe e, infine, dal testo della chat.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testSendTextMessage() throws Exception {
		// prepara i dati per il test
		tester.setId(id);
		String chatText = "ciao mona!";
		clients.put(otherId, other);
		when(buffer.toString()).thenReturn(
				String.format("[7, %d, \"%s\"]", otherId, chatText));
		// invoca il metodo da testare
		tester.onTextMessage(buffer);
		// verifica l'output
		writer.flush();
		String message = writer.toString();
		assertEquals(String.format("7|%d|%s", id, chatText), message);
	}
}
