package org.softwaresynthesis.mytalk.server.call;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.softwaresynthesis.mytalk.server.abook.IUserData;

/**
 * Test della classe CallList, che mappa la relazione molti-a-molti tra gli
 * utenti e le chiamate ad essi relative nello storico delle chiamate
 * 
 * @author Diego Beraldin
 * @version 2.0
 */
@RunWith(MockitoJUnitRunner.class)
public class CallListTest {
	// dati di test
	@Mock
	private IUserData user;
	@Mock
	private IUserData someoneElse;
	@Mock
	private ICall call;
	@Mock
	private ICall someOtherCall;
	@Mock
	private CallList other;

	// oggetto da testare
	private CallList tester;

	/**
	 * Configura il comportamento dei mock prima di ogni test e inizializza
	 * l'oggetto da sottoporre a verifica
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Before
	public void setUp() {
		when(user.getMail()).thenReturn("indirizzo5@dominio.it");
		when(call.getId()).thenReturn(1L);
		when(other.getCall()).thenReturn(call);
		when(other.getUser()).thenReturn(user);
		tester = new CallList(1L);
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

	/**
	 * Verifica il corretto comportamento del metodo equal che si basa sulla
	 * chiamata e sull'utente coivolto in essa che caratterizzano ogni voce
	 * della tabella CallLists del sistema di persistenza
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testEquals() {
		tester.setCall(call);
		tester.setUser(user);
		assertTrue(tester.equals(other));
		when(other.getUser()).thenReturn(someoneElse);
		assertFalse(tester.equals(other));
		when(other.getUser()).thenReturn(user);
		when(other.getCall()).thenReturn(someOtherCall);
		assertFalse(tester.equals(other));
	}
}
