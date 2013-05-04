package org.softwaresynthesis.mytalk.server.call;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test della classe Call che modella le chiamate fra gli utenti
 * 
 * @author Diego Beraldin
 * @version 2.0
 */
public class CallTest {
	// oggetto da testare
	private static Call tester;
	// altri dati necessari al test
	private static Date startDate;
	private static Date endDate;
	private static ICallList call;

	/**
	 * Inizializza i dati necessari ai test
	 */
	@BeforeClass
	public static void setUpBeforeClass() {
		tester = new Call(1L);
		startDate = mock(Date.class);
		endDate = mock(Date.class);
		call = mock(ICallList.class);
	}

	/**
	 * Verifica il corretto recupero del numero identificativo delle chiamate
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
	 * Verifica la corretta impostazione e il recupero della data di inizio
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testStartDate() {
		tester.setStartDate(startDate);
		Date result = tester.getStartDate();
		assertNotNull(result);
		assertEquals(startDate, result);
	}

	/**
	 * Verifica la corretta impostazione e il recupero della data di fine
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testEndDate() {
		tester.setEndDate(endDate);
		Date result = tester.getEndDate();
		assertNotNull(result);
		assertEquals(endDate, result);
	}

	/**
	 * Verifica la corretta impostazione della collezione di CallList che
	 * coinvolgono una determinata chiamata
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testCallList() {
		Set<ICallList> calls = new HashSet<ICallList>();
		tester.setCallList(calls);
		Set<ICallList> result = tester.getCallList();
		assertNotNull(result);
		assertEquals(calls, result);
	}

	/**
	 * Verifica l'aggiunta di una CallList alla collezione di voci che sono
	 * associate a una determinata chiamata
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testAddCall() {
		Set<ICallList> calls = tester.getCallList();
		tester.addCall(call);
		assertTrue(calls.contains(call));
	}

	/**
	 * Verifica la corretta rimozione di una CallList alla collezione di voci
	 * che sono associate a una determinata chiamata
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testRemoveCall() {
		Set<ICallList> calls = tester.getCallList();
		tester.removeCall(call);
		assertFalse(calls.contains(call));
	}

	/**
	 * Verifica che il metodo equals della classe Call (che considera l'id delle
	 * chiamate) si comporti nel modo atteso
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testEquals() {
		ICall other = mock(Call.class);
		when(other.getId()).thenReturn(1L);
		assertTrue(tester.equals(other));
		when(other.getId()).thenReturn(-1L);
		assertFalse(tester.equals(other));
	}
}
