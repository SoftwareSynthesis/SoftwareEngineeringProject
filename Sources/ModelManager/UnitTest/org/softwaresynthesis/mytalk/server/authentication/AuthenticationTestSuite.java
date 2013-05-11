package org.softwaresynthesis.mytalk.server.authentication;

import org.junit.runner.RunWith;

import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.softwaresynthesis.mytalk.server.authentication.controller.*;

@RunWith(Suite.class)
@SuiteClasses({ AuthenticationModuleTest.class, CredentialLoaderTest.class,
		NameLoaderTest.class, PasswordLoaderTest.class,
		PrincipalImplTest.class, LoginControllerTest.class,
		LogoutControllerTest.class, QuestionControllerTest.class })
public class AuthenticationTestSuite {

}
