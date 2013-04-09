package org.softwaresynthesis.mytalk.server.connection;

import static org.junit.Assert.fail;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.Map;
import java.util.HashMap;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import javax.servlet.http.HttpServletRequest;
import org.softwaresynthesis.mytalk.server.connection.PushInbound.State;

public class ChannelServletTest {
	// oggetto da testare
	private static ChannelServlet tester;

	@BeforeClass
	public static void setUpBeforeClass() {
		tester = new ChannelServlet();
	}

	@Test
	public void testCreateWebSocketInbound() {
		// crea una finta richiesta HTTP :)
		HttpServletRequest request = mock(HttpServletRequest.class);

		PushInbound result = null;
		// invoca il metodo da testare
		result = (PushInbound) tester.createWebSocketInbound("", request);

		// verifica dell'output ottenuto
		assertNotNull(result);
		State state = result.getState();
		assertTrue(state == State.AVAILABLE);
	}

	@Test
	public void testPutClient() {
		// crea dati finti per il test
		PushInbound channel = mock(PushInbound.class);
		Long id = 1L;

		// invoca il metodo da testare
		ChannelServlet.putClient(id, channel);

		try {
			// recupera i clients
			Map<Long, PushInbound> clients = ChannelServlet.getClients();
			// verifica il contenuto della mappa
			assertFalse(clients.isEmpty());
			assertTrue(clients.containsKey(id));
			PushInbound result = clients.get(id);
			assertEquals(channel, result);
		} catch (Throwable error) {
			fail(error.getMessage());
		} finally {
			// pulisce il campo dati 'sporcato' dal test
			ChannelServlet.setClients(new HashMap<Long, PushInbound>());
		}
	}

	@Test
	public void testFindClient() {
		// crea un client per il test e lo inserisce nella mappa
		PushInbound channel = mock(PushInbound.class);
		Long id0 = 1L;
		Long id1 = 2L;
		Map<Long, PushInbound> clients = new HashMap<Long, PushInbound>();
		clients.put(id0, channel);
		ChannelServlet.setClients(clients);

		try {
			// verifica cosa succede con un oggetto PushInbound valido
			PushInbound result = null;
			result = ChannelServlet.findClient(id0);
			assertNotNull(result);
			assertEquals(channel, result);
			// verifica cosa succede quando la chiave non è presente
			result = ChannelServlet.findClient(id1);
			assertNull(result);
		} catch (Throwable error) {
			fail(error.getMessage());
		} finally {
			// pulisce il campo dati 'sporcato' dal test
			ChannelServlet.setClients(new HashMap<Long, PushInbound>());
		}
	}

	@Test
	public void testRemoveClient() {
		// crea un client per il test e lo inserisce nella mappa
		PushInbound channel = mock(PushInbound.class);
		Long id = 1L;
		Map<Long, PushInbound> clients = new HashMap<Long, PushInbound>();
		clients.put(id, channel);
		ChannelServlet.setClients(clients);

		try {
			// invoca il metodo da testare
			ChannelServlet.removeClient(channel);
			// verifica quel che avviene nella mappa
			Map<Long, PushInbound> result = ChannelServlet.getClients();
			assertFalse(result.containsKey(id));
		} catch (Throwable error) {
			fail(error.getMessage());
		} finally {
			// pulisce il campo dati 'sporcato' dal test
			ChannelServlet.setClients(new HashMap<Long, PushInbound>());
		}
	}

	@Test
	public void testGetState() {
		// crea i tre client necessari per il test
		PushInbound channelAvailable = mock(PushInbound.class);
		when(channelAvailable.getState()).thenReturn(State.AVAILABLE);
		Long idAvailable = 1L;
		PushInbound channelOccupied = mock(PushInbound.class);
		when(channelOccupied.getState()).thenReturn(State.OCCUPIED);
		Long idOccupied = 2L;
		Long idOffline = 3L;
		// inserisce la mappa fra i dati accessibili alla servlet
		Map<Long, PushInbound> clients = new HashMap<Long, PushInbound>();
		clients.put(idAvailable, channelAvailable);
		clients.put(idOccupied, channelOccupied);
		ChannelServlet.setClients(clients);

		try {
			// test con utente presente e offline
			String result = ChannelServlet.getState(idAvailable);
			assertNotNull(result);
			assertEquals("available", result);

			// test con utente presente e occupato
			result = ChannelServlet.getState(idOccupied);
			assertNotNull(result);
			assertEquals("occupied", result);

			// test con utente non presente
			result = ChannelServlet.getState(idOffline);
			assertNotNull(result);
			assertEquals("offline", result);
		} catch (Throwable error) {
			fail(error.getMessage());
		} finally {
			// pulisce il campo dati 'sporcato' dal test
			ChannelServlet.setClients(new HashMap<Long, PushInbound>());
		}
	}

}
