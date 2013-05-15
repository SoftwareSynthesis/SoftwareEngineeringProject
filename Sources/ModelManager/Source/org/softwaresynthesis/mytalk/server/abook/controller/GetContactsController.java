package org.softwaresynthesis.mytalk.server.abook.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import org.softwaresynthesis.mytalk.server.AbstractController;
import org.softwaresynthesis.mytalk.server.ControllerManager;
import org.softwaresynthesis.mytalk.server.abook.IAddressBookEntry;
import org.softwaresynthesis.mytalk.server.abook.IUserData;
import org.softwaresynthesis.mytalk.server.dao.DataPersistanceManager;

public class GetContactsController extends AbstractController{
	/**
	 * Ritorna tutti i contatti
	 * della propria rubrica
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
		Set<IAddressBookEntry> contacts = null;
		IAddressBookEntry entry = null;
		Iterator<IAddressBookEntry> iterator = null;
		Boolean first = null;
		
		try
		{
			dao = super.getDAOFactory();
			email = super.getUserMail();
			myUser = dao.getUserData(email);
			contacts = myUser.getAddressBook();
			iterator = contacts.iterator();
			result = "{";
			first = true;
			while(iterator.hasNext() == true)
			{
				entry = iterator.next();
				//Se gruppo == null, allora il contatto non � in un gruppo
				if (entry.getGroup() == null){
					if (first == false)
					{
						result += ",";
					}
					first = false;
					friend = entry.getContact();
					result += "\"" + friend.getId() + "\":";
					result += "{\"name\":\"" + friend.getName() + "\"";
					result += ", \"surname\":\"" + friend.getSurname() + "\"";
					result += ", \"email\":\"" + friend.getMail() + "\"";
					result += ", \"id\":\"" + friend.getId() + "\"";
					result += ", \"picturePath\":\"" + friend.getPath() + "\"";
					result += ", \"state\":\"" + ControllerManager.getState(friend.getId()) + "\"";
					result += ", \"blocked\":" + entry.getBlocked() + "}";
				}
			}
			result += "}";
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
