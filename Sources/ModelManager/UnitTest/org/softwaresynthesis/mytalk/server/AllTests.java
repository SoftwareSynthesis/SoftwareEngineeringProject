package org.softwaresynthesis.mytalk.server;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.softwaresynthesis.mytalk.server.abook.AddressBookTestSuite;
import org.softwaresynthesis.mytalk.server.dao.DAOTestSuite;
import org.softwaresynthesis.mytalk.server.authentication.AuthenticationTestSuite;
import org.softwaresynthesis.mytalk.server.connection.ConnectionTestSuite;

@RunWith(Suite.class)
@SuiteClasses({ AddressBookTestSuite.class, DAOTestSuite.class,
		AuthenticationTestSuite.class, ConnectionTestSuite.class })
public class AllTests {

}