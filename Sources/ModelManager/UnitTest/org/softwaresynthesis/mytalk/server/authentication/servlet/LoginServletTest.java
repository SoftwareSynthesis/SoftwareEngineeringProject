package org.softwaresynthesis.mytalk.server.authentication.servlet;

import static org.junit.Assert.*;

import java.io.File;

import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletRequest;
import org.junit.BeforeClass;
import org.junit.Test;

import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;

public class LoginServletTest {

	private static LoginServlet tester;
	private static ServletRunner runner;
	private static ServletUnitClient client;
	
	/*
	 *  FIXME 
	 *	String separator = System.getProperty("file.separator");
	 *  path += separator + "Conf" + separator + "LoginConfiguration.conf";
	 * 
	 */

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		File desc = new File("WebContent/WEB-INF/web.xml");
		runner = new ServletRunner(desc);
		runner.registerServlet("LoginAuthentication",
				LoginServlet.class.getName());
		client = runner.newClient();
	}

	@Test
	public void testDoPostHttpServletRequestHttpServletResponse() {
		try {
			WebRequest request = new PostMethodWebRequest(
					"http://LoginAuthentication");
			request.setParameter("username", "indirizzo5@dominio.it");
			request.setParameter("password", "password");
			WebResponse response = client.getResponse(request);
			assertNotNull(response);
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}

}
