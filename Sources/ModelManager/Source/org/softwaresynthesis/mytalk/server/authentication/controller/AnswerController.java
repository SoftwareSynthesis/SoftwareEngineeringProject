package org.softwaresynthesis.mytalk.server.authentication.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.softwaresynthesis.mytalk.server.AbstractController;
import org.softwaresynthesis.mytalk.server.abook.IUserData;
import org.softwaresynthesis.mytalk.server.authentication.security.ISecurityStrategy;
import org.softwaresynthesis.mytalk.server.dao.DataPersistanceManager;

/**
 * Controller che ha lo scopo di verificare
 * la risposta alla domanda segreta per il
 * recupero della password
 * 
 * @author 	Andrea Meneghinello
 * @version	3.0
 */
public class AnswerController extends AbstractController
{
	/**
	 * Verifica la risposta fornita alla domanda
	 * segreta, se corretta l'utente riceverà
	 * nella mail la password di accesso al
	 * sistema MyTalk
	 */
	@Override
	protected void doAction(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		DataPersistanceManager dao = getDAOFactory();
		ISecurityStrategy strategy = getSecurityStrategyFactory();
		String mail = request.getParameter("username");
		String answer = request.getParameter("answer");
		String result = null;
		String password = null;
		IUserData user = dao.getUserData(mail);
		PrintWriter writer = null;
		if (answer != null)
		{
			try
			{
				answer = strategy.encode(answer);
				if (answer.equals(user.getAnswer()))
				{
					password = strategy.decode(user.getPassword());
					this.sendMail("mytalk@softwaresynthesis.org", user.getMail(), "Recupero password", password);
					result = "true";
				}
				else
				{
					result = "null";
				}
			}
			catch (Exception ex) 
			{
				result = "null";
			}
		}
		writer = response.getWriter();
		writer.write(result);
	}

	@Override
	protected boolean check(HttpServletRequest request)
	{
		return true;
	}
	
	boolean sendMail(String sender, String receiver, String subject, String text)
	{
		boolean result = false;
		Properties properties = null;
		Session session = null;
		try
		{
			//Creazione di una mail session
			properties = new Properties();
			properties.put("mail.smtp.host", "out.alice.it");
			session = Session.getDefaultInstance(properties);
			
			//Creazione degli indirizzi
			InternetAddress sen = new InternetAddress(sender);
			InternetAddress rec = new InternetAddress(receiver);
			
			//Creazione del messaggio da inviare
			MimeMessage message = new MimeMessage(session);
			message.setSubject(subject);
			message.setText(text);
			message.setFrom(sen);
			message.setRecipient(Message.RecipientType.TO, rec);
			
			//Invio del messaggio di posta
			actuallySendMessage(message);
			result = true;
		}
		catch (MessagingException ex)
		{
			result = false;
		}
		return result;
	}
	
	void actuallySendMessage(MimeMessage message) throws MessagingException {
		Transport.send(message);
	}
}
