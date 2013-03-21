package org.softwaresynthesis.mytalk.server.abook;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet che ha il compito di bloccare un
 * contatto della rubrica
 * 
 * @author  Andrea Meneghinello
 * @version	%I%, %G%
 */
public class AddressBookDoBlockServlet extends HttpServlet 
{
	private static final long serialVersionUID = 10018L;
	
	/**
	 * Inizializza la servlet
	 */
	public AddressBookDoBlockServlet()
	{
		super();
	}
	
	/**
	 * Esegue la richiesta di per il blocco di un
	 * contatto alla rubrica ricevuta tramite
	 * richiesta HTTP GET
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
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		HttpSession session = null;
		PrintWriter writer = response.getWriter();
		String result = null;
		try
		{
			
		}
		catch (Exception ex)
		{
			
		}
	}
}