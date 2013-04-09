package org.softwaresynthesis.mytalk.server.connection;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.io.IOException;
import org.softwaresynthesis.mytalk.server.connection.PushInbound.State;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.Map;
import java.util.HashMap;

public class PushInboundTest {
	private static PushInbound tester;

	@BeforeClass
	public static void setUpBeforeClass() {
		tester = new PushInbound();
	}

	@Test
	public void testId() {
		Long id = 1L;
		tester.setId(id);
		assertEquals(id, tester.getId());
	}

	@Test
	public void testState() {
		State state = State.OCCUPIED;
		tester.setState(state);
		assertEquals(state, tester.getState());
	}

	@Test(expected = IOException.class)
	public void testOnBinaryMessageByteBuffer() throws IOException {
		ByteBuffer buffer = mock(ByteBuffer.class);
		tester.onBinaryMessage(buffer);
	}

	@Test
	public void testConnectClient() {
		// prepara i dati per il test
		Long id = 1L;
		// crea un finto messaggio
		CharBuffer message = mock(CharBuffer.class);
		when(message.toString()).thenReturn(
				"[\"1\", \"" + id.toString() + "\"]");

		try {
			// invoca il metodo da testare
			tester.onTextMessage(message);

			// verifica che il client sia stato inserito correttamente
			Map<Long, PushInbound> clients = ChannelServlet.getClients();
			assertFalse(clients.isEmpty());
			assertEquals(1, clients.size());
		} catch (Throwable error) {
			fail(error.getMessage());
		} finally {
			// azzera nuovamente l'array dei clients
			ChannelServlet.setClients(new HashMap<Long, PushInbound>());
		}
	}

	@Test
	public void testDisconnectClient() {
		// crea i dati per il test
		Map<Long, PushInbound> clients = ChannelServlet.getClients();
		Long id = 1L;
		PushInbound channel = mock(PushInbound.class);
		clients.put(id, channel);

		// crea un finto messaggio
		CharBuffer message = mock(CharBuffer.class);
		when(message.toString()).thenReturn(
				"[\"4\", \"" + id.toString() + "\"]");

		try {
			// invoca il metodo da testare
			tester.onTextMessage(message);

			// verifica la rimozione effettiva del canale
			assertFalse(clients.containsKey(id));
			assertTrue(clients.isEmpty());
		} catch (Throwable error) {
			fail(error.getMessage());
		} finally {
			// azzera nuovamente l'array dei clients
			ChannelServlet.setClients(new HashMap<Long, PushInbound>());
		}
	}
}
