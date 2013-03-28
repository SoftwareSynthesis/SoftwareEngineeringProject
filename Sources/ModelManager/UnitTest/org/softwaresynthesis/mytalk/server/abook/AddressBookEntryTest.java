package org.softwaresynthesis.mytalk.server.abook;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test dei metodi della classe {@link AddressBookEntry}
 * 
 * @author Andrea Meneghinello
 * @version %I%, %G%
 */
public class AddressBookEntryTest {
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
		group = new Group(1L);
	}

	/**
	 * Testa la restituzione dell'id
	 */
	@Test
	public void testId() {
		Long id = 1L;
		tester.setId(id);
		Long result = tester.getId();
		assertNotNull(result);
		assertEquals(result, (Object) id);
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
		assertTrue(contact.equals(result));
	}

	/**
	 * Verifica la corretta impostazione e il recupero del gruppo cui appartiene
	 * la voce della rubrica
	 */
	@Test
	public void testGroup() {
		tester.setGroup(group);
		IGroup result = tester.getGroup();
		assertNotNull(result);
		assertTrue(result.equals(group));
	}

	/**
	 * Verifica la corretta impostazione e il recupero del proprietario della
	 * voce della rubrica
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
	 */
	@Test
	public void testBlocked() {
		tester.setBlocked(true);
		assertTrue(tester.getBlocked());
		tester.setBlocked(false);
		assertFalse(tester.getBlocked());
	}

	/**
	 * Test del metodo equals
	 */
	@Test
	public void testEquals() {
		IAddressBookEntry other = new AddressBookEntry(1L);
		other.setContact(contact);
		other.setOwner(contact);
		assertEquals(other, tester);
	}

	/**
	 * Test del metodo toString()
	 */
	@Test
	public void testToString() {
		String string = String.format(
				"AddressBookEntry[contact: %s, owner: %s, group: %s]", contact,
				contact, group);
		assertEquals(string, tester.toString());
	}

	/**
	 * 
	 */
	@Test
	public void testToJson() {
		tester.setBlocked(false);
		String string = String
				.format("{\"id\":\"%d\", \"contact\":\"%s\", \"group\":\"%s\", \"blocked\":\"%s\"}",
						contact.getId(), contact.toJson(), group.toJson(), false);
		assertTrue(string.equals(tester.toJson()));
	}
}