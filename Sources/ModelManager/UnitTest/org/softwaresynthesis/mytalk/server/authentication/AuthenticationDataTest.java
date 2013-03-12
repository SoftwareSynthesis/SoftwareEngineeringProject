package org.softwaresynthesis.mytalk.server.authentication;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Test;

/**
 * Test sui metodi della classe {@link AuthenticationData}
 * 
 * @author 	Andrea Meneghinello
 * @version %I%, %G%
 */
public class AuthenticationDataTest
{
	/**
	 * Effettua il test di uguaglianza di
	 * due oggetti {@link AuthenticationData}
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 */
	@Test
	public static void equalsTest()
	{
		AuthenticationData data1 = new AuthenticationData("indirizzo1@dominio.it", "password");
		AuthenticationData data2 = new AuthenticationData("indirizzo1@dominio.it", "password");
		boolean result = data1.equals(data2);
		assertTrue(result);
	}
	
	/**
	 * Effettua il test di diseguaglianza di
	 * due oggetti {@link AuthenticationData}
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 */
	public static void differentTest()
	{
		AuthenticationData data1 = new AuthenticationData("indirizzo1@dominio.it", "password");
		AuthenticationData data2 = new AuthenticationData("indirizzo2@dominio.it", "password");
		boolean result = data1.equals(data2);
		assertFalse(result);
	}
}