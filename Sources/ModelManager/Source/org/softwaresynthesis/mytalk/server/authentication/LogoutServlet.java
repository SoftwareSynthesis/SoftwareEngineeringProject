package org.softwaresynthesis.mytalk.server.authentication;

import java.io.IOException;
import java.io.PrintWriter;
import javax.security.auth.login.LoginContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LogoutServlet
 */
public class LogoutServlet extends HttpServlet 
{
	private static final long serialVersionUID = 10001L;
	
	public LogoutServlet()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession(false);
		LoginContext context = null;
		PrintWriter writer = response.getWriter();
		String result = null;
		try
		{
			context = (LoginContext)session.getAttribute("context");
			context.logout();
			result = "true";
		}
		catch (Exception ex)
		{
			result = "false";
		}
		writer.write(result);
	}
}