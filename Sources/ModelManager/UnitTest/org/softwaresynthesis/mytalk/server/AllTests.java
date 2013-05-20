package org.softwaresynthesis.mytalk.server;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.softwaresynthesis.mytalk.server.abook.AddressBookTestSuite;
import org.softwaresynthesis.mytalk.server.authentication.AuthenticationTestSuite;
import org.softwaresynthesis.mytalk.server.call.CallTestSuite;
import org.softwaresynthesis.mytalk.server.connection.ConnectionTestSuite;
import org.softwaresynthesis.mytalk.server.dao.DAOTestSuite;
import org.softwaresynthesis.mytalk.server.message.MessageTestSuite;

@RunWith(Suite.class)
@SuiteClasses({ AddressBookTestSuite.class, AuthenticationTestSuite.class,
		DAOTestSuite.class, CallTestSuite.class, ConnectionTestSuite.class,
		MessageTestSuite.class, AbstractControllerTest.class,
		ControllerManagerTest.class })
public class AllTests {

}