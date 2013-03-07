package org.softwaresynthesis.mytalk.server.authentication;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;
import java.security.Principal;
import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.softwaresynthesis.mytalk.server.abook.IUserData;
import org.softwaresynthesis.mytalk.server.dao.UserDataDAO;

@WebServlet(description = "Servlet che offre le funzionalità di login", urlPatterns = { "/LoginManager" })
public class LoginManager extends HttpServlet 
{
	private static final long serialVersionUID = 100L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		AuthenticationData credential = null;
		CredentialLoader loader = null;
		Gson converter = null;
		LoginContext context = null;
		Object[] principalsArray = null;
		PrintWriter writer = null;
		Set<Principal> principals = null;
		Subject subject = null;
		String mail = null;
		String pathFileConfig = null;
		String result = null;
		UserDataDAO daoObject = null;
		try
		{
			pathFileConfig = System.getenv("MyTalkAuthentication");
			credential = new AuthenticationData(request.getParameter("username"), request.getParameter("password"));
			loader = new CredentialLoader(credential, new AESAlgorithm());
			context = new LoginContext(pathFileConfig, loader);
			context.login();
			subject = context.getSubject();
			principals = subject.getPrincipals();
			principalsArray = principals.toArray();
			mail = principalsArray[0].toString();
			daoObject = new UserDataDAO();
			IUserData user = daoObject.getByEmail(mail);
			converter = new Gson();
			result = converter.toJson(user);
		}
		catch (LoginException ex)
		{
			result = "null";
		}
		finally
		{
			writer = response.getWriter();
			writer.write(result);
		}
	}
	
	public static void main(String[] args)
	{
		UserDataDAO daoObject = new UserDataDAO();
		IUserData user = daoObject.getByEmail("indirizzo5@dominio.it");
		Gson gson = new Gson();
		String result = gson.toJson(user);
		System.out.println(result);
	}
}