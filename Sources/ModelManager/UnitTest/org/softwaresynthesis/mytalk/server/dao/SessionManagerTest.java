package org.softwaresynthesis.mytalk.server.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.hibernate.SessionFactory;
import org.junit.Test;

/**
 * Test (purtroppo) leggermente triviale che verifica {@link SessionManager}
 * 
 * @author Diego Beraldin
 * @version 2.0
 */
public class SessionManagerTest {
	// oggetto da testare
	private static final SessionManager tester = SessionManager.getInstance();

	/**
	 * Verifica che sia possibile ottenere una SessionFactory a partire dal
	 * SessionManager di invocazione e che questa sia univoca indipendentemente
	 * dal riferimento su cui è invocato getSessionFactory().
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testGetSessionFactory() {
		SessionFactory factory = tester.getSessionFactory();
		assertNotNull(factory);
		// FIXME qui mi piacerebbe fare di più ma il new Configuration() mi
		// mette un po' i bastoni fra le ruote
		SessionFactory otherFactory = SessionManager.getInstance()
				.getSessionFactory();
		assertTrue(factory == otherFactory);
	}

	/**
	 * Verifica che tutti i riferimenti ottenuti tramite getInstance() siano
	 * validi e puntino allo stesso oggetto nello heap.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testGetInstance() {
		ISessionManager other = SessionManager.getInstance();
		assertNotNull(other);
		assertTrue(tester == other);
	}
}
