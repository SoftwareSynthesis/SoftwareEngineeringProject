package org.softwaresynthesis.mytalk.server.call;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Test della classe Call che modella le chiamate fra gli utenti
 * 
 * @author Diego Beraldin
 * @version 2.0
 */
@RunWith(MockitoJUnitRunner.class)
public class CallTest {
	@Mock
	private Date startDate;
	@Mock
	private Date endDate;
	@Mock
	private ICallList call;
	@Mock
	private Call other;
	private Call tester;

	/**
	 * Inizializza l'oggetto da sottoporre a verifica
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Before
	public void setUp() {
		// inizializza l'oggetto da testare
		tester = new Call(1L);
		// configura il mock
		when(other.getId()).thenReturn(1L);
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
		tester.setStart(startDate);
		Date result = tester.getStart();
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
		tester.setEnd(endDate);
		Date result = tester.getEnd();
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
		tester.setCalls(calls);
		Set<ICallList> result = tester.getCalls();
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
		Set<ICallList> calls = tester.getCalls();
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
		Set<ICallList> calls = tester.getCalls();
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
		assertTrue(tester.equals(other));
		when(other.getId()).thenReturn(-1L);
		assertFalse(tester.equals(other));
	}
}
