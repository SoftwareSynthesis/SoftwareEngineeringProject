package org.softwaresynthesis.mytalk.server.dao;

import java.util.List;

import org.softwaresynthesis.mytalk.server.abook.UserData;
import org.softwaresynthesis.mytalk.server.abook.IUserData;
import org.softwaresynthesis.mytalk.server.abook.IGroup;
import org.softwaresynthesis.mytalk.server.abook.Group;
import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class GroupDAOTest {
	
	private static GroupDAO tester;
	private static IGroup group;
	private static IUserData owner;
	
	/**
	 * Operazioni preliminari per lo svolgimento del test
	 * 
	 * @author diego
	 */
	@BeforeClass
	public static void setUpBeforeClass() {
		tester = new GroupDAO();
		group = new Group(1L);
		owner = new UserData(1L);
		group.setOwner(owner);
		group.setName("amici");
	}
	
	@Test
	public void testInsert() {
		boolean result = tester.insert(group);
		assertTrue(result);
	}

	@Test
	public void testGetByID() {
		Long id = group.getId();
		IGroup result = tester.getByID(id);
		assertNotNull(result);
		assertTrue(group.equals(result));
	}

	@Test
	public void testGetByOwner() {
		List<IGroup> result = tester.getByOwner(owner.getId());
		assertNotNull(result);
		assertTrue(result.size() == 1);
		assertTrue(result.contains(group));
		IGroup retrieved = result.get(0);
		assertTrue(group.equals(retrieved));
	}

	@Test
	public void testGetByOwnerAndName() {
		IGroup result = tester.getByOwnerAndName(owner.getId(), "amici");
		assertNotNull(result);
		assertTrue(group.equals(result));
	}

	@Test
	public void testUpdate() {
		group.setName("famiglia");
		boolean result = tester.update(group);
		assertTrue(result);
	}
	
	@Test
	public void testDelete() {
		boolean result = tester.delete(group);
		assertTrue(result);
	}
	
	@Test
	public void testGetByIdNonexistent() {
		Long id = -1L;
		IGroup result = tester.getByID(id);
		assertNull(result);
	}
	
	@Test
	public void testGetByOwnerAndNameNonexistent() {
		IGroup result = tester.getByOwnerAndName(owner.getId(), "");
		assertNull(result);
	}
	
	@Test
	public void testGetByOwnerNonexistent() {
		Long id = -1L;
		List<IGroup> result = tester.getByOwner(id);
		assertTrue(result.isEmpty());
	}
}
