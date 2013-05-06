package org.softwaresynthesis.mytalk.server.dao.util;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.junit.Before;
import org.junit.Test;
import org.softwaresynthesis.mytalk.server.IMyTalkObject;
import org.softwaresynthesis.mytalk.server.dao.ISessionManager;

public class NotInitializeTest {
	// finta HQLQuery
	private final Query hql = mock(Query.class);
	// finta collezione di IMyTalkObject
	@SuppressWarnings("unchecked")
	private List<IMyTalkObject> collection = mock(List.class);
	// query di test
	private final String query = "ThisIsNotAQuery";
	// transazione fittizia sul database
	private final Transaction transaction = mock(Transaction.class);
	// sessione fittizia che deve essere restituita dalla factory
	private final Session session = mock(Session.class);
	// SessionFactory fittizia che deve essere ottenuta dal SessionManager
	private final SessionFactory factory = mock(SessionFactory.class);
	// SessionManager fittizio da passare al costruttore
	private final ISessionManager manager = mock(ISessionManager.class);

	// oggetto da testare
	private final GetUtil tester = new NotInitialize(manager);

	@Before
	public void setUp() {
		// configura il comportamento della HQL query
		when(hql.list()).thenReturn(collection);
		// configura il comportamento della sessione
		when(session.beginTransaction()).thenReturn(transaction);
		when(session.createQuery(query)).thenReturn(hql);
		// configura il comportamento della SessionFactory
		when(factory.openSession()).thenReturn(session);
		// configura il comportamento del SessionManager
		when(manager.getSessionFactory()).thenReturn(factory);
	}

	@Test
	public void testExecute() {
		fail("Not yet implemented");
	}

}
