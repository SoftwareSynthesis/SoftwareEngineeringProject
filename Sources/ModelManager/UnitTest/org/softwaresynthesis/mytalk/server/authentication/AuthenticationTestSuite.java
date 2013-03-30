package org.softwaresynthesis.mytalk.server.authentication;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.softwaresynthesis.mytalk.server.authentication.servlet.*;

@RunWith(Suite.class)
@SuiteClasses({ AESAlgorithmTest.class, AuthenticationDataTest.class,
		AuthenticationModuleTest.class, CredentialLoaderTest.class,
		PrincipalImplTest.class, LoginServletTest.class,
		LogoutServletTest.class, RegisterServlet.class})
public class AuthenticationTestSuite {

}
