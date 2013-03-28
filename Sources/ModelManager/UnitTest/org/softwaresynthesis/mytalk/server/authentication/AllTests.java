package org.softwaresynthesis.mytalk.server.authentication;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AESAlgorithmTest.class, AuthenticationDataTest.class,
		AuthenticationModuleTest.class, CredentialLoaderTest.class,
		PrincipalImplTest.class })
public class AllTests {

}
