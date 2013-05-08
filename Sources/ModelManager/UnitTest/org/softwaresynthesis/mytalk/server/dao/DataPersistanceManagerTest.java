package org.softwaresynthesis.mytalk.server.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.softwaresynthesis.mytalk.server.IMyTalkObject;
import org.softwaresynthesis.mytalk.server.abook.IUserData;
import org.softwaresynthesis.mytalk.server.dao.util.GetUtil;
import org.softwaresynthesis.mytalk.server.dao.util.ModifyUtil;
import org.softwaresynthesis.mytalk.server.dao.util.UtilFactory;

/**
 * Test della classe {@link DataPersistanceManager} che verifica l'invocazione
 * dei metodi corretti sulle utilità per la gestione della persistenza dei dati
 * 
 * @author Diego Beraldin
 * @version 2.0
 */
public class DataPersistanceManagerTest {
	// oggetto fittizio del database
	private final IMyTalkObject object = mock(IMyTalkObject.class);
	// un po' di mock per le utilità
	private final GetUtil getter = mock(GetUtil.class);
	private final ModifyUtil modifier = mock(ModifyUtil.class);
	// ecco il magnifico mock della classe factory che permetterà ai
	// verificatori di fare un bel po' di giochi di prestigio :)
	private final UtilFactory factory = mock(UtilFactory.class);
	// sessione fittizia di comunicazione con il database
	private final ISessionManager manager = mock(ISessionManager.class);
	// oggetto da testare
	private final DataPersistanceManager tester = new DataPersistanceManager(
			manager, factory);

	/**
	 * Inizializza il comportamento dei mock che sono comuni a tutti i test e,
	 * in particolare, della classe factory che restituisce le utilità
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Before
	public void setUp() {
		// configura il comportamento della factory
		when(factory.getCallUtil(manager)).thenReturn(getter);
		when(factory.getGenericUtil(manager)).thenReturn(getter);
		when(factory.getGroupUtil(manager)).thenReturn(getter);
		when(factory.getUserDataUtil(manager)).thenReturn(getter);
		when(factory.getDeleteUtil(manager)).thenReturn(modifier);
		when(factory.getInsertUtil(manager)).thenReturn(modifier);
		when(factory.getUpdateUtil(manager)).thenReturn(modifier);
	}

	/**
	 * Tenta di eseguire una cancellazione che va a buon fine e verifica che sia
	 * invocato il metodo getDeleteUtil() per ottenere un'utilità di
	 * cancellazione. Inoltre nel corso del test si verifica che, se
	 * l'operazione svolta tramite l'utilità di cancellazione ha successo, il
	 * metodo restituisca correttamente <code>true</code>.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testDeleteSuccessfully() {
		// configura il comportamento del mock per la cancellazione
		when(modifier.execute(object)).thenReturn(true);
		// invoca il metodo da testare
		boolean result = tester.delete(object);
		// verifica il risultato ottenuto
		assertNotNull(result);
		assertTrue(result);
		// verifica l'utilizzo dei mock
		verify(factory).getDeleteUtil(manager);
		verify(modifier).execute(object);
	}

	/**
	 * Tenta di eseguire una cancellazione che non va a buon fine verificando
	 * che sia invocato il metodo getDeleteUtil() sulla factory per procurarsi
	 * un'utilità di cancellazione. Verifica che sia invocato il metodo
	 * execute() per la cancellazione e che il valore restituito dal metodo sia
	 * <code>false</code>, come ci si aspetta nel caso in cui l'operazione sul
	 * database non vada a buon fine.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testDeleteUnsuccessfully() {
		// configura il comportamento del mock per la cancellazione
		when(modifier.execute(object)).thenReturn(false);
		// invoca il metodo da testare
		boolean result = tester.delete(object);
		// verifica il risultato ottenuto
		assertNotNull(result);
		assertFalse(result);
		// verifica l'utilizzo dei mock
		verify(factory).getDeleteUtil(manager);
		verify(modifier).execute(object);
	}

	/**
	 * Tenta di eseguire un inserimento che va a buon fine e verifica che sia
	 * invocato il metodo getInsertUtil() per ottenere un'utilità di
	 * inserimento. Inoltre nel corso del test si verifica che, se l'operazione
	 * svolta tramite l'utilità di inserimento ha successo, il metodo
	 * restituisca correttamente <code>true</code>.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testInsertSuccessfully() {
		// configura il comportamento del mock per la cancellazione
		when(modifier.execute(object)).thenReturn(true);
		// invoca il metodo da testare
		boolean result = tester.delete(object);
		// verifica il risultato ottenuto
		assertNotNull(result);
		assertTrue(result);
		// verifica l'utilizzo dei mock
		verify(factory).getDeleteUtil(manager);
		verify(modifier).execute(object);
	}

	/**
	 * Tenta di eseguire un inserimento che non va a buon fine verificando che
	 * sia invocato il metodo getInsertUtil() sulla factory per procurarsi
	 * un'utilità di inserimento. Verifica che sia invocato il metodo execute()
	 * per portare a termine l'operazione di inseirmento e che il valore
	 * restituito dal metodo sia <code>false</code>, come ci si aspetta nel caso
	 * in cui l'operazione sul database non vada a buon fine.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testInsertUnsuccessfully() {
		// configura il comportamento del mock per la cancellazione
		when(modifier.execute(object)).thenReturn(false);
		// invoca il metodo da testare
		boolean result = tester.insert(object);
		// verifica il risultato ottenuto
		assertNotNull(result);
		assertFalse(result);
		// verifica l'utilizzo dei mock
		verify(factory).getInsertUtil(manager);
		verify(modifier).execute(object);
	}

	/**
	 * Tenta di eseguire un'operazione di aggiornamento che va a buon fine e
	 * verifica che sia invocato il metodo getUpdateUtil() per ottenere
	 * un'utilità di aggiornamento. Inoltre nel corso del test si verifica che,
	 * se l'operazione svolta tramite l'utilità di aggiornamento ha successo, il
	 * metodo restituisca correttamente <code>true</code>.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testUpdateSuccessfully() {
		// configura il comportamento del mock per la cancellazione
		when(modifier.execute(object)).thenReturn(true);
		// invoca il metodo da testare
		boolean result = tester.delete(object);
		// verifica il risultato ottenuto
		assertNotNull(result);
		assertTrue(result);
		// verifica l'utilizzo dei mock
		verify(factory).getUpdateUtil(manager);
		verify(modifier).execute(object);
	}

	/**
	 * Tenta di eseguire un'operazione di aggiornamento che non va a buon fine
	 * verificando che sia invocato il metodo getUpdateUtil() sulla factory per
	 * procurarsi un'utilità di aggiornamento. Verifica che sia invocato il
	 * metodo execute() per portare a termine l'aggiornamento e che il valore
	 * restituito dal metodo sia <code>false</code>, come ci si aspetta nel caso
	 * in cui l'operazione sul database non vada a buon fine.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testUpdateUnsuccessfully() {
		// configura il comportamento del mock per la cancellazione
		when(modifier.execute(object)).thenReturn(false);
		// invoca il metodo da testare
		boolean result = tester.insert(object);
		// verifica il risultato ottenuto
		assertNotNull(result);
		assertFalse(result);
		// verifica l'utilizzo dei mock
		verify(factory).getUpdateUtil(manager);
		verify(modifier).execute(object);
	}

	@Test
	public void testGetAddressBookEntry() {
		fail("Non ho voglia/tempo di farlo");
	}

	@Test
	public void testGetCallHistory() {
		fail("Non ho voglia/tempo di farlo");
	}

	@Test
	public void testGetGroupLong() {
		fail("Non ho voglia/tempo di farlo");
	}

	@Test
	public void testGetGroupIUserData() {
		fail("Non ho voglia/tempo di farlo");
	}

	@Test
	public void testGetGroupIUserDataString() {
		fail("Non ho voglia/tempo di farlo");
	}

	@Test
	public void testGetMessageNewKey() {
		fail("Non ho voglia/tempo di farlo");
	}

	@Test
	public void testGetMessage() {
		fail("Non ho voglia/tempo di farlo");
	}

	@Test
	public void testGetMessages() {
		fail("Non ho voglia/tempo di farlo");
	}

	/**
	 * Verifica la possibilità di recuperare i dati di un utente dal database
	 * disponendo del relativo indirizzo email. In particolare, il test assicura
	 * che il risultato sia uguale alle attese (in base a come è stato
	 * configurato il mock di GetUserDataUtil), e verifica inoltre il corretto
	 * utilizzo dei mock. In particolare, verifica che sia eseguita ESATTAMENTE
	 * la query impostata nella variabile 'query' e il fatto che dalla
	 * collezione di oggetti ritornata dalla execute del GetUtil sia estratto il
	 * primo elemento e che non sia MAI invocato get con argomenti superiori
	 * allo zero, che la lista sia scorsa solo se non è vuota e che la
	 * UtilFactory sia utilizzata per procurarsi la corretta istanza di GetUtil.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testGetUserDataByEmail() {
		// indirizzo email di prova
		String mail = "ThisIsNotAnEmailAddress";
		// query che deve essere eseguita
		String query = "from UserData as u where u.mail = " + "'" + mail + "'";
		// mock dell'utente
		IUserData user = mock(IUserData.class);
		// mock della collezione di utenti
		@SuppressWarnings("unchecked")
		List<IMyTalkObject> list = mock(List.class);
		// configura il comportamento dei mock
		when(list.get(0)).thenReturn(user);
		when(getter.execute(query)).thenReturn(list);

		// invoca il metodo da testare
		IUserData result = tester.getUserData(mail);

		// verifica il risultato ottenuto
		assertNotNull(result);
		assertEquals(user, result);

		// verifica il corretto utilizzo dei mock
		verify(factory).getUserDataUtil(manager);
		verify(getter).execute(query);
		verify(list).isEmpty();
		verify(list).get(0);
		verify(list, never()).get(argThat(new ArgumentMatcher<Integer>() {
			@Override
			public boolean matches(Object arg) {
				return ((Integer) arg).intValue() > 0;
			}
		}));
	}

	/**
	 * Verifica il comportamento della classe nel momento in cui si tenta di
	 * recuperare un utente con un indirizzo email inesistente (cioè la query
	 * restituisce un insieme vuoto). Il test assicura che in questo caso la
	 * collezione restituita sia, come atteso, il riferimento nullo. Inoltre il
	 * test verifica il corretto utilizzo dei mock, cioè che sia utilizzata la
	 * classe factory per ottenere il GetUtil corretto, che il metodo execute di
	 * quest'ultimo sia invocato ESATTAMENTE con la stringa impostata nella
	 * variabile query, che si controlli se la collezione restituita è vuota e,
	 * dal momento che appare come tale, che non sia MAI invocato il metodo get
	 * su quest'ultima con un parametro intero.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testGetUserDataByEmailEmpty() {
		// indirizzo email di prova
		String mail = "ThisIsNotAnEmailAddress";
		// query che deve essere eseguita
		String query = "from UserData as u where u.mail = " + "'" + mail + "'";
		// mock della collezione di utenti
		@SuppressWarnings("unchecked")
		List<IMyTalkObject> list = mock(List.class);
		// configura il comportamento dei mock
		when(list.isEmpty()).thenReturn(true);
		when(getter.execute(query)).thenReturn(list);

		// invoca il metodo da testare
		IUserData result = tester.getUserData(mail);

		// verifica il risultato ottenuto
		assertNull(result);

		// verifica il corretto utilizzo dei mock
		verify(factory).getUserDataUtil(manager);
		verify(getter).execute(query);
		verify(list).isEmpty();
		verify(list, never()).get(anyInt());
	}

	/**
	 * Verifica la possibilità di recuperare i dati di un utente dal database
	 * disponendo del relativo numero identificativo. In particolare, il test
	 * assicura che il risultato sia uguale alle attese (in base a come è stato
	 * configurato il mock di GetUserDataUtil), e verifica inoltre il corretto
	 * utilizzo dei mock. In particolare, verifica che sia eseguita ESATTAMENTE
	 * la query impostata nella variabile 'query' e il fatto che dalla
	 * collezione di oggetti ritornata dalla execute del GetUtil sia estratto il
	 * primo elemento e che non sia MAI invocato get con argomenti superiori
	 * allo zero, che la lista sia scorsa solo se non è vuota e che la
	 * UtilFactory sia utilizzata per procurarsi la corretta istanza di GetUtil.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testGetUserDataById() {
		// identificativo di prova
		Long id = 1L;
		// query che deve essere eseguita
		String query = "from UserData as u where u.id = " + id;
		// mock dell'utente
		IUserData user = mock(IUserData.class);
		// mock della collezione di utenti
		@SuppressWarnings("unchecked")
		List<IMyTalkObject> list = mock(List.class);
		// configura il comportamento dei mock
		when(list.get(0)).thenReturn(user);
		when(getter.execute(query)).thenReturn(list);

		// invoca il metodo da testare
		IUserData result = tester.getUserData(id);

		// verifica il risultato ottenuto
		assertNotNull(result);
		assertEquals(user, result);

		// verifica il corretto utilizzo dei mock
		verify(factory).getUserDataUtil(manager);
		verify(getter).execute(query);
		verify(list).isEmpty();
		verify(list).get(0);
		verify(list, never()).get(argThat(new ArgumentMatcher<Integer>() {
			@Override
			public boolean matches(Object arg) {
				return ((Integer) arg).intValue() > 0;
			}
		}));
	}

	/**
	 * Verifica il comportamento della classe nel momento in cui si tenta di
	 * recuperare un utente con un id inesistente (cioè la query restituisce un
	 * insieme vuoto). Il test assicura che in questo caso la collezione
	 * restituita sia, come atteso, il riferimento nullo. Inoltre il test
	 * verifica il corretto utilizzo dei mock, cioè che sia utilizzata la classe
	 * factory per ottenere il GetUtil corretto, che il metodo execute di
	 * quest'ultimo sia invocato ESATTAMENTE con la stringa impostata nella
	 * variabile query, che si controlli se la collezione restituita è vuota e,
	 * dal momento che appare come tale, che non sia MAI invocato il metodo get
	 * su quest'ultima con un parametro intero.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testGetUserDataByIdEmpty() {
		// identificativo di prova
		Long id = 1L;
		// query che deve essere eseguita
		String query = "from UserData as u where u.id = " + id;
		// mock della collezione di utenti
		@SuppressWarnings("unchecked")
		List<IMyTalkObject> list = mock(List.class);
		// configura il comportamento dei mock
		when(list.isEmpty()).thenReturn(true);
		when(getter.execute(query)).thenReturn(list);

		// invoca il metodo da testare
		IUserData result = tester.getUserData(id);

		// verifica il risultato ottenuto
		assertNull(result);

		// verifica il corretto utilizzo dei mock
		verify(factory).getUserDataUtil(manager);
		verify(getter).execute(query);
		verify(list).isEmpty();
		verify(list, never()).get(anyInt());
	}

	/**
	 * TODO da completare una volta che il metodo sarà definitivo
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testGetUserDataGeneric() {
		fail("Non ho voglia/tempo di farlo");
	}
}
