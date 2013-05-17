package org.softwaresynthesis.mytalk.server.dao.util;

import static org.junit.Assert.*;

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
import org.softwaresynthesis.mytalk.server.dao.ISessionManager;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GetUtilTest {
	private final String queryString = "ThisIsNotAQuery";
	private final Long result = 1L;
	private GetUtil tester;
	@Mock
	ISessionManager manager;
	@Mock
	SessionFactory factory;
	@Mock
	Session session;
	@Mock
	Transaction transaction;
	@Mock
	List<IMyTalkObject> list;
	@Mock
	Query query;

	/**
	 * Configura il comportamento dei mock e inizializza l'oggetto da testare
	 * prima di ognuna delle verifiche contenute in questo caso di test.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Before
	public void setUp() {
		// comportamento della query
		when(query.uniqueResult()).thenReturn(result);
		when(query.list()).thenReturn(list);
		// comportamento della gestore di sessione
		when(manager.getSessionFactory()).thenReturn(factory);
		// comportamento della factory
		when(factory.openSession()).thenReturn(session);
		// comportamento della sessione
		when(session.beginTransaction()).thenReturn(transaction);
		when(session.createQuery(queryString)).thenReturn(query);
		// inizializza l'oggetto da testare
		tester = spy(new GetUtil(manager) {
			@Override
			protected void doInitialize(List<IMyTalkObject> collection) {
			}
		});
	}

	/**
	 * Verifica il comportamento del metodo uniqueResult quando l'operazione va
	 * a buon fine. In particolare, il test verifica che il risultato
	 * dell'invocazione del metodo corrisponda alle aspettative, in base a come
	 * è stato configurato il mock della collezione che è utilizzato nel test.
	 * Inoltre si controlla che i mock siano utilizzati nel modo corretto per
	 * l'apertura di una sessione creata a partire dal {@link SessionManager},
	 * che a partire dalla sessione sia avviata una transazione e una query, che
	 * la transazione sia chiusa con il metodo commit() e che la sessione di
	 * interazione con la base di dati sia chiusa correttamente con
	 * l'invocazione dei metodi flush() e close().
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testUniqueResultCorrectly() {
		// invoca il metodo da testare
		Long retrievedId = tester.uniqueResult(queryString);
		assertNotNull(retrievedId);
		assertEquals(result, retrievedId);

		// verifica il corretto utilizzo dei mock
		verify(manager).getSessionFactory();
		verify(factory).openSession();
		verify(session).beginTransaction();
		verify(session).createQuery(queryString);
		verify(query).uniqueResult();
		verify(transaction).commit();
		verify(transaction, never()).rollback();
		verify(session).flush();
		verify(session).close();
	}

	/**
	 * Verifica il comportamento del metodo uniqueResult quando l'operazione non
	 * può essere realizzata correttamente perché si verifica un errore a
	 * transazione iniziata. In particolare, il test verifica che il risultato
	 * dell'invocazione sia <code>null</code>, come desiderato in questo caso.
	 * Inoltre si controlla che i mock siano utilizzati nel modo corretto per
	 * l'apertura di una sessione creata a partire dal {@link SessionManager},
	 * che a partire dalla sessione sia avviata una transazione e una query, che
	 * la transazione sia annullata con il metodo rollback() e non sia mai
	 * chiusa con commit() e che la sessione di interazione con la base di dati
	 * sia chiusa correttamente con l'invocazione dei metodi flush() e close().
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testUniqueResultWithError() {
		// si verifica un errore nell'esecuzione del metodo
		when(query.uniqueResult()).thenThrow(new RuntimeException());

		// invoca il metodo da testare
		Long retrievedId = tester.uniqueResult(queryString);
		assertNull(retrievedId);

		// verifica il corretto utilizzo dei mock
		verify(manager).getSessionFactory();
		verify(factory).openSession();
		verify(session).beginTransaction();
		verify(session).createQuery(queryString);
		verify(query).uniqueResult();
		verify(transaction, never()).commit();
		verify(transaction).rollback();
		verify(session).flush();
		verify(session).close();
	}

	/**
	 * Verifica il comportamento della classe nel momento in cui il metodo
	 * uniqueResult non termina con successo per l'impossibilità di aprire una
	 * transazione. In particolare, il test verifica che non avvengano
	 * interazioni con la transazione e che non sia mai invocato il metodo
	 * uniqueResult() sulla query HQL generata a partire dalla stringa passata
	 * come parametro al metodo uniqueResult del {@link GetUtil}.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testUniqueResultWithoutTransaction() {
		// impedisce la creazione di una transazione
		when(session.beginTransaction()).thenThrow(new RuntimeException());

		// invoca il metodo da testare
		Long retrievedId = tester.uniqueResult(queryString);
		assertNull(retrievedId);

		// verifica il corretto utilizzo dei mock
		verify(manager).getSessionFactory();
		verify(factory).openSession();
		verify(session).beginTransaction();
		verify(session).createQuery(queryString);
		verify(query, never()).uniqueResult();
		verifyZeroInteractions(transaction);
		verify(session).flush();
		verify(session).close();
	}

	/**
	 * Verifica il comportamento del metodo execute (template method)
	 * assicurandosi che siano compiute le operazioni necessarie a tutte le
	 * operazioni di SELECT sulla base di dati, vale a dire che sia aperta una
	 * sessione di interazione con il database a partire dalla SessionFactory
	 * ottenuta dal SessionManager, che sia aperta una transazione e creata una
	 * HQL query con il testo passato come parametro al metodo execute, che sia
	 * invocato il metodo list() sulla query, che la transazione sia chiusa con
	 * successo (e che non sia MAI invocato il rollback()) e che la sessione sia
	 * chiusa nel modo corretto. Infine, il test verifica la corretta
	 * applicazione del pattern TEMPLATE METHOD verificando che execute richiami
	 * il doInitialize astratto sovrascritto dalle sottoclassi.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testExecuteCorrectly() {
		// invoca il metodo da testare
		List<IMyTalkObject> result = tester.execute(queryString);

		// verifica l'output
		assertNotNull(result);
		assertEquals(list, result);

		// verifica il corretto utilizzo dei mock
		verify(manager).getSessionFactory();
		verify(factory).openSession();
		verify(session).beginTransaction();
		verify(session).createQuery(queryString);
		verify(query).list();
		verify(transaction).commit();
		verify(transaction, never()).rollback();
		verify(session).flush();
		verify(session).close();
		verify(tester).doInitialize(list);
	}

	/**
	 * Verifica il comportamento del metodo execute assicurandosi che siano
	 * compiute le operazioni necessarie a tutte le operazioni di SELECT sulla
	 * base di dati, quando si verifica un errore in fase di esecuzione
	 * dell'interrogazione ma dopo l'apertura di una transazione. Il test
	 * verifica che in questo caso sia aperta una sessione di interazione con il
	 * database a partire dalla SessionFactory ottenuta dal SessionManager, che
	 * sia aperta una transazione e creata una HQL query con il testo passato
	 * come parametro al metodo execute, che sia invocato il metodo list() sulla
	 * query, che della transazione sia effettuato il rollback() e MAI il
	 * commit() e che infine la sessione sia chiusa nel modo corretto.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testExecuteWithError() {
		// si verifica un errore nel recupero dei dati
		when(query.list()).thenThrow(new RuntimeException());

		// invoca il metodo da testare
		List<IMyTalkObject> result = tester.execute(queryString);

		// verifica l'output
		assertNull(result);

		// verifica il corretto utilizzo dei mock
		verify(manager).getSessionFactory();
		verify(factory).openSession();
		verify(session).beginTransaction();
		verify(session).createQuery(queryString);
		verify(query).list();
		verify(transaction, never()).commit();
		verify(transaction).rollback();
		verify(session).flush();
		verify(session).close();
		verify(tester, never()).doInitialize(list);
	}
}
