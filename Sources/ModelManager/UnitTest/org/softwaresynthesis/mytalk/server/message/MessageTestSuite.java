package org.softwaresynthesis.mytalk.server.message;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.softwaresynthesis.mytalk.server.message.controller.AddMessageControllerTest;
import org.softwaresynthesis.mytalk.server.message.controller.DeleteMessageControllerTest;
import org.softwaresynthesis.mytalk.server.message.controller.GetMessagesControllerTest;
import org.softwaresynthesis.mytalk.server.message.controller.UpdateMessageControllerTest;

@RunWith(Suite.class)
@SuiteClasses({ MessageTest.class, AddMessageControllerTest.class,
		DeleteMessageControllerTest.class, GetMessagesControllerTest.class,
		UpdateMessageControllerTest.class })
public class MessageTestSuite {

}
