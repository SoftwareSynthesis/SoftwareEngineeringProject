package org.softwaresynthesis.mytalk.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.StreamInbound;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.softwaresynthesis.mytalk.server.authentication.controller.LoginController;
import org.softwaresynthesis.mytalk.server.connection.PushInbound;

/**
 * Verifica della classe {@link ControllerManager}
 * 
 * @author Diego Beraldin
 * @version 2.0
 */
@RunWith(MockitoJUnitRunner.class)
public class ControllerManagerTest {
	private final String operation = "login";
	private final String className = "org.softwaresynthesis.mytalk.server.authentication.controller.LoginController";
	private final Long clientId = 1L;
	private Writer writer;
	private ControllerManager tester;
	@Mock
	private IController controller;
	@Mock
	private HttpServletRequest request;
	@Mock
	private HttpServletResponse response;
	@Mock
	private ServletConfig configuration;
	@Mock
	private PushInbound ws;

	/**
	 * Inizializza l'oggetto da sottoporre a verifica prima di tutti i test in
	 * modo da caricare la mappa interna dei nomi qualificati dei controller.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Before
	public void setUp() throws Exception {
		// configura il comportamento della richiesta
		when(request.getParameter("operation")).thenReturn(operation);
		// inizializza il writer
		writer = new StringWriter();
		// inizializza il comportamento della risposta
		when(response.getWriter()).thenReturn(new PrintWriter(writer));
		// configura il comportamento del mock del controller
		doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) {
				Object[] args = invocation.getArguments();
				HttpServletResponse mockResponse = (HttpServletResponse) args[1];
				try {
					mockResponse.getWriter().write("ciao");
				} catch (IOException e) {
					fail(e.getMessage());
				}
				return null;
			}
		}).when(controller)
				.execute(any(HttpServletRequest.class), eq(response));
		// inizializza l'oggetto da testare
		tester = spy(new ControllerManager());
		// inizializza la mappa dei controller
		tester.init(configuration);
		// azzera la lista dei client noti alla servlet
		ControllerManager.clients = new HashMap<Long, PushInbound>();
	}

	/**
	 * Verifica la corretta inizializzazione della servlet e il fatto che, a
	 * inizializzazione avvenuta, sia possibile ottenere la mappa contenente i
	 * nomi dei controller. Si verifica inoltre che la mappa ottenuta abbia la
	 * struttura appropriata rispetto al file .properties a partire dal quale
	 * deve essere configurata la mappa.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testInit() {
		// invoca il metodo da testare
		Hashtable<String, String> controllers = tester.getControllers();

		// verifica l'output ottenuto
		assertNotNull(controllers);
		assertFalse(controllers.isEmpty());
		Set<String> keys = controllers.keySet();
		assertEquals(23, keys.size());
	}

	/**
	 * Verifica il comportamento della servlet nel momento in cui viene invocato
	 * il metodo doPost. In particolare, si verifica che sia estratto il
	 * parametro 'operation' dalla richiesta HTTP, e che sia invocato
	 * correttamente il metodo execute sul controller associato all'operazione
	 * passando a quest'ultimo la richiesta e la risposta con cui è invocato il
	 * metodo da testare. Inoltre si verifica che sulla risposta HTTP sia
	 * effettivamente stampata la stringa che il mock del controller utilizzato
	 * dal test è stato configurato per stampare.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testDoPost() throws Exception {
		// bypassa il metodo createController ;)
		when(tester.createController(className)).thenReturn(controller);

		// invoca il metodo da testare
		tester.doPost(request, response);

		// verifica l'output ottenuto
		writer.flush();
		String result = writer.toString();
		assertNotNull(result);
		assertEquals("ciao", result);

		// verifica il corretto utilizzo dei mock
		verify(request).getParameter("operation");
		verify(controller).execute(request, response);
	}

	/**
	 * Verifica il comportamento del metodo doPost nel momento in cui la stringa
	 * estratta dalla richiesta HTTP tramite il parametro 'operation' non
	 * permette di identificare un controller valido da istanziare. Il test
	 * controlla che in una simile circostanza la pagina di risposta sia
	 * lasciata vuota.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testDoPostClassNotFound() throws Exception {
		// simula il verificarsi di un errore
		when(tester.createController(className)).thenThrow(
				new ClassNotFoundException());

		// invoca il metodo da testare
		tester.doPost(request, response);

		// verifica l'output ottenuto
		writer.flush();
		String result = writer.toString();
		assertNotNull(result);
		assertTrue(result.isEmpty());

		// verifica il corretto utilizzo dei mock
		verify(request).getParameter("operation");
		verifyZeroInteractions(controller);
	}

	/**
	 * Verifica il comportamento del metodo doPost nel momento in cui la stringa
	 * estratta dalla richiesta HTTP tramite il parametro 'operation'
	 * identifichi un controller che non è possibile istanziare in questo
	 * contesto perché non ha il livello corretto di accessibilità. Il test
	 * controlla che in una simile circostanza la pagina di risposta sia
	 * lasciata vuota.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testDoPostIllegalAccess() throws Exception {
		// simula il verificarsi di un errore
		when(tester.createController(className)).thenThrow(
				new IllegalAccessException());

		// invoca il metodo da testare
		tester.doPost(request, response);

		// verifica l'output ottenuto
		writer.flush();
		String result = writer.toString();
		assertNotNull(result);
		assertTrue(result.isEmpty());

		// verifica il corretto utilizzo dei mock
		verify(request).getParameter("operation");
		verifyZeroInteractions(controller);
	}

	/**
	 * Verifica il comportamento del metodo doPost nel momento in cui la stringa
	 * estratta dalla richiesta HTTP tramite il parametro 'operation'
	 * identifichi un controller che non è possibile istanziare in questo
	 * contesto perché non è presente un costruttore di default o si tratta di
	 * una classe astratta. Il test controlla che in una simile circostanza la
	 * pagina di risposta sia lasciata vuota.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testDoPostInstantiationException() throws Exception {
		// simula il verificarsi di un errore
		when(tester.createController(className)).thenThrow(
				new InstantiationException());

		// invoca il metodo da testare
		tester.doPost(request, response);

		// verifica l'output ottenuto
		writer.flush();
		String result = writer.toString();
		assertNotNull(result);
		assertTrue(result.isEmpty());

		// verifica il corretto utilizzo dei mock
		verify(request).getParameter("operation");
		verifyZeroInteractions(controller);
	}

	/**
	 * Verifica il comportamento del metodo doPost nel momento in cui la
	 * richiesta HTTP con cui viene invocato non contiene tutti i parametri che
	 * la servlet si aspetta e, in particolare, manchi il parametro obbligatorio
	 * 'operation'. Il test verifica che la pagina di risposta in una simile
	 * circostanza sia lasciata vuota e che non venga instanziato alcun
	 * controller a causa dell'invocazione del metodo.
	 * 
	 * TODO da documentare!
	 * 
	 * @version 2.0
	 * @author Diego Beraldin
	 */
	@Test
	public void testDoPostWithoutParameter() throws Exception {
		// priva la richiesta del parametro richiesto
		when(request.getParameter("operation")).thenReturn(null);

		// invoca il metodo da testare
		tester.doPost(request, response);

		// verifica l'output ottenuto
		writer.flush();
		String result = writer.toString();
		assertNotNull(result);
		assertTrue(result.isEmpty());

		// verifica il corretto utilizzo dei mock
		verify(request).getParameter("operation");
		verify(tester, never()).createController(anyString());
		verifyZeroInteractions(controller);
	}

	/**
	 * Verifica il comportamento del metodo factory che crea il canale di
	 * comunicazione WebSocket tra client e server. In particolare il test
	 * verifica che il valore ritornato sia un riferimento del sottotipo
	 * corretto di MessageInbound, per l'applicazione corretta del design
	 * pattern Factory Method.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testCreateWebSocket() {
		StreamInbound result = tester.createWebSocketInbound("pippo", request);
		assertNotNull(result);
		assertTrue(result instanceof MessageInbound);
		assertTrue(result instanceof PushInbound);
	}

	/**
	 * Verifica il comportamento della classe nel momento in cui viene invocato
	 * il metodo destroy() su un'istanza della classe {@link ControllerManager}.
	 * In particolare il test sia assicura che sia impostata a <code>null</code>
	 * la variabile di istanza che memorizza la mappa che associa le operazioni
	 * ai nomi dei controller.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testDestroy() {
		// invoca il metodo da testare
		tester.destroy();

		// verifica che sia ottenuto il risultato desiderato
		Hashtable<String, String> controllers = tester.getControllers();
		assertNull(controllers);
	}

	/**
	 * Verifica che l'istanziazione dei controller ottenuta tramite la
	 * reflection avvenga in maniera corretta nel caso in cui il nome
	 * qualificato passato come parametro corrisponda a una classe a cui il
	 * caricatore ha accesso.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testCreateControllerSuccessfully() throws Exception {
		Object result = tester.createController(className);
		assertNotNull(result);
		assertTrue(result instanceof LoginController);
	}

	/**
	 * Verifica il comportamento del metodo createController se il parametro
	 * passato non corrisponde ad alcuna classe nota al caricatore. In tal caso
	 * il test ha successo solo se è sollevata un'eccezione di tipo
	 * ClassNotFoundException, come desiderato in questa circostanza.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test(expected = ClassNotFoundException.class)
	public void testCreateControllerUnsuccessfully() throws Exception {
		tester.createController("this.is.not.a.valid.Class");
		fail("Dovevo sollevare eccezione!");
	}

	/**
	 * Verifica che sia possibile aggiungere un nuovo canale di comunicazione
	 * aperto con un client all'interno delle strutture dati della serlvet.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testPutClient() {
		ControllerManager.putClient(clientId, ws);
		assertTrue(ControllerManager.clients.containsKey(clientId));
		PushInbound channel = ControllerManager.clients.get(clientId);
		assertEquals(ws, channel);
	}

	/**
	 * Verifica la possibilità di recuperare un canale di comunicazione aperto
	 * con un client a partire dall'identificativo del client stesso.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testFindClient() {
		ControllerManager.clients.put(clientId, ws);
		PushInbound result = ControllerManager.findClient(clientId);
		assertNotNull(result);
		assertEquals(ws, result);
		result = ControllerManager.findClient(2L);
		assertNull(result);
	}

	/**
	 * Verifica che sia possibile rimuovere con successo un client dalle
	 * strutture dati della servelet avendo a disposizione il numero
	 * identificativo del client stesso.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testRemoveClient() {
		ControllerManager.clients.put(clientId, ws);
		ControllerManager.removeClient(ws);
		assertFalse(ControllerManager.clients.containsKey(clientId));
		assertFalse(ControllerManager.clients.containsValue(ws));
	}

	/**
	 * Verifica che è possibile recuperare la rappresentazione in forma di
	 * stringa dello stato di un determinato client noto il numero
	 * identificativo che lo identifica in maniera univoca fra quelli
	 * memorizzati nelle strutture dati internet alla servlet.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testGetState() {
		// il client cercato è disponibile
		when(ws.getState()).thenReturn(PushInbound.State.AVAILABLE);
		ControllerManager.clients.put(clientId, ws);
		String result = ControllerManager.getState(clientId);
		assertEquals("available", result);
		// il client cercato è occupato
		when(ws.getState()).thenReturn(PushInbound.State.OCCUPIED);
		result = ControllerManager.getState(clientId);
		assertEquals("occupied", result);
		// il client cercato è offline
		ControllerManager.clients.clear();
		result = ControllerManager.getState(clientId);
		assertEquals("offline", result);
	}

	/**
	 * Sssssh! Di questo metodo non deve sapere niente nessuno, se arrivi a
	 * leggere fino a questo punto - mi raccomando - acqua in bocca!
	 */
	public static Map<Long, PushInbound> getClients() {
		return ControllerManager.clients;
	}
}
