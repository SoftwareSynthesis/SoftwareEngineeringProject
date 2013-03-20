package org.softwaresynthesis.mytalk.server.abook;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.softwaresynthesis.mytalk.server.connection.ChannelServlet;

/**
 * Servlet che ha il compito di fornire
 * la rubrica di un utente del sistema
 * mytalk
 * 
 * @author 	Andrea Meneghinello
 * @version	%I%, %G%
 */
public final class AddressBookGetContactsServlet extends HttpServlet 
{
	private static final long serialVersionUID = 10011L;
	
	/**
	 * Inizializza la servlet
	 */
	public AddressBookGetContactsServlet()
	{
		super();
	}
	
	/**
	 * Esegue la richiesta di per il download
	 * della rubrica ricevuta tramite richiesta
	 * HTTP GET
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
	 * Esegue la richiesta di per il download
	 * della rubrica ricevuta tramite richiesta
	 * HTTP POST
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
		boolean next = false;
		HttpSession session = null;
		IAddressBookEntry entry = null;
		Iterator<IAddressBookEntry> iterator = null;
		IUserData user = null;
		IUserData friend = null;
		PrintWriter writer = response.getWriter();
		Set<IAddressBookEntry> contacts = null;
		String result = null;
		try
		{
			session = request.getSession(false);
			user = (IUserData)session.getAttribute("user");
			contacts = user.getAddressBook();
			iterator = contacts.iterator();
			result = "{";
			while(next = iterator.hasNext())
			{
				entry = iterator.next();
				friend = entry.getContact();
				result += "\"" + friend.getId() + "\":";
				result += "{\"name\":\"" + friend.getName() + "\"";
				result += ", \"surname\":\"" + friend.getSurname() + "\"";
				result += ", \"email\":\"" + friend.getMail() + "\"";
				result += ", \"id\":\"" + friend.getId() + "\"";
				result += ", \"picturePath\":\"" + friend.getPath() + "\"";
				result += ", \"state\":\"" + ChannelServlet.getState(friend.getId()) + "\"";
				result += ", \"blocked\":\"" + entry.getBlocked() + "\"}";
				if (next == true)
				{
					result += ",";
				}
			}
			result = "}";
		}
		catch (Exception ex)
		{
			result = "null";
		}
		writer.write(result);
	}
}