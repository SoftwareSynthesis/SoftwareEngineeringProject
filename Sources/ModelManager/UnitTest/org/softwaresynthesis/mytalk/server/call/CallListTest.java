package org.softwaresynthesis.mytalk.server.call;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.BeforeClass;
import org.junit.Test;
import org.softwaresynthesis.mytalk.server.abook.IUserData;

/**
 * Test della classe CallList, che mappa la relazione molti-a-molti tra gli
 * utenti e le chiamate ad essi relative nello storico delle chiamate
 * 
 * @author Diego Beraldin
 * @version 2.0
 */
public class CallListTest {
	// oggetto da testare
	private static CallList tester;
	// altri dati di test (stub)
	private static IUserData user;
	private static ICall call;

	/**
	 * Inizializza i dati necessari all'esecuzione dei test
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@BeforeClass
	public static void setUpBeforeClass() {
		tester = new CallList(1L);
		tester.setId(1L);
		user = mock(IUserData.class);
		when(user.getMail()).thenReturn("indirizzo5@dominio.it");
		call = mock(ICall.class);
		when(call.getId()).thenReturn(1L);
	}

	/**
	 * Verifica il corretto recupero del numero identificativo di una CallList
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
	 * Verifica la corretta impostazione e il recupero dell'utente cui è
	 * associata la voce nella tabella CallLists
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testUser() {
		tester.setUser(user);
		IUserData result = tester.getUser();
		assertNotNull(result);
		assertEquals(user, result);
	}

	/**
	 * Verifica la corretta impostazione e il recupro del riferimento alla
	 * chiamata cui è associata la voce nella tabella CallLists
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testCall() {
		tester.setCall(call);
		ICall result = tester.getCall();
		assertNotNull(result);
		assertEquals(call, result);
	}

	/**
	 * Verifica la corretta impostazione e il recupero del flag booleano in base
	 * al quale è possibile determinare se l'utente era stato il chiamante
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testCaller() {
		tester.setCaller(true);
		boolean result = tester.getCaller();
		assertNotNull(result);
		assertTrue(result);
		tester.setCaller(false);
		result = tester.getCaller();
		assertNotNull(result);
		assertFalse(result);
	}
}
