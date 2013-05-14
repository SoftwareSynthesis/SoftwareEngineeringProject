package org.softwaresynthesis.mytalk.server.abook.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.softwaresynthesis.mytalk.server.AbstractController;
import org.softwaresynthesis.mytalk.server.abook.IAddressBookEntry;
import org.softwaresynthesis.mytalk.server.abook.IUserData;
import org.softwaresynthesis.mytalk.server.dao.DataPersistanceManager;

public class BlockContactController extends AbstractController{

	/**
	 * Blocca un contatto
	 * nella propria rubrica
	 */
	@Override
	protected void doAction(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		DataPersistanceManager dao = null;
		String result = null;
		PrintWriter writer = null;
		String email = null;
		IUserData myUser = null;
		IUserData friend = null;
		Long idFriend = null;
		Set<IAddressBookEntry> entrys = null;
		Iterator<IAddressBookEntry> iterator = null;
		IAddressBookEntry entry = null;
		
		try
		{
			idFriend = Long.parseLong(request.getParameter("contactId"));
			dao = getDAOFactory();
			email = getUserMail();
			myUser = dao.getUserData(email);
			friend = dao.getUserData(idFriend);
			if (friend != null){
				entrys = myUser.getAddressBook();
				iterator = entrys.iterator();
				while (iterator.hasNext() == true)
				{
					entry = iterator.next();
					if (entry.getContact().equals(friend) == true)
					{
						entry.setBlocked(true);
					}
				}
				dao.update(entry);
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
