package org.softwaresynthesis.mytalk.server.dao;

import org.softwaresynthesis.mytalk.server.abook.UserData;
import org.softwaresynthesis.mytalk.server.abook.Group;
import org.softwaresynthesis.mytalk.server.abook.IAddressBookEntry;
import org.softwaresynthesis.mytalk.server.abook.AddressBookEntry;
import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class AddressBookEntryDAOTest {
	private static AddressBookEntryDAO tester;
	private static IAddressBookEntry entry;

	@BeforeClass
	public static void setUpBeforeClass() {
		tester = new AddressBookEntryDAO();
		entry = new AddressBookEntry(1L);
		entry.setGroup(new Group(1L));
		entry.setContact(new UserData(1L));
		entry.setBlocked(false);
		entry.setOwner(new UserData(2L));
	}

	/**
	 * Verifica che avvenga correttamente l'inserimento di un nuovo contatto nel
	 * database dell'applicativo
	 * 
	 * @author diego
	 */
	@Test
	public void testInsert() {
		boolean result = tester.insert(entry);
		assertTrue(result);

	}

	@Test
	public void testUpdate() {
		entry.setBlocked(false);
		boolean result = tester.update(entry);
		assertTrue(result);
	}

	@Test
	public void testDelete() {
		boolean result = tester.delete(entry);
		assertTrue(result);
	}

}
