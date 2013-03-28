package org.softwaresynthesis.mytalk.server.abook.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServlet;
import org.softwaresynthesis.mytalk.server.abook.AddressBookEntry;
import org.softwaresynthesis.mytalk.server.abook.IGroup;
import org.softwaresynthesis.mytalk.server.abook.IUserData;
import org.softwaresynthesis.mytalk.server.dao.AddressBookEntryDAO;
import org.softwaresynthesis.mytalk.server.dao.GroupDAO;
import org.softwaresynthesis.mytalk.server.dao.UserDataDAO;

/**
 * Servlet cha ha il compito di aggiungere
 * alla rubrica un nuovo contatto
 * 
 * @author 	Andrea Meneghinello
 * @version	1.0
 */
public final class AddressBookDoAddContactServlet extends HttpServlet 
{
	private static final long serialVersionUID = 10011L;
	
	/**
	 * Inizializza la servlet
	 */
	public AddressBookDoAddContactServlet()
	{
		super();
	}
	
	/**
	 * Esegue la richiesta di per l'aggiunta di un
	 * nuovo contatto alla rubrica ricevuta tramite
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
	
	/**
	 * Esegue la richiesta di per l'aggiunta di un
	 * nuovo contatto alla rubrica ricevuta tramite
	 * richiesta HTTP POST
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
		AddressBookEntry myEntry = null;
		AddressBookEntry frEntry = null;
		AddressBookEntryDAO entryDAO = null;
		IGroup frGroup = null;
		IGroup myGroup = null;
		HttpSession session = null;
		long contactId = -1L;
		IUserData user = null;
		IUserData friend = null;
		PrintWriter writer = null;
		GroupDAO groupDAO = null;
		String mail = null;
		String result = null;
		UserDataDAO userDAO = null;
		try
		{
			session = request.getSession(false);
			contactId = Long.parseLong(request.getParameter("contactId"));
			mail = (String)session.getAttribute("username");
			userDAO = new UserDataDAO();
			user = userDAO.getByEmail(mail);
			friend = userDAO.getByID(contactId);
			if (friend != null)
			{
				myEntry = new AddressBookEntry();
				frEntry = new AddressBookEntry();
				groupDAO = new GroupDAO();
				myGroup = groupDAO.getByOwnerAndName(user.getId(), "addrBookEntry");
				myEntry.setContact(friend);
				myEntry.setGroup(myGroup);
				myEntry.setBlocked(false);
				frGroup = groupDAO.getByOwnerAndName(friend.getId(), "addrBookEntry");
				frEntry.setBlocked(false);
				frEntry.setContact(user);
				frEntry.setGroup(frGroup);
				user.addAddressBookEntry(myEntry);
				friend.addAddressBookEntry(frEntry);
				userDAO.update(user);
				userDAO.update(friend);
//				entryDAO = new AddressBookEntryDAO();
//				entryDAO.insert(myEntry);
//				entryDAO.insert(frEntry);
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
			Prova.Scrivi(ex.getMessage());
		}
		finally
		{
			writer = response.getWriter();
			writer.write(result);
		}
	}
}