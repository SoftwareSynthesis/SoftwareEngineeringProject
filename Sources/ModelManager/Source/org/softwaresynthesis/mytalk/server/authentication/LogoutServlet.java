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
 * Servlet che ha il compito di terminare
 * l'accesso al sistema mytalk di un utente
 * 
 * @author	Andrea Meneghinello
 * @version	%I%, %G%
 */
public final class LogoutServlet extends HttpServlet 
{
	private static final long serialVersionUID = 10001L;
	
	/**
	 * Inizializza la servlet
	 */
	public LogoutServlet()
	{
		super();
	}

	/**
	 * Esegue la richiesta di logout ricevuta
	 * tramite richiesta HTTP GET
	 * 
	 * @param	request		contiene i parametri di input
	 * 						(username, password) per il
	 * 						corretto svolgimento dell'operazione	
	 * @param	response	contiene le risposte prodotte dalla
	 * 						servlet che verranno inviate ai client
	 * @throws	{@link IOException} se si verificano errori durante
	 * 			operazioni di IO
	 * @throws	{@link ServletException} se si verificano errori
	 * 			interni alla servlet
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		this.doPost(request, response);
	}

	/**
	 * Esegue la richiesta di logout ricevuta
	 * tramite richiesta HTTP POST
	 * 
	 * @param	request		contiene i parametri di input
	 * 						(username, password) per il
	 * 						corretto svolgimento dell'operazione	
	 * @param	response	contiene le risposte prodotte dalla
	 * 						servlet che verranno inviate ai client
	 * @throws	{@link IOException} se si verificano errori durante
	 * 			operazioni di IO
	 * @throws	{@link ServletException} se si verificano errori
	 * 			interni alla servlet
	 */
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