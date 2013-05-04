package org.softwaresynthesis.mytalk.server.authentication;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.softwaresynthesis.mytalk.server.authentication.servlet.*;

@RunWith(Suite.class)
@SuiteClasses({ AuthenticationModuleTest.class, CredentialLoaderTest.class,
		NameLoaderTest.class, PasswordLoaderTest.class,
		PrincipalImplTest.class, LoginServletTest.class,
		LogoutServletTest.class, RegisterServletTest.class })
public class AuthenticationTestSuite {

}
