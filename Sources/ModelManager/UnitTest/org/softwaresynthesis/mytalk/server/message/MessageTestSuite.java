package org.softwaresynthesis.mytalk.server.message;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.softwaresynthesis.mytalk.server.message.controller.GetMessagesControllerTest;
import org.softwaresynthesis.mytalk.server.message.controller.UpdateMessageController;

@RunWith(Suite.class)
@SuiteClasses({ MessageTest.class, GetMessagesControllerTest.class,
		UpdateMessageController.class })
public class MessageTestSuite {

}
