package org.softwaresynthesis.mytalk.server.authentication;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.security.Principal;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.softwaresynthesis.mytalk.server.abook.IUserData;
import org.softwaresynthesis.mytalk.server.authentication.security.ISecurityStrategy;
import org.softwaresynthesis.mytalk.server.dao.DataPersistanceManager;

/**
 * Questa è la nuova fiammante versione del test di {@link AuthenticationModule}
 * che è un vero test di unità e che cerca di raggiungere tutti i casi limite
 * possibili nell'autenticazione.
 * 
 * @author Diego Beraldin
 * @version 2.0
 */
@RunWith(MockitoJUnitRunner.class)
public class AuthenticationModuleTest {
	private final String username = "indirizzo5@dominio.it";
	private final String password = "password";
	private Set<Principal> principalSet;
	// questo non andrebbe fatto così ma è final
	private Subject subject = new Subject();
	private AuthenticationModule tester;
	@Mock
	private ISecurityStrategy strategy;
	@Mock
	private CredentialLoader loader;
	@Mock
	private CallbackHandler handler;
	@Mock
	Map<String, ?> map;
	@Mock
	private DataPersistanceManager dao;
	@Mock
	private Principal principal;
	@Mock
	Loader nameLoader;
	@Mock
	Loader passwordLoader;
	@Mock
	IUserData user;

	/**
	 * Inizializza l'oggetto da testare e configura il comportamento dei mock
	 * prima di ognuna delle verifiche contenute in questo caso di test.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Before
	public void setUp() throws Exception {
		// comportamento dell'utente
		when(user.getPassword()).thenReturn(password);
		// comportamento del gestore di persistenza
		when(dao.getUserData(username)).thenReturn(user);
		// comportamento dei due Callback
		when(nameLoader.getData()).thenReturn(username);
		when(passwordLoader.getData()).thenReturn(password);
		// comportamento del CallbackHandler
		doNothing().when(handler).handle(any(Callback[].class));
		// crea l'oggetto da testare e lo inizializza
		tester = spy(new AuthenticationModule());
		tester.initialize(subject, handler, map, map);
		when(tester.getDAOFactory()).thenReturn(dao);
		when(tester.getPrincipal()).thenReturn(principal);
		principalSet = new HashSet<Principal>();
		principalSet.add(principal);
		when(tester.getPrincipals()).thenReturn(principalSet);
		when(tester.getUsername()).thenReturn(username);
		when(tester.createNameLoader()).thenReturn(nameLoader);
		when(tester.createPasswordLoader()).thenReturn(passwordLoader);
	}

	/**
	 * Verifica il comportamento del metodo abort() quando viene invocato senza
	 * che sia stato precedentemente effettuato un login con successo. Il test
	 * verifica che in questo caso il metodo restituisca <code>false</code> e
	 * che non venga mai effettuato il logout.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testAbortWihtoutLogin() throws Exception {
		when(tester.isLoggedIn()).thenReturn(false);
		boolean result = tester.abort();
		assertFalse(result);
		verify(tester, never()).logout();
	}

	/**
	 * Verifica il comportamento del metodo abort() se invocato dopo che è stato
	 * effettuato il login ma l'operazione è committed. In tal caso il test
	 * verifica che il risultato della chiamata di metodo sia <code>true</code>
	 * e che non sia effettuato il logout.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testAbortCommitted() throws Exception {
		when(tester.isLoggedIn()).thenReturn(true);
		when(tester.isCommitted()).thenReturn(true);
		boolean result = tester.abort();
		assertTrue(result);
		verify(tester, never()).logout();
	}

	/**
	 * Verifica il comportamento del metodo abort() dopo che è stata effettuata
	 * un'operazione di login. Il test verifica che sia effettivamente
	 * richiamata l'operazione per il logout e che il metodo restituisca, come
	 * atteso, <code>true</code>.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testAbortUncommitted() throws Exception {
		when(tester.isLoggedIn()).thenReturn(true);
		boolean result = tester.abort();
		assertTrue(result);
		verify(tester).logout();
	}

	/**
	 * Verifica il comportamento del metodo logout() se l'operazione può essere
	 * portata a termine con successo. In particolare, il test verifica che la
	 * chiamata di metodo restituisca, come atteso, il valore <code>true</code>.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testLogoutSuccessfully() throws Exception {
		boolean result = tester.logout();
		assertTrue(result);
	}

	/**
	 * Verifica il comportamento della classe nel momento in cui si tenta di
	 * effettuare il logout ma viene sollevata un'eccezione nel momento in cui
	 * si tenta di accedere ai dati di autenticazione. Il test ha successo se, e
	 * soltanto se, viene come desiderato sollevata un'eccezione.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test(expected = Exception.class)
	public void testLogoutUnsuccessfully() throws Exception {
		when(tester.getPrincipals()).thenThrow(new LoginException());
		boolean result = tester.logout();
		assertFalse(result);
	}

	/**
	 * Verifica il comportamento del metodo commit() controllando che, nelle
	 * opportune condizioni, sia possibile portare a termine correttamente
	 * l'operazione (fuffa style).
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testCommit() throws Exception {
		boolean result = tester.commit();
		assertTrue(result);
	}

	/**
	 * Verifica il comportamento del metodo login() nel momento in cui
	 * sussistono le condizioni per portare a termine con successo l'operazione.
	 * In particolare il test verifica che il valore restituito
	 * deall'invocazione del metodo sia, come desiderato, <code>true</code>.
	 * Inoltre si controlla che siano creati correttamente i callback e che
	 * siano gestiti dal CallbackHandler.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testLoginSuccessfully() throws Exception {
		boolean result = tester.login();
		assertTrue(result);
		// verifica il corretto utilizzo dei mock
		verify(tester).getDAOFactory();
		verify(tester).createNameLoader();
		verify(tester).createPasswordLoader();
		verify(dao).getUserData(username);
		verify(user).getPassword();
		verify(handler).handle(any(Callback[].class));
	}

	/**
	 * Verifica il comportamento del metodo login nel momento in cui non è
	 * possibile gestire in maniera corretta i dati di autenticazione perché
	 * viene sollevata una IOException. In particolare, il test ha successo se e
	 * solo se viene sollevato un errore di tipo LoginException da parte
	 * dell'oggetto di verifica. Inoltre si verifica la corretta interazione con
	 * i mock, assicurando che non siano recuperati dal databse i dati relativi
	 * all'utente.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test(expected = LoginException.class)
	public void testLoginIOException() throws Exception {
		doThrow(new IOException()).when(handler).handle(any(Callback[].class));
		boolean result = tester.login();
		assertFalse(result);
		// verifica il corretto utilizzo dei mock
		verify(tester, never()).getDAOFactory();
		verify(tester).createNameLoader();
		verify(tester).createPasswordLoader();
		verifyZeroInteractions(dao);
		verifyZeroInteractions(user);
		verify(handler).handle(any(Callback[].class));
	}

	/**
	 * Verifica il comportamento del metodo login nel momento in cui non è
	 * possibile gestire in maniera corretta i dati di autenticazione perché un
	 * callback (in questo caso quello della password) non è di tipo supportato
	 * e quindi non può essere trattata correttamente. In particolare, il test
	 * ha successo se e solo se viene sollevato un errore di tipo LoginException
	 * da parte dell'oggetto di verifica. Inoltre si verifica la corretta
	 * interazione con i mock, assicurando che non siano recuperati dal databse
	 * i dati relativi all'utente.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test(expected = LoginException.class)
	public void testLoginUnsupportedCallback() throws Exception {
		doThrow(new UnsupportedCallbackException(passwordLoader)).when(handler)
				.handle(any(Callback[].class));
		boolean result = tester.login();
		assertFalse(result);
		// verifica il corretto utilizzo dei mock
		verify(tester, never()).getDAOFactory();
		verify(tester).createNameLoader();
		verify(tester).createPasswordLoader();
		verifyZeroInteractions(dao);
		verifyZeroInteractions(user);
		verify(handler).handle(any(Callback[].class));
	}

	/**
	 * Verifica il comportamento della classe nel momento in cui si tenta di
	 * effettuare il login ma lo username fornito non corrisponde ad alcuno
	 * degli utenti registrati al sistema. Il test ha successo se e solo se
	 * viene sollevata un'eccezione di tipo FailedLoginException, come atteso in
	 * un simile caso. Inoltre si verifica il corretto utilizzo dei mock
	 * controllando che vengano creati e gestiti i Callback e che sia utilizzato
	 * il sistema di gestione della persistenza dei dati per cercare l'utente
	 * (senza successo) ma che non siano recuperati altri dati.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test(expected = FailedLoginException.class)
	public void testLoginWrongUsername() throws Exception {
		when(dao.getUserData(username)).thenReturn(null);
		boolean result = tester.login();
		assertFalse(result);
		// verifica il corretto utilizzo dei mock
		verify(tester).getDAOFactory();
		verify(tester).createNameLoader();
		verify(tester).createPasswordLoader();
		verify(dao).getUserData(username);
		verify(user, never()).getPassword();
		verify(handler).handle(any(Callback[].class));
	}

	/**
	 * Verifica il comportamento della classe nel momento in cui si tenta di
	 * effettuare il login ma a fronte di un username esistente non viene
	 * fornita la password corretta. Il test ha successo se e solo se viene
	 * sollevata un'eccezione di tipo FailedLoginException, come atteso in un
	 * simile caso. Inoltre si verifica il corretto utilizzo dei mock
	 * controllando che vengano creati e gestiti i Callback e che sia utilizzato
	 * il sistema di gestione della persistenza dei dati per cercare i dati
	 * relativi all'utente, in particolare la versione criptata della password
	 * memorizzata in fase di registrazione.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test(expected = FailedLoginException.class)
	public void testLoginWrongPassword() throws Exception {
		when(passwordLoader.getData()).thenReturn("I'm wrong!");
		boolean result = tester.login();
		assertFalse(result);
		// verifica il corretto utilizzo dei mock
		verify(tester).getDAOFactory();
		verify(tester).createNameLoader();
		verify(tester).createPasswordLoader();
		verify(dao).getUserData(username);
		verify(user).getPassword();
		verify(handler).handle(any(Callback[].class));
	}
}
