package org.softwaresynthesis.mytalk.server.abook;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServlet;

import org.softwaresynthesis.mytalk.server.dao.UserDataDAO;

public class AddressBookDoAddContactServlet extends HttpServlet 
{
	private static final long serialVersionUID = 10012L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		this.doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		AddressBookEntry myEntry = null;
		AddressBookEntry frEntry = null;
		HttpSession session = null;
		long contactId = -1L;
		IUserData user = null;
		IUserData friend = null;
		PrintWriter writer = response.getWriter();
		UserDataDAO userDAO = new UserDataDAO();
		String result = null;
		try
		{
			session = request.getSession(false);
			contactId = Long.parseLong(request.getParameter("contactId"));
			user = (IUserData)session.getAttribute("user");
			friend = userDAO.getByID(contactId);
			if (friend != null)
			{
				myEntry = new AddressBookEntry();
				frEntry = new AddressBookEntry();
				myEntry.setBlocked(false);
				myEntry.setContact(friend);
				myEntry.setGroup(null);
				frEntry.setBlocked(false);
				frEntry.setContact(user);
				frEntry.setGroup(null);
				user.addAddressBookEntry(myEntry);
				friend.addAddressBookEntry(frEntry);
				userDAO.update(user);
				userDAO.update(friend);
				result = "true";
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
		writer.write(result);
	}
}