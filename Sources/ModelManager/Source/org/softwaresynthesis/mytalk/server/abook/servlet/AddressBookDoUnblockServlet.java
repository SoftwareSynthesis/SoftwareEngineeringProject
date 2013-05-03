package org.softwaresynthesis.mytalk.server.abook.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.softwaresynthesis.mytalk.server.abook.IAddressBookEntry;
import org.softwaresynthesis.mytalk.server.abook.IUserData;
import org.softwaresynthesis.mytalk.server.dao.UserDataDAO;

/**
 * Servlet che ha il compito di sbloccare un
 * contatto della rubrica
 * 
 * @author  Andrea Meneghinello
 * @version	1.0
 */
public final class AddressBookDoUnblockServlet extends HttpServlet 
{
	private static final long serialVersionUID = 10019L;
	
	/**
	 * Inizializza la servlet
	 */
	public AddressBookDoUnblockServlet()
	{
		super();
	}
	
	/**
	 * Esegue la richiesta di per lo sblocco di un
	 * contatto alla rubrica ricevuta tramite
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
	 * Esegue la richiesta di per lo sblocco di un
	 * contatto alla rubrica ricevuta tramite
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
		HttpSession session = null;
		IAddressBookEntry entry = null;
		Iterator<IAddressBookEntry> iterator = null;
		IUserData user = null;
		IUserData friend = null;
		Long contactId = null;
		PrintWriter writer = null;
		Set<IAddressBookEntry> entrys = null;
		String mail = null;
		String result = null;
		UserDataDAO userDAO = null;
		try
		{
			session = request.getSession(false);
			mail = (String)session.getAttribute("username");
			contactId = Long.parseLong(request.getParameter("contactId"));
			userDAO = new UserDataDAO();
			user = userDAO.getByEmail(mail);
			friend = userDAO.getByID(contactId);
			if (friend != null)
			{
				entrys = user.getAddressBook();
				iterator = entrys.iterator();
				while (iterator.hasNext() == true)
				{
					entry = iterator.next();
					if (entry.getContact().equals(friend) == true)
					{
						entry.setBlocked(false);
					}
				}
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