package org.softwaresynthesis.mytalk.server.abook.controller;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.softwaresynthesis.mytalk.server.AbstractController;
import org.softwaresynthesis.mytalk.server.abook.IAddressBookEntry;
import org.softwaresynthesis.mytalk.server.abook.IGroup;
import org.softwaresynthesis.mytalk.server.abook.IUserData;
import org.softwaresynthesis.mytalk.server.dao.DataPersistanceManager;

public class DeleteGroupController extends AbstractController{
	/**
	 * Elimina un gruppo
	 * dalla propria rubrica
	 */
	@Override
	protected void doAction(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		String result = null;
		PrintWriter writer = null;
		Long groupId = null; 
		String email = null;
		IUserData myUser = null;
		DataPersistanceManager dao = null;
		IGroup group = null;
		Set<IAddressBookEntry> entrys = null;
		IAddressBookEntry entry = null;
		Iterator<IAddressBookEntry> iterator = null;
		
		try
		{
			groupId = Long.parseLong(request.getParameter("groupId"));
			dao = getDAOFactory();
			email = getUserMail();
			myUser = dao.getUserData(email);
			group = dao.getGroup(groupId);
			if (group != null)
			{
				if (group.getOwner().getId().equals(myUser.getId()))
				{
					entrys = group.getAddressBook();
					iterator = entrys.iterator();
					while (iterator.hasNext() == true)
					{
						entry = iterator.next();
						dao.delete(entry);
					}
					dao.delete(group);
					result = "true";
				}
				else
				{
					result = "false";
				}
				
			}
			else
			{
				result = "false";
			}
			
		}
		catch (Exception ex)
		{
			result = "false";
		}
		finally
		{
			writer = response.getWriter();
			writer.write(result);
		}
	}
}
