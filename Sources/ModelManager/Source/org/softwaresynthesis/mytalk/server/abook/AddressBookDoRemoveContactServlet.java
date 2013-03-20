package org.softwaresynthesis.mytalk.server.abook;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.softwaresynthesis.mytalk.server.dao.UserDataDAO;

/**
 * Servlet cha ha il compito di rimuovere
 * un contatto dalla rubrica di un utente
 * 
 * @author 	Andrea Meneghinello
 * @version	%I%, %G%
 */
public final class AddressBookDoRemoveContactServlet extends HttpServlet 
{
	private static final long serialVersionUID = 10013L;
	
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
		IAddressBookEntry myEntry = null;
		IAddressBookEntry frEntry = null;
		IUserData user = null;
		IUserData friend = null;
		Long contactId = null;
		PrintWriter writer = response.getWriter();
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
				myEntry = new AddressBookEntry();
				frEntry = new AddressBookEntry();
				myEntry.setContact(friend);
				myEntry.setOwner(user);
				frEntry.setContact(user);
				frEntry.setOwner(friend);
				user.removeAddressBookEntry(frEntry);
				friend.removeAddressBookEntry(myEntry);
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