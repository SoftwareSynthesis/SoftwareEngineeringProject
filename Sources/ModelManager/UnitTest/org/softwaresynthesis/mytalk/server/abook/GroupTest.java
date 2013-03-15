package org.softwaresynthesis.mytalk.server.abook;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test dei metodi della classe {@link Group}
 * 
 * @author 	Andrea Meneghinello
 * @version	%I%, %G%
 */
public class GroupTest 
{
	private static Group tester;
	
	/**
	 * Preparazione dell'oggetto {@link UserData}
	 * prima dell'esecuzione dei test
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 */
	@BeforeClass
	public static void setupBeforeClass()
	{
		tester = new Group(1L);
		tester.setName("name");
	}
	
	/**
	 * Testa la corretta conversione di
	 * una istanza di {@link AddressBookEntry}
	 * in formato JSON
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 */
	@Test
	public static void testToJson()
	{
		String toCompare = "{\"id\": \"1\", \"name\": \"name\"}";
		String groupJSON = tester.toJson();
		assertNotNull(groupJSON);
		assertEquals(groupJSON, toCompare);
	}
	
	/**
	 * Testa il metodo get dell'identificatore
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 */
	@Test
	public static void testId()
	{
		Long id = tester.getId();
		assertNotNull(id);
		assertEquals(id, (Object)1L);
	}
	
	/**
	 * Testa i metodi set/get del nome
	 * del gruppo
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 */
	@Test
	public static void testName()
	{
		String name = tester.getName();
		assertNotNull(name);
		assertEquals(name, "name");
	}
	
	/**
	 * Testa il metodo equals
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 */
	@Test
	public static void testEquals()
	{
		boolean result = false;
		Group group1 = new Group(1L);
		Group group2 = new Group(2L);
		result = tester.equals(group1);
		assertTrue(result);
		result = tester.equals(group2);
		assertFalse(result);
	}
}