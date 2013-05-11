package org.softwaresynthesis.mytalk.server.abook;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Test dei metodi della classe {@link AddressBookEntry}
 * 
 * @author Andrea Meneghinello
 * @author Diego Beraldin
 * @version 2.0
 */
@RunWith(MockitoJUnitRunner.class)
public class AddressBookEntryTest {
	@Mock
	private UserData contact;
	@Mock
	private Group group;
	@Mock
	private AddressBookEntry other;
	private AddressBookEntry tester;

	/**
	 * Reinizializza i mock prima di ogni test
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Before
	public void setUp() {
		// configura il comportamento del contatto
		when(contact.getId()).thenReturn(1L);
		when(contact.getMail()).thenReturn("paperino@paperopoli.it");
		// configura il comportamento del gruppo
		when(group.getName()).thenReturn("dummy");
		when(other.getContact()).thenReturn(contact);
		when(other.getOwner()).thenReturn(contact);
		// inizializza l'oggetto da testare
		tester =  new AddressBookEntry(1L);
	}

	/**
	 * Testa la restituzione dell'id del contatto
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testId() {
		Long result = tester.getId();
		assertNotNull(result);
		assertEquals((Object) 1L, result);
	}

	/**
	 * Verifica la corretta impostazione e recupero del contatto contenuto nella
	 * voce della rubrica
	 * 
	 * @author Andrea Meneghinello
	 * @version 1.0
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
	 * @version 2.0
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
	 * @version 2.0
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
	 * @version 2.0
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
	 * @version 2.0
	 */
	@Test
	public void testEquals() {
		tester.setContact(contact);
		tester.setOwner(contact);
		assertTrue(tester.equals(other));
	}
}