package org.softwaresynthesis.mytalk.server.authentication.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.security.auth.login.LoginContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.softwaresynthesis.mytalk.server.AbstractController;
import org.softwaresynthesis.mytalk.server.ControllerManager;
import org.softwaresynthesis.mytalk.server.abook.IUserData;
import org.softwaresynthesis.mytalk.server.connection.PushInbound;
import org.softwaresynthesis.mytalk.server.dao.DataPersistanceManager;

/**
 * Controller che gestisce il logout
 * dal sistema MyTalk
 * 
 * @author 	Andrea Meneghinello
 * @version	3.0
 */
public final class LogoutController extends AbstractController 
{
	/**
	 * Esegue l'operazione di logout dal
	 * sistema MyTalk
	 */
	@Override
	protected void doAction(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		HttpSession session = null;
		LoginContext context = null;
		PrintWriter writer = null;
		String result = null;
		Long id = null;
		String email = null;
		PushInbound client = null;
		IUserData user = null;
		DataPersistanceManager dao = null;
		try
		{
			dao = new DataPersistanceManager();
			email = getUserMail();
			user = dao.getUserData(email);
			id = user.getId();
			client = ControllerManager.findClient(id);
			ControllerManager.removeClient(client);
			session = request.getSession(false);
			context = (LoginContext)session.getAttribute("context");
			context.logout();
			result = "true";
		}
		catch (Exception ex)
		{
			result = "null";
		}
		finally
		{
			if (session != null)
			{
				session.invalidate();
			}
			writer = response.getWriter();
			writer.write(result);
		}
	}
}