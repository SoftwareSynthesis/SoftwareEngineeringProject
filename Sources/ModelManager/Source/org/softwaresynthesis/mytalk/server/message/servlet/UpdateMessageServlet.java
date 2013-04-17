package org.softwaresynthesis.mytalk.server.message.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.softwaresynthesis.mytalk.server.dao.MessageDAO;
import org.softwaresynthesis.mytalk.server.message.IMessage;

/**
 * Servlet implementation class UpdateMessageServlet
 */
@WebServlet("/UpdateMessageServlet")
public class UpdateMessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1000058L;
       
    /**
     * Inizializza la servlet
     */
    public UpdateMessageServlet() {
        super();
    }

    /**
	 * Modifica un messaggio in segreteria
	 * tramite richiesta HTTP GET
	 * 
	 * @param	request		contiene i parametri di input
	 * 						(msgSegreteria, stato) per il
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
	 * Modifica un messaggio in segreteria
	 * tramite richiesta HTTP POST
	 * 
	 * @param	request		contiene i parametri di input
	 * 						(msgSegreteria, stato) per il
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
		MessageDAO messageDAO = null;
		IMessage message = null;
		Long stato = null;
		try
		{
			id = Long.parseLong(request.getParameter("id"));
			stato = Long.parseLong(request.getParameter("valueToSet"));
			messageDAO = new MessageDAO();
			message = messageDAO.getByID(id);
			if (message != null){
				message.setNewer(stato);
				messageDAO.update(message);
				result = "true";
			}
			else
			{
				result = "false";
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
