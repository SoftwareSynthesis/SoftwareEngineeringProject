package org.softwaresynthesis.mytalk.server.abook;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Test dei metodi della classe {@link Group}
 * 
 * @author Andrea Meneghinello
 * @version 2.0
 */
@RunWith(MockitoJUnitRunner.class)
public class GroupTest {
	@Mock
	private IUserData contact;
	@Mock
	private IAddressBookEntry entry;
	private Group tester;

	/**
	 * Reimposta il comportamento dei mock e reinizializza l'oggetto da testare
	 * prima di ogni verifica di questo caso di test
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Before
	public void setUp() {
		// configura il comportamento dei mock
		when(contact.getId()).thenReturn(1L);
		when(contact.getMail()).thenReturn("paperino@paperopoli.it");
		when(entry.getId()).thenReturn(1L);
		// inizializza l'oggetto da testare
		tester = new Group(1L);
	}

	/**
	 * Testa il metodo get dell'identificatore
	 * 
	 * @author Andrea Meneghinello
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
	 * Testa i metodi set/get del nome del gruppo
	 * 
	 * @author Andrea Meneghinello
	 * @version 1.0
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
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testEquals() {
		tester.setName("dummy");
		IGroup other = mock(Group.class);
		when(other.getName()).thenReturn("dummy");
		assertTrue(tester.equals(other));
		when(other.getName()).thenReturn("pippo");
		assertFalse(tester.equals(other));
	}

	/**
	 * Verifica la corretta impostazione e il recupero del proprietario di un
	 * gruppo della rubricca
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testOwner() {
		IUserData owner = mock(IUserData.class);
		tester.setOwner(owner);
		IUserData result = tester.getOwner();
		assertNotNull(result);
		assertEquals(owner, result);
	}

	/**
	 * Verifica la corretta impostazione e recupero della rubrica
	 * 
	 * @author Diego Beraldin
	 * @version 1.0
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
	 * 
	 * @author Diego Beraldin
	 * @version 1.0
	 */
	@Test
	public void testAddAddressBookEntry() {
		tester.addAddressBookEntry(entry);
		Set<IAddressBookEntry> addressBook = tester.getAddressBook();
		assertTrue(addressBook.contains(entry));
	}

	/**
	 * Verifica la rimozione di un contatto dal gruppo
	 * 
	 * @author Diego Beraldin
	 * @version 1.0
	 */
	@Test
	public void testRemoveAddressBookEntry() {
		tester.removeAddressBookEntry(entry);
		Set<IAddressBookEntry> addressBook = tester.getAddressBook();
		assertFalse(addressBook.contains(entry));
	}
}