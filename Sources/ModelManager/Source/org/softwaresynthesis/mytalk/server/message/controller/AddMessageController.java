package org.softwaresynthesis.mytalk.server.message.controller;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
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
		GenerateFileName file = null;
		String sender = null;
		Long receiver = null;
		InputStream inputStream = null;
		Part filePart = null;
		FileOutputStream out = null;
		String path = null;
		String separator = null;
		IUserData send = null;
		IUserData rec = null;
		IMessage message = null;
		GregorianCalendar gc = null;
		String data = null;
		
		try
		{
			dao = super.getDAOFactory();
			sender = super.getUserMail();
			receiver = Long.parseLong(getValue(request.getPart("receiver")));
			filePart = request.getPart("msg");
			if (filePart != null)
			{
				inputStream = filePart.getInputStream();
			}
			
			separator = System.getProperty("file.separator");
			path = "Secretariat" + separator;
			file = new GenerateFileName();
			path += file.next().toString() + ".wav";
			out = new FileOutputStream(path);
			out.write(IOUtils.readFully(inputStream, -1, false));
			out.close();
			send = dao.getUserData(sender);
			rec = dao.getUserData(receiver);
			message = new Message();
			message.setId(file.next());
			message.setSender(send);
			message.setReceiver(rec);
			
			gc = new GregorianCalendar();
			int day = gc.get(Calendar.DAY_OF_MONTH);
			int month = gc.get(Calendar.MONTH);
			int year = gc.get(Calendar.YEAR);
			data = "" + year + "-" + month + "-" + day;
			/* FIXME da sistemare la data
			message.setDate(data);
			*/
			dao.insert(message);
			
			result = "true";
			writer = response.getWriter();
			writer.write(result);
			
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
	
	private static String getValue(Part part) throws IOException {
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
