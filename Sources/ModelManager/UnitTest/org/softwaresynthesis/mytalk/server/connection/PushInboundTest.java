package org.softwaresynthesis.mytalk.server.connection;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.softwaresynthesis.mytalk.server.ControllerManagerTest.getClients;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.Map;

import org.apache.catalina.websocket.WsOutbound;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.softwaresynthesis.mytalk.server.connection.PushInbound.State;

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
	@Mock
	private ByteBuffer charBuffer;
	@Mock
	private CharBuffer buffer;
	@Mock
	private PushInbound other;
	@Mock
	private WsOutbound outbound;
	private StringWriter writer;

	/**
	 * Reinizializza tutti gli elementi comuni a tutti i test e l'oggetto da
	 * testare stesso, prima di tutte le verifiche qui contenute.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Before
	public void setUp() throws Exception {
		writer = new StringWriter();
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
		clients = getClients();
		tester = new PushInbound() {
			@Override
			WsOutbound getWsOutbound(PushInbound inbound) {
				return outbound;
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
	 * TODO da documentare
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
	 * TODO da documentare
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testState() {
		State state = State.OCCUPIED;
		tester.setState(state);
		assertEquals(state, tester.getState());
	}

	/**
	 * TODO da documentare
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test(expected = IOException.class)
	public void testOnBinaryMessageByteBuffer() throws IOException {
		tester.onBinaryMessage(charBuffer);
	}

	/**
	 * TODO da documentare
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
	 * TODO da documentare
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
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testSendIdToCallee() throws Exception {
		// prepara il messaggio e il finto 'peer' del client
		clients.put(otherId, other);
		when(buffer.toString()).thenReturn(
				String.format("[\"3\", \"%d\"]", id));
		// invoca il metodo da testare
		tester.setId(id);
		tester.onTextMessage(buffer);
		// verifica l'output
		writer.flush();
		String message = writer.toString();
		assertEquals("3|" + id, message);
	}
}
