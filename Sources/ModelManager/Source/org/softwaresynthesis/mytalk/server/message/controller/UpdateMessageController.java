package org.softwaresynthesis.mytalk.server.message.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.softwaresynthesis.mytalk.server.AbstractController;
import org.softwaresynthesis.mytalk.server.abook.IUserData;
import org.softwaresynthesis.mytalk.server.dao.DataPersistanceManager;
import org.softwaresynthesis.mytalk.server.message.IMessage;

public class UpdateMessageController extends AbstractController{
	/**
	 * Aggiorna un messaggio nella
	 * segreteria di un utente
	 */
	@Override
	protected void doAction(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		DataPersistanceManager dao = null;
		String result = null;
		PrintWriter writer = null;
		IMessage message = null;
		String stato = null;
		Boolean state = null;
		String email = null;
		IUserData user = null;
		Long id = null;
		
		try
		{
			dao = getDAOFactory();
			id = Long.parseLong(request.getParameter("idMessage"));
			stato = request.getParameter("valueToSet");
			email = getUserMail();
			user = dao.getUserData(email);
			if (stato.equals("true"))
				state = true;
			else
				state = false;
			message = dao.getMessage(id);
			if (message != null && message.getReceiver() == user){
				message.setNewer(state);
				dao.update(message);
				result = "true";
			}
			else
			{
				result = "null";
			}
			
			
		}
		catch (Exception ex)
		{
			result = "null";
		}
		finally
		{
			writer = response.getWriter();
			writer.write(result);
		}
	}
}
