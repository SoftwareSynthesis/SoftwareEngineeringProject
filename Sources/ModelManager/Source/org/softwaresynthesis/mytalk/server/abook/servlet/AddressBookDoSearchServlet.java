package org.softwaresynthesis.mytalk.server.abook.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServlet;
import org.softwaresynthesis.mytalk.server.abook.IUserData;
import org.softwaresynthesis.mytalk.server.dao.UserDataDAO;

/**
 * Servlet cha ha il compito di effetture una
 * ricerca di utenti nel database
 * 
 * @author 	Andrea Meneghinello
 * @version	1.0
 */
public class AddressBookDoSearchServlet extends HttpServlet 
{
	private static final long serialVersionUID = 100110L;
	
	/*
	 * Inizializza la servlet
	 */
	public AddressBookDoSearchServlet()
	{
		super();
	}
	
	/**
	 * Esegue la richiesta di per la ricerca di
	 * utenti ricevuta tramite richiesta HTTP GET
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
	 * Esegue la richiesta di per la ricerca di
	 * utenti ricevuta tramite richiesta HTTP POST
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
		Iterator<IUserData> iterator = null;
		IUserData entry = null;
		List<IUserData> users = null;
		PrintWriter writer = null;
		String parameter = null;
		String result = null;
		UserDataDAO userDAO = null;
		try
		{
			parameter = request.getParameter("param");
			userDAO = new UserDataDAO();
			users = userDAO.searchGeneric(parameter);
			iterator = users.iterator();
			result = "{";
			while(iterator.hasNext() == true)
			{
				entry = iterator.next();
				result += "\"" + entry.getId() + "\":";
				result += "{\"name\":\"" + entry.getName() + "\"";
				result += ", \"surname\":\"" + entry.getSurname() + "\"";
				result += ", \"email\":\"" + entry.getMail() + "\"";
				result += ", \"id\":\"" + entry.getId() + "\"";
				result += ", \"picturePath\":\"" + entry.getPath() + "\"";
				result += ", \"state\":\"offline\"";
				result += ", \"blocked\":\"false\"}";
				if (iterator.hasNext() == true)
				{
					result += ",";
				}
			}
			result = "}";
		}
		catch (Exception ex)
		{
			result = "false";
		}
		writer = response.getWriter();
		writer.write(result);
	}
}