package org.softwaresynthesis.mytalk.server.authentication;

import static org.junit.Assert.*;

import java.security.Principal;

import org.junit.BeforeClass;
import org.junit.Test;

public class PrincipalImplTest {
	
	private static Principal tester;

	@BeforeClass
	public static void setUpBeforeClass() {
		tester = new PrincipalImpl("pippo");
	}

	/**
	 * Testa che l'element sia recuperato correttamente
	 * 
	 * @author diego
	 */
	@Test
	public void testGetName() {
		assertTrue(tester.getName().equals("pippo"));
	}

	/**
	 * Testa l'impelementazione del metodo di uguaglianza
	 * 
	 * @author diego
	 */
	@Test
	public void testEquals() {
		Principal other = new PrincipalImpl("pippo");
		boolean result = tester.equals(other);
		assertTrue(result);
		other = new PrincipalImpl("pluto");
		result = tester.equals(other);
		assertFalse(result);
	}

	/**
	 * Testa la conversione in stringa della classe PrincipalImpl
	 * 
	 * @author diego
	 */
	@Test
	public void testToString() {
		String toCompare = "PrincipalImpl[element: pippo]";
		assertTrue(toCompare.equals(tester.toString()));
	}

}
