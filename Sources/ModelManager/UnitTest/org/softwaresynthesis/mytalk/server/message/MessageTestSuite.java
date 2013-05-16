package org.softwaresynthesis.mytalk.server.message;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.softwaresynthesis.mytalk.server.message.controller.GetMessagesControllerTest;

@RunWith(Suite.class)
@SuiteClasses({ MessageTest.class, GetMessagesControllerTest.class })
public class MessageTestSuite {

}
