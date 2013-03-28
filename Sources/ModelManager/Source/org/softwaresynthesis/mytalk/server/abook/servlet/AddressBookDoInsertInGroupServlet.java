package org.softwaresynthesis.mytalk.server.abook.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServlet;
import org.softwaresynthesis.mytalk.server.abook.AddressBookEntry;
import org.softwaresynthesis.mytalk.server.abook.IAddressBookEntry;
import org.softwaresynthesis.mytalk.server.abook.IGroup;
import org.softwaresynthesis.mytalk.server.abook.IUserData;
import org.softwaresynthesis.mytalk.server.dao.GroupDAO;
import org.softwaresynthesis.mytalk.server.dao.UserDataDAO;

/**
 * Servlet cha ha il compito di aggiungere
 * un contatto ad un gruppo
 * 
 * @author 	Andrea Meneghinello
 * @version	1.0
 */
public final class AddressBookDoInsertInGroupServlet extends HttpServlet 
{
	private static final long serialVersionUID = 10013L;
	
	/**
	 * Inizializza la servlet
	 */
	public AddressBookDoInsertInGroupServlet()
	{
		super();
	}
	
	/**
	 * Esegue la richiesta di per l'aggiunta di un
	 * contatto ad un gruppo tramite richiesta HTTP GET
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
	 * contatto ad un gruppo tramite richiesta HTTP POST
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
		GroupDAO groupDAO = null;
		HttpSession session = null;
		IAddressBookEntry entry = null;
		IGroup group = null;
		IUserData friend = null;
		IUserData user = null;
		Long contactId = null;
		Long groupId = null;
		PrintWriter writer = null;
		String mail = null;
		String result = null;
		UserDataDAO userDAO = null;
		try
		{
			session = request.getSession(false);
			mail = (String)session.getAttribute("username");
			userDAO = new UserDataDAO();
			groupDAO = new GroupDAO();
			contactId = Long.parseLong(request.getParameter("contactId"));
			groupId = Long.parseLong(request.getParameter("groupId"));
			user = userDAO.getByEmail(mail);
			friend = userDAO.getByID(contactId);
			group = groupDAO.getByID(groupId);
			if (group != null)
			{
				entry = new AddressBookEntry();
				entry.setBlocked(false);
				entry.setOwner(user);
				entry.setContact(friend);
				entry.setGroup(group);
				user.addAddressBookEntry(entry);
				userDAO.update(user);
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