package org.softwaresynthesis.mytalk.server.abook.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServlet;
import org.softwaresynthesis.mytalk.server.abook.IAddressBookEntry;
import org.softwaresynthesis.mytalk.server.abook.IGroup;
import org.softwaresynthesis.mytalk.server.abook.IUserData;
import org.softwaresynthesis.mytalk.server.dao.GroupDAO;
import org.softwaresynthesis.mytalk.server.dao.UserDataDAO;

/**
 * Servlet cha ha il compito di ottenere
 * i gruppi delle propria rubrica con i
 * contatti presenti in essi
 * 
 * @author 	Andrea Meneghinello
 * @version	1.0
 */
public final class AddressBookGetGroupsServlet extends HttpServlet 
{
	private static final long serialVersionUID = 10017L;
	
	/**
	 * Inizializza la servlet
	 */
	public AddressBookGetGroupsServlet()
	{
		super();
	}
	
	/**
	 * Esegue la richiesta di per ottenere i gruppi
	 * ed i relativi contatti ricevuta tramite
	 * richiesta HTTP GET
	 * 
	 * @param	request		contiene i parametri di input
	 * 						per il corretto svolgimento dell'operazione	
	 * @param	response	contiene le risposte prodotte dalla
	 * 						servlet che verranno inviate ai client
	 * @throws	{@link IOException} se si verificano errori durante
	 * 			operazioni di IO
	 * @throws	{@link ServletException} se si verificano errori
	 * 			interni alla servlet
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		this.doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		GroupDAO groupDAO = null;
		HttpSession session = null;
		IAddressBookEntry entry = null;
		IGroup group = null;
		Iterator<IAddressBookEntry> entryIter = null;
		Iterator<IGroup> groupIter = null;
		IUserData user = null;
		List<IGroup> groups = null;
		PrintWriter writer = null;
		Set<IAddressBookEntry> addEntry = null;
		String mail = null;
		String result = null;
		UserDataDAO userDAO = null;
		try
		{
			session = request.getSession(false);
			mail = (String)session.getAttribute("username");
			userDAO = new UserDataDAO();
			groupDAO = new GroupDAO();
			user = userDAO.getByEmail(mail);
			groups = groupDAO.getByOwner(user.getId());
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
						result += entry.getId().toString();
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