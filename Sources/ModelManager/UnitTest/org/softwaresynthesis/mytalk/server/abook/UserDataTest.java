package org.softwaresynthesis.mytalk.server.abook;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test dei metodi della classe {@link AddressBookEntry}
 * 
 * @author Andrea Meneghinello
 * @version %I%, %G%
 */
public class UserDataTest {
	private static IUserData tester;

	/**
	 * Preparazione dell'oggetto {@link UserData} prima dell'esecuzione dei test
	 * 
	 * @author Andrea Meneghinello
	 * @version %I%, %G%
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
		String toCompare = "{\"name\":\"name\", \"surname\":\"surname\", \"email\":\"indirizzo1@dominio.it\", \"id\":\"1\", \"picturePath\":\"path\"}";
		String userJSON = tester.toJson();
		assertNotNull(userJSON);
		assertEquals(userJSON, toCompare);
	}

	/**
	 * Esegue un test sul metodo get dell'ID
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
	 * Esegue un test sui metodi set/get dell'e-mail
	 * 
	 * @author Andrea Meneghinello
	 * @version %I%, %G%
	 */
	@Test
	public void testEmail() {
		 String mail = tester.getMail();
		 assertNotNull(mail);
		 assertEquals(mail, "indirizzo1@dominio.it");
	}

	/**
	 * Esegue un test sui metodi set/get della password
	 * 
	 * @author Andrea Meneghinello
	 * @version %I%, %G%
	 */
	@Test
	public void testPassword() {
		String password = tester.getPassword();
		assertNotNull(password);
		assertEquals(password, "password");
	}

	/**
	 * Esegue un test sui metodi set/get della domanda segreta
	 * 
	 * @author Andrea Meneghinello
	 * @version %I%, %G%
	 */
	@Test
	public void testQuestion() {
		String question = tester.getQuestion();
		assertNotNull(question);
		assertEquals(question, "question");
	}

	/**
	 * Esegue un test sui metodi set/get della risposta alla domanda segreta
	 * 
	 * @author Andrea Meneghinello
	 * @version %I%, %G%
	 */
	@Test
	public void testAnswer() {
		String answer = tester.getAnswer();
		assertNotNull(answer);
		assertEquals(answer, "answer");
	}

	/**
	 * Esegue un test sui metodi set/get del nome utente
	 * 
	 * @author Andrea Meneghinello
	 * @version %I%, %G%
	 */
	@Test
	public void testName() {
		String name = tester.getName();
		assertNotNull(name);
		assertEquals(name, "name");
	}

	/**
	 * Esegue un test sui metodi set/get del cognome
	 * 
	 * @author Andrea Meneghinello
	 * @version %I%, %G%
	 */
	@Test
	public void testSurname() {
		String surname = tester.getSurname();
		assertNotNull(surname);
		assertEquals(surname, "surname");
	}

	/**
	 * Esegue un test sui metodi set/get dell'immagine profilo
	 * 
	 * @author Andrea Meneghinello
	 * @version %I%, %G%
	 */
	@Test
	public void testPicturePath() {
		 String path = tester.getPath();
		 assertNotNull(path);
		 assertEquals(path, "path");
	}

	/**
	 * Testa il metodo equals
	 * 
	 * @author Andrea Meneghinello
	 * @version %I%, %G%
	 */
	public void testEquals() {
		 boolean result = false;
		 IUserData user = new UserData();
		 user.setMail("indirizzo1@dominio.it");
		 result = tester.equals(user);
		 assertTrue(result);
		 user.setMail("indirizzo2@dominio.it");
		 result = tester.equals(user);
		 assertFalse(result);
	}
}