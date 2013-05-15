package org.softwaresynthesis.mytalk.server.abook;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.softwaresynthesis.mytalk.server.abook.controller.AddContactControllerTest;
import org.softwaresynthesis.mytalk.server.abook.controller.AddGroupControllerTest;
import org.softwaresynthesis.mytalk.server.abook.controller.AddInGroupControllerTest;
import org.softwaresynthesis.mytalk.server.abook.controller.BlockContactControllerTest;
import org.softwaresynthesis.mytalk.server.abook.controller.DeleteContactControllerTest;
import org.softwaresynthesis.mytalk.server.abook.controller.DeleteFromGroupControllerTest;
import org.softwaresynthesis.mytalk.server.abook.controller.GetGroupsControllerTest;

@RunWith(Suite.class)
@SuiteClasses({ AddressBookEntryTest.class, GroupTest.class,
		UserDataTest.class, AddContactControllerTest.class,
		AddGroupControllerTest.class, AddInGroupControllerTest.class,
		BlockContactControllerTest.class, DeleteContactControllerTest.class,
		DeleteFromGroupControllerTest.class, GetGroupsControllerTest.class })
public class AddressBookTestSuite {

}
