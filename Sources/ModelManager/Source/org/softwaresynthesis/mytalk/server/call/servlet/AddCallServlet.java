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
import javax.servlet.http.HttpSession;

import org.softwaresynthesis.mytalk.server.abook.IUserData;
import org.softwaresynthesis.mytalk.server.call.Call;
import org.softwaresynthesis.mytalk.server.call.CallList;
import org.softwaresynthesis.mytalk.server.call.ICall;
import org.softwaresynthesis.mytalk.server.call.ICallList;
import org.softwaresynthesis.mytalk.server.dao.CallListDAO;
import org.softwaresynthesis.mytalk.server.dao.UserDataDAO;
import org.softwaresynthesis.mytalk.server.dao.CallDAO;

/**
 * Servlet implementation class GetCallServlet
 */
@WebServlet("/AddCallServlet")
public class AddCallServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1000060L;
       
    /**
     * inizializzazione della servlet
     */
    public AddCallServlet() {
        super();
    }

    /**
	 * Inserisce una chiamata
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
	 * Inserisce una chiamata
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
		Long myId = null;
		Long idFriend = null;
		CallListDAO callListDAO = null;
		ICall call = null;
		IUserData myUser = null;
		IUserData friend = null;
		UserDataDAO userDataDAO = null;
		ICallList callList = null;
		HttpSession session = null;
		GregorianCalendar gc = null;
		String data = null;
		CallDAO callDAO = null;
		String idCall = null;
		Long caller = null;
		
		try
		{
			session = request.getSession(false);
			myId = (Long)session.getAttribute("id");
			idFriend = Long.parseLong(request.getParameter("idFriend"));
			caller = Long.parseLong(request.getParameter("caller"));
			call = new Call();
			callDAO = new CallDAO();
			gc = new GregorianCalendar();
			int day = gc.get(Calendar.DAY_OF_MONTH);
			int month = gc.get(Calendar.MONTH);
			int year = gc.get(Calendar.YEAR);
			int hour = gc.get(Calendar.HOUR_OF_DAY);
			int min = gc.get(Calendar.MINUTE);
			data = "" + year + "-" + month + "-" + day + " " + hour + "-" + min;
			idCall = "" + myId + "-" + data;
			call.setId(idCall);
			call.setStartDate(data);
			callDAO.insert(call);
			
			call = callDAO.getById(idCall);
			
			if (call != null)
			{
				callListDAO = new CallListDAO();
				userDataDAO = new UserDataDAO();
				myUser = userDataDAO.getByID(myId);
				friend = userDataDAO.getByID(idFriend);
				
				if (myUser != null)
				{
					callList = new CallList();
					callList.setIdUser(myUser);
					callList.setIdCall(call);
					callList.setCaller(caller);
					callListDAO.insert(callList);
				}
				if (friend != null)
				{
					callList = new CallList();
					callList.setIdUser(friend);
					callList.setIdCall(call);
					callList.setCaller(caller);
					callListDAO.insert(callList);
				}
			}
			
			result = idCall;
			writer = response.getWriter();
			writer.write(result);
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