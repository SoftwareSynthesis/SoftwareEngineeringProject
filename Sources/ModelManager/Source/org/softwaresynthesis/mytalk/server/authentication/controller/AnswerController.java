package org.softwaresynthesis.mytalk.server.authentication.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
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
		String host = "smtp.gmail.com";
		int port = 465; //porta 25 per non usare SSL
		 
	    Properties props = new Properties();
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.user", "MyTalk@softwaresynthesis.org");
	    props.put("mail.smtp.host", host);
	    props.put("mail.smtp.port", port);
	 
	    // commentare la riga seguente per non usare SSL 
	    props.put("mail.smtp.starttls.enable","true");
	    props.put("mail.smtp.socketFactory.port", port);
	 
	    // commentare la riga seguente per non usare SSL 
	    props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	    props.put("mail.smtp.socketFactory.fallback", "false");
	 
	    Session session = Session.getInstance(props, null);
	    session.setDebug(true);
	 
	    // Creazione delle BodyParts del messaggio
	    MimeBodyPart messageBodyPart1 = new MimeBodyPart();
	 
	    try
	    {
	      // COSTRUZIONE DEL MESSAGGIO
	      Multipart multipart = new MimeMultipart();
	      MimeMessage msg = new MimeMessage(session);
	 
	      // header del messaggio
	      msg.setSubject("Recupero password");
	      msg.setSentDate(new Date());
	      msg.setFrom(new InternetAddress("MyTalk@softwaresynthesis.org"));
	 
	      // destinatario
	      msg.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
	 
	      // corpo del messaggio
	      messageBodyPart1.setText("Messaggio automatico per il recuepero password del sistema MyTalk.\nNome utente: " + receiver + "\nPassword: " + text);
	      multipart.addBodyPart(messageBodyPart1);

	 
	      // inserimento delle parti nel messaggio
	      msg.setContent(multipart);
	 
	      Transport transport = session.getTransport("smtps"); //("smtp") per non usare SSL
	      transport.connect(host, "software.synthesis@gmail.com", "ingegneria");
	      transport.sendMessage(msg, msg.getAllRecipients());
	      transport.close();

	      return true;
	 
	    }catch(AddressException ae)
	    {
	      return false;
	    }catch(NoSuchProviderException nspe)
	    {
	      return false;
	    }catch(MessagingException me)
	    {
	      return false;
	    }
	}
	
	void actuallySendMessage(MimeMessage message) throws MessagingException {
		Transport.send(message);
	}
}