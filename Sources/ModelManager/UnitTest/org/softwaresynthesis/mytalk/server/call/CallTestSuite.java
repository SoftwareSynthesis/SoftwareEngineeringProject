package org.softwaresynthesis.mytalk.server.call;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.softwaresynthesis.mytalk.server.call.controller.AddCallControllerTest;
import org.softwaresynthesis.mytalk.server.call.controller.GetCallsControllerTest;

@RunWith(Suite.class)
@SuiteClasses({ CallListTest.class, CallTest.class,
		GetCallsControllerTest.class, AddCallControllerTest.class })
public class CallTestSuite {

}
