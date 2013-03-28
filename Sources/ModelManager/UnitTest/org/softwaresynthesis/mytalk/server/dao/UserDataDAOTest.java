package org.softwaresynthesis.mytalk.server.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;
import org.softwaresynthesis.mytalk.server.abook.IUserData;
import org.softwaresynthesis.mytalk.server.abook.UserData;

/**
 * Test dei metodi della classe {@link UserDataDAO}
 * 
 * @author Andrea Meneghinello
 * @version 1.0
 */
public class UserDataDAOTest {
	private static IUserData user;
	private static UserDataDAO tester;

	/**
	 * Inizializza i campi dati per l'esecuzione dei test
	 */
	@BeforeClass
	public static void setupBeforeClass() {
		tester = new UserDataDAO();
		user = new UserData();
		user.setMail("indirizzo6@dominio.it");
		user.setPassword("password");
		user.setQuestion("come mi chiamo");
		user.setAnswer("maria");
		user.setName("maria");
		user.setSurname("goretti");
		user.setPath("Image/Img01.png");
	}

	/**
	 * Testa il metodo di inserimento di uno {@link IUserData} nel database del
	 * sistema mytalk
	 */
	@Test
	public void testInsert() {
		boolean result = tester.insert(user);
		assertTrue(result);
	}

	/**
	 * Testa il metodo di ricerca tramite di l'indirizzo e-mail di uno
	 * {@link IUserData}
	 */
	@Test
	public void testGetByEmail() {
		IUserData found = null;
		String mail = "indirizzo6@dominio.it";
		String foundMail = null;
		found = tester.getByEmail(mail);
		assertNotNull(found);
		foundMail = found.getMail();
		assertNotNull(foundMail);
		assertEquals(mail, foundMail);
	}

	/**
	 * Testa il metodo di aggiornamento di uno {@link IUserData} nel database
	 * del sistema mytalk
	 */
	@Test
	public void testUpdate() {
		boolean result = false;
		user.setName("fiorella");
		user.setSurname("mannoia");
		result = tester.update(user);
		assertTrue(result);
	}

	/**
	 * Testa il metodo di cancellazione di uno {@link IUserData} dal database
	 * del sistema mytalk
	 */
	@Test
	public void testDelete() {
		boolean result = tester.delete(user);
		assertTrue(result);
	}

	/**
	 * Testa il metodo per la ricerca generica di uno {@link IUserData} nel
	 * database del sistema mytalk
	 */
	@Test
	public void testSearchGeneric() {
		IUserData user = new UserData();
		String mail = "indirizzo2@dominio.it";
		user.setMail(mail);
		List<IUserData> founds = null;
		founds = tester.searchGeneric(mail);
		assertNotNull(founds);
		assertFalse(founds.size() == 0);
		assertTrue(founds.contains(user));
	}
}