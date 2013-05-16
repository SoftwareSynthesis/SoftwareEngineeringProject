package org.softwaresynthesis.mytalk.server.message.controller;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.softwaresynthesis.mytalk.server.abook.IUserData;
import org.softwaresynthesis.mytalk.server.dao.DataPersistanceManager;
import org.softwaresynthesis.mytalk.server.message.IMessage;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AddMessageControllerTest {
	private final String username = "indirizzo5@dominio.it";
	private final Long messageId = 1L;
	private Writer writer;
	private AddMessageController tester;
	@Mock
	private HttpServletRequest request;
	@Mock
	private HttpServletResponse response;
	@Mock
	private Part part;
	@Mock
	private IUserData sender;
	@Mock
	private IUserData receiver;
	@Mock
	private IMessage message;
	@Mock
	private DataPersistanceManager dao;
	@Mock
	private InputStream istream;

	@Before
	public void setUp() throws Exception {
		// comportamento della part
		when(part.getInputStream()).thenReturn(istream);
		// comportamento della richiesta
		when(request.getPart("receiver")).thenReturn(part);
		when(request.getPart("msg")).thenReturn(part);
		// comportamento della risposta
		writer = new StringWriter();
		when(response.getWriter()).thenReturn(new PrintWriter(writer));
		// comportamento del gestore di persistenza
		when(dao.getMessageNewKey()).thenReturn(messageId);

		// inizializza l'oggetto da testare
		tester = new AddMessageController() {
			@Override
			protected DataPersistanceManager getDAOFactory() {
				return dao;
			}

			@Override
			protected String getUserMail() {
				return username;
			}

			@Override
			String getValue(Part part) {
				return "1";
			}

			@Override
			void writeFile(String path, InputStream istream) {
			}
			
			@Override
			IMessage createMessage() {
				return message;
			}
		};
	}

	/**
	 * TODO da documentare!
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testAddCorrectMessage() throws Exception {
		System.err.println(System.getProperty("file.separator"));
		// invoca il metodo da testare
		tester.doAction(request, response);
		
		// verifica l'output
		writer.flush();
		String responseText = writer.toString();
		assertEquals("true", responseText);
	}

}
