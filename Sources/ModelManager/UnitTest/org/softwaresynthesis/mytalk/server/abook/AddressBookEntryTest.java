package org.softwaresynthesis.mytalk.server.abook;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test dei metodi della classe {@link AddressBookEntry}
 * 
 * @author Andrea Meneghinello
 * @author Diego Beraldin
 * @version 2.0
 */
public class AddressBookEntryTest {
	// oggetto da testare
	private static AddressBookEntry tester;
	// dati di test
	private static IUserData contact;
	private static IGroup group;

	/**
	 * Crea l'oggetto da testare
	 * 
	 * @author Andrea Meneghinello
	 * @version %I%, %G%
	 */
	@BeforeClass
	public static void setupBeforeClass() {
		tester = new AddressBookEntry(1L);
		contact = new UserData(1L);
		contact.setMail("paperino@paperopoli.it");
		group = mock(IGroup.class);
		when(group.getName()).thenReturn("dummy");
	}

	/**
	 * Testa la restituzione dell'id e la sua impostazione 
	 * 
	 * @author Diego Beraldin
	 */
	@Test
	public void testGetId() {
		Long id = 1L;
		Long result = tester.getId();
		assertNotNull(result);
		assertEquals(result, id);
	}

	/**
	 * Verifica la corretta impostazione e recupero del contatto contenuto nella
	 * voce della rubrica
	 * 
	 * @author Andrea Meneghinello
	 * @version %I%, %G%
	 */
	@Test
	public void testContact() {
		tester.setContact(contact);
		IUserData result = tester.getContact();
		assertNotNull(result);
		assertEquals(contact, result);
	}

	/**
	 * Verifica la corretta impostazione e il recupero del gruppo cui appartiene
	 * la voce della rubrica
	 * 
	 * @author Diego Beraldin
	 */
	@Test
	public void testGroup() {
		tester.setGroup(group);
		IGroup result = tester.getGroup();
		assertNotNull(result);
		assertEquals(group, result);
	}

	/**
	 * Verifica la corretta impostazione e il recupero del proprietario della
	 * voce della rubrica
	 * 
	 * @author Diego Beraldin
	 */
	@Test
	public void testOwner() {
		tester.setOwner(contact);
		IUserData result = tester.getOwner();
		assertNotNull(result);
		assertEquals(result, contact);
	}

	/**
	 * Verifica che il contatto sia correttamente bloccato nella rubrica
	 * 
	 * @author Diego Beraldin
	 */
	@Test
	public void testBlocked() {
		tester.setBlocked(true);
		assertTrue(tester.getBlocked());
		tester.setBlocked(false);
		assertFalse(tester.getBlocked());
	}

	/**
	 * Test del metodo equals di AddressBookEntry
	 * 
	 * @author Diego Beraldin
	 */
	@Test
	public void testEquals() {
		IAddressBookEntry other = new AddressBookEntry(1L);
		other.setContact(contact);
		other.setOwner(contact);
		assertEquals(other, tester);
	}
}