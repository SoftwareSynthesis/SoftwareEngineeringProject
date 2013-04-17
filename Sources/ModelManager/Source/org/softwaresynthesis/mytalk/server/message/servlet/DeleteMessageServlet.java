package org.softwaresynthesis.mytalk.server.message.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.softwaresynthesis.mytalk.server.dao.MessageDAO;
import org.softwaresynthesis.mytalk.server.message.IMessage;

/**
 * Servlet implementation class DeleteMessageServlet
 */
@WebServlet("/DeleteMessageServlet")
public class DeleteMessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1000056L;
       
    /**
     * inizializzazione della servlet
     */
    public DeleteMessageServlet() {
        super();
    }

    /**
	 * Elimina un messaggio in segreteria
	 * tramite richiesta HTTP GET
	 * 
	 * @param	request		contiene i parametri di input
	 * 						(idMessage) per il
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
	 * Elimina un messagggio in segreteria di un utente
	 * tramite richiesta HTTP POST
	 * 
	 * @param	request		contiene i parametri di input
	 * 						(idMessage) per il
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
		File file = null;
		String path = null;
		String separator = null;
		try
		{
			id = Long.parseLong(request.getParameter("idMessage"));
			messageDAO = new MessageDAO();
			message = messageDAO.getByID(id);
			if (message != null){
				messageDAO.delete(message);
				path = System.getenv("MyTalkConfiguration");
				separator = System.getProperty("file.separator");
				path += separator + "MyTalk" + separator + "Secretariat" + separator;
				path += id.toString() + ".wav";
				file = new File(path);
				if(!(file.delete()))
				{
					result = "false";
				}
				else
				{
						result = "true";
				}
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
