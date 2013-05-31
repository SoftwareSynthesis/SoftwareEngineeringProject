package org.softwaresynthesis.mytalk.server.message.controller;

import java.io.File;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.softwaresynthesis.mytalk.server.AbstractController;
import org.softwaresynthesis.mytalk.server.abook.IUserData;
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
		IUserData user = null;
		
		try
		{
			dao = getDAOFactory();
			id = Long.parseLong(request.getParameter("idMessage"));
			message = dao.getMessage(id);
			user = dao.getUserData(getUserMail());
			if (message != null && message.getReceiver().getId() == user.getId()){
				separator = System.getProperty("file.separator");
				path = System.getenv("MyTalkConfiguration");
				path += separator + "MyTalk" + separator + "Secretariat" + separator;
				path += id.toString() + ".wav";
				if (deleteFile(path) == false)
				{
					result = "null";
				}
				else
				{
					dao.delete(message);
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
	
	/**
	 * Elimina il file dal disco
	 * 
	 * @param path
	 *            percorso del file che deve essere cancellato
	 * @return <code>true</code> se l'operazione va a buon fine,
	 *         <code>false</code> altrimenti
	 */
	boolean deleteFile(String path)
	{
		File file = new File(path);
		return file.delete();
	}
}
