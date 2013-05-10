package org.softwaresynthesis.mytalk.server.message.controller;

import java.io.File;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.softwaresynthesis.mytalk.server.AbstractController;
import org.softwaresynthesis.mytalk.server.dao.DataPersistanceManager;
import org.softwaresynthesis.mytalk.server.message.IMessage;

public class DeleteMessageController extends AbstractController{
	/**
	 * Elimina un messaggio nella
	 * segreteria di un utente
	 */
	@Override
	protected void doAction(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		DataPersistanceManager dao = null;
		String result = null;
		PrintWriter writer = null;
		IMessage message = null;
		Long id = null;
		String separator = null;
		String path = null;
		File file = null;
		
		try
		{
			dao = super.getDAOFactory();
			id = Long.parseLong(request.getParameter("idMessage"));
			message = dao.getMessage(id);
			if (message != null){
				dao.delete(message);
				separator = System.getProperty("file.separator");
				path = "Secretariat" + separator;
				path += id.toString() + ".wav";
				file = new File(path);
				if(!(file.delete()))
				{
					result = "null";
				}
				else
				{
					result = "true";
				}
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
