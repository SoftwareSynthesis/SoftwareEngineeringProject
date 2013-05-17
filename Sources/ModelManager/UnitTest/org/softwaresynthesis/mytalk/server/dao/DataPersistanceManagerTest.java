package org.softwaresynthesis.mytalk.server.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.softwaresynthesis.mytalk.server.IMyTalkObject;
import org.softwaresynthesis.mytalk.server.abook.IGroup;
import org.softwaresynthesis.mytalk.server.abook.IUserData;
import org.softwaresynthesis.mytalk.server.dao.util.GetUtil;
import org.softwaresynthesis.mytalk.server.dao.util.ModifyUtil;
import org.softwaresynthesis.mytalk.server.dao.util.UtilFactory;
import org.softwaresynthesis.mytalk.server.message.IMessage;

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
	private IGroup group;
	@Mock
	private IMessage message;
	@Mock
	private List<IMyTalkObject> list;
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
		// inizializza l'oggetto da testare
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

	/**
	 * Verifica la possibilità di recuperare dal sistema di persistenza un
	 * gruppo della rubrica di un utente a partire dall'identificativo univoco
	 * del gruppo stesso. Il test verifica che il risultato sia conforme alle
	 * aspettative, in base a come è stato configurato il mock di
	 * {@link GetUtil}, che dalla lista restituita dall'invocazione del suo
	 * metodo execute sia correttamente estratto l'elemento in testa. Inoltre si
	 * controlla che il metodo execute dell'utilità sia invocato esattamente con
	 * la stringa che corrisponde alla query HQL necessaria al recupero dei
	 * dati.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testGetGroupLong() {
		// id del gruppo
		Long groupId = 1L;
		// query che deve essere eseguita
		String query = "from Groups as g where g.id = " + "'" + groupId + "'";
		// comportamento dei mock
		when(list.get(0)).thenReturn(group);
		when(getter.execute(query)).thenReturn(list);

		// invoca il metodo da testare
		IGroup result = tester.getGroup(groupId);

		// verifica il risultato ottenuto
		assertNotNull(result);
		assertEquals(group, result);

		// verifica il corretto utilizzo dei mock
		verify(getter).execute(query);
		verify(list).get(0);
		verify(list).isEmpty();
	}

	/**
	 * Verifica il comportamento della classe nel momento in cui è richiesto di
	 * recuperare dal sistema di persistenza un gruppo a partire da un campo
	 * numerico identificativo che non corrisponde ad alcuno dei gruppi presenti
	 * nel sistema. In particolare, il test verifica che il risultato
	 * dell'invocazione del metodo sia, come desiderato <code>null</code> e che
	 * non siano effettuate operazioni di estrazione dalla lista restituita dal
	 * metodo execute del {@link GetUtil} dal momento che quest'ultima è vuota.
	 * si controlla inoltre che quest'ultimo metodo sia richiamato passando come
	 * parametro esattamente la stringa HQL necessaria al recupero dei dati.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testGetGroupLongEmpty() {
		// id del gruppo
		Long groupId = 1L;
		// query che deve essere eseguita
		String query = "from Groups as g where g.id = " + "'" + groupId + "'";
		// comportamento dei mock
		when(list.isEmpty()).thenReturn(true);
		when(getter.execute(query)).thenReturn(list);

		// invoca il metodo da testare
		IGroup result = tester.getGroup(groupId);

		// verifica il risultato ottenuto
		assertNull(result);

		// verifica il corretto utilizzo dei mock
		verify(getter).execute(query);
		verify(list, never()).get(anyInt());
		verify(list).isEmpty();
	}

	/**
	 * Verifica il comportamento della classe nel momento in cui è richiesto di
	 * recuperare la lista di gruppi della rubrica che hanno come proprietario
	 * un determinato utente. In particolare, il test verifica che la collezione
	 * restituita corrisponda alle aspettative (in base a come è stato
	 * configurato il mock del {@link GetUtil}) e che sia effettivamente
	 * invocato il metodo execute passando la query HQL corretta per interrogare
	 * il database.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testGetGroupIUserData() {
		// query che deve essere eseguita
		String query = "from Groups as g where g.owner = " + "'" + user + "'";
		// comportamento dei mock
		when(list.get(0)).thenReturn(group);
		when(getter.execute(query)).thenReturn(list);

		// invoca il metodo da testare
		List<IGroup> result = tester.getGroup(user);
		assertNotNull(result);
		assertFalse(result.isEmpty());
		assertEquals(list, result);

		// verifica il corretto utilizzo dei mock
		verify(getter).execute(query);
		verify(list, times(2)).isEmpty();
	}

	/**
	 * Verifica il comportamento della classe nel momento in cui è richiesto di
	 * recuperare la lista di gruppi della rubrica che hanno come proprietario
	 * un determinato utente. In particolare, il test verifica che nel caso in
	 * cui la collezione di gruppi sia vuota il valore ritornato sia
	 * effettivamente <code>null</code> e che sia effettivamente invocato il
	 * metodo execute passando la query HQL corretta per interrogare il
	 * database.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testGetGroupIUserDataEmpty() {
		// query che deve essere eseguita
		String query = "from Groups as g where g.owner = " + "'" + user + "'";
		// comportamento dei mock
		when(list.isEmpty()).thenReturn(true);
		when(getter.execute(query)).thenReturn(list);

		// invoca il metodo da testare
		List<IGroup> result = tester.getGroup(user);
		assertNull(result);

		// verifica il corretto utilizzo dei mock
		verify(getter).execute(query);
		verify(list).isEmpty();
	}

	/**
	 * Verifica la possibilità di recuperare il più piccolo id libero per il
	 * prossimo inserimento nella tabella dei messaggi in segreteria del
	 * database. In particolare, il test controlla che il risultato sia conforme
	 * alle aspettative e che cioè il valore sia superiore di un'unità rispetto
	 * a quanto restituito dal metodo getUniqueResult dal {@link GetUtil} e che
	 * questo metodo sia invocato correttamente.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testGetMessageNewKey() {
		// id dell'ultimo inserimento in Messages
		Long lastId = 1L;
		// query da eseguire
		String query = "max(id) from Messages";
		// ignoriamo il metodo-portoghese
		when(getter.uniqueResult(query)).thenReturn(lastId);

		// invoca il metodo da testare
		Long result = tester.getMessageNewKey();
		assertNotNull(result);
		assertTrue(result == lastId + 1);

		// verifica il corretto utilizzo dei mock
		verify(getter).uniqueResult(query);
	}

	/**
	 * Verifica la possibilità di recuperare dal sistema di persistenza un
	 * messaggio della segreteria telefonica a partire dall'identificativo
	 * univoco del messaggio stesso. Il test verifica che il risultato sia
	 * conforme alle aspettative, in base a come è stato configurato il mock di
	 * {@link GetUtil}, che dalla lista restituita dall'invocazione del suo
	 * metodo execute sia correttamente estratto l'elemento in testa. Inoltre si
	 * controlla che il metodo execute dell'utilità sia invocato esattamente con
	 * la stringa che corrisponde alla query HQL necessaria al recupero dei
	 * dati.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testGetMessage() {
		// identificativo di prova
		Long id = 1L;
		// query che deve essere eseguita
		String query = "from Messages as m where m.id = " + "'" + id + "'";
		// comportamento dei mock
		when(list.get(0)).thenReturn(message);
		when(getter.execute(query)).thenReturn(list);

		// invoca il metodo da testare
		IMessage result = tester.getMessage(id);

		// verifica l'output
		assertNotNull(result);
		assertEquals(message, result);

		// verifica il corretto utilizzo dei mock
		verify(getter).execute(query);
		verify(list).isEmpty();
		verify(list).get(0);
	}

	/**
	 * Verifica il comportamento della classe nel momento in cui è richiesto di
	 * recuperare dal sistema di persistenza un messaggio della segreteria a
	 * partire da un campo numerico identificativo che non corrisponde ad alcuno
	 * dei messaggi presenti. In particolare, il test verifica che il risultato
	 * dell'invocazione del metodo sia, come desiderato <code>null</code> e che
	 * non siano effettuate operazioni di estrazione dalla lista restituita dal
	 * metodo execute del {@link GetUtil} dal momento che quest'ultima è vuota.
	 * si controlla inoltre che quest'ultimo metodo sia richiamato passando come
	 * parametro esattamente la stringa HQL necessaria al recupero dei dati.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testGetMessageEmpty() {
		// identificativo di prova
		Long id = 1L;
		// query che deve essere eseguita
		String query = "from Messages as m where m.id = " + "'" + id + "'";
		// comportamento dei mock
		when(list.isEmpty()).thenReturn(true);
		when(getter.execute(query)).thenReturn(list);

		// invoca il metodo da testare
		IMessage result = tester.getMessage(id);

		// verifica l'output
		assertNull(result);

		// verifica il corretto utilizzo dei mock
		verify(getter).execute(query);
		verify(list).isEmpty();
		verify(list, never()).get(anyInt());
	}

	/**
	 * Verifica il comportamento della classe nel momento in cui è richiesto di
	 * recuperare dal sistema di persistenza una lista di messaggi della
	 * segreteria a partire da un utente che ha almeno un messaggio nella
	 * propria segreteria telefonica. In particolare, il test verifica che il
	 * risultato dell'invocazione del metodo sia, conforme alle aspettative, a
	 * seconda di come è stato configurato il mock di {@GetUtil}. Si
	 * controlla inoltre che il metodo execute di quest'ultima classe sia
	 * richiamato passando come parametro esattamente la stringa HQL necessaria
	 * al recupero dei dati.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testGetMessages() {
		// query da eseguire
		String query = "from Messages as m where m.receiver = " + "'" + user
				+ "'";
		// comportamento dei mock
		when(getter.execute(query)).thenReturn(list);

		// invoca il metodo da testare
		List<IMessage> result = tester.getMessages(user);
		assertNotNull(result);
		assertEquals(list, result);

		// verifica il corretto utilizzo dei mock
		verify(getter).execute(query);
		verify(list).isEmpty();
	}

	/**
	 * Verifica il comportamento della classe nel momento in cui è richiesto di
	 * recuperare dal sistema di persistenza una lista di messaggi della
	 * segreteria a partire da un utente che non ha alcun messaggio nella
	 * propria segreteria telefonica. In particolare, il test verifica che il
	 * risultato dell'invocazione del metodo sia, come desiderato
	 * <code>null</code>. Si controlla inoltre che il metodo execute di
	 * {@link GetUtil} sia richiamato passando come parametro esattamente la
	 * stringa HQL necessaria al recupero dei dati.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testGetMessagesEmpty() {
		// query da eseguire
		String query = "from Messages as m where m.receiver = " + "'" + user
				+ "'";
		// comportamento dei mock
		when(getter.execute(query)).thenReturn(list);
		when(list.isEmpty()).thenReturn(true);

		// invoca il metodo da testare
		List<IMessage> result = tester.getMessages(user);
		assertNull(result);

		// verifica il corretto utilizzo dei mock
		verify(getter).execute(query);
		verify(list).isEmpty();
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
		verify(list).get(0);
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
		verify(list).get(0);
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
