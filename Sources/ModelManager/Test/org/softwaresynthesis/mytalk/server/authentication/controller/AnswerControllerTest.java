package org.softwaresynthesis.mytalk.server.authentication.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.softwaresynthesis.mytalk.server.abook.IUserData;
import org.softwaresynthesis.mytalk.server.authentication.security.ISecurityStrategy;
import org.softwaresynthesis.mytalk.server.dao.DataPersistanceManager;

/**
 * Verifica della classe {@link AnswerController}
 * 
 * @author Diego Beraldin
 * @version 2.0
 */
@RunWith(MockitoJUnitRunner.class)
public class AnswerControllerTest {
	private final String username = "indirizzo5@dominio.it";
	private final String systemEmail = "MyTalk@softwaresynthesis.org";
	private final String answer = "Antonella";
	private final String encryptedAnswer = "LZKEMNtyFhmiVzqBi2LLNA==";
	private final String wrongAnswer = "Carmela";
	private final String encryptedWrongAnswer = "NBRbp3GFMyig5T7TJ6ohSg==";
	private final String password = "panettone";
	private final String encryptedPassword = "fvNCqhL7tO4pZiJIwTp25A==";
	private AnswerController tester;
	private Writer writer;
	@Mock
	private ISecurityStrategy strategy;
	@Mock
	private DataPersistanceManager dao;
	@Mock
	private IUserData user;
	@Mock
	private HttpServletRequest request;
	@Mock
	private HttpServletResponse response;
	@Mock
	private Transport transport;
	@Captor
	private ArgumentCaptor<MimeMessage> argMsg;
	@Captor
	private ArgumentCaptor<Properties> argProp;

	/**
	 * Reinizializza l'oggetto da testare e configura il comportamento dei mock
	 * prima dell'esecuzione di ogni verifica contenuta in questo caso di test
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Before
	public void setUp() throws Exception {
		// configura il mock della strategia
		when(strategy.encode(answer)).thenReturn(encryptedAnswer);
		when(strategy.decode(encryptedPassword)).thenReturn(password);
		when(strategy.encode(wrongAnswer)).thenReturn(encryptedWrongAnswer);
		// configura il gestore di persistenza
		when(dao.getUserData(username)).thenReturn(user);
		// configura il comportamento dell'utente
		when(user.getMail()).thenReturn(username);
		when(user.getAnswer()).thenReturn(encryptedAnswer);
		when(user.getPassword()).thenReturn(encryptedPassword);
		// configura il comportamento della richiesta
		when(request.getParameter("username")).thenReturn(username);
		when(request.getParameter("answer")).thenReturn(answer);
		// azzera il buffer per la risposta
		writer = new StringWriter();
		// configura il comportamento della risposta
		when(response.getWriter()).thenReturn(new PrintWriter(writer));
		// inizializza l'oggetto da testare (come di consueto)
		tester = spy(new AnswerController() {
			@Override
			protected DataPersistanceManager getDAOFactory() {
				return dao;
			}

			@Override
			protected ISecurityStrategy getSecurityStrategyFactory() {
				return strategy;
			}

			@Override
			Transport getTransport(Session session, String protocol) {
				return transport;
			}
		});
	}

	/**
	 * Verifica il funzionamento corretto del metodo utilizzato per inviare il
	 * messaggio contenente la password, in particolare controllando che sia
	 * invocato il metodo di invio e che il messaggio passato come parametro
	 * abbia i campi mittente, destinatario (TO), l'oggetto e il testo
	 * desiderati dal chiamante del metodo sendMessage.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testSendMail() throws Exception {
		// invoca il metodo da testare e verifica l'output
		Boolean result = tester.sendMail(systemEmail, username,
				"Recupero password", password);
		assertTrue(result);

		// verifica il corretto utilizzo dei mock
		verify(tester).getSession(argProp.capture());
		Properties properties = argProp.getValue();
		assertEquals("true", properties.getProperty("mail.smtp.auth"));
		assertEquals("MyTalk@softwaresynthesis.org",
				properties.getProperty("mail.smtp.user"));
		assertEquals("smtp.gmail.com", properties.getProperty("mail.smtp.host"));

		verify(transport).connect("smtp.gmail.com",
				"software.synthesis@gmail.com", "ingegneria");
		verify(transport).sendMessage(argMsg.capture(), any(Address[].class));
		MimeMessage msg = argMsg.getValue();
		assertEquals(systemEmail, msg.getFrom()[0].toString());
		assertEquals("Recupero password", msg.getSubject());
		assertEquals(username,
				msg.getRecipients(RecipientType.TO)[0].toString());
		String recoveredPassword = ((MimeMultipart) msg.getContent())
				.getBodyPart(0).getContent().toString();
		assertEquals(
				"Messaggio automatico per il recuepero password del sistema MyTalk.\n"
						+ "Nome utente: indirizzo5@dominio.it\n"
						+ "Password: panettone", recoveredPassword);
	}

	/**
	 * Verifica che il metodo di controllo della superclasse sia sovrascritto in
	 * modo corretto, tale cioè da restituire <code>true</code> in ogni caso,
	 * indipendentemente dalla richiesta che viene passata ad esso come
	 * parametro. Il test si assicura di questo verificando il risultato e
	 * sincerandosi del fatto che la richiesta HTTP non sia utilizzata per
	 * estrarre parametri o informazioni di sessione che potrebbero influenzare
	 * il risultato.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testCheck() {
		// invoca il metodo da testare
		Boolean result = tester.check(request);
		// verifica il risultato ottenuto
		assertTrue(result);
		// verifica il corretto uso dei mock
		verify(request, never()).getParameter(anyString());
		verify(request, never()).getSession(anyBoolean());
	}

	/**
	 * Verifica la possibilità di recuperare la password da parte di un utente
	 * che è registrato al sistema e che fornisce pertanto uno username valido.
	 * Il test verifica che la stringa restituita sia effettivamente 'true', che
	 * il sistema di persistenza dei dati sia utilizzato per recuperare
	 * l'utente, la risposta segreta codificata e la password e che la strategia
	 * di crittografia sia utilizzata per codificare la risposta contenuta nella
	 * richiesta e per decodificare la password presente nel database.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testRecoverPasswordSuccessfully() throws Exception {
		// by-passa metodo scomodo
		when(
				tester.sendMail(anyString(), anyString(),
						eq("Recupero password"), eq(password)))
				.thenReturn(true);
		// invoca il metodo da testare
		tester.doAction(request, response);

		// verifica l'output
		writer.flush();
		String responseText = writer.toString();
		assertEquals("true", responseText);

		// verifica il corretto utilizzo dei mock
		verify(dao).getUserData(username);
		verify(strategy).encode(answer);
		verify(strategy).decode(encryptedPassword);
		verify(request).getParameter("answer");
		verify(request).getParameter("username");
		verify(response).getWriter();
		verify(user).getAnswer();
		verify(user).getPassword();
	}

	/**
	 * Verifica il comportamento del metodo doAction nel momento in cui la
	 * risposta contenuta come parametro nella richiesta che giunge al
	 * controller non corrisponde alla risposta che l'utente aveva impostato in
	 * fase di registrazione. Il test verifica che sia stampata sulla risposta
	 * la stringa 'null', dopo aver tentato di codificare la password in
	 * ingresso. Si controlla inoltre che non sia MAI recuperata la password e
	 * che non sia effettuata ALCUNA operazione di decodifica.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testRecoverPasswordWrongAnswer() throws Exception {
		// la risposta fornita non è corretta
		when(request.getParameter("answer")).thenReturn(wrongAnswer);
		// invoca il metodo da testare
		tester.doAction(request, response);

		// verifica l'output
		writer.flush();
		String responseText = writer.toString();
		assertEquals("null", responseText);

		// verifica il corretto utilizzo dei mock
		verify(dao).getUserData(username);
		verify(strategy).encode("Carmela");
		verify(strategy, never()).decode(anyString());
		verify(request).getParameter("answer");
		verify(request).getParameter("username");
		verify(response).getWriter();
		verify(user).getAnswer();
		verify(user, never()).getPassword();
	}

	/**
	 * Verifica l'impossibilità di recuperare la password da parte di un utente
	 * che fornisce uno username non presente nella base di dati. Il test
	 * verifica che la stringa stampata sulla risposta sia effettivamente
	 * 'null', come desiderato in caso di fallimento.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testRecoverPasswordUnsuccessfully() throws Exception {
		String fakeUsername = "pippo";
		when(request.getParameter("username")).thenReturn(fakeUsername);
		when(dao.getUserData(fakeUsername)).thenReturn(null);

		// invoca il metodo da testare
		tester.doAction(request, response);

		// verifica l'output ottenuto
		writer.flush();
		String responseText = writer.toString();
		assertEquals("null", responseText);

		// verifica il corretto utilizzo dei mock
		verify(dao).getUserData(fakeUsername);
		verify(strategy).encode(answer);
		verifyZeroInteractions(user);
		verify(request).getParameter("answer");
		verify(request).getParameter("username");
		verify(response).getWriter();
	}

	/**
	 * Verifica il comportamento del metodo doAction nel momento in cui si
	 * verifica un errore nell'invio dell'email contenente la password
	 * dimenticata dall'utente che la richiede. In particolare il test verifica
	 * che sia stampata sulla pagina di risposta la stringa 'null' che denota un
	 * errore lato server.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testRecoverPasswordUnableToSendMail() throws Exception {
		// impedisce l'invio del messaggio
		doThrow(new MessagingException()).when(transport).close();

		// invoca il metodo da testare
		tester.doAction(request, response);

		// verifica l'output ottenuto
		writer.flush();
		String responseText = writer.toString();
		assertEquals("null", responseText);

		// verifica il corretto utilizzo dei mock
		verify(dao).getUserData(username);
		verify(strategy).encode(answer);
		verify(strategy).decode(encryptedPassword);
		verify(request).getParameter("answer");
		verify(request).getParameter("username");
		verify(response).getWriter();
		verify(user).getAnswer();
		verify(user).getPassword();
	}

	/**
	 * Verifica il comportamento del metodo doAction se la richiesta HTTP con
	 * cui viene invocato non contiene tutti i parametri obbligatori per portare
	 * a termine con successo l'operazione di recupero della password, in
	 * particolare, nel caso in cui manchi la risposta alla domanda segreta. Il
	 * test verifica che sulla risposta sia stampata, come desiderato in questo
	 * caso, la stringa 'null' e che non siano utilizzati i dati recuperati dal
	 * database né che siano effettuate operazioni di codifica e decodifica.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testRecoverPasswordWrongData() throws Exception {
		// manca un parametro nella richiesta
		when(request.getParameter("answer")).thenReturn(null);

		// invoca il metodo da testare
		tester.doAction(request, response);

		// verifica l'output ottenuto
		writer.flush();
		String responseText = writer.toString();
		assertEquals("null", responseText);

		// verifica il corretto utilizzo dei mock
		verify(dao).getUserData(username);
		verifyZeroInteractions(strategy);
		verify(request).getParameter("answer");
		verify(request).getParameter("username");
		verify(response).getWriter();
		verifyZeroInteractions(user);
	}
}
