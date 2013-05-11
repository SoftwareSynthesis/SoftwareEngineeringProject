package org.softwaresynthesis.mytalk.server.dao;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.softwaresynthesis.mytalk.server.dao.util.DeleteUtilTest;
import org.softwaresynthesis.mytalk.server.dao.util.GetCallUtilTest;
import org.softwaresynthesis.mytalk.server.dao.util.GetGroupUtilTest;
import org.softwaresynthesis.mytalk.server.dao.util.GetUserDataUtilTest;
import org.softwaresynthesis.mytalk.server.dao.util.InsertUtilTest;
import org.softwaresynthesis.mytalk.server.dao.util.NotInitializeTest;
import org.softwaresynthesis.mytalk.server.dao.util.UpdateUtilTest;
import org.softwaresynthesis.mytalk.server.dao.util.UtilFactoryTest;

@RunWith(Suite.class)
@SuiteClasses({ DataPersistanceManagerTest.class, SessionManagerTest.class,
		DeleteUtilTest.class, GetCallUtilTest.class, GetGroupUtilTest.class,
		GetUserDataUtilTest.class, InsertUtilTest.class,
		NotInitializeTest.class, UpdateUtilTest.class, UtilFactoryTest.class })
public class DAOTestSuite {
}
