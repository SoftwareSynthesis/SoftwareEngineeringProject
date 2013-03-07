package org.softwaresynthesis.mytalk.server.authentication;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import javax.security.auth.login.LoginContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.softwaresynthesis.mytalk.server.abook.IUserData;
import org.softwaresynthesis.mytalk.server.dao.UserDataDAO;

@WebServlet(description = "Servlet che offre le funzionalità di login", urlPatterns = { "/LoginManager" })
public class LoginManager extends HttpServlet 
{
	private static final long serialVersionUID = 100L;
	
	private LoginContext context;
	
	/**
	 * Esegue il login o il logout di un utente nel sistema mytalk
	 * 1 = login
	 * 0 = logout
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @param 	request		Richiesta ricevuta in input
	 * @param	response	Risposta inviata dalla servlet al cliente
	 * @throws	{@link IOException} se vi sono errori relativi all'I/O
	 * @throws	{@link ServletException} se vi sono errori durante l'esecuzione
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		int operation = Integer.parseInt(request.getParameter("operation"));
		HttpSession session = null;
		PrintWriter writer = null;
		String result = "null";
		switch(operation)
		{
			case 0:
				{
					session = request.getSession(false);
					result = this.doLogout(session);
					
				}break;
			case 1:
				{
					result = this.doLogin(request.getParameter("username"), request.getParameter("password"));
					if(result.equals("null") == false)
					{
						session = request.getSession(true);
						session.setAttribute("context", context);
					}
				}break;
		}
		writer = response.getWriter();
		writer.write(result);
	}
	
	/**
	 * Esegue il login o il logout di un utente nel sistema mytalk
	 * 1 = login
	 * 0 = logout
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @param 	request		Richiesta ricevuta in input
	 * @param	response	Risposta inviata dalla servlet al cliente
	 * @throws	{@link IOException} se vi sono errori relativi all'I/O
	 * @throws	{@link ServletException} se vi sono errori durante l'esecuzione
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		doPost(request, response);
	}
	
	/**
	 * Esegue il login di un utente nel sistema mytalk
	 * 
	 * @param 	username	username dell'utente
	 * @param 	password	password dell'utente
	 * @return	Lo {@Link IUserData} in formato Json se il
	 * 			login è andato a buon fine, "null" altrimenti
	 */
	private String doLogin(String username, String password)
	{
		AuthenticationData credential = null;
		CredentialLoader loader = null;
		Gson converter = null;
		IUserData user = null;
		String pathFileConfig = null;
		String result = null;
		UserDataDAO daoObject = null;
		try
		{
			pathFileConfig = System.getenv("MyTalkAuthentication");
			credential = new AuthenticationData(username, password);
			loader = new CredentialLoader(credential, new AESAlgorithm());
			context = new LoginContext(pathFileConfig, loader);
			daoObject = new UserDataDAO();
			converter = new Gson();
			context.login();
			user = daoObject.getByEmail(username);
			result = converter.toJson(user);
		}
		catch (Exception ex)
		{
			result = "null";
		}
		return result;
	}
	
	/**
	 * Esegue il logout di un utente dal sistema mytalk
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @param 	session	Sessione contenente le informazioni
	 * @return	La stringa null che determina se il logout
	 * 			è andato a buon fine
	 */
	private String doLogout(HttpSession session)
	{
		context = (LoginContext)session.getAttribute("context");
		try
		{
			context.logout();
		}
		catch (Exception ex)
		{
			System.err.println("Errore durante la procedura di logout");
		}
		finally
		{
			session.invalidate();
		}
		return "null";
	}
}