package org.softwaresynthesis.mytalk.server.abook;

import org.junit.Test;

/**
 * Test dei metodi della classe {@link AddressBookEntry}
 * 
 * @author 	Andrea Meneghinello
 * @version	%I%, %G%
 */
public class AddressBookEntryTest 
{
	private static AddressBookEntry tester;
	
	/**
	 * Prepara i campi membro all'esecuzione
	 * dei test
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 */
	public static void setupBeforeClass()
	{
		tester = new AddressBookEntry(1L);
		tester.setBlocked(false);
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
		//TODO
	}
}