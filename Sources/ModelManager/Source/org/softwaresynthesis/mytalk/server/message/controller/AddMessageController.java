package org.softwaresynthesis.mytalk.server.message.controller;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.softwaresynthesis.mytalk.server.AbstractController;
import org.softwaresynthesis.mytalk.server.abook.IUserData;
import org.softwaresynthesis.mytalk.server.dao.DataPersistanceManager;
import org.softwaresynthesis.mytalk.server.message.IMessage;
import org.softwaresynthesis.mytalk.server.message.Message;

import sun.misc.IOUtils;

public class AddMessageController extends AbstractController{
	/**
	 * Aggiunge un messaggio nella
	 * segreteria di un utente
	 */
	@Override
	protected void doAction(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		DataPersistanceManager dao = null;
		String result = null;
		PrintWriter writer = null;
		String sender = null;
		Long receiver = null;
		InputStream inputStream = null;
		Part filePart = null;

		String path = null;
		String separator = null;
		IUserData send = null;
		IUserData rec = null;
		IMessage message = null;
		
		try
		{
			dao = getDAOFactory();
			sender = getUserMail();
			receiver = Long.parseLong(getValue(request.getPart("receiver")));
			filePart = request.getPart("msg");
			if (filePart != null)
			{
				inputStream = filePart.getInputStream();
			}
			
			path = System.getenv("MyTalkConfiguration");
			separator = System.getProperty("file.separator");
			path += separator + "MyTalk" + separator + "Secretariat" + separator;
			path += dao.getMessageNewKey() + ".wav";
			writeFile(path, inputStream);
			send = dao.getUserData(sender);
			rec = dao.getUserData(receiver);
			message = createMessage();
			message.setId(dao.getMessageNewKey());
			message.setSender(send);
			message.setReceiver(rec);
			message.setDate(getCurrentDate());
			dao.insert(message);
			
			result = "true";
		}
		catch (Exception ex)
		{
			result = "null";
		}
		finally
		{
			writer = response.getWriter();
			writer.write(result);
		}
	}
	
	Date getCurrentDate() {
		return new Date();
	}
	
	IMessage createMessage() {
		return new Message();
	}
	
	void writeFile(String path, InputStream inputStream) throws IOException {
		FileOutputStream out = null;
		out = new FileOutputStream(path);
		out.write(IOUtils.readFully(inputStream, -1, false));
		out.close();
	}
	
	/**
	 * Estrae una stringa da un oggetto
	 * di tipo Part
	 */
	String getValue(Part part) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				part.getInputStream(), "UTF-8"));
		StringBuilder value = new StringBuilder();
		char[] buffer = new char[1024];
		for (int length = 0; (length = reader.read(buffer)) > 0;) {
			value.append(buffer, 0, length);
		}
		return value.toString();
	}
}
