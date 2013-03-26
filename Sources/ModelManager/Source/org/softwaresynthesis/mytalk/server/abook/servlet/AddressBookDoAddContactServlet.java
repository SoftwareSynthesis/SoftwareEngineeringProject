package org.softwaresynthesis.mytalk.server.abook.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServlet;
import org.softwaresynthesis.mytalk.server.abook.AddressBookEntry;
import org.softwaresynthesis.mytalk.server.abook.Group;
import org.softwaresynthesis.mytalk.server.abook.IGroup;
import org.softwaresynthesis.mytalk.server.abook.IUserData;
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
		IGroup frGroup = null;
		IGroup myGroup = null;
		HttpSession session = null;
		long contactId = -1L;
		IUserData user = null;
		IUserData friend = null;
		PrintWriter writer = response.getWriter();
		GroupDAO groupDAO = null;
		UserDataDAO userDAO = new UserDataDAO();
		String result = null;
		try
		{
			Prova.Scrivi("AVVIO");
			session = request.getSession(false);
			Prova.Scrivi("SESSIONE");
			contactId = Long.parseLong(request.getParameter("contactId"));
			Prova.Scrivi("ID");
			user = (IUserData)session.getAttribute("user");
			Prova.Scrivi("USER");
			friend = userDAO.getByID(contactId);
			Prova.Scrivi("AMICO");
			if (friend != null)
			{
				myEntry = new AddressBookEntry();
				frEntry = new AddressBookEntry();
				groupDAO = new GroupDAO();
				myGroup = new Group();
				myGroup.setName("addrBookEntry");
				myGroup.setOwner(user);
				myEntry.setBlocked(false);
				myEntry.setContact(friend);
				myEntry.setGroup(myGroup);
				frGroup = groupDAO.getByOwnerAndName(user.getId(), "");
				frEntry.setBlocked(false);
				frEntry.setContact(user);
				frEntry.setGroup(frGroup);
				Prova.Scrivi("AVVIO INSERIMENTI");
				user.addAddressBookEntry(myEntry);
				Prova.Scrivi("NEL MEZZO");
				friend.addAddressBookEntry(frEntry);
				Prova.Scrivi("AMICO NON NULLO");
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
			Prova.Scrivi(ex.getMessage());
		}
		writer.write(result);
	}
}