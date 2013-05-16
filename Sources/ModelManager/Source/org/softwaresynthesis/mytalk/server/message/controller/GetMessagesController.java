package org.softwaresynthesis.mytalk.server.message.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.softwaresynthesis.mytalk.server.AbstractController;
import org.softwaresynthesis.mytalk.server.abook.IUserData;
import org.softwaresynthesis.mytalk.server.dao.DataPersistanceManager;
import org.softwaresynthesis.mytalk.server.message.IMessage;

public class GetMessagesController extends AbstractController{
	/**
	 * Recupera i messaggi nella
	 * segreteria di un utente
	 */
	@Override
	protected void doAction(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		DataPersistanceManager dao = null;
		String result = null;
		PrintWriter writer = null;
		List<IMessage> messages = null;
		Iterator<IMessage> messageIter = null;
		IMessage message = null;
		String separator = System.getProperty("file.separator");
		String email = null;
		IUserData user = null;
		
		try
		{
			dao = getDAOFactory();
			email = getUserMail();
			user = dao.getUserData(email);
			messages = dao.getMessages(user);
			if (messages != null)
			{
				messageIter = messages.iterator();
				result = "[";
				while (messageIter.hasNext() == true)
				{
					message = messageIter.next();
					result += "{";
					result += "\"id\":\"" + message.getId() + "\"";
					result += ", \"sender\":\"" + message.getSender().getId() + "\"";
					result += ", \"status\":\"" + message.getNewer() + "\"";
					result += ", \"video\":\"" + message.getVideo() + "\"";
					result += ", \"date\":\"" + message.getDate() + "\"";
					result += ", \"src\":\"Secretariat" + separator + message.getId() + ".wav\"";
					result += "}";
					if (messageIter.hasNext() == true)
					{
						result += ", ";
					}
				}
				result += "]";
			}
			else
			{
				result = "[]";
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
