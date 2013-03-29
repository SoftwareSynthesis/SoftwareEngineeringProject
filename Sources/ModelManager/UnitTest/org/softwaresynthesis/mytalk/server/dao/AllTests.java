package org.softwaresynthesis.mytalk.server.dao;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AddressBookEntryDAOTest.class, GroupDAOTest.class,
		HibernateUtilTest.class, UserDataDAOTest.class })
public class AllTests {

}
