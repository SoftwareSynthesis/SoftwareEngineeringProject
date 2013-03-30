package org.softwaresynthesis.mytalk.server.abook;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.softwaresynthesis.mytalk.server.abook.servlet.*;

@RunWith(Suite.class)
@SuiteClasses({ AddressBookEntryTest.class, GroupTest.class,
		UserDataTest.class, AddressBookGetContactsServletTest.class,
		AddressBookDoAddContactServletTest.class,
		AddressBookDoRemoveContactServletTest.class,
		AddressBookDoCreateGroupServletTest.class,
		AddressBookDoDeleteGroupServletTest.class,
		AddressBookDoInsertInGroupServletTest.class,
		AddressBookDoRemoveFromGroupServletTest.class,
		AddressBookDoBlockServletTest.class })
public class AddressBookTestSuite {

}
