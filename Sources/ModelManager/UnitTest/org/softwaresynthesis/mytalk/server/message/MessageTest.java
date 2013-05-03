package org.softwaresynthesis.mytalk.server.message;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;
import org.softwaresynthesis.mytalk.server.abook.IUserData;
import org.softwaresynthesis.mytalk.server.abook.UserData;

/**
 * Test della classe Message
 * 
 * @author Diego Beraldin
 * @version 2.0
 */
public class MessageTest {
	private static Message tester;
	private static Date dummyDate;
	private static boolean isVideo;
	private static boolean isNew;
	private static IUserData sender;
	private static IUserData receiver;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// inizializza l'oggetto da testare
		tester = new Message(1L);
		// inizializza i dati necessari al test
		dummyDate = mock(Date.class);
		isVideo = false;
		isNew = false;
		receiver = mock(UserData.class);
		when(receiver.getMail()).thenReturn("indirizzo5@dominio.it");
		sender = mock(UserData.class);
		when(sender.getMail()).thenReturn("indirizzo5@dominio.it");
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
		tester.setNewer(isNew);
		boolean result = tester.getNewer();
		assertNotNull(result);
		assertEquals(isNew, result);
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
		tester.setVideo(isVideo);
		boolean result = tester.getVideo();
		assertNotNull(result);
		assertEquals(isVideo, result);
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
	
	@Test
	public void testEquals() {
		Message other = mock(Message.class);
		when(other.getSender()).thenReturn(sender);
		when(other.getReceiver()).thenReturn(receiver);
		when(other.getDate()).thenReturn(dummyDate);
		assertTrue(tester.equals(other));
	}
}
