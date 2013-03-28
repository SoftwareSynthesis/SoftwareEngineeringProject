package org.softwaresynthesis.mytalk.server.abook;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test dei metodi della classe {@link Group}
 * 
 * @author Andrea Meneghinello
 * @version %I%, %G%
 */
public class GroupTest {
	private static Group tester;
	private static IUserData contact = new UserData(1L);
	private static IAddressBookEntry entry;

	/**
	 * Preparazione dell'oggetto {@link UserData} prima dell'esecuzione dei test
	 * 
	 * @author Andrea Meneghinello
	 * @version %I%, %G%
	 */
	@BeforeClass
	public static void setupBeforeClass() {
		tester = new Group();
		contact.setMail("paperino@paperopoli.it");
		entry = new AddressBookEntry(1L);
	}

	/**
	 * Testa il metodo get dell'identificatore
	 * 
	 * @author Andrea Meneghinello
	 * @version %I%, %G%
	 */
	@Test
	public void testId() {
		Long id = 1L;
		tester.setId(id);
		Long result = tester.getId();
		assertNotNull(result);
		assertEquals(result, id);
	}

	/**
	 * Testa i metodi set/get del nome del gruppo
	 * 
	 * @author Andrea Meneghinello
	 * @version %I%, %G%
	 */
	@Test
	public void testName() {
		String name = "nome";
		tester.setName(name);
		String result = tester.getName();
		assertNotNull(result);
		assertEquals(result, name);
	}

	/**
	 * Testa il metodo equals
	 * 
	 * @author Andrea Meneghinello
	 * @version %I%, %G%
	 */
	@Test
	public void testEquals() {
		boolean result = false;
		Group group1 = new Group(1L);
		Group group2 = new Group(2L);
		result = tester.equals(group1);
		assertTrue(result);
		result = tester.equals(group2);
		assertFalse(result);
	}

	/**
	 * Testa la corretta conversione di una istanza di {@link AddressBookEntry}
	 * in formato JSON
	 * 
	 * @author Andrea Meneghinello
	 * @version %I%, %G%
	 */
	@Test
	public void testToJson() {
		String name = "name";
		tester.setName(name);
		Long id = 1L;
		tester.setId(id);
		String toCompare = String.format("{\"id\":\"%d\", \"name\":\"%s\"}",
				id, name);
		String groupJSON = tester.toJson();
		assertNotNull(groupJSON);
		assertEquals(groupJSON, toCompare);
	}

	/**
	 * Verifica la corretta impostazione e recupero del proprietario del gruppo
	 */
	@Test
	public void testOwner() {
		tester.setOwner(contact);
		IUserData result = tester.getOwner();
		assertNotNull(result);
		assertEquals(contact, result);
	}

	/**
	 * Verifica la corretta impostazione e recupero della rubrica
	 */
	@Test
	public void testAddressBook() {
		Set<IAddressBookEntry> addressBook = new HashSet<IAddressBookEntry>();
		tester.setAddressBook(addressBook);
		Set<IAddressBookEntry> result = tester.getAddressBook();
		assertNotNull(result);
		assertEquals(addressBook, result);
	}

	/**
	 * Verifica l'inserimento di un nuovo contatto all'interno del gruppo
	 */
	@Test
	public void testAddAddressBookEntry() {
		tester.addAddressBookEntry(entry);
		Set<IAddressBookEntry> addressBook = tester.getAddressBook();
		assertTrue(addressBook.contains(entry));
	}
	
	@Test
	public void testRemoveAddressBookEntry() {
		tester.removeAddressBookEntry(entry);
		Set<IAddressBookEntry> addressBook = tester.getAddressBook();
		assertFalse(addressBook.contains(entry));
	}
}