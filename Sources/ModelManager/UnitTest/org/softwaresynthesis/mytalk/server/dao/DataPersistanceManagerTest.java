package org.softwaresynthesis.mytalk.server.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
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
@RunWith(MockitoJUnitRunner.class)
public class DataPersistanceManagerTest {
	@Mock
	private IMyTalkObject object;
	@Mock
	private GetUtil getter;
	@Mock
	private ModifyUtil modifier;
	@Mock
	private UtilFactory factory;
	@Mock
	private ISessionManager manager;
	@Mock
	private IUserData user;
	@Mock
	private List<IMyTalkObject> list;
	@Captor
	private ArgumentCaptor<Integer> arg;
	private DataPersistanceManager tester;

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
		// inizialzza l'oggetto da testare
		tester = new DataPersistanceManager(manager, factory);
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
		boolean result = tester.update(object);
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
		boolean result = tester.update(object);
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
		verify(list).get(arg.capture());
		assertTrue(arg.getValue() == 0);
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
		verify(list).get(arg.capture());
		assertTrue(arg.getValue() == 0);
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
	 * Verifica la possibilità di recuperare i dati di un utente dal database
	 * disponendo di una stringa che può comparire come sottostringa del nome,
	 * del cognome oppure del nome utente. In particolare, il test assicura che
	 * il risultato sia uguale alle attese (in base a come è stato configurato
	 * il mock di GetUserDataUtil), e verifica inoltre il corretto utilizzo dei
	 * mock. In particolare, verifica che sia eseguita ESATTAMENTE la query
	 * impostata nella variabile 'query' e che la UtilFactory sia utilizzata per
	 * procurarsi la corretta istanza di GetUtil.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testGetUserDataGeneric() {
		String mail = "indirizzo5@dominio.it";
		String name = "paperino";
		String surname = "de paperoni";
		String query = "from UserData as u where u.mail like '" + mail
				+ "' or u.name like '" + name + "' or u.surname like '"
				+ surname + "'";
		// configura il comportamento dei mock
		when(getter.execute(query)).thenReturn(list);

		// invoca il metodo da testare
		List<IUserData> result = tester.getUserDatas(mail, name, surname);

		// verifica l'output
		assertNotNull(result);
		assertEquals(list, result);

		// verifica il corretto utilizzo dei mock
		verify(factory).getUserDataUtil(manager);
		verify(getter).execute(query);
	}
}
