package org.softwaresynthesis.mytalk.server.authentication;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.security.Principal;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test della classe PrincipalImpl che ha lo scopo di verificare il corretto
 * comportamento rispetto ai metodi dell'interfaccia Principal (recupero
 * dell'elemento identificativo) nonché la corretta implementazione del metodo
 * di confronto basato sugli identificativi univoci
 * 
 * @author Diego Beraldin
 * @version 2.0
 */
public class PrincipalImplTest {
	private static Principal tester;
	private static final String element = "pippo";

	/**
	 * Inizializza i dati di test e l'oggetto da sottoporrea verifica
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@BeforeClass
	public static void setUpBeforeClass() {
		tester = new PrincipalImpl(element);
	}

	/**
	 * Testa che l'elemento identificativo univoco che caratterizza il Principal
	 * sia recuperato correttamente a partire da una istanza di PrincipalImpl
	 * 
	 * @author Diego Beraldin
	 * @version 1.0
	 */
	@Test
	public void testGetName() {
		assertTrue(tester.getName().equals(element));
	}

	/**
	 * Testa l'implementazione del metodo di uguaglianza fra due PrincipalImpl,
	 * che è basata sul confronto fra gli elementi identificativi che li
	 * carettarizzano e che si suppongono essere univoci per ogni elemento del
	 * dominio dei Principal.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testEquals() {
		Principal other = mock(PrincipalImpl.class);
		when(other.getName()).thenReturn(element);
		boolean result = tester.equals(other);
		assertNotNull(result);
		assertTrue(result);
		when(other.getName()).thenReturn("dummy");
		result = tester.equals(other);
		assertNotNull(result);
		assertFalse(result);
	}
}
