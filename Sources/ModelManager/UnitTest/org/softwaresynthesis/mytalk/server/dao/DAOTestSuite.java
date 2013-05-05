package org.softwaresynthesis.mytalk.server.dao;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.softwaresynthesis.mytalk.server.dao.util.DeleteUtilTest;
import org.softwaresynthesis.mytalk.server.dao.util.InsertUtilTest;
import org.softwaresynthesis.mytalk.server.dao.util.UpdateUtilTest;
import org.softwaresynthesis.mytalk.server.dao.util.UtilFactoryTest;

@RunWith(Suite.class)
@SuiteClasses({ SessionManagerTest.class, DeleteUtilTest.class,
		InsertUtilTest.class, UpdateUtilTest.class, UtilFactoryTest.class })
public class DAOTestSuite {
}
