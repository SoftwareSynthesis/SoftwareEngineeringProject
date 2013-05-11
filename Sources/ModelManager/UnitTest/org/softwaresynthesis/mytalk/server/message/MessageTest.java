package org.softwaresynthesis.mytalk.server.message;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.softwaresynthesis.mytalk.server.abook.IUserData;

/**
 * Test della classe Message
 * 
 * @author Diego Beraldin
 * @version 2.0
 */
@RunWith(MockitoJUnitRunner.class)
public class MessageTest {
	@Mock
	private Date dummyDate;
	@Mock
	private IUserData sender;
	@Mock
	private IUserData receiver;
	@Mock
	private Message other;
	private Message tester;

	/**
	 * Reimposta il comportamento dei mock e inizializza l'oggetto da testare
	 * prima di ogni test
	 */
	@Before
	public void setUp() {
		// configura i mock
		when(receiver.getMail()).thenReturn("indirizzo5@dominio.it");
		when(sender.getMail()).thenReturn("indirizzo5@dominio.it");
		when(other.getSender()).thenReturn(sender);
		when(other.getReceiver()).thenReturn(receiver);
		when(other.getDate()).thenReturn(dummyDate);
		// inizializza l'oggetto da testare
		tester = new Message(1L);
	}

	/**
	 * Testa il metodo get dell'identificatore
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testId() {
		Long result = tester.getId();
		assertNotNull(result);
		assertEquals((Object) 1L, result);
	}

	/**
	 * Verifica la corretta impostazione del mittente del messaggio
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testSender() {
		tester.setSender(sender);
		IUserData result = tester.getSender();
		assertNotNull(result);
		assertEquals(sender, result);
	}

	/**
	 * Verifica la corretta impostazione e il recupero del destinatario del
	 * messaggio della segreteria telefonica
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testReceiver() {
		tester.setReceiver(receiver);
		IUserData result = tester.getReceiver();
		assertNotNull(result);
		assertEquals(receiver, result);
	}

	/**
	 * Verifica la corretta impostazione e il recupero del flag booleano newer
	 * dei messaggi della segreteria telefonica
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testNewer() {
		tester.setNewer(true);
		boolean result = tester.getNewer();
		assertNotNull(result);
		assertTrue(result);
		tester.setNewer(false);
		result = tester.getNewer();
		assertNotNull(result);
		assertFalse(result);
	}

	/**
	 * Verifica la corretta impostazione e il recupero del flag booleano video
	 * dei messaggi della segreteria telefonica
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testVideo() {
		tester.setVideo(true);
		boolean result = tester.getVideo();
		assertNotNull(result);
		assertTrue(result);
		tester.setVideo(false);
		result = tester.getVideo();
		assertNotNull(result);
		assertFalse(result);
	}

	/**
	 * Verifica la corretta impostazione e il recupero della data di un
	 * messaggio nella segreteria telefonica
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testDate() {
		tester.setDate(dummyDate);
		Date result = tester.getDate();
		assertNotNull(result);
		assertEquals(dummyDate, result);
	}

	/**
	 * Verifica che il metodo equals basato sul mittente, il destinatario e la
	 * data di invio del messaggio sia implementato correttamente.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testEquals() {
		tester.setSender(sender);
		tester.setReceiver(receiver);
		tester.setDate(dummyDate);
		assertTrue(tester.equals(other));
	}
}
