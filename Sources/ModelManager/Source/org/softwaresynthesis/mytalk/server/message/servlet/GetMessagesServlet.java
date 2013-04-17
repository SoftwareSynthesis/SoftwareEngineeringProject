package org.softwaresynthesis.mytalk.server.message.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.softwaresynthesis.mytalk.server.dao.MessageDAO;
import org.softwaresynthesis.mytalk.server.message.IMessage;

/**
 * Servlet implementation class GetMessagesServlet
 */
@WebServlet("/GetMessagesServlet")
public class GetMessagesServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1000057L;
       
    /**
     * inizializzazione della servlet
     */
    public GetMessagesServlet() {
        super();
    }

    /**
	 * Restiuisce i messaggi in segreteria
	 * di un utente tramite richiesta HTTP GET
	 * 
	 * @param	request		contiene i parametri di input
	 * 						(idUser) per il
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
	 * Restiuisce i messaggi in segreteria
	 * di un utente tramite richiesta HTTP POST
	 * 
	 * @param	request		contiene i parametri di input
	 * 						(idUser) per il
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
		List<IMessage> messages = null;
		File file = null;
		String path = null;
		String separator = null;
		try
		{
			id = Long.parseLong(request.getParameter("idUser"));
			messageDAO = new MessageDAO();
			messages = messageDAO.getByReceiver(id);
			if (messages != null){
				
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