package org.softwaresynthesis.mytalk.server.call.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.softwaresynthesis.mytalk.server.call.ICall;

/**
 * Servlet implementation class GetCallServlet
 */
@WebServlet("/AddCallServlet")
public class EndCallServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1000060L;
       
    /**
     * inizializzazione della servlet
     */
    public EndCallServlet() {
        super();
    }

    /**
	 * Inserisce la data di terminazione di una chiamata
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
	 * Inserisce la data di terminazione di una chiamata
	 * tramite richiesta HTTP POST
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
		ICall call = null;
		CallDAO callDAO = null;
		GregorianCalendar gc = null;
		String data = null;
		
		try
		{
			id = Long.parseLong(request.getParameter("id"));
			callDAO = new CallDAO();
			call = callDAO.getById(id);
			if (call != null){
				gc = new GregorianCalendar();
				int day = gc.get(Calendar.DAY_OF_MONTH);
				int month = gc.get(Calendar.MONTH);
				int year = gc.get(Calendar.YEAR);
				int hour = gc.get(Calendar.HOUR_OF_DAY);
				int min = gc.get(Calendar.MINUTE);
				data = "" + year + "-" + month + "-" + day + " " + hour + "-" + min;
				call.setEndDate(data);
				callDAO.update(call);
				
				result = "true";
			}
			else
			{
				result = "false";
			}
			
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