package org.softwaresynthesis.mytalk.server.dao.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.softwaresynthesis.mytalk.server.IMyTalkObject;
import org.softwaresynthesis.mytalk.server.abook.Group;
import org.softwaresynthesis.mytalk.server.dao.ISessionManager;

/**
 * Verifica della classe {@link GetUserDataUtil} per l'esecuzione delle query
 * sugli utenti del sistema.
 * 
 * @author Diego Beraldin
 * @version 2.0
 */
@RunWith(MockitoJUnitRunner.class)
public class GetGroupUtilTest {
	private final List<IMyTalkObject> list = new ArrayList<IMyTalkObject>();
	private final String query = "ThisIsNotAQuery";
	@Mock
	private Group group;
	@Mock
	private Query hql;
	@Mock
	private Transaction transaction;
	@Mock
	private Session session;
	@Mock
	private SessionFactory factory;
	@Mock
	private ISessionManager manager;
	private GetUtil tester;

	/**
	 * Reinizializza il comportamento dei mock prima di ciascuno dei test
	 * (alcuni possono essere sovrascritti nel corpo del metodo di test).
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Before
	public void setUp() {
		// configura il mock del gruppo e lo aggiunge alla lista
		list.add(group);
		// configura il comportamento della HQL query
		when(hql.list()).thenReturn(list);
		// configura il comportamento della sessione
		when(session.beginTransaction()).thenReturn(transaction);
		when(session.createQuery(query)).thenReturn(hql);
		// configura il comportamento della SessionFactory
		when(factory.openSession()).thenReturn(session);
		// configura il comportamento del SessionManager
		when(manager.getSessionFactory()).thenReturn(factory);
		// inizializza l'oggetto da testare
		tester = new GetGroupUtil(manager);
	}

	/**
	 * Verifica la corretta esecuzione del metodo nel caso in cui la ricerca
	 * vada a buon fine e, in particolare, verifica che la lista ritornata sia
	 * ottenuta mediante il metodo list() invocato sulla query HQL ottenuta
	 * sulla sessione a partire dalla stringa passata come parametro al metodo.
	 * È inoltre verificato che sui gruppi della lista sia invocato il metodo
	 * getAddressBook() per ottenere la collezione che deve essere inizializzata
	 * con Hibernate. Il test verifica anche che sia ottenuta una SessionFactory
	 * a partire dal SessionManager e che a partire da questa sia ottenuta una
	 * sessione, che sia aperta una transazione e creata una query HQL, su cui
	 * si invoca list(). È infine verificato che si termini con successo la
	 * transazione tramite il metodo commit() e che la sessione di interazione
	 * con il database sia finalizzata in modo corretto con i metodi flush() e
	 * close().
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testExecuteCorrectly() {
		// invoca il metodo da testare
		List<IMyTalkObject> resultingList = tester.execute(query);

		// verifica che i mock siano utilizzati nel modo corretto
		verify(manager).getSessionFactory();
		verify(factory).openSession();
		verify(session).createQuery(query);
		verify(session).beginTransaction();
		verify(hql).list();
		verify(transaction).commit();
		verify(transaction, never()).rollback();
		verify(session).flush();
		verify(session).close();
		verify(group).getAddressBook();

		// verifica la correttezza del risultato ottenuto
		assertNotNull(resultingList);
		assertEquals(list, resultingList);
	}

	/**
	 * Verifica il comportamento della classe nel momento in cui non è possibile
	 * avviare una transazione per l'interazione con il database. In
	 * particolare, il test verifica che non siano mai invocati i metodi
	 * rollback() e commit() su una transazione inesistente e che non sia mai
	 * invocato il metodo list() che effettua la query. Il test assicura altresì
	 * che la sessione sia aperta e finalizzata in modo corretto e che sia
	 * utilizzata sia per creare la query sul database che per tentare di
	 * avviare una transazione. Infine, si cotrolla che non siano mai invocati i
	 * metodi di inizializzazione sulla lista di gruppi associata alla query,
	 * dal momento che non è possibile avviare una transazione.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testExecuteWithoutTransaction() {
		// impedisce l'apertura di una transazione
		when(session.beginTransaction()).thenThrow(new RuntimeException());

		// invoca il metodo da testare
		List<IMyTalkObject> resultingList = tester.execute(query);

		// verifica che i mock siano stati utilizzati nel modo corretto
		verify(manager).getSessionFactory();
		verify(factory).openSession();
		verify(session).createQuery(query);
		verify(session).beginTransaction();
		verify(hql, never()).list();
		verify(transaction, never()).commit();
		verify(transaction, never()).rollback();
		verify(session).flush();
		verify(session).close();
		verify(group, never()).getAddressBook();

		// verifica il risultato ottenuto
		assertNull(resultingList);
	}

	/**
	 * Il test controlla il comportamento del metodo execute nel caso in cui al
	 * momento di effettuare la query dovesse verificarsi un evento eccezionale.
	 * In particolare, il test assicura che non sia mai invocato il metodo
	 * commit() sulla transazione e che sia invece invocato il rollback().
	 * Inoltre, è garantito che durante l'esecuzione del metodo sia aperta una
	 * sessione di interazione con il database e che quest'ultima sia
	 * finalizzata in modo corretto. Infine, si controlla che non siano mai
	 * invocati metodi sulla lista di gruppi che corrisponde alla query di
	 * ricerca con cui è invocato execute, dal momento che in fase di
	 * interrogazione del database avviene un errore.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testExecuteUnableToPerformQuery() {
		// solleva un'eccezione non controllata nel momento in cui si esegue la
		// query tramite il metodo list()
		when(hql.list()).thenThrow(new RuntimeException());

		// invoca il metodo da testare
		List<IMyTalkObject> resultingList = tester.execute(query);

		// verfica che i mock siano stati utilizzati nel modo corretto
		verify(manager).getSessionFactory();
		verify(factory).openSession();
		verify(session).createQuery(query);
		verify(session).beginTransaction();
		verify(hql).list();
		verify(transaction, never()).commit();
		verify(transaction).rollback();
		verify(session).flush();
		verify(session).close();
		verify(group, never()).getAddressBook();

		// verifica il risultato ottenuto
		assertNull(resultingList);
	}
}
