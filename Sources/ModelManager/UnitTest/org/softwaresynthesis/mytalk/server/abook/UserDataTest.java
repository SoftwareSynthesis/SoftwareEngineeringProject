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
@RunWith(MockitoJUnitRunner.class)
public class UserDataTest {
	private final Long id = 1L;
	private final String email = "indrizzo5@dominio.it";
	private final String password = "password";
	private final String question = "ThisIsNotAQuestion";
	private final String answer = "ThisIsNotAnAnswer";
	private final String name = "paperino";
	private final String surname = "de paperoni";
	private final String path = "ThisIsNotAPath";
	@Mock
	private Message newMessage;
	@Mock
	private AddressBookEntry newEntry;
	@Mock
	private CallList newCall;
	private IUserData tester;

	/**
	 * Inizializza l'oggetto da testare
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Before
	public void setUp() {
		tester = new UserData();
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
		tester.setId(id);
		Long result = tester.getId();
		assertNotNull(result);
		assertEquals(id, result);
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
		tester.setMail(email);
		String result = tester.getMail();
		assertNotNull(result);
		assertEquals(result, email);
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
		tester.setPassword(password);
		String result = tester.getPassword();
		assertNotNull(result);
		assertEquals(password, result);
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
		tester.setQuestion(question);
		String result = tester.getQuestion();
		assertNotNull(result);
		assertEquals(question, result);
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
		tester.setAnswer(answer);
		String result = tester.getAnswer();
		assertNotNull(result);
		assertEquals(answer, result);
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
		tester.setName(name);
		String result = tester.getName();
		assertNotNull(result);
		assertEquals(name, result);
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
		tester.setSurname(surname);
		String result = tester.getSurname();
		assertNotNull(result);
		assertEquals(surname, result);
	}

	/**
	 * Esegue un test sui metodi set/get dell'immagine profilo
	 * 
	 * @author Andrea Meneghinello
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testPicturePath() {
		tester.setPath(path);
		String result = tester.getPath();
		assertNotNull(result);
		assertEquals(path, result);
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
		tester.setCalls(calls);
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
		tester.setMessages(messages);
		Set<IMessage> result = tester.getMessages();
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
	 * Testa l'aggiunta di una nuova chiamata nello storico delle chiamate di un
	 * determinato utente
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testAddCall() {
		Set<ICallList> calls = tester.getCalls();
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
		Set<IMessage> messages = tester.getMessages();
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
	 * Testa la rimozione di una chiamata dallo storico delle chiamate di un
	 * determinato utente
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testRemoveCall() {
		Set<ICallList> calls = tester.getCalls();
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
		Set<IMessage> messages = tester.getMessages();
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
		tester.setMail(email);
		IUserData user = mock(UserData.class);
		when(user.getMail()).thenReturn(email);
		assertTrue(tester.equals(user));
		when(user.getMail()).thenReturn("indirizzo2@dominio.it");
		assertFalse(tester.equals(user));
	}
}