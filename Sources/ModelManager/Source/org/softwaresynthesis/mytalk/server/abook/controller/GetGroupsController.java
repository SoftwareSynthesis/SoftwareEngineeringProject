package org.softwaresynthesis.mytalk.server.abook.controller;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.softwaresynthesis.mytalk.server.AbstractController;
import org.softwaresynthesis.mytalk.server.abook.IAddressBookEntry;
import org.softwaresynthesis.mytalk.server.abook.IGroup;
import org.softwaresynthesis.mytalk.server.abook.IUserData;
import org.softwaresynthesis.mytalk.server.dao.DataPersistanceManager;

public class GetGroupsController extends AbstractController{
	/**
	 * Ritorna tutti i gruppi
	 * di un contatto
	 */
	@Override
	protected void doAction(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		DataPersistanceManager dao = null;
		String result = null;
		PrintWriter writer = null;
		String email = null;
		IUserData myUser = null;
		IGroup group = null;
		Iterator<IGroup> groupIter = null;
		List<IGroup> groups = null;
		Set<IAddressBookEntry> addEntry = null;
		Iterator<IAddressBookEntry> entryIter = null;
		IAddressBookEntry entry = null;
		
		try
		{
			dao = getDAOFactory();
			email = getUserMail();
			myUser = dao.getUserData(email);
			groups = dao.getGroup(myUser);
			if (groups != null)
			{
				groupIter = groups.iterator();
				result = "{";
				while (groupIter.hasNext() == true)
				{
					group = groupIter.next();
					addEntry = group.getAddressBook();
					entryIter = addEntry.iterator();
					result += "\"" + group.getId() + "\":{";
					result += "\"name\":" + "\"" + group.getName() + "\",";
					result += "\"id\":" + "\"" + group.getId() + "\",";
					result += "\"contacts\":[";
					while (entryIter.hasNext() == true)
					{
						entry = entryIter.next();
						result += entry.getContact().getId().toString();
						if (entryIter.hasNext() == true)
						{
							result += ",";
						}
					}
					result += "]}";
					if (groupIter.hasNext())
					{
						result += ",";
					}
				}
				result += "}";
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
		catch (Throwable ex)
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
