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
 * Servlet cha ha il compito di rimuovere
 * un contatto dalla rubrica di un utente
 * 
 * @author 	Andrea Meneghinello
 * @version	1.0
 */
public final class AddressBookDoRemoveContactServlet extends HttpServlet 
{
	private static final long serialVersionUID = 10012L;
	
	/**
	 * Inizializza la servlet
	 */
	public AddressBookDoRemoveContactServlet()
	{
		super();
	}
	
	/**
	 * Esegue la richiesta di per l'eliminazione
	 * di un conttatto della rubrica ricevuta tramite
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
	 * Esegue la richiesta di per l'eliminazione
	 * di un conttatto della rubrica ricevuta tramite
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
		PrintWriter writer = response.getWriter();
		Set<IAddressBookEntry> entrys = null;
		String result = null;
		UserDataDAO userDAO = new UserDataDAO();
		try
		{
			session = request.getSession(false);
			user = (IUserData)session.getAttribute("user");
			contactId = Long.parseLong(request.getParameter("contactId"));
			friend = userDAO.getByID(contactId);
			if (friend != null)
			{
				entrys = user.getAddressBook();
				iterator = entrys.iterator();
				while (iterator.hasNext() == true)
				{
					entry = iterator.next();
					if (entry.getContact().equals(friend) == true && entry.getOwner().equals(user) == true)
					{
						user.removeAddressBookEntry(entry);
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
		writer.write(result);
	}
}