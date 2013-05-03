package org.softwaresynthesis.mytalk.server;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.softwaresynthesis.mytalk.server.abook.IUserData;

/**
 * Implementa la struttura di esecuzione di
 * ogni singolo controller.
 * 
 * @author 	Andrea Meneghinello
 * @version 3.0
 */
public abstract class AbstractController implements IController
{
	private IUserData user;
	
	/**
	 * Crea un istanza del nuovo controller
	 */
	public AbstractController()
	{
		this.user = null;
	}
	
	@Override
	public final void execute(HttpServletRequest request, HttpServletResponse response) 
	{
		if (this.check(request))
		{
			this.doAction(request, response);
		}
	}
	
	/**
	 * Verifica se l'utente è autenticato presso il
	 * sistema e possa di conseguenza portare a termine
	 * l'operazione
	 * 
	 * @param 	request	{@link HttpServletRequest} richiesta giunta dal client
	 * @return	true se l'utente è autorizzato a procedere, false altrimenti
	 */
	protected boolean check(HttpServletRequest request)
	{
		//TODO
		return false;
	}
	
	/**
	 * Esegue l'operazione richiesta
	 * 
	 * @param 	request		{@link HttpServletRequest} parametri in input dall'utente
	 * @param 	response	{@link HttpServletResponse} parametri in output dal controller
	 */
	protected abstract void doAction(HttpServletRequest request, HttpServletResponse response);
	
	/**
	 * Restituisce l'utente che ha fatto il login
	 * 
	 * @return	{@link IUserData} utente autenticato
	 */
	IUserData getUser()
	{
		return this.user;
	}
}