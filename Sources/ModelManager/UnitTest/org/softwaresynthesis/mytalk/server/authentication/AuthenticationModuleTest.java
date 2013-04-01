package org.softwaresynthesis.mytalk.server.authentication;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import java.security.Principal;
import java.util.Set;
import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Verifica la possibilità di effettuare un login al sistema
 *
 * @author Andrea Meneghinello
 * @author Diego Beraldin
 */
public class AuthenticationModuleTest {
	// FIXME tutto questo test è da rivedere!
	private static LoginContext tester;

	@BeforeClass
	public static void setUpBeforeClass() throws LoginException {
		System.setProperty("java.security.auth.login.config",
				"/var/lib/tomcat7/webapps/MyTalk/Conf/LoginConfiguration.conf");
		AuthenticationData data = new AuthenticationData(
				"indirizzo5@dominio.it", "password");
		CredentialLoader loader = new CredentialLoader(data, new AESAlgorithm());
		tester = new LoginContext("Configuration", loader);
	}

	@Test
	public void testLogin() {
		Subject subject = null;
		Object[] principalsArray = null;
		Set<Principal> principals = null;
		String data = null;
		try {
			tester.login();
			subject = tester.getSubject();
			principals = subject.getPrincipals();
			assertNotNull(principals);
			principalsArray = principals.toArray();
			data = principalsArray[0].toString();
			assertEquals("PrincipalImpl[element: indirizzo5@dominio.it]", data);
		} catch (LoginException ex) {
			fail(ex.getMessage());
		}
	}
}
