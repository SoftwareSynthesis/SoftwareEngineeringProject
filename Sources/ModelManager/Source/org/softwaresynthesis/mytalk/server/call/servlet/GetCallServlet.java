package org.softwaresynthesis.mytalk.server.call.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.softwaresynthesis.mytalk.server.abook.IUserData;
import org.softwaresynthesis.mytalk.server.call.ICallList;
import org.softwaresynthesis.mytalk.server.dao.CallListDAO;
import org.softwaresynthesis.mytalk.server.dao.UserDataDAO;

/**
 * Servlet implementation class GetCallServlet
 */
@WebServlet("/GetCallServlet")
public class GetCallServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1000060L;
       
    /**
     * inizializzazione della servlet
     */
    public GetCallServlet() {
        super();
    }

    /**
	 * Restiuisce la lista chiamate
	 * di un utente tramite richiesta HTTP GET
	 * 
	 * @param	request		contiene i parametri di input per il
	 * 						corretto svolgimento dell'operazione	
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
	 * Restituisce la lista chiamate
	 * di un utente tramite richiesta HTTP POST
	 * 
	 * @param	request		contiene i parametri di input per il
	 * 						corretto svolgimento dell'operazione	
	 * @param	response	contiene le risposte prodotte dalla
	 * 						servlet che verranno inviate ai client
	 * @throws	{@link IOException} se si verificano errori durante
	 * 			operazioni di IO
	 * @throws	{@link ServletException} se si verificano errori
	 * 			interni alla servlet
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String result = null;
		PrintWriter writer = null;
		Long id = null;
		CallListDAO callListDAO = null;
		//CallDAO callDAO = null;
		List<ICallList> callsList = null;
		IUserData myUser = null;
		UserDataDAO userDataDAO = null;
		Iterator<ICallList> callListIter = null;
		ICallList callList = null;
		HttpSession session = null;
		try
		{
			session = request.getSession(false);
			id = (Long)session.getAttribute("id");
			callListDAO = new CallListDAO();
			userDataDAO = new UserDataDAO();
			myUser = userDataDAO.getByID(id);
			callsList = callListDAO.getByUser(myUser);
			if (callsList != null){
				callListIter = callsList.iterator();
				result = "[";
				while (callListIter.hasNext() == true)
				{
					callList = callListIter.next();
					result += "{";
					result += "\"name\":\"" + callList.getIdUser().getName() + " " + callList.getIdUser().getSurname() + "\"";
					result += ", \"start\":\"" + callList.getIdCall().getStartDate() + "\"";
					result += ", \"end\":\"" + callList.getIdCall().getEndDate() + "\"";
					result += ", \"caller\":\"" + callList.getCaller() + "\"";
					if (callListIter.hasNext() == true)
					{
						result += "},";
					}
				}
				result += "}]";
			}
			result = "[]";
		}
		catch(Exception ex)
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