package org.softwaresynthesis.mytalk.server.abook;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.softwaresynthesis.mytalk.server.abook.controller.AccountSettingsController;
import org.softwaresynthesis.mytalk.server.abook.controller.AddContactControllerTest;
import org.softwaresynthesis.mytalk.server.abook.controller.AddGroupControllerTest;
import org.softwaresynthesis.mytalk.server.abook.controller.AddInGroupControllerTest;
import org.softwaresynthesis.mytalk.server.abook.controller.BlockContactControllerTest;
import org.softwaresynthesis.mytalk.server.abook.controller.DeleteContactControllerTest;
import org.softwaresynthesis.mytalk.server.abook.controller.DeleteFromGroupControllerTest;
import org.softwaresynthesis.mytalk.server.abook.controller.DeleteGroupControllerTest;
import org.softwaresynthesis.mytalk.server.abook.controller.GetContactsControllerTest;
import org.softwaresynthesis.mytalk.server.abook.controller.GetGroupsControllerTest;
import org.softwaresynthesis.mytalk.server.abook.controller.SearchControllerTest;
import org.softwaresynthesis.mytalk.server.abook.controller.UnblockContactControllerTest;

@RunWith(Suite.class)
@SuiteClasses({ AddressBookEntryTest.class, GroupTest.class,
		UserDataTest.class, AddContactControllerTest.class,
		AccountSettingsController.class, AddGroupControllerTest.class,
		AddInGroupControllerTest.class, BlockContactControllerTest.class,
		DeleteContactControllerTest.class, DeleteFromGroupControllerTest.class,
		DeleteGroupControllerTest.class, GetContactsControllerTest.class,
		GetGroupsControllerTest.class, SearchControllerTest.class,
		UnblockContactControllerTest.class })
public class AddressBookTestSuite {

}
