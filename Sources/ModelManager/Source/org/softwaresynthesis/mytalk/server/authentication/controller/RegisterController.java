package org.softwaresynthesis.mytalk.server.authentication.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.softwaresynthesis.mytalk.server.AbstractController;
import org.softwaresynthesis.mytalk.server.abook.Group;
import org.softwaresynthesis.mytalk.server.abook.IGroup;
import org.softwaresynthesis.mytalk.server.abook.IUserData;
import org.softwaresynthesis.mytalk.server.abook.UserData;
import org.softwaresynthesis.mytalk.server.authentication.security.ISecurityStrategy;
import org.softwaresynthesis.mytalk.server.dao.DataPersistanceManager;

import sun.misc.IOUtils;

/**
 * Controller che gestisce la registrazione
 * di un nuovo utente
 * 
 * @author 	Andrea Meneghinello
 * @version	3.0
 */
public class RegisterController extends AbstractController 
{
	/**
	 * Esegue la registrazione di un nuovo utente
	 * nel sistema MyTalk
	 */
	@Override
	protected void doAction(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		DataPersistanceManager dao = null;
		ISecurityStrategy strategy = null;
		IUserData user = null;
		PrintWriter writer = null;
		String answer = null;
		String mail = null;
		String name = null;
		String password = null;
		String path = null;
		String question = null;
		String result = null;
		String surname = null;
		InputStream inputStream = null;
		Part filePart = null;
		FileOutputStream out = null;
		IGroup group = null;
		
		try
		{
			strategy = getSecurityStrategyFactory();
			mail = request.getParameter("username");
			password = request.getParameter("password");
			password = strategy.encode(password);
			question = request.getParameter("question");
			answer = request.getParameter("answer");
			answer = strategy.encode(answer);
			name = request.getParameter("name");
			surname = request.getParameter("surname");
			filePart = request.getPart("picturePath");
			user = new UserData();
			user.setMail(mail);
			user.setPassword(password);
			user.setQuestion(question);
			user.setAnswer(answer);
			user.setName(name);
			user.setSurname(surname);
			if (filePart == null)
			{
				path = "img/contactImg/Default.png";
				user.setPath(path);
			}
			else
			{
				inputStream = filePart.getInputStream();
				Scrivi("SIZE "+ filePart.getSize());
				path = System.getenv("MyTalkConfiguration");
				String separator = System.getProperty("file.separator");
				path += separator + "MyTalk" + separator + "img" + separator + "contactImg" + separator + mail + ".png";
				out = new FileOutputStream(path);
				out.write(IOUtils.readFully(inputStream, -1, false));
				out.close();
				user.setPath("img/contactImg/" + mail + ".png");
			}
			dao = getDAOFactory();
			dao.insert(user);
			group = new Group();
			group.setName("addrBookEntry");
			group.setOwner(user);
			dao.insert(group);
			result = "{\"name\":\"" + user.getName() + "\"";
			result += ", \"surname\":\"" + user.getSurname() + "\"";
			result += ", \"email\":\"" + user.getMail() + "\"";
			result += ", \"id\":\"" + user.getId() + "\"";
			result += ", \"picturePath\":\"" + user.getPath() + "\"}";
		}
		catch (Exception ex)
		{
			result = "null";
			Scrivi(ex.getMessage());
		}
		catch (Throwable ex)
		{
			Scrivi(ex.getMessage());
		}
		finally
		{
			writer = response.getWriter();
			writer.write(result);
		}
	}
	
	/**
	 * Ridefinizione del metodo di controllo
	 * 
	 * @return	true
	 */
	@Override
	protected boolean check(HttpServletRequest request)
	{
		return true;
	}
	
	private void Scrivi(String txt)
	{
		try
		{
			FileOutputStream s = new FileOutputStream("DEBUG.txt", true);
			PrintStream p = new PrintStream(s);
			p.println(txt);
			p.close();
			File f = new File("DEBUG.txt");
			FileWriter w = new FileWriter(f);
			w.write(txt);
			w.flush();
			w.close();
		}
		catch (IOException e) {}
	}
}