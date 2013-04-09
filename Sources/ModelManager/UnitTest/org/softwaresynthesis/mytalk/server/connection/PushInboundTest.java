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
	public void testIdentifyClient() {
		Long id = 1L;
		CharBuffer message = mock(CharBuffer.class);
		// per verificare identificazione
		when(message.toString()).thenReturn(
				"[\"1\", \"" + id.toString() + "\"]");

		try {
			tester.onTextMessage(message);
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

}
