package org.softwaresynthesis.mytalk.server.authentication;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AuthenticationModuleTest.class, CredentialLoaderTest.class,
		NameLoaderTest.class, PasswordLoaderTest.class, PrincipalImplTest.class })
public class AuthenticationTestSuite {

}
