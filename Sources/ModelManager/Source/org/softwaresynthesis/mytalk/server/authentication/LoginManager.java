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

/**
 * Servlet per eseguire la procedura di login,
 * logout, registrazione e recupero della password
 * 
 * @author	Andrea Meneghinello
 * @version	%I%, %G%
 */
public final class LoginManager extends HttpServlet
{
	private static final long serialVersionUID = 10000123L;
       
    /**
     * Inizializza la servlet, definendo eventuali
     * configurazioni
     * 
     * @author	Andrea Meneghinello
     * @version	%I%, %G%
     */
    public LoginManager() 
    {
        super();
    }

	/**
	 * Esegue le funzioni della servlet
	 * come se fosse stata richiamata con
	 * il metodo POST
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @param	request 	{@link HttpServletRequest} richieste
	 * 						provenienti dal client
	 * @param	response	{@link HttpServletResponse} contenente
	 * 						le risposte da fornire al client
	 * @see 	LoginManager#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		this.doPost(request, response);
	}

	/**
	 * Servlet che espone le seguenti funzionalità
	 * 0 - Logout;
	 * 1 - Login;
	 * 2 - Registrazione;
	 * 3 - Proposta domanda di recupero password;
	 * 4 - Verifica risposta ed invio mail con password;
	 * passare 
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Integer type = -1;
		HttpSession session = null;
		Object operation = request.getParameter("operation");
		PrintWriter writer = null;
		String result = null;
		if(operation != null)
		{
			type = (Integer)operation;
		}
		switch (type)
		{
			case 0:
			{
				session = request.getSession(false);
				this.doLogout(session);
				session.invalidate();
			}break;
			case 1:
			{
				String username = request.getParameter("username");
				String password = request.getParameter("password");
				session = request.getSession(true);
				if (username != null && password != null)
				{
					result = this.doLogin(session, username, password);
				}
				else
				{
					result = "null";
				}
			}break;
			case 2:
			{
				
			}break;
			case 3:
			{
				
			}break;
			case 4:
			{
				
			}break;
			default:
			{
				result = "null";
			}
		}
		writer = response.getWriter();
		writer.write(result);
	}
	
	/**
	 * Esegue la procedura di login per autenticare
	 * un utente al sistema
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @param	session		{@link HttpSession} sessione con il client
	 * @param 	username	{@link String} username dell'utete
	 * @param 	password	{@link String} password dell'utete
	 * @return	{@link String} con l'utente in formato JSON se
	 * 			il login è superato, altrimenti "null"
	 */
	private String doLogin(HttpSession session, String username, String password)
	{
		IAuthenticationData credential = new AuthenticationData(username, password);
		CredentialLoader loader = null;
		IUserData user = null;
		LoginContext context = null;
		String pathFileConfig = System.getenv("MyTalkConfiguration") + "\\LoginConfiguration.conf";
		String result = null;
		UserDataDAO userDAO = null;
		try
		{
			loader = new CredentialLoader(credential, new AESAlgorithm());
			context = new LoginContext(pathFileConfig, loader);
			userDAO = new UserDataDAO();
			context.login();
			session.setAttribute("LoginContext", context);
			user = userDAO.getByEmail(username);
			result = user.toJson();
		}
		catch (Exception ex)
		{
			result = "null";
		}
		return result;
	}
	
	/**
	 * Esegue il logout di un utente dal sistema
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @param 	session	{@link HttpSessionSession}
	 * 					sessione aperta con il client
	 * 					in fase di login
	 */
	private void doLogout(HttpSession session)
	{
		LoginContext context = null;
		Object objContext = null;
		if (session != null)
		{
			objContext = session.getAttribute("LoginContext");
			if (objContext instanceof LoginContext)
			{
				context = (LoginContext)objContext;
				try
				{
					context.logout();
				}
				catch (Exception ex)
				{
				}
			}
		}
	}
}