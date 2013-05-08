package org.softwaresynthesis.mytalk.server.dao.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.junit.Before;
import org.junit.Test;
import org.softwaresynthesis.mytalk.server.IMyTalkObject;
import org.softwaresynthesis.mytalk.server.dao.ISessionManager;

/**
 * Test di {@link InsertUtil} che testa il 'template method' execute e la
 * corretta implementazione del doAction che, se tutto va a buon fine, invoca il
 * metodo save() sulla sessione. Il test è fatto in modo da coprire tutti i rami
 * e la gestione delle eccezioni che viene fatta in ModifyUtil.
 * 
 * @author Diego Beraldin
 * @version 2.0
 */
public class InsertUtilTest {
	// dati di test (simula un oggetto da rendere persistente)
	private final IMyTalkObject object = mock(IMyTalkObject.class);
	// transazione fittizia sul database
	private final Transaction transaction = mock(Transaction.class);
	// sessione fittizia di interazione con il database
	private final Session session = mock(Session.class);
	// SessionFactory fittizia per generare la sessione
	private final SessionFactory factory = mock(SessionFactory.class);
	// SessionManager fittizio per generare la SessionFactory
	private final ISessionManager manager = mock(ISessionManager.class);
	// oggetto da testare
	private final InsertUtil tester = new InsertUtil(manager);

	/**
	 * Reinizializza il comportamento dei mock prima di ciascuno dei test
	 * (alcuni possono essere sovrascritti nel corpo del metodo di test). Fare
	 * questo passaggio ogni volta è necessario per contare il numero di volte
	 * che i vengono invocati i metodi su questi mock tramite il verify.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Before
	public void setUp() {
		// configura il comportamento della sessione
		when(session.beginTransaction()).thenReturn(transaction);

		// configura il comportamento della factory
		when(factory.openSession()).thenReturn(session);

		// configura il comportamento del gestore di sessioni
		when(manager.getSessionFactory()).thenReturn(factory);
	}

	/**
	 * Test che verifica che l'esecuzione di un inserimento nel database sia
	 * portata a termine in maniera corretta. In particolare si verifica che il
	 * metodo execute sia invocato con successo e restituendo <code>true</code>
	 * e che nel corso della sua esecuzione sia invocato: una (e una sola) volta
	 * il metodo getSessionFactory() su SessionManager, una (e una sola) volta
	 * openSession() su SessionFactory, una (e una sola) volta
	 * beginTransaction() sulla sessione, una (e una sola) volta save(object)
	 * sulla sessione, una (e una sola) volta commit() sulla transazione, una (e
	 * una sola) volta flush() sulla sessione, una (e una sola) volta close()
	 * sulla sessione e che non sia MAI invocato rollback() sulla transazione.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testExecuteCorrectly() {
		// invoca il metodo da testare
		Boolean result = tester.execute(object);

		// verifiche sull'utilizzo dei mock
		verify(manager).getSessionFactory();
		verify(factory).openSession();
		verify(session).beginTransaction();
		verify(session).save(object);
		verify(transaction).commit();
		verify(transaction, never()).rollback();
		verify(session).flush();
		verify(session).close();

		// verifica del risultato ottenuto
		assertTrue(result);
	}

	/**
	 * Test che verifica il comportamento del metodo execute nel momento in cui
	 * si verifica un errore e non è possibile iniziare una transazione verso il
	 * database. Questo avviene facendo in modo che all'invocazione di
	 * beginTransaction() sulla sessione sia sollevata una RuntimeException. In
	 * particolare, il test controlla che il metodo execute restituisca il
	 * valore <code>false</code>, che non sia invocato MAI il metodo save()
	 * sulla sessione al di fuori di una transazione e che non siano mai
	 * invocati i metodi commit() e rollback() di una transazione di fatto
	 * inesistente. Il test si assicura invece che la sessione costruita, aperta
	 * e chiusa correttamente.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testExecuteWithoutTransaction() {
		// impedisce l'apertura di una sessione
		when(session.beginTransaction()).thenThrow(new RuntimeException());

		// invoca il metodo da testare
		Boolean result = tester.execute(object);

		// verifiche sull'utilizzo dei mock
		verify(manager).getSessionFactory();
		verify(factory).openSession();
		verify(session).beginTransaction();
		verify(session, never()).save(object);
		verify(transaction, never()).commit();
		verify(transaction, never()).rollback();
		verify(session).flush();
		verify(session).close();

		// verifica del risultato ottenuto
		assertFalse(result);
	}

	/**
	 * Lo scopo del test è verificare il comportamento della classe nel momento
	 * in cui, dopo l'apertura di una sessione e l'avvio di una transazione, non
	 * è possibile portare a termine la scrittura sul database. In particolare
	 * il test verifica che sia invocato correttamente il rollback() della
	 * transazione (e MAI il commit()), e che la sessione sia creata, aperta,
	 * utilizzata per avviare una transazione e chiusa nel modo corretto. Anche
	 * in questo caso si controlla che il metodo execute di InsertUtil
	 * restituisca, come atteso, <code>false</code> perché l'operazione che
	 * doveva essere svolta non è andata a buon fine.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testExecuteUnableToPerformAction() {
		// impedisce il salvataggio
		when(session.save(object)).thenThrow(new RuntimeException());

		// invoca il metodo da testare
		Boolean result = tester.execute(object);

		// verifiche sull'utilizzo dei mock
		verify(manager).getSessionFactory();
		verify(factory).openSession();
		verify(session).beginTransaction();
		verify(session).save(object);
		verify(transaction, never()).commit();
		verify(transaction).rollback();
		verify(session).flush();
		verify(session).close();

		// verifica del risultato ottenuto
		assertFalse(result);
	}
}
