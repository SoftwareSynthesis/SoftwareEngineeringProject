package org.softwaresynthesis.mytalk.server.abook;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;
import org.softwaresynthesis.mytalk.server.call.CallList;
import org.softwaresynthesis.mytalk.server.call.ICallList;
import org.softwaresynthesis.mytalk.server.message.IMessage;
import org.softwaresynthesis.mytalk.server.message.Message;

/**
 * Test dei metodi della classe {@link AddressBookEntry}
 * 
 * @author Andrea Meneghinello
 * @author Diego Beraldin
 * @version 2.0
 */
public class UserDataTest {
	private static IUserData tester;
	private static IMessage newMessage;
	private static IAddressBookEntry newEntry;
	private static ICallList newCall;

	/**
	 * Preparazione dell'oggetto {@link UserData} prima dell'esecuzione dei test
	 * 
	 * @author Andrea Meneghinello
	 * @version 1.0
	 */
	@BeforeClass
	public static void setupBeforeClass() {
		tester = new UserData(1L);
		tester.setMail("indirizzo1@dominio.it");
		tester.setPassword("password");
		tester.setQuestion("question");
		tester.setAnswer("answer");
		tester.setName("name");
		tester.setSurname("surname");
		tester.setPath("path");

		newMessage = mock(Message.class);
		newEntry = mock(AddressBookEntry.class);
		newCall = mock(CallList.class);
	}

	/**
	 * Esegue un test sul metodo get dell'ID
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
	 * Esegue un test sui metodi set/get dell'e-mail
	 * 
	 * @author Andrea Meneghinello
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testEmail() {
		String mail = tester.getMail();
		assertNotNull(mail);
		assertEquals("indirizzo1@dominio.it", mail);
	}

	/**
	 * Esegue un test sui metodi set/get della password
	 * 
	 * @author Andrea Meneghinello
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testPassword() {
		String password = tester.getPassword();
		assertNotNull(password);
		assertEquals("password", password);
	}

	/**
	 * Esegue un test sui metodi set/get della domanda segreta
	 * 
	 * @author Andrea Meneghinello
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testQuestion() {
		String question = tester.getQuestion();
		assertNotNull(question);
		assertEquals("question", question);
	}

	/**
	 * Esegue un test sui metodi set/get della risposta alla domanda segreta
	 * 
	 * @author Andrea Meneghinello
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testAnswer() {
		String answer = tester.getAnswer();
		assertNotNull(answer);
		assertEquals("answer", answer);
	}

	/**
	 * Esegue un test sui metodi set/get del nome utente
	 * 
	 * @author Andrea Meneghinello
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testName() {
		String name = tester.getName();
		assertNotNull(name);
		assertEquals("name", name);
	}

	/**
	 * Esegue un test sui metodi set/get del cognome
	 * 
	 * @author Andrea Meneghinello
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testSurname() {
		String surname = tester.getSurname();
		assertNotNull(surname);
		assertEquals("surname", surname);
	}

	/**
	 * Esegue un test sui metodi set/get dell'immagine profilo
	 * 
	 * @author Andrea Meneghinello
	 * @version 2.0
	 */
	@Test
	public void testPicturePath() {
		String path = tester.getPath();
		assertNotNull(path);
		assertEquals("path", path);
	}

	/**
	 * Testa i metodi get/set per impostare la rubrica
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testAddressBook() {
		Set<IAddressBookEntry> entries = new HashSet<IAddressBookEntry>();
		tester.setAddressBook(entries);
		Set<IAddressBookEntry> result = tester.getAddressBook();
		assertNotNull(result);
		assertEquals(entries, result);
	}

	/**
	 * Testa i metodi get/set per impostare la lista delle chiamate
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testCallList() {
		Set<ICallList> calls = new HashSet<ICallList>();
		tester.setCallList(calls);
		Set<IAddressBookEntry> result = tester.getAddressBook();
		assertNotNull(result);
		assertEquals(calls, result);
	}

	/**
	 * Testa i metodi get/set per impostare la rubrica
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testMessage() {
		Set<IMessage> messages = new HashSet<IMessage>();
		tester.setMessage(messages);
		Set<IMessage> result = tester.getMessage();
		assertNotNull(result);
		assertEquals(messages, result);
	}

	/**
	 * Testa l'aggiunta di un nuovo contatto alla rubrica di un utente
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testAddAddressBookEntry() {
		Set<IAddressBookEntry> entries = tester.getAddressBook();
		tester.addAddressBookEntry(newEntry);
		assertTrue(entries.contains(newEntry));
	}

	/**
	 * FIXME Chiarire lo scopo di questo test!
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testAddCall() {
		Set<ICallList> calls = tester.getCallList();
		tester.addCall(newCall);
		assertTrue(calls.contains(newCall));
	}

	/**
	 * Testa l'aggiunta di un nuovo messaggio alla segreteria di un utente
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testAddMessage() {
		Set<IMessage> messages = tester.getMessage();
		tester.addMessage(newMessage);
		assertTrue(messages.contains(newMessage));
	}

	/**
	 * Testa la rimozione di un nuovo contatto alla rubrica di un utente
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testRemoveAddressBookEntry() {
		Set<IAddressBookEntry> entries = tester.getAddressBook();
		tester.removeAddressBookEntry(newEntry);
		assertFalse(entries.contains(newEntry));
	}

	/**
	 * FIXME Chiarire lo scopo di questo test!
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testRemoveCall() {
		Set<ICallList> calls = tester.getCallList();
		tester.removeCall(newCall);
		assertFalse(calls.contains(newCall));
	}

	/**
	 * Testa la rimozione di un nuovo messaggio alla segreteria di un utente
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testRemoveMessage() {
		Set<IMessage> messages = tester.getMessage();
		tester.removeMessage(newMessage);
		assertFalse(messages.contains(newMessage));
	}

	/**
	 * Testa il metodo equals della classe (basato sull'email)
	 * 
	 * @author Andrea Meneghinello
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testEquals() {
		IUserData user = mock(UserData.class);
		when(user.getMail()).thenReturn("indirizzo1@dominio.it");
		assertTrue(tester.equals(user));
		when(user.getMail()).thenReturn("indirizzo2@dominio.it");
		assertFalse(tester.equals(user));
	}
}