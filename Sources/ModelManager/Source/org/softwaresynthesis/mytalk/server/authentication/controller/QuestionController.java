package org.softwaresynthesis.mytalk.server.authentication.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.softwaresynthesis.mytalk.server.AbstractController;
import org.softwaresynthesis.mytalk.server.abook.IUserData;
import org.softwaresynthesis.mytalk.server.dao.DataPersistanceManager;

/**
 * Controller che gestisce la visualizzazione della
 * domanda segreta per il recupero della password
 * 
 * @author 	Andrea Meneghinello
 * @version	3.0
 */
public class QuestionController extends AbstractController 
{
	/**
	 * Propone la domanda per il recupero della
	 * password smarrita
	 */
	@Override
	protected void doAction(HttpServletRequest request,	HttpServletResponse response) throws IOException 
	{
		DataPersistanceManager dao = getDAOFactory();
		String mail = request.getParameter("username");
		IUserData user = dao.getUserData(mail);
		String question = user.getQuestion();
		PrintWriter writer = response.getWriter();
		writer.write(question);
	}
	
	@Override
	protected boolean check(HttpServletRequest request)
	{
		return true;
	}
}