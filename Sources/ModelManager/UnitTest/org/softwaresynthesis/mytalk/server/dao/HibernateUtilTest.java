package org.softwaresynthesis.mytalk.server.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.hibernate.SessionFactory;
import org.junit.Test;

/**
 * Test dei metodi della classe {@link HiberanteUtil}
 * 
 * @author Andrea Meneghinello
 * @version 1.0
 */
public class HibernateUtilTest {
	/**
	 * Testa l'effettiva presenza di un unica istanza della classe
	 * 
	 * @author Andrea Meneghinello
	 */
	@Test
	public void testGetInstance() {
		HibernateUtil instance1 = HibernateUtil.getInstance();
		HibernateUtil instance2 = HibernateUtil.getInstance();
		assertNotNull(instance1);
		assertNotNull(instance2);
		assertEquals(instance1, instance2);
	}

	/**
	 * Testa la presenza di una sessionFactory configurata correttamente per la
	 * comunicazione con il database
	 * 
	 * @author Andrea Meneghinello
	 */
	@Test
	public void testGetFactory() {
		HibernateUtil instance = HibernateUtil.getInstance();
		SessionFactory factory = instance.getFactory();
		assertNotNull(factory);
	}
}