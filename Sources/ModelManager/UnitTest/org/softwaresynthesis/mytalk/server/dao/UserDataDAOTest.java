package org.softwaresynthesis.mytalk.server.dao;

import static org.junit.Assert.*;
import org.softwaresynthesis.mytalk.server.abook.IUserData;
import org.softwaresynthesis.mytalk.server.abook.UserData;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.List;

public class UserDataDAOTest
{
	
	private static UserDataDAO tester;
	private static IUserData user;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		tester = new UserDataDAO();
		user = new UserData();
		user.setEmail("indirizzo5@dominio.it");
		user.setPassword("password");
		user.setName("Maria");
		user.setSurname("Goretti");
		user.setQuestion("Come mi chiamo");
		user.setAnswer("Maria");
	}
	
	@Test
	public void testInsert()
	{
		assertTrue(tester.insert(user));
	}
	
//	@Test
//	public void testGetByEmail()
//	{
//		IUserData retrieved = tester.getByEmail("indirizzo4@dominio.it");
//		String email = retrieved.getEmail();
//		assertEquals(email, "indirizzo4@dominio.it");
//	}
//	
//	@Test
//	public void testGetByNameAndSurname()
//	{
//		List<IUserData> list = tester.getByNameAndSurname("Piero", "Pelu");
//		assertNotNull(list);
//		assertFalse(list.isEmpty());
//		IUserData retrieved = list.get(0);
//		assertEquals(retrieved.getName(), "Piero");
//		assertEquals(retrieved.getSurname(), "Pelu");
//	}
//	
//	@Test
//	public void testUpdate()
//	{
//		user.setName("Fiorella");
//		user.setSurname("Mannoia");
//		assertTrue(tester.update(user));
//	}
//
//	@Test
//	public void testDelete()
//	{
//		assertTrue(tester.delete(user));
//	}
}
