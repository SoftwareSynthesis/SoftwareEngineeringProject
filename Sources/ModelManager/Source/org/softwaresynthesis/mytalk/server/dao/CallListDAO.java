package org.softwaresynthesis.mytalk.server.dao;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.softwaresynthesis.mytalk.server.call.ICallList;
import org.softwaresynthesis.mytalk.server.abook.IUserData;

/**
 * Espone le funzione per la manipolazione di oggetti
 * di tipo {@link ICallList} con il database
 * 
 * @author 	Schivo Marco
 * @version	1.0
 */
public class CallListDAO 
{
	/**
	 * Cancella l' {@link ICallList} dal database se presente
	 * 
	 * @param 	message	{@link ICallList} che deve essere eliminato
	 * 					dal database
	 * @return	true se l'operazione e' andata a buon fine, false altrimenti
	 */
	public boolean delete(ICallList callList)
	{
		boolean flag = false;
		HibernateUtil util = null;
		Session session = null;
		SessionFactory factory = null;
		Transaction transaction = null;
		try
		{
			util = HibernateUtil.getInstance();
			factory = util.getFactory();
			session = factory.openSession();
			transaction = session.beginTransaction();
			session.delete(callList);
			transaction.commit();
			flag = !flag;
		}
		catch (RuntimeException ex)
		{
			if (transaction != null)
			{
				transaction.rollback();
			}
		}
		finally
		{
			if (session != null)
			{
				session.flush();
				session.close();
			}
		}
		return flag;
	}
	
	/**
	 * Aggiorna un {@link ICallList} nel database
	 * 
	 * @param 	message	{@link ICallList} che deve
	 * 					essere aggiornato
	 * @return	true se l'operazione e' andata buon fine,
	 * 			false altrimenti
	 */
	public boolean update(ICallList callList)
	{
		boolean flag = false;
		HibernateUtil util = null;
		Session session = null;
		SessionFactory factory = null;
		Transaction transaction = null;
		try
		{
			util = HibernateUtil.getInstance();
			factory = util.getFactory();
			session = factory.openSession();
			transaction = session.beginTransaction();
			session.update(callList);
			transaction.commit();
			flag = !flag;
		}
		catch (RuntimeException ex)
		{
			if (transaction != null)
			{
				transaction.rollback();
			}
		}
		finally
		{
			if (session != null)
			{
				session.flush();
				session.close();
			}
		}
		return flag;
	}
	
	/**
	 * Inserisce un nuovo {@link ICallList} nel database
	 * 
	 * @param 	message	{@link ICallList} da inserire}
	 * @return	true se l'operazione va a buon fine, false
	 * 			altrimenti
	 */
	public boolean insert(ICallList callList)
	{
		boolean flag = false;
		HibernateUtil util = null;
		Session session = null;
		SessionFactory factory = null;
		Transaction transaction = null;
		try
		{
			util = HibernateUtil.getInstance();
			factory = util.getFactory();
			session = factory.openSession();
			transaction = session.beginTransaction();
			session.save(callList);
			transaction.commit();
			flag = !flag;
		}
		catch (RuntimeException ex)
		{
			if (transaction != null)
			{
				transaction.rollback();
			}
		}
		finally
		{
			if (session != null)
			{
				session.flush();
				session.close();
			}
		}
		return flag;
	}
	
	
	
	/**
	 * Interroga il database per ottenere una lista di 
	 * {@link ICallList} che ha come utente l'utente
	 * dato in input
	 * 
	 * @param 	userToFind	utente {@link IUserData}
	 * @return	lista di {@link ICallList} con utente
	 * 			userToFind
	 */
	@SuppressWarnings("unchecked")
	public List<ICallList> getByUser(IUserData userToFind)
	{
		HibernateUtil util = null;
		List<ICallList> callLists = null;
		Query query = null;
		Session session = null;
		SessionFactory factory = null;
		String hqlQuery = "from CallLists as c where c.ID_user.id = :user";
		Transaction transaction = null;
		try
		{
			util = HibernateUtil.getInstance();
			factory = util.getFactory();
			session = factory.openSession();
			query = session.createQuery(hqlQuery);
			query.setString("user", (userToFind.getId()).toString());
			transaction = session.beginTransaction();
			callLists = (List<ICallList>)query.list();
			transaction.commit();
		}
		catch (RuntimeException ex)
		{
			if (transaction != null)
			{
				transaction.rollback();
			}
			callLists = null;
		}
		finally
		{
			if (session != null)
			{
				session.flush();
				session.close();
			}
		}
		return callLists;
	}

}