package org.softwaresynthesis.mytalk.server.authentication.controller;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import org.softwaresynthesis.mytalk.server.authentication.security.ISecurityStrategy;
import org.softwaresynthesis.mytalk.server.dao.DataPersistanceManager;

@RunWith(MockitoJUnitRunner.class)
public class RegisterControllerTest {
	private final String username = "indirizzo5@dominio.it";
	private final String password = "password";
	private final String question = "Come si chiama la mia gatta?";
	private final String answer = "Antonella";
	private final String name = "paperino";
	private final String surname = "de paperoni";
	private final String picturePath = "ThisIsNotAPath.jpg";
	@Mock
	private InputStream istream;
	@Mock
	private Part filePart;
	@Mock
	private ISecurityStrategy strategy;
	@Mock
	private DataPersistanceManager dao;
	@Mock
	private HttpServletRequest request;
	@Mock
	private HttpServletResponse response;
	private Writer writer;
	private RegisterController tester;

	@Before
	public void setUp() throws Exception {
		// configura il comportamento del contenuto multipart
		when(filePart.getInputStream()).thenReturn(istream);
		// configura il comportamento della richiesta
		when(request.getParameter("username")).thenReturn(username);
		when(request.getParameter("password")).thenReturn(password);
		when(request.getParameter("question")).thenReturn(question);
		when(request.getParameter("answer")).thenReturn(answer);
		when(request.getParameter("name")).thenReturn(name);
		when(request.getParameter("surname")).thenReturn(surname);
		when(request.getParameter("picturePath")).thenReturn(picturePath);
		when(request.getPart("picturePath")).thenReturn(filePart);
		// azzera il contenuto dello StringBuffer per la risposta
		writer = new StringWriter();
		// configura il comportamento della risposta
		when(response.getWriter()).thenReturn(new PrintWriter(writer));

		/*
		 * Oggetto da testare in cui viene fatto l'overriding al volo dei metodi
		 * che non interessa testare e che restituiranno invece i mock
		 * indispensabili all'esecuzione delle verifiche successive
		 */
		tester = new RegisterController() {
			@Override
			protected DataPersistanceManager getDAOFactory() {
				return dao;
			}
		};
	}

	/**
	 * Verifica che il metodo di controllo della superclasse sia sovrascritto in
	 * modo corretto, tale cioè da restituire <code>true</code> in ogni caso,
	 * indipendentemente dalla richiesta che viene passata come parametro. Il
	 * test si assicura di questo verificando il risultato e controllando che la
	 * richiesta HTTP non sia utilizzata per estrarre parametri o informazioni
	 * di sessione che potrebbero influenzare il risultato.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testCheck() {
		// invoca il metodo da testare
		assertTrue(tester.check(request));
		// verifica il corretto utilizzo dei mock
		verify(request, never()).getSession(anyBoolean());
		verify(request, never()).getParameter(anyString());
	}

	@Test
	public void testRegisterCorrectUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testRegisterWrongUser() {
		fail("Not yet implemented");
	}
}
