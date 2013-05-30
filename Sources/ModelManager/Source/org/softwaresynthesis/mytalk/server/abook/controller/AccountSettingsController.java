package org.softwaresynthesis.mytalk.server.abook.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.softwaresynthesis.mytalk.server.AbstractController;
import org.softwaresynthesis.mytalk.server.abook.IUserData;
import org.softwaresynthesis.mytalk.server.dao.DataPersistanceManager;

import sun.misc.IOUtils;

public class AccountSettingsController extends AbstractController {
	
	@Override
	protected void doAction(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String email = null;
		IUserData user = null;
		String name = null;
		String surname = null;
		String path = null;
		Part filePart = null;
		InputStream istream = null;
		FileOutputStream ostream = null;
		DataPersistanceManager dao = null;
		PrintWriter writer = null;
		String result = null;
		try {
			// inizializza i dati
			name = request.getParameter("name");
			surname = request.getParameter("surname");
			filePart = request.getPart("picturePath");
			email = getUserMail();
			dao = getDAOFactory();
			user = dao.getUserData(email);
			// aggiorna i dati dell'utente
			user.setName(name);
			user.setSurname(surname);
			if (filePart != null) {
				istream = filePart.getInputStream();
				path = System.getenv("MyTalkConfiguration");
				String separator = System.getProperty("file.separator");
				path += separator + "MyTalk" + separator + "img" + separator
						+ "contactImg" + separator + email + ".png";
				ostream = createFileOutputStream(path);
				ostream.write(readFully(istream));
				ostream.close();
				user.setPath("img/contactImg/" + email + ".png");
			}
			result = "true";
		} catch (Exception e) {
			result = "null";
		} finally {
			writer = response.getWriter();
			writer.write(result);
		}
	}

	FileOutputStream createFileOutputStream(String path) throws Exception {
		return new FileOutputStream(path);
	}

	byte[] readFully(InputStream istream) throws Exception {
		return IOUtils.readFully(istream, -1, false);
	}
}
