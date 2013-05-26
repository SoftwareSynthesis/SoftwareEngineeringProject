package org.softwaresynthesis.mytalk.server.dao.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import org.hibernate.HibernateException;
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

/**
 * Verifica della classe (astratta) {@link ModifyUtil} che testa la corretta
 * implementazione del Template Method execute per l'esecuizione delle
 * operazioni di inserimento/aggiornamento/cancellazione sulla base di dati.
 * 
 * @author Diego Beraldin
 * @version 2.0
 */
@RunWith(MockitoJUnitRunner.class)
public class ModifyUtilTest {
	private ModifyUtil tester;
	@Mock
	private ISessionManager manager;
	@Mock
	private SessionFactory factory;
	@Mock
	private Session session;
	@Mock
	private Transaction transaction;
	@Mock
	private IMyTalkObject object;

	/**
	 * Inizializza l'oggetto da sottoporre a test e configura il comportamento
	 * dei mock prima di tutte le verifiche contenute in questo caso di test.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Before
	public void setUp() {
		when(session.beginTransaction()).thenReturn(transaction);
		when(factory.openSession()).thenReturn(session);
		when(manager.getSessionFactory()).thenReturn(factory);
		tester = spy(new ModifyUtil(manager) {
			@Override
			protected void doAction(Session session, IMyTalkObject object) {
				// sono solo un segnaposto per rendere concreto il metodo!
			}
		});
	}

	/**
	 * Verifica il comportamento del metodo execute quando l'operazione di
	 * inserimento, aggiunta oppure di cancellazione può essere portata a
	 * termine con successo perché non si verificano errori nel modello di
	 * interazione con il database ivi contenuto. Il test verifica in
	 * particolare che il l'invocazione del metodo restituisca <code>true</code>
	 * , che sia aperta e chiusa nel modo corretto una sessione, che sia avviata
	 * una transazione e che sia invocato su di essa il metodo commit() e mai il
	 * rollback(). Inoltre, si controlla anche la corretta implementazione del
	 * design pattern Template Method, verificando che il metodo termina senza
	 * che si verifichino errori viene effettivamente invocato il metodo
	 * astratto doAction che le sottoclassi di {@link ModifyUtil} dovranno
	 * rendere concreto.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testExecuteSuccessfully() {
		// invoca il metodo da testare
		boolean result = tester.execute(object);
		// verifica l'output
		assertTrue(result);
		// verifica il corretto utilizzo dei mock
		verify(manager).getSessionFactory();
		verify(factory).openSession();
		verify(session).beginTransaction();
		verify(session).flush();
		verify(session).close();
		verify(transaction).commit();
		verify(transaction, never()).rollback();
		verify(tester).doAction(session, object);
	}

	/**
	 * Verifica il comportamento del metodo execute nel momento in cui il
	 * modello di interazione con il database non può terminare con successo
	 * perché si verifica un errore nell'esecuzione dell'operazione concreta di
	 * inserimento, cancellazione o aggiornamento di una voce del database. Il
	 * test verifica che in tal caso l'invocazione del metodo restituisca
	 * <code>false</code> , che sia aperta e chiusa nel modo corretto una
	 * sessione di interazione con la base di dati, che sia avviata una
	 * transazione e che su quest'ultima sia invocato il rollback() e mai il
	 * commit().
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testExecuteUnsuccessfully() {
		// si verifica un errore
		doThrow(new RuntimeException()).when(tester).doAction(session, object);
		// invoca il metodo da testare
		boolean result = tester.execute(object);
		// verifica l'output
		assertFalse(result);
		// verifica il corretto utilizzo dei mock
		verify(manager).getSessionFactory();
		verify(factory).openSession();
		verify(session).beginTransaction();
		verify(session).flush();
		verify(session).close();
		verify(transaction, never()).commit();
		verify(transaction).rollback();
		verify(tester).doAction(session, object);
	}

	/**
	 * Verifica il comportamento del metodo execute nel momento in cui il
	 * framework Hibernate solleva un errore in fase di avvio della transazione
	 * per l'interazione con la base di dati. Il test verifica che in questo
	 * caso l'invocazione del metodo restituisca <code>false</code>, che sia
	 * aperta e chiusa nel modo corretto una sessione per l'interazione con il
	 * database e che non sia mai invocato il metodo doAction.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testExecuteWithoutTransaction() {
		// si verifica un errore
		when(session.beginTransaction()).thenThrow(
				new HibernateException("Ouch"));
		// invoca il metodo da testare
		boolean result = tester.execute(object);
		// verifica l'output
		assertFalse(result);
		// verifica il corretto utilizzo dei mock
		verify(manager).getSessionFactory();
		verify(factory).openSession();
		verify(session).beginTransaction();
		verify(session).flush();
		verify(session).close();
		verifyZeroInteractions(transaction);
		verify(tester, never()).doAction(any(Session.class),
				any(IMyTalkObject.class));
	}

	/**
	 * Verifica il comportamento del metodo execute nel momento in cui il
	 * framework Hibernate solleva un errore in fase di apertura di una sessione
	 * per l'interazione con la base di dati. Il test verifica che in questo
	 * caso l'invocazione del metodo restituisca <code>false</code> e che non
	 * sia mai invocato il metodo doAction.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testExecuteWithoutSession() {
		// si verifica un errore
		when(factory.openSession()).thenThrow(new HibernateException("Ouch"));
		// invoca il metodo da testare
		boolean result = tester.execute(object);
		// verifica l'output
		assertFalse(result);
		// verifica il corretto utilizzo dei mock
		verify(manager).getSessionFactory();
		verify(factory).openSession();
		verifyZeroInteractions(session);
		verifyZeroInteractions(transaction);
		verify(tester, never()).doAction(any(Session.class),
				any(IMyTalkObject.class));
	}
}
