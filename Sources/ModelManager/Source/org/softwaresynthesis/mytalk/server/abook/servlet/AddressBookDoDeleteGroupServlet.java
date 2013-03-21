package org.softwaresynthesis.mytalk.server.abook.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServlet;
import org.softwaresynthesis.mytalk.server.abook.IAddressBookEntry;
import org.softwaresynthesis.mytalk.server.abook.IGroup;
import org.softwaresynthesis.mytalk.server.dao.AddressBookEntryDAO;
import org.softwaresynthesis.mytalk.server.dao.GroupDAO;

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
		AddressBookEntryDAO entryDAO = new AddressBookEntryDAO();
		GroupDAO groupDAO = new GroupDAO();
		IGroup group = null;
		IAddressBookEntry entry = null;
		Iterator<IAddressBookEntry> iterator = null;
		Long groupId = null;
		PrintWriter writer = response.getWriter();
		Set<IAddressBookEntry> entrys = null;
		String result = null;
		try
		{
			groupId = Long.parseLong(request.getParameter("groupId"));
			group = groupDAO.getByID(groupId);
			if (group != null)
			{
				entrys = group.getAddressBook();
				iterator = entrys.iterator();
				while (iterator.hasNext() == true)
				{
					entry = iterator.next();
					entry.setGroup(null);
					entryDAO.update(entry);
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
		writer.write(result);
	}
}