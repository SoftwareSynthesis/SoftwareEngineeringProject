package org.softwaresynthesis.mytalk.server;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Implementa la struttura di esecuzione di
 * ogni singolo controller.
 * 
 * @author 	Andrea Meneghinello
 * @version 3.0
 */
public abstract class AbstractController implements IController
{
	private String userMail;
	
	@Override
	public final void execute(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		if (this.check(request))
		{
			this.doAction(request, response);
		}
	}
	
	/**
	 * Verifica se l'utente è autenticato presso il
	 * sistema e possa di conseguenza portare a termine
	 * l'operazione richiesta
	 * 
	 * @param 	request	{@link HttpServletRequest} richiesta giunta dal client
	 * @return	true se l'utente è autorizzato a procedere, false altrimenti
	 */
	protected boolean check(HttpServletRequest request)
	{
		boolean result = false;
		HttpSession session = request.getSession(false);
		if (session != null)
		{
			String mail = (String)session.getAttribute("username");
			if (mail != null && mail.equals("") == false)
			{
				result = true;
			}
		}
		return result;
	}
	
	/**
	 * Esegue l'operazione richiesta
	 * 
	 * @param 	request		{@link HttpServletRequest} parametri in input dall'utente
	 * @param 	response	{@link HttpServletResponse} parametri in output dal controller
	 */
	protected abstract void doAction(HttpServletRequest request, HttpServletResponse response) throws IOException;
	
	/**
	 * Restituisce l'indirizzo mail dell'utente che
	 * ha fatto il login
	 * 
	 * @return	{@link String} indirizzo mail dell'utente
	 * 			autenticato
	 */
	protected String getUserMail()
	{
		return this.userMail;
	}
	
	/**
	 * Imposta l'indirizzo mail dell'utente che
	 * ha fatto il login
	 * 
	 * @param 	mail	{@link String} indirizzo mail
	 * 					dell'utente autenticato
	 */
	protected void setUserMail(String mail)
	{
		this.userMail = mail;
	}
}