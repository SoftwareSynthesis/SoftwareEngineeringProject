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
	 * @param	request 	{@link HttpServletRequest} con i dati
	 * 						provenienti dal client
	 * @param	response	{@link HttpServletResponse} con i dati
	 * 						da fornire al client
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
	 * 
	 *  @param	request		{@link HttpServletRequest} con i dati
	 *  					provenienti dal client
	 *  @param	response	{@link	HttpServletRequest} con i dati
	 *  					da fornire al client
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Integer type = -1;
		HttpSession session = null;
		String operation = request.getParameter("operation");
		PrintWriter writer = null;
		String result = null;
		if(operation != null)
		{
			type = Integer.parseInt(operation);
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
	 * @param	session		{@link HttpSession} sessione con il client
	 * @param 	username	{@link String} username dell'utete
	 * @param 	password	{@link String} password dell'utete
	 * @return	{@link String} con l'utente in formato JSON se
	 * 			il login è superato, altrimenti "null"
	 */
	private String doLogin(HttpSession session, String username, String password)
	{
		System.setProperty("java.security.auth.login.config", System.getenv("MyTalkConfiguration") + "\\MyTalk\\Conf\\LoginConfiguration.conf");
		IAuthenticationData credential = new AuthenticationData(username, password);
		CredentialLoader loader = null;
		IUserData user = null;
		LoginContext context = null;
		String result = null;
		UserDataDAO userDAO = null;
		try
		{
			loader = new CredentialLoader(credential, new AESAlgorithm());
			context = new LoginContext("Configuration", loader);
			userDAO = new UserDataDAO();
			context.login();
			user = userDAO.getByEmail(username);
			session.setAttribute("LoginContext", context);
			session.setAttribute("User", user);
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
		return result;
	}
	
	/**
	 * Esegue il logout di un utente dal sistema
	 * 
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
	
	/**
	 * Restituisce l'istanza nella forma {@link String}
	 * 
	 * @return	{@link String} rappresentante l'istanza
	 */
	public String toString()
	{
		return "LoginManager";
	}
}