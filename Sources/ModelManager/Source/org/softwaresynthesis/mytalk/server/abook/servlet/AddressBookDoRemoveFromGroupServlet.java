package org.softwaresynthesis.mytalk.server.abook.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.softwaresynthesis.mytalk.server.abook.AddressBookEntry;
import org.softwaresynthesis.mytalk.server.abook.IAddressBookEntry;
import org.softwaresynthesis.mytalk.server.abook.IGroup;
import org.softwaresynthesis.mytalk.server.abook.IUserData;
import org.softwaresynthesis.mytalk.server.dao.GroupDAO;
import org.softwaresynthesis.mytalk.server.dao.UserDataDAO;

/**
 * Servlet cha ha il compito di rimuovere
 * un contatto da un gruppo
 * 
 * @author 	Andrea Meneghinello
 * @version	1.0
 */
public final class AddressBookDoRemoveFromGroupServlet extends HttpServlet {
	private static final long serialVersionUID = 10014L;
       
    /**
     * Inizializza la servlet
     */
    public AddressBookDoRemoveFromGroupServlet() 
    {
        super();
    }

    /**
	 * Esegue la richiesta di per la rimozione di un
	 * contatto da un gruppo tramite richiesta HTTP GET
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		this.doPost(request, response);
	}

	/**
	 * Esegue la richiesta di per la rimozione di un
	 * contatto da un gruppo tramite richiesta HTTP POST
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
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
			contactId = Long.parseLong(request.getParameter("contactId"));
			groupId = Long.parseLong(request.getParameter("groupId"));
			userDAO = new UserDataDAO();
			groupDAO = new GroupDAO();
			user = userDAO.getByEmail(mail);
			friend = userDAO.getByID(contactId);
			group = groupDAO.getByID(groupId);
			if (group != null)
			{
				// FIXME sei sicuro che sia il modo giusto di fare le cose?
				// dopo l'update(user) la AddressBookEntry è bella che rimasta nel database!
				entry = new AddressBookEntry();
				entry.setBlocked(false);
				entry.setOwner(user);
				entry.setContact(friend);
				entry.setGroup(group);
				user.removeAddressBookEntry(entry);
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