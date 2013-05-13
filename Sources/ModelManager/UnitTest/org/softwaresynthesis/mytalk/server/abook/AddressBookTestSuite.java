package org.softwaresynthesis.mytalk.server.abook;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.softwaresynthesis.mytalk.server.abook.controller.AddContactController;

@RunWith(Suite.class)
@SuiteClasses({ AddressBookEntryTest.class, GroupTest.class,
		UserDataTest.class, AddContactController.class })
public class AddressBookTestSuite {

}
