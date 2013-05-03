package org.softwaresynthesis.mytalk.server.authentication.controller;

import java.io.PrintWriter;

import javax.security.auth.login.LoginContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.softwaresynthesis.mytalk.server.AbstractController;
import org.softwaresynthesis.mytalk.server.abook.IUserData;
import org.softwaresynthesis.mytalk.server.authentication.CredentialLoader;
import org.softwaresynthesis.mytalk.server.authentication.security.AESAlgorithm;
import org.softwaresynthesis.mytalk.server.dao.DataPersistanceManager;

/**
 * Controller che gestisce il login 
 * al sistema Mytalk
 * 
 * @author 	Marco Schivo
 * @version 3.0
 */
public class LoginController extends AbstractController{

	@Override
	/**
	 * Esegue la richiesta di login per un utente ricevuta 
	 * 
	 * @author 	Marco Schivo
	 * @version 3.0
	 */
	protected void doAction(HttpServletRequest request,	HttpServletResponse response) {
		String email = null;
		AESAlgorithm algorithm = null;
		CredentialLoader loader = null;
		LoginContext context = null;
		DataPersistanceManager manager = null;
		IUserData user = null;
		HttpSession session = null;
		String result = null;
		PrintWriter writer = null;
		
		try{
			email = request.getParameter("username");
			algorithm = new AESAlgorithm();
			loader = new CredentialLoader(request);
			context = new LoginContext("Configuration", loader);
			context.login();			
			manager = new DataPersistanceManager();
			user = manager.getUserData(email);
			if(user != null){
				session.setAttribute("context", context);
				session.setAttribute("username", user.getMail());
				result = "{\"name\":\"" + user.getName() + "\"";
				result += ", \"surname\":\"" + user.getSurname() + "\"";
				result += ", \"email\":\"" + user.getMail() + "\"";
				result += ", \"id\":\"" + user.getId() + "\"";
				result += ", \"picturePath\":\"" + user.getPath() + "\"}";
			}
		}
		catch (Exception ex)
		{		
		}
		finally
		{
			context = null;
			loader = null;
			email = null;
			writer = response.getWriter();
			writer.write(result);
		}
	}
	
	@Override
	/**
	 * Ridefinizione metodo check 
	 * 
	 * @author 	Marco Schivo
	 * @version 3.0
	 */
	protected boolean check(HttpServletRequest request)
	{
		return true;
	}
}
