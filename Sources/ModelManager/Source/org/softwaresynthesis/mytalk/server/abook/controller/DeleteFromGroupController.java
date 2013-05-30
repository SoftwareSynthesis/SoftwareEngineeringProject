package org.softwaresynthesis.mytalk.server.abook.controller;

import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import org.softwaresynthesis.mytalk.server.AbstractController;
import org.softwaresynthesis.mytalk.server.abook.IAddressBookEntry;
import org.softwaresynthesis.mytalk.server.abook.IGroup;
import org.softwaresynthesis.mytalk.server.abook.IUserData;
import org.softwaresynthesis.mytalk.server.dao.DataPersistanceManager;

public class DeleteFromGroupController extends AbstractController{
	/**
	 * Rimuove un contatto da un gruppo
	 * della propria rubrica
	 */
	@Override
	protected void doAction(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		DataPersistanceManager dao = null;
		String result = null;
		PrintWriter writer = null;
		Long contactId = null;
		Long groupId = null;
		String email = null;
		IUserData myUser = null;
		IUserData friend = null;
		IGroup group = null;
		IAddressBookEntry entry = null;
		Set<IAddressBookEntry> entrys = null;
		Iterator<IAddressBookEntry> iterator = null;
		
		try
		{
			dao = getDAOFactory();
			contactId = Long.parseLong(request.getParameter("contactId"));
			groupId = Long.parseLong(request.getParameter("groupId"));
			email = getUserMail();
			myUser = dao.getUserData(email);
			friend = dao.getUserData(contactId);
			group = dao.getGroup(groupId);
			if (group != null && friend != null){
				entrys = myUser.getAddressBook();
				iterator = entrys.iterator();
				result = "null";
				while (iterator.hasNext() == true && result.equals("null"))
				{
					entry = iterator.next();
					if (entry.getContact().equals(friend) && entry.getGroup().equals(group) && entry.getOwner().equals(myUser))
					{
						dao.delete(entry);
						dao.update(myUser);
						result = "true";
					}
					else
					{
						result = "null";
					}
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
