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
			dao = getDAOFactory();
			email = getUserMail();
			myUser = dao.getUserData(email);
			contacts = myUser.getAddressBook();
			iterator = contacts.iterator();
			result = "{";
			first = true;
			while(iterator.hasNext() == true)
			{
				entry = iterator.next();
				if (entry.getGroup().getName().equals("addrBookEntry")) {
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
					result += ", \"state\":\"" + getContactState(friend) + "\"";
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
	
	/**
	 * Restituisce la rappresentazione a stringa dello stato del contatto
	 * 
	 * @param contact
	 *            il contatto di cui deve essere recuperato lo stato
	 * @return una stringa che identifica lo stato
	 */
	String getContactState(IUserData contact)
	{
		String state = ControllerManager.getState(contact.getId());
		return state;
	}
}
