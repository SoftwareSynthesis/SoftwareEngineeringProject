package org.softwaresynthesis.mytalk.server.abook;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.softwaresynthesis.mytalk.server.connection.ChannelServlet;

public class AddressBookGetContactsServlet extends HttpServlet 
{
	private static final long serialVersionUID = 10011L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		this.doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		boolean next = false;
		HttpSession session = null;
		IAddressBookEntry entry = null;
		Iterator<IAddressBookEntry> iterator = null;
		IUserData user = null;
		IUserData friend = null;
		PrintWriter writer = response.getWriter();
		Set<IAddressBookEntry> contacts = null;
		String result = null;
		try
		{
			session = request.getSession(false);
			user = (IUserData)session.getAttribute("user");
			contacts = user.getAddressBook();
			iterator = contacts.iterator();
			result = "{";
			while(next = iterator.hasNext())
			{
				entry = iterator.next();
				friend = entry.getContact();
				result += "\"" + friend.getId() + "\":";
				result += "{\"name\":\"" + friend.getName() + "\"";
				result += ", \"surname\":\"" + friend.getSurname() + "\"";
				result += ", \"email\":\"" + friend.getMail() + "\"";
				result += ", \"id\":\"" + friend.getId() + "\"";
				result += ", \"picturePath\":\"" + friend.getPath() + "\"";
				result += ", \"state\":\"" + ChannelServlet.getState(friend.getId()) + "\"";
				result += ", \"blocked\":\"" + entry.getBlocked() + "\"}";
				if (next == true)
				{
					result += ",";
				}
			}
			result = "}";
		}
		catch (Exception ex)
		{
			result = "null";
		}
		writer.write(result);
	}
}