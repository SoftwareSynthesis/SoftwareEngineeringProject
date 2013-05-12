package org.softwaresynthesis.mytalk.server.authentication;

import org.junit.runner.RunWith;

import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.softwaresynthesis.mytalk.server.authentication.controller.*;
import org.softwaresynthesis.mytalk.server.authentication.security.AESAlgorithmTest;
import org.softwaresynthesis.mytalk.server.authentication.security.AESDecodeTest;
import org.softwaresynthesis.mytalk.server.authentication.security.AESEncodeTest;

@RunWith(Suite.class)
@SuiteClasses({ AESAlgorithmTest.class, AESDecodeTest.class,
		AESEncodeTest.class, AuthenticationModuleTest.class,
		CredentialLoaderTest.class, NameLoaderTest.class,
		PasswordLoaderTest.class, PrincipalImplTest.class,
		LoginControllerTest.class, LogoutControllerTest.class,
		QuestionControllerTest.class, RegisterControllerTest.class })
public class AuthenticationTestSuite {

}
