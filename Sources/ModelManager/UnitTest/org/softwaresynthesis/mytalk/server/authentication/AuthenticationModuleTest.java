package org.softwaresynthesis.mytalk.server.authentication;

import static org.junit.Assert.*;
import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import org.junit.BeforeClass;
import org.junit.Test;

public class AuthenticationModuleTest 
{
	private static LoginContext tester;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{
		System.setProperty("java.security.auth.login.config", "LoginConf.conf");
		AuthenticationData data = new AuthenticationData("indirizzo5@dominio.it", "password");
		CredentialLoader loader = new CredentialLoader(data, new AESAlgorithm());
		tester = new LoginContext("LoginConf", loader);
	}
	
	@Test
	public void testLogin()
	{
		Subject subject = null;
		try
		{
			tester.login();
			subject = tester.getSubject();
			assertEquals("indirizzo5@dominio.it", subject.toString());
		}
		catch (LoginException ex)
		{
			fail("Login fallito");
		}
	}
}
