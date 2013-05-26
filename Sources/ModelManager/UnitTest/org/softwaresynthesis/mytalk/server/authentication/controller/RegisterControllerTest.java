package org.softwaresynthesis.mytalk.server.authentication.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.FileOutputStream;
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
import org.softwaresynthesis.mytalk.server.IController;
import org.softwaresynthesis.mytalk.server.IMyTalkObject;
import org.softwaresynthesis.mytalk.server.abook.IGroup;
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
	private final String name = "paolino";
	private final String surname = "paperino";
	private final String picturePath = "img/contactImg/" + username + ".png";
	private Writer writer;
	private RegisterController tester;
	@Mock
	private InputStream istream;
	@Mock
	private FileOutputStream ostream;
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
	@Mock
	private IController loginController;
	@Captor
	private ArgumentCaptor<IMyTalkObject> argument;

	@Before
	public void setUp() throws Exception {
		// configura il comportamento della strategia di crittografia
		when(strategy.encode(anyString())).thenAnswer(new Answer<String>() {
			@Override
			public String answer(InvocationOnMock invocation) {
				return (String) invocation.getArguments()[0];
			}
		});
		// configura il comportamento del finto controller
		doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) {
				try {
					HttpServletResponse resp = (HttpServletResponse) invocation
							.getArguments()[1];
					String result = "{\"name\":\"" + name + "\"";
					result += ", \"surname\":\"" + surname + "\"";
					result += ", \"email\":\"" + username + "\"";
					result += ", \"id\":\"" + -1 + "\"";
					result += ", \"picturePath\":\"" + picturePath + "\"}";
					resp.getWriter().write(result);
				} catch (Exception e) {
				}
				return null;
			}
		}).when(loginController).execute(request, response);
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

			@Override
			IController createLoginController() {
				return loginController;
			}

			@Override
			FileOutputStream createFileOutputStream(String path) {
				return ostream;
			}

			@Override
			byte[] readFully(InputStream istream) {
				return null;
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
	public void testRegisterCorrectUserWithoutPicture() throws Exception {
		// l'utente non ha immagine
		when(request.getPart("picturePath")).thenReturn(null);
		// riconfigura il comportamento del finto controller
		doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) {
				try {
					HttpServletResponse resp = (HttpServletResponse) invocation
							.getArguments()[1];
					String result = "{\"name\":\"" + name + "\"";
					result += ", \"surname\":\"" + surname + "\"";
					result += ", \"email\":\"" + username + "\"";
					result += ", \"id\":\"" + -1 + "\"";
					result += ", \"picturePath\":\"img/contactImg/Default.png\"}";
					resp.getWriter().write(result);
				} catch (Exception e) {
				}
				return null;
			}
		}).when(loginController).execute(request, response);

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
		verify(dao, times(2)).insert(argument.capture());
		IUserData captured = (IUserData) argument.getAllValues().get(0);
		assertEquals(username, captured.getMail());
		assertEquals(question, captured.getQuestion());
		assertEquals(answer, captured.getAnswer());
		assertEquals(password, captured.getPassword());
		assertEquals(name, captured.getName());
		assertEquals(surname, captured.getSurname());
		assertEquals("img/contactImg/Default.png", captured.getPath());
		IGroup capturedGroup = (IGroup) argument.getAllValues().get(1);
		assertEquals("addrBookEntry", capturedGroup.getName());
		IUserData owner = capturedGroup.getOwner();
		assertEquals(captured, owner);
		verify(response).getWriter();
		verify(request, times(6)).getParameter(anyString());
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
		verify(dao, times(2)).insert(argument.capture());
		IUserData captured = (IUserData) argument.getAllValues().get(0);
		assertEquals(username, captured.getMail());
		assertEquals(question, captured.getQuestion());
		assertEquals(answer, captured.getAnswer());
		assertEquals(password, captured.getPassword());
		assertEquals(name, captured.getName());
		assertEquals(surname, captured.getSurname());
		assertEquals(picturePath, captured.getPath());
		IGroup capturedGroup = (IGroup) argument.getAllValues().get(1);
		assertEquals("addrBookEntry", capturedGroup.getName());
		IUserData owner = capturedGroup.getOwner();
		assertEquals(captured, owner);
		verify(response).getWriter();
		verify(request, times(6)).getParameter(anyString());
		verify(strategy).encode(answer);
		verify(strategy).encode(password);
	}

	/**
	 * Verifica l'impossibilità di portare a termine la procedura di
	 * registrazione per un utente la cui richiesta non contiene tutti i dati
	 * necessari alla registrazione, perché il nome utente è uguale alla stringa
	 * vuota. Il test verifica che il testo stampato sulla pagina di risposta
	 * sia la stringa 'null' come atteso, che non sia MAI utilizzato l'algoritmo
	 * di crittografia e che non siano MAI effettuate in casi simili operazioni
	 * di inserimento nella base di dati del server.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testRegisterWrongUsername() throws Exception {
		// rimuove un parametro obbligatorio per la registrazione
		when(request.getParameter("username")).thenReturn("");

		// invoca il metodo da testare
		tester.doAction(request, response);

		// verifica l'output ottenuto
		writer.flush();
		String responseText = writer.toString();
		assertEquals("null", responseText);

		// verifica il corretto utilizzo dei mock
		verify(dao, never()).insert(any(IMyTalkObject.class));
	}

	/**
	 * Verifica l'impossibilità di portare a termine la procedura di
	 * registrazione per un utente la cui richiesta non contiene tutti i dati
	 * necessari alla registrazione, perché la password è uguale alla stringa
	 * vuota. Il test verifica che il testo stampato sulla pagina di risposta
	 * sia la stringa 'null' come atteso, che non sia MAI utilizzato l'algoritmo
	 * di crittografia e che non siano MAI effettuate in casi simili operazioni
	 * di inserimento nella base di dati del server.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testRegisterWrongPassword() throws Exception {
		// rimuove un parametro obbligatorio per la registrazione
		when(request.getParameter("password")).thenReturn("");

		// invoca il metodo da testare
		tester.doAction(request, response);

		// verifica l'output ottenuto
		writer.flush();
		String responseText = writer.toString();
		assertEquals("null", responseText);

		// verifica il corretto utilizzo dei mock
		verify(dao, never()).insert(any(IMyTalkObject.class));
	}

	/**
	 * Verifica l'impossibilità di portare a termine la procedura di
	 * registrazione per un utente la cui richiesta non contiene tutti i dati
	 * necessari alla registrazione, perché la domanda segreta è uguale alla
	 * stringa vuota. Il test verifica che il testo stampato sulla pagina di
	 * risposta sia la stringa 'null' come atteso, che non sia MAI utilizzato
	 * l'algoritmo di crittografia e che non siano MAI effettuate in casi simili
	 * operazioni di inserimento nella base di dati del server.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testRegisterWrongQuestion() throws Exception {
		// rimuove un parametro obbligatorio per la registrazione
		when(request.getParameter("question")).thenReturn("");

		// invoca il metodo da testare
		tester.doAction(request, response);

		// verifica l'output ottenuto
		writer.flush();
		String responseText = writer.toString();
		assertEquals("null", responseText);

		// verifica il corretto utilizzo dei mock
		verify(dao, never()).insert(any(IMyTalkObject.class));
	}

	/**
	 * Verifica l'impossibilità di portare a termine la procedura di
	 * registrazione per un utente la cui richiesta non contiene tutti i dati
	 * necessari alla registrazione, perché la risposta alla domanda segreta è
	 * uguale alla stringa vuota. Il test verifica che il testo stampato sulla
	 * pagina di risposta sia la stringa 'null' come atteso, che non sia MAI
	 * utilizzato l'algoritmo di crittografia e che non siano MAI effettuate in
	 * casi simili operazioni di inserimento nella base di dati del server.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testRegisterWrongAnswer() throws Exception {
		// rimuove un parametro obbligatorio per la registrazione
		when(request.getParameter("answer")).thenReturn("");

		// invoca il metodo da testare
		tester.doAction(request, response);

		// verifica l'output ottenuto
		writer.flush();
		String responseText = writer.toString();
		assertEquals("null", responseText);

		// verifica il corretto utilizzo dei mock
		verify(dao, never()).insert(any(IMyTalkObject.class));
	}

	/**
	 * Verifica il comportamento del metodo doAction se lo username fornito
	 * nella richiesta HTTP è già presente nella tabella degli utenti del
	 * sistema compromettendo la riuscita dell'operazione di inserimento. Il
	 * test verifica che in questo caso il testo stampato sulla risposta sia,
	 * come desiderato, la stringa 'null'. Inoltre si verifica che siano
	 * estratti tutti i parametri necessari dalla richiesta e che sia creata una
	 * nuova istanza di IUserData con tutti i campi dati impostati nel modo
	 * corretto.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testRegisterDuplicatePrimaryKey() throws Exception {
		// solleva un errore inaspettato nel database
		when(dao.insert(any(IUserData.class)))
				.thenThrow(new RuntimeException());

		// invoca il metodo da testare
		tester.doAction(request, response);

		// verifica l'output ottenuto
		writer.flush();
		String responseText = writer.toString();
		assertEquals("null", responseText);

		// verifica il corretto utilizzo dei mock
		verify(dao).insert(argument.capture());
		IUserData captured = (IUserData) argument.getAllValues().get(0);
		assertEquals(username, captured.getMail());
		assertEquals(question, captured.getQuestion());
		assertEquals(answer, captured.getAnswer());
		assertEquals(password, captured.getPassword());
		assertEquals(name, captured.getName());
		assertEquals(surname, captured.getSurname());
		assertEquals(picturePath, captured.getPath());
		verify(response).getWriter();
		verify(request, times(6)).getParameter(anyString());
		verify(strategy).encode(answer);
		verify(strategy).encode(password);
	}
}
