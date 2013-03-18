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
import org.softwaresynthesis.mytalk.server.dao.UserDataDAO;

/**
 * La servlet ha il compito di gestire tutte
 * le operazioni che coinvolgono la rubrica
 * personale di un utente del sistema mytalk
 * 
 * @author 	Andrea Meneghinello
 * @version	%I%, %G%
 */
public class AddressBookManager extends HttpServlet
{
	private static final long serialVersionUID = 10000124L;
	
	/**
	 * Esegue le operazioni come se la servlet
	 * fosse stata chiamata tramite il metodo
	 * POST
	 * 
	 * @param	request 	{@link HttpServletRequest} con i dati
	 * 						provenienti dal client
	 * @param	response	{@link HttpServletResponse} con i dati
	 * 						da fornire al client
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		this.doPost(request, response);
	}
	
	/**
	 *  0 Ottieni i contatti della rubrica
	 *  1 aggiungi contatto alla rubrica
	 *  2 Elimina contatto dalla rubrica
	 *  
	 *  
	 *  @param	request		{@link HttpServletRequest} con i dati
	 *  					provenienti dal client
	 *  @param	response	{@link	HttpServletRequest} con i dati
	 *  					da fornire al client
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		Integer type = -1;
		HttpSession session = request.getSession(false);
		String operation = request.getParameter("operation");
		PrintWriter writer = null;
		String result = null;
		if (operation != null)
		{
			type = Integer.parseInt(operation);
		}
		switch (type)
		{
			case 0:
				{
					if (session != null)
					{
						result = this.doOperationZero(session);
					}
					else
					{
						result = "null";
					}
				}break;
			case 1:
				{
					String obj = request.getParameter("contactId");
					Long contactId = null;
					if (session != null && obj != null)
					{
						contactId = Long.parseLong(obj);
						result = this.doOperationOne(session, contactId);
					}
					else
					{
						result = "false";
					}
				}break;
			case 2:
				{
					String obj = request.getParameter("contactId");
					Long contactId = null;
					if (session != null && obj != null)
					{
						contactId = Long.parseLong(obj);
						result = this.doOperationTwo(session, contactId);
					}
					else
					{
						result = "false";
					}
				}break;
			case 3:
				{
				
				}break;
			case 4:
				{
				
				}break;
			case 5:
				{
				
				}break;
			case 6:
				{
				
				}break;
			case 7:
				{
				
				}break;
			case 8:
				{
				
				}break;
			case 9:
				{
				
				}break;
			case 10:
				{
				}break;
			default:
				{
					result = "null";
				}break;
		}
		writer = response.getWriter();
		writer.write(result);
	}
	
	private String doOperationZero(HttpSession session)
	{
		IAddressBookEntry entry = null;
		Iterator<IAddressBookEntry> iter = null;
		IUserData user = null;
		IUserData friend = null;
		Set<IAddressBookEntry> contacts = null;
		Object obj = null;
		String result = null;
		try
		{
			obj = session.getAttribute("User");
			if (obj instanceof IUserData)
			{
				user = (IUserData)obj;
			}
			contacts = user.getAddressBook();
			iter = contacts.iterator();
			result = "{";
			while(iter.hasNext())
			{
				entry = iter.next();
				friend = entry.getContact();
				result += "\"" + friend.getId() + "\":";
				result += "{\"name\":\"" + friend.getName() + "\"";
				result += ", \"surname\":\"" + friend.getSurname() + "\"";
				result += ", \"email\":\"" + friend.getMail() + "\"";
				result += ", \"id\":\"" + friend.getId() + "\"";
				result += ", \"picturePath\":\"" + friend.getPath() + "\"";
				result += ", \"state\":\"" + ChannelServlet.getState(friend.getId()) + "\"";
				result += ", \"blocked\":\"" + entry.getBlocked() + "\"}";
				if (iter.hasNext())
				{
					result += ",";
				}
			}
		}
		catch (Exception ex)
		{
			result = "null";
		}
		return result;
	}
	
	private String doOperationOne(HttpSession session, Long contactId)
	{
		AddressBookEntry myEntry = null;
		AddressBookEntry friendEntry = null;
		IUserData user = null;
		IUserData friend = null;
		Object obj = null;
		String result = null;
		UserDataDAO userDAO = null;
		try
		{
			obj = session.getAttribute("User");
			if (obj instanceof IUserData)
			{
				userDAO = new UserDataDAO();
				user = (IUserData)obj;
				friend = userDAO.getByID(contactId);
				if (friend != null)
				{
					myEntry = new AddressBookEntry();
					friendEntry = new AddressBookEntry();
					myEntry.setContact(friend);
					myEntry.setBlocked(false);
					friendEntry.setContact(user);
					friendEntry.setBlocked(false);
					user.addAddressBookEntry(myEntry);
					friend.addAddressBookEntry(friendEntry);
					userDAO.update(user);
					userDAO.update(friend);
					result = "true";
				}
			}
		}
		catch (Exception ex)
		{
			result = "false";
		}
		return result;
	}
	
	private String doOperationTwo(HttpSession session, Long contactId)
	{
		AddressBookEntry myEntry = null;
		AddressBookEntry friendEntry = null;
		IUserData user = null;
		IUserData friend = null;
		Object obj = null;
		String result = null;
		UserDataDAO userDAO = null;
		try
		{
			obj = session.getAttribute("User");
			if (obj instanceof IUserData)
			{
				userDAO = new UserDataDAO();
				user = (IUserData)obj;
				friend = userDAO.getByID(contactId);
				if (friend != null)
				{
					myEntry = new AddressBookEntry();
					friendEntry = new AddressBookEntry();
					myEntry.setContact(friend);
					myEntry.setOwner(user);
					friendEntry.setContact(user);
					friendEntry.setOwner(friend);
					user.removeAddressBookEntry(myEntry);
					friend.removeAddressBookEntry(friendEntry);
					userDAO.update(user);
					userDAO.update(friend);
					result = "true";
				}
			}
		}
		catch (Exception ex)
		{
			result = "false";
		}
		return result;
	}
}