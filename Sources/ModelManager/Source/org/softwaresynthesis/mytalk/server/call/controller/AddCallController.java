package org.softwaresynthesis.mytalk.server.call.controller;

import java.io.IOException;
import java.io.Writer;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.softwaresynthesis.mytalk.server.AbstractController;
import org.softwaresynthesis.mytalk.server.abook.IUserData;
import org.softwaresynthesis.mytalk.server.call.Call;
import org.softwaresynthesis.mytalk.server.call.CallList;
import org.softwaresynthesis.mytalk.server.call.ICall;
import org.softwaresynthesis.mytalk.server.call.ICallList;
import org.softwaresynthesis.mytalk.server.dao.DataPersistanceManager;

public class AddCallController extends AbstractController {

	@Override
	protected void doAction(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Long userId = null;
		String email = null;
		IUserData user = null;
		IUserData other = null;
		ICall call = null;
		ICallList userCall = null;
		ICallList otherCall = null;
		Date date = null;
		Writer writer = null;
		DataPersistanceManager dao = null;
		String result = null;

		try {
			// inizializza i dati
			email = getUserMail();
			userId = Long.parseLong(request.getParameter("contactId"));
			dao = getDAOFactory();
			user = dao.getUserData(email);
			other = dao.getUserData(userId);
			date = createCurrentDate();
			call = createCall();

			// inserisce la chiamata
			call.setStart(date);
			call.setEnd(date);
			dao.insert(call);

			// inserisce la prima CallList
			userCall = new CallList();
			userCall.setCall(call);
			userCall.setUser(user);
			userCall.setCaller(true);
			dao.insert(userCall);

			// inserisce la seconda CallList
			otherCall = new CallList();
			otherCall.setCall(call);
			otherCall.setUser(other);
			otherCall.setCaller(false);
			dao.insert(otherCall);

			result = "true";
		} catch (Exception e) {
			result = "null";
		} finally {
			writer = response.getWriter();
			writer.write(result);
		}
	}

	Date createCurrentDate() {
		return new Date();
	}

	ICall createCall() {
		return new Call();
	}
}
