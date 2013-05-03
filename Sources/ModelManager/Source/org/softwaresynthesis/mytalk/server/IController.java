package org.softwaresynthesis.mytalk.server;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Rappresenta il generico controller che
 * può essere lanciato dal server a seguito
 * di una richista HTTP GET o HTTP POST
 * 
 * @author 	Andrea Meneghinello
 * @version	3.0
 */
public interface IController 
{
	/**
	 * Eseuge l'operazione associata al controller
	 * 
	 * @param 	request		{@link HttpServletRequest} contiene le informazioni inviate dal client
	 * 						per completare l'operazione richiesta
	 * @param 	response	{@link HttpServletResponse} contine le informazione inviate dal server
	 * 						per informare il client dell'esito della richiesta
	 */
	public void execute(HttpServletRequest request, HttpServletResponse response);
}