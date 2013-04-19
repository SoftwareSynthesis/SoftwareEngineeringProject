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
import org.softwaresynthesis.mytalk.server.dao.CallDAO;
import org.softwaresynthesis.mytalk.server.message.ICall;

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
	 * Restiuisce la lista chiamate
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
		CallDAO callDAO = null;
		List<ICall> calls = null;
		Iterator<ICall> callIter = null;
		ICall call = null;
		String separator = null;
		HttpSession session = null;
		try
		{
			session = request.getSession(false);
			id = (Long)session.getAttribute("id");
			separator = System.getProperty("file.separator");
			callDAO = new CallDAO();
			calls = callDAO.getById(id);
			if (messages != null)
			{
				messageIter = messages.iterator();
				result = "[";
				while (messageIter.hasNext() == true)
				{
					message = messageIter.next();
					result += "{";
					result += "\"id\":\"" + message.getId() + "\"";
					result += ", \"sender\":\"" + message.getSender() + "\"";
					result += ", \"status\":\"" + message.getNewer() + "\"";
					result += ", \"video\":\"" + message.getVideo() + "\"";
					result += ", \"date\":\"" + message.getDate() + "\"";
					result += ", \"src\":\"Secretariat" + separator + message.getId() + ".wav\"";
					if (messageIter.hasNext() == true)
					{
						result += "},";
					}
				}
				result += "}]";
			
			}
			else
			{
				result = "[]";
			}
		}catch(Exception ex)
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