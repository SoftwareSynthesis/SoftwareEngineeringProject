package org.softwaresynthesis.mytalk.server.authentication;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test sui metodi della classe {@link AuthenticationData}
 * 
 * @author Andrea Meneghinello
 * @version %I%, %G%
 */
public class AuthenticationDataTest {

	private static AuthenticationData tester;

	@BeforeClass
	public static void setupBeforeClass() {
		tester = new AuthenticationData("indirizzo1@dominio.it", "password");
	}

	/**
	 * Effettua il test di uguaglianza di due oggetti {@link AuthenticationData}
	 * 
	 * @author Andrea Meneghinello
	 * @version %I%, %G%
	 */
	@Test
	public void equalsTest() {
		AuthenticationData equal = new AuthenticationData(
				"indirizzo1@dominio.it", "password");
		assertTrue(tester.equals(equal));
		AuthenticationData different = new AuthenticationData(
				"indirizzo2@dominio.it", "password");
		assertFalse(tester.equals(different));
	}
	
	/**
	 * Verifica il getter dello username
	 * 
	 * @author diego
	 */
	@Test
	public void testGetUsername() {
		assertTrue(tester.getUsername().equals("indirizzo1@dominio.it"));
	}
	
	/**
	 * Verifica il getter della password
	 * 
	 * @author diego
	 */
	@Test
	public void testGetPassword() {
		assertTrue(tester.getPassword().equals("password"));
	}

}