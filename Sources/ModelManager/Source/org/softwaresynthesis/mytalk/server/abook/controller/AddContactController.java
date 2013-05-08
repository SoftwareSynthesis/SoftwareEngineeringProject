package org.softwaresynthesis.mytalk.server.abook.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.softwaresynthesis.mytalk.server.AbstractController;
import org.softwaresynthesis.mytalk.server.abook.AddressBookEntry;
import org.softwaresynthesis.mytalk.server.abook.IAddressBookEntry;
import org.softwaresynthesis.mytalk.server.abook.IUserData;
import org.softwaresynthesis.mytalk.server.dao.DataPersistanceManager;

public class AddContactController extends AbstractController {
	/**
	 * Esegue l'aggiunta di un nuovo utente
	 * nella propria rubrica
	 */
	@Override
	protected void doAction(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		DataPersistanceManager dao = null;
		String result = null;
		PrintWriter writer = null;
		Long idUser = null;
		IUserData friend = null;
		IUserData myUser = null;
		IAddressBookEntry myEntry = null;
		IAddressBookEntry frEntry = null;
		String myEmail = null;
		try
		{
			idUser = Long.parseLong(request.getParameter("contactId"));
			dao = super.getDAOFactory();
			friend = dao.getUserData(idUser);
			myEntry = new AddressBookEntry();
			frEntry = new AddressBookEntry();
			myEmail = super.getUserMail();
			myUser = dao.getUserData(myEmail);
			
			myEntry.setContact(friend);
			myEntry.setBlocked(false);
			myEntry.setOwner(myUser);
			
			frEntry.setContact(myUser);
			frEntry.setBlocked(false);
			frEntry.setOwner(friend);
			
			dao.insert(myEntry);
			dao.insert(frEntry);
			
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
}
