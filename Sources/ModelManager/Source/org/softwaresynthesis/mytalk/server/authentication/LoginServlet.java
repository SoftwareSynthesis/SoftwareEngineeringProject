package org.softwaresynthesis.mytalk.server.authentication;

import java.io.IOException;
import java.io.PrintWriter;
import javax.security.auth.login.LoginContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.softwaresynthesis.mytalk.server.abook.IUserData;
import org.softwaresynthesis.mytalk.server.dao.UserDataDAO;

public class LoginServlet extends HttpServlet 
{
	private static final long serialVersionUID = 10002L;
	
	public LoginServlet()
	{
		super();
		String path = System.getenv("MyTalkauthentication");
		path += "\\MyTalk\\Conf\\LoginConfiguration.conf";
		System.setProperty("java.security.auth.login.config", path);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		this.doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		CredentialLoader loader = null;
		HttpSession session = null;
		IAuthenticationData credential;
		IUserData user = null;
		LoginContext context = null;
		PrintWriter writer = response.getWriter();
		String password = null;
		String result = null;
		String username = null;
		UserDataDAO userDAO = null;
		try
		{
			session = request.getSession(true);
			username = request.getParameter("username");
			password = request.getParameter("password");
			credential = new AuthenticationData(username, password);
			loader = new CredentialLoader(credential, new AESAlgorithm());
			context = new LoginContext("Configuration", loader);
			context.login();
			userDAO = new UserDataDAO();
			user = userDAO.getByEmail(username);
			session.setAttribute("context", context);
			session.setAttribute("user", user);
			result = user.toJson();
		}
		catch (Exception ex)
		{
			if (session != null)
			{
				session.invalidate();
			}
			result = "null";
		}
		writer.write(result);
	}
}
