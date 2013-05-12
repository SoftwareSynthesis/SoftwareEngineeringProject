package org.softwaresynthesis.mytalk.server.authentication.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
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
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.softwaresynthesis.mytalk.server.IMyTalkObject;
import org.softwaresynthesis.mytalk.server.abook.IUserData;
import org.softwaresynthesis.mytalk.server.abook.UserData;
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
	private final String picturePath = "ThisIsNotAPath";
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
	@Captor
	private ArgumentCaptor<IUserData> argument;
	private Writer writer;
	private RegisterController tester;

	@Before
	public void setUp() throws Exception {
		// configura il comportamento della strategia di crittografia
		when(strategy.encode(anyString())).thenAnswer(new Answer<String>() {
			@Override
			public String answer(InvocationOnMock invocation) {
				return (String) invocation.getArguments()[0];
			}
		});

		// configura il comportamento del contenuto multipart
		when(filePart.getInputStream()).thenReturn(istream);
		// configura il comportamento della richiesta
		when(request.getParameter("username")).thenReturn(username);
		when(request.getParameter("password")).thenReturn(password);
		when(request.getParameter("question")).thenReturn(question);
		when(request.getParameter("answer")).thenReturn(answer);
		when(request.getParameter("name")).thenReturn(name);
		when(request.getParameter("surname")).thenReturn(surname);
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

			@Override
			protected ISecurityStrategy getSecurityStrategyFactory() {
				return strategy;
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

	/**
	 * Il test ha lo scopo di verificare la corretta registrazione al sistema di
	 * un utente a partire da una richiesta contenente tutti i parametri
	 * necessari obbligatoriamente per portare a termine l'operazione e tutti i
	 * parametri facoltativi fatto salvo per l'immagine personale. In
	 * particolare, si controlla che la stringa stampata sulla pagina di
	 * risposta corrisponda, come atteso, alla rappresentazione in formato JSON
	 * dei dati dell'utente. Inoltre, il test assicura che avvenga
	 * effettivamente l'inserimento dei dati del nuovo utente nel database del
	 * sistema e che l'oggetto {@link UserData} inserito contenga effettivamente
	 * i dati corrispondenti alla richiesta, verificando che sia utilizzato
	 * l'algoritmo di crittografia per la password e per la risposta alla
	 * domanda segreta.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testRegisterCorrectUser() throws Exception {
		// invoca il metodo da testare
		tester.doAction(request, response);

		// verifica l'output ottenuto
		writer.flush();
		String responseText = writer.toString();
		String toCompare = String
				.format("{\"name\":\"%s\", \"surname\":\"%s\", \"email\":\"%s\", \"id\":\"%s\", \"picturePath\":\"%s\"}",
						name, surname, username, -1,
						"img/contactImg/Default.png");
		assertEquals(toCompare, responseText);

		// verifica il corretto uso dei mock
		verify(dao).insert(argument.capture());
		IUserData captured = argument.getValue();
		assertEquals(username, captured.getMail());
		assertEquals(question, captured.getQuestion());
		assertEquals(answer, captured.getAnswer());
		assertEquals(password, captured.getPassword());
		assertEquals(name, captured.getName());
		assertEquals(surname, captured.getSurname());
		assertEquals("img/contactImg/Default.png", captured.getPath());
		verify(response).getWriter();
		verify(request, times(7)).getParameter(anyString());
		verify(strategy).encode(answer);
		verify(strategy).encode(password);
	}

	/**
	 * Il test ha lo scopo di verificare la corretta registrazione al sistema di
	 * un utente a partire da una richiesta contenente tutti i parametri
	 * necessari obbligatoriamente per portare a termine l'operazione nonché
	 * tutti i parametri facoltativi. In particolare, si controlla che la
	 * stringa stampata sulla pagina di risposta corrisponda, come atteso, alla
	 * rappresentazione in formato JSON dei dati dell'utente. Inoltre, il test
	 * assicura che avvenga effettivamente l'inserimento dei dati del nuovo
	 * utente nel database del sistema e che l'oggetto {@link UserData} inserito
	 * contenga effettivamente i dati corrispondenti alla richiesta, verificando
	 * che sia utilizzato l'algoritmo di crittografia per la password e per la
	 * risposta alla domanda segreta.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testRegisterCorrectUserWithPicture() throws Exception {
		// associa il percorso di un'immagine alla richiesta HTTP
		when(request.getParameter("picturePath")).thenReturn(picturePath);

		// invoca il metodo da testare
		tester.doAction(request, response);

		// verifica l'output ottenuto
		writer.flush();
		String responseText = writer.toString();
		String toCompare = String
				.format("{\"name\":\"%s\", \"surname\":\"%s\", \"email\":\"%s\", \"id\":\"%s\", \"picturePath\":\"%s\"}",
						name, surname, username, -1, picturePath);
		assertEquals(toCompare, responseText);

		// verifica il corretto utilizzo dei mock
		verify(dao).insert(argument.capture());
		IUserData captured = argument.getValue();
		assertEquals(username, captured.getMail());
		assertEquals(question, captured.getQuestion());
		assertEquals(answer, captured.getAnswer());
		assertEquals(password, captured.getPassword());
		assertEquals(name, captured.getName());
		assertEquals(surname, captured.getSurname());
		assertEquals(picturePath, captured.getPath());
		verify(response).getWriter();
		verify(request, times(7)).getParameter(anyString());
		verify(strategy).encode(answer);
		verify(strategy).encode(password);
	}

	/**
	 * Verifica l'impossibilità di portare a termine la procedura di
	 * registrazione per un utente la cui richiesta non contiene tutti i dati
	 * necessari alla registrazione, ad esempio il nome utente. Il test verifica
	 * che il testo stampato sulla pagina di risposta sia la stringa 'null' come
	 * atteso, che non sia MAI utilizzato l'algoritmo di crittografia e che non
	 * siano MAI effettuate in casi simili operazioni di inserimento nella base
	 * di dati del server.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testRegisterWrongUser() throws Exception {
		// rimuove un parametro obbligatorio per la registrazione
		when(request.getParameter("username")).thenReturn(null);

		// invoca il metodo da testare
		tester.doAction(request, response);

		// verifica l'output ottenuto
		writer.flush();
		String responseText = writer.toString();
		assertEquals("null", responseText);

		// verifica il corretto utilizzo dei mock
		verify(dao, never()).insert(any(IMyTalkObject.class));
		verify(strategy, never()).encode(anyString());
	}
}
