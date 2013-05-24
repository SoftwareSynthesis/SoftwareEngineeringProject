package org.softwaresynthesis.mytalk.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.softwaresynthesis.mytalk.server.authentication.security.AESAlgorithm;
import org.softwaresynthesis.mytalk.server.authentication.security.ISecurityStrategy;
import org.softwaresynthesis.mytalk.server.dao.DataPersistanceManager;

/**
 * I verificatori non sono impazziti! Qui testiamo solo i metodi NON ASTRATTI e
 * che nel loro corpo NON richiamano metodi astratti, vale a dire le uniche cose
 * che ha senso testare in una classe astratta! :) Inoltre, dato che una
 * siffatta classe non può essere istanziata, useremo uno speciale tipo di mock
 * che richiama ove possibile i metodi reali.
 * 
 * @author Diego Beraldin
 * @version 2.0
 */
@RunWith(MockitoJUnitRunner.class)
public class AbstractControllerTest {
	private final String emailAddress = "indirizzo5@dominio.it";
	@Mock
	private HttpServletRequest request;
	@Mock
	private HttpServletResponse response;
	@Mock
	private HttpSession session;
	private StringWriter writer;
	private AbstractController tester;

	/**
	 * Configura tutti gli oggetti che sono necessari all'esecuzione di tutti i
	 * test (all'occorrenza il comportamento di qualche mock potrà essere
	 * ridefinito in determinati test particolari se il comportamento che deve
	 * esibire è diverso da quello predefinsito)
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Before
	public void setUp() throws Exception {
		// inizializza l'oggetto da testare
		tester = mock(AbstractController.class, Mockito.CALLS_REAL_METHODS);
		// configura il comportamento della sessione
		when(session.getAttribute("username")).thenReturn(emailAddress);
		// configura il comportamento della richiesta
		when(request.getParameter("username")).thenReturn(emailAddress);
		when(request.getSession(false)).thenReturn(session);
		// reinizializza il writer e configura il comportamento della risposta
		writer = new StringWriter();
		when(response.getWriter()).thenReturn(new PrintWriter(writer));
		// configura il comportamento del mock per il metodo astratto
		doNothing().when(tester).doAction(request, response);
	}

	/**
	 * Verifica che sia possibile ottenere un'istanza valida di
	 * {@link DataPersistanceManager} dall'interno del controller astratto e di
	 * tutte le sue sottoclassi.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testGetDAOFactory() {
		// invoca il metodo da testare
		DataPersistanceManager manager = tester.getDAOFactory();
		// verifica il risultato ottenuto
		assertNotNull(manager);
	}

	/**
	 * Verifica che sia possibile recuperare un riferimento a una strategia di
	 * autenticazione valida e, in particolare, che si tratti di un'istanza
	 * dell'algoritmo di crittografia AES a 128 bit che è stato prescelto.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testGetSecurityStrategyFactory() {
		// invoca il metodo da testare
		ISecurityStrategy algorithm = tester.getSecurityStrategyFactory();
		// verifica il risultato ottenuto
		assertNotNull(algorithm);
		assertTrue(algorithm instanceof AESAlgorithm);
	}

	/**
	 * Verifica il comportamento della classe nel momento in cui si invoca il
	 * metodo check tramite una richiesta HTTP associata ad una sessione valida,
	 * vale a dire che contiene i dati necessari ad identificare l'utente dopo
	 * che è avvenuta la registrazione. Il test verifica che in tal caso il
	 * metodo check restituisca, come atteso, <code>true</code> e che la
	 * richiesta HTTP e la sessione ad essa associata siano utilizzate nel modo
	 * corretto.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testCheckSuccessfully() {
		// invoca il metodo da testare
		Boolean result = tester.check(request);
		// verifica il risultato ottenuto
		assertNotNull(result);
		assertTrue(result);

		// verifica che il campo userMail sia inizializzato
		String email = tester.getUserMail();
		assertNotNull(email);
		assertEquals(emailAddress, email);

		// verifica il corretto utilizzo dei mock
		verify(request).getSession(anyBoolean());
		verify(session).getAttribute("username");

	}

	/**
	 * Verifica il comportamento del metodo check nel momento in cui la
	 * richiesta che viene passata ad esso come parametro non corrisponde ad un
	 * utente autenticato, vale a dire non è associata a una sessione
	 * all'interno della quale sono memorizzati i dati identificativi
	 * dell'utente. Il test si assicura che in tal caso il metodo check
	 * restituisca, come atteso, il valore <code>false</code>, verificando
	 * inoltre che i mock della richiesta HTTP e della sessione ad essa
	 * associata siano utilizzati nel modo corretto.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testCheckUnsuccessfully() {
		// invalida la sessione di autenticazione
		when(session.getAttribute("username")).thenReturn("");

		// invoca il metodo da testare
		Boolean result = tester.check(request);

		// verifica l'output ottenuto
		assertNotNull(result);
		assertFalse(result);

		// verifica che il campo email sia rimasto non inizializzato
		String mail = tester.getUserMail();
		assertNull(mail);

		// verifica l'utilizzo corretto dei mock
		verify(request).getSession(anyBoolean());
		verify(session).getAttribute("username");
	}

	/**
	 * Verifica il comportamento del metodo execute quando il controllo di
	 * autenticazione è superato. In particolare, il test controlla che sia
	 * invocato il metodo doAction e che sulla risposta sia effettivamente
	 * scritto il testo che lo stub del metodo doAction è stato configurato per
	 * stampare.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testExecuteSuccessfully() throws Exception {
		// riconfigura il doAction in modo che scriva qualcosa sulla risposta
		doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Exception {
				Object[] args = invocation.getArguments();
				HttpServletResponse mockResponse = (HttpServletResponse) args[1];
				mockResponse.getWriter().write("true");
				return null;
			}
		}).when(tester).doAction(request, response);

		// invoca il metodo da testare
		tester.execute(request, response);

		// verifica l'output ottenuto
		writer.flush();
		String result = writer.toString();
		assertNotNull(result);
		assertEquals("true", result);

		// verifica il corretto utilizzo dei mock
		verify(tester).check(request);
		verify(tester).doAction(request, response);
		verify(response).getWriter();
	}

	/**
	 * Verifica il comportamento della classe nel momento in cui viene invocato
	 * il metodo con una richiesta non associata a una sessione di
	 * autenticazione valida. Il test in questo caso verifica che sia eseguito
	 * il metodo check una volta, che non sia MAI eseguito il metodo doAction()
	 * del controller e che sulla risposta sia stampata, come richiesto, la
	 * stringa 'null' dopo aver estratto il writer ad essa associato.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testExecuteUnsuccessfully() throws Exception {
		// fa in modo che il test effettuato da check non sia superato
		when(session.getAttribute("username")).thenReturn("");

		// invoca il metodo da testare
		tester.execute(request, response);

		// verifica il risultato ottenuto
		writer.flush();
		String result = writer.toString();
		assertNotNull(result);
		assertEquals("null", result);

		// verifica il corretto utilizzo dei mock
		verify(tester).check(request);
		verify(tester, never()).doAction(request, response);
		verify(response).getWriter();

	}
}
