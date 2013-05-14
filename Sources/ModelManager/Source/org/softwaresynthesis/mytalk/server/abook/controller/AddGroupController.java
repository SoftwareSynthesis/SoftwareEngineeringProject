package org.softwaresynthesis.mytalk.server.abook.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.softwaresynthesis.mytalk.server.AbstractController;
import org.softwaresynthesis.mytalk.server.abook.Group;
import org.softwaresynthesis.mytalk.server.abook.IGroup;
import org.softwaresynthesis.mytalk.server.abook.IUserData;
import org.softwaresynthesis.mytalk.server.dao.DataPersistanceManager;

public class AddGroupController extends AbstractController {
	/**
	 * Esegue l'aggiunta di un nuovo gruppo
	 * nella propria rubrica
	 */
	@Override
	protected void doAction(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		DataPersistanceManager dao = null;
		String result = null;
		String name = null;
		IGroup group = null;
		PrintWriter writer = null;
		String myEmail = null;
		IUserData myUser = null;
		
		try
		{
			name = request.getParameter("groupName");
			dao = getDAOFactory();
			myEmail = getUserMail();
			myUser = dao.getUserData(myEmail);
			group = new Group();
			group.setName(name);
			group.setOwner(myUser);
			dao.insert(group);
			
			result = "true";
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
