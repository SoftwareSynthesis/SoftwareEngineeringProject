package org.softwaresynthesis.mytalk.server.abook.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;

import org.softwaresynthesis.mytalk.server.abook.IAddressBookEntry;
import org.softwaresynthesis.mytalk.server.abook.IGroup;
import org.softwaresynthesis.mytalk.server.abook.IUserData;
import org.softwaresynthesis.mytalk.server.dao.AddressBookEntryDAO;
import org.softwaresynthesis.mytalk.server.dao.GroupDAO;
import org.softwaresynthesis.mytalk.server.dao.UserDataDAO;

/**
 * Servlet cha ha il compito di eliminare
 * un gruppo della rubrica
 * 
 * @author 	Andrea Meneghinello
 * @version	1.0
 */
public final class AddressBookDoDeleteGroupServlet extends HttpServlet 
{
	private static final long serialVersionUID = 10016L;
	
	/**
	 * Inizializzazione della servlet
	 */
	public AddressBookDoDeleteGroupServlet()
	{
		super();
	}
	
	/**
	 * Esegue la richiesta di per l'eliminazione di un
	 * gruppo tramite richiesta HTTP GET
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
	
	/**
	 * Esegue la richiesta di per l'eliminazione di un
	 * gruppo tramite richiesta HTTP POST
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		AddressBookEntryDAO entryDAO = null;
		GroupDAO groupDAO = null;
		HttpSession session = null;
		IGroup group = null;
		IAddressBookEntry entry = null;
		Iterator<IAddressBookEntry> iterator = null;
		IUserData user = null;
		Long groupId = null;
		PrintWriter writer = null;
		Set<IAddressBookEntry> entrys = null;
		String mail = null;
		String result = null;
		UserDataDAO userDAO = null;
		try
		{
			groupId = Long.parseLong(request.getParameter("groupId"));
			session = request.getSession(false);
			mail = (String)session.getAttribute("username");
			userDAO = new UserDataDAO();
			user = userDAO.getByEmail(mail);
			groupDAO = new GroupDAO();
			group = groupDAO.getByID(groupId);
			if (group != null)
			{
				if (group.getOwner().equals(user) == false)
				{
					throw new Exception();
				}
				entrys = group.getAddressBook();
				iterator = entrys.iterator();
				entryDAO = new AddressBookEntryDAO();
				while (iterator.hasNext() == true)
				{
					entry = iterator.next();
					entryDAO.delete(entry);
				}
				groupDAO.delete(group);
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
		finally
		{
			writer = response.getWriter();
			writer.write(result);
		}
	}
}