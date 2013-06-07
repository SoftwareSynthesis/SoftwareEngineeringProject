package org.softwaresynthesis.mytalk.server.dao.util;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.softwaresynthesis.mytalk.server.dao.ISessionManager;

/**
 * Verifica della classe {@link UtilFactory} che permette di assicurare che
 * vengano restituite istanze della classe di utilità valide e del tipo corretto
 * per le operazioni che devono essere eseguite successivamente per la gestione
 * della persistenza dei dati.
 * 
 * @author Diego Beraldin
 * @version 2.0
 */
@RunWith(MockitoJUnitRunner.class)
public class UtilFactoryTest {
	@Mock
	private ISessionManager session;
	private  UtilFactory tester;
	
	/**
	 * Reinizializza l'oggetto da testare
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Before
	public void setUp() {
		tester = new UtilFactory();
	}

	/**
	 * Verifica che sia possibile ottenere un'istanza valida di DeleteUtil a
	 * partire dalla classe factory, e che il SessionManager sia utilizzato nel
	 * modo corretto per la costruzione dell'oggetto da restituire.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testGetDeleteUtil() {
		ModifyUtil util = tester.getDeleteUtil(session);
		verify(session, never()).getSessionFactory();
		assertNotNull(util);
		assertTrue(util instanceof DeleteUtil);
	}

	/**
	 * Verifica che sia possibile ottenere un'istanza valida di InsertUtil a
	 * partire dalla classe factory, e che il SessionManager sia utilizzato nel
	 * modo corretto per la costruzione dell'oggetto da restituire.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testGetInsertUtil() {
		ModifyUtil util = tester.getInsertUtil(session);
		verify(session, never()).getSessionFactory();
		assertNotNull(util);
		assertTrue(util instanceof InsertUtil);
	}

	/**
	 * Verifica che sia possibile ottenere un'istanza valida di UpdateUtil a
	 * partire dalla classe factory, e che il SessionManager sia utilizzato nel
	 * modo corretto per la costruzione dell'oggetto da restituire.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testGetUpdateUtil() {
		ModifyUtil util = tester.getUpdateUtil(session);
		verify(session, never()).getSessionFactory();
		assertNotNull(util);
		assertTrue(util instanceof UpdateUtil);
	}

	/**
	 * Verifica che sia possibile ottenere un'istanza valida di GetCallUtil a
	 * partire dalla classe factory, e che il SessionManager sia utilizzato nel
	 * modo corretto per la costruzione dell'oggetto da restituire.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testGetCallUtil() {
		GetUtil util = tester.getCallUtil(session);
		verify(session, never()).getSessionFactory();
		assertNotNull(util);
		assertTrue(util instanceof GetCallUtil);
	}

	/**
	 * Verifica che sia possibile ottenere un'istanza valida di GetGroupUtil a
	 * partire dalla classe factory, e che il SessionManager sia utilizzato nel
	 * modo corretto per la costruzione dell'oggetto da restituire.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testGetGroupUtil() {
		GetUtil util = tester.getGroupUtil(session);
		verify(session, never()).getSessionFactory();
		assertNotNull(util);
		assertTrue(util instanceof GetGroupUtil);
	}

	/**
	 * Verifica che sia possibile ottenere un'istanza valida di GetUserDataUtil
	 * a partire dalla classe factory, e che il SessionManager sia utilizzato
	 * nel modo corretto per la costruzione dell'oggetto da restituire.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testGetUserDataUtil() {
		GetUtil util = tester.getUserDataUtil(session);
		verify(session, never()).getSessionFactory();
		assertNotNull(util);
		assertTrue(util instanceof GetUserDataUtil);

	}

	/**
	 * Verifica che sia possibile ottenere un'istanza valida di NotInitialize a
	 * partire dalla classe factory, e che il SessionManager sia utilizzato nel
	 * modo corretto per la costruzione dell'oggetto da restituire.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testGetGenericUtil() {
		GetUtil util = tester.getGenericUtil(session);
		verify(session, never()).getSessionFactory();
		assertNotNull(util);
		assertTrue(util instanceof NotInitialize);
	}
}
