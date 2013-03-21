package org.softwaresynthesis.mytalk.server.abook.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.softwaresynthesis.mytalk.server.abook.Group;
import org.softwaresynthesis.mytalk.server.abook.IGroup;
import org.softwaresynthesis.mytalk.server.abook.IUserData;
import org.softwaresynthesis.mytalk.server.dao.GroupDAO;

/**
 * Servlet che ha il compito di inserire
 * un nuovo gruppo nella rubrica di un utente
 * 
 * @author 	Andrea Meneghinello
 * @version	%I%, %G%
 */
public final class AddressBookDoCreateGroupServlet extends HttpServlet 
{
	private final static long serialVersionUID = 10015L;
	
	/**
	 * Inizializza la servlet
	 */
	public AddressBookDoCreateGroupServlet()
	{
		super();
	}
	
	/**
	 * Esegue la richiesta di per l'inserimento di un
	 * nuovo gruppo tramite richiesta HTTP GET
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
	 * Esegue la richiesta di per l'inserimento di un
	 * nuovo gruppo tramite richiesta HTTP POST
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
		GroupDAO groupDAO = new GroupDAO();
		HttpSession session = null;
		IGroup group = null;
		IUserData user = null;
		PrintWriter writer = response.getWriter();
		String name = null;
		String result = null;
		try
		{
			session = request.getSession(false);
			user = (IUserData)session.getAttribute("user");
			name = request.getParameter("groupName");
			if(name != null && name.equals("") == false)
			{
				group = new Group();
				group.setName(name);
				group.setOwner(user);
				groupDAO.insert(group);
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