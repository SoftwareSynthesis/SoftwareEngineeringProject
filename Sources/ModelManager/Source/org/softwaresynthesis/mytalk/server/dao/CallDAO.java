package org.softwaresynthesis.mytalk.server.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.softwaresynthesis.mytalk.server.call.ICall;

/**
 * Espone le funzione per la manipolazione di oggetti
 * di tipo {@link ICall} con il database
 * 
 * @author 	Andrea Meneghinello
 * @version	1.0
 */
public class CallDAO 
{
	/**
	 * Elimina una chiamata dal database di
	 * mytalk
	 * 
	 * @param 	call	{@link ICall} che deve
	 * 					essere eliminata
	 * @return	true se l'operazione è andata a
	 * 			buon fine false altrimenti
	 */
	public boolean delete(ICall call)
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
			session.delete(call);
			transaction.commit();
			flag = !flag;
		}
		catch (RuntimeException ex)
		{
			if (transaction != null)
			{
				transaction.rollback();
			}
			flag = false;
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
	 * Aggiorna una chiamata nel database di
	 * mytalk
	 * 
	 * @param 	call	{@link ICall} che deve
	 * 					essere aggiornata
	 * @return	true se l'operazione è andata a
	 * 			buon fine false altrimenti
	 */
	public boolean update(ICall call)
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
			session.update(call);
			transaction.commit();
			flag = !flag;
		}
		catch (RuntimeException ex)
		{
			if (transaction != null)
			{
				transaction.rollback();
			}
			flag = false;
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
	 * Inserisce una nuova chiamata nel database
	 * di mytalk
	 * 
	 * @param 	call	{@link ICall} chiamata da
	 * 					inserire
	 * @return	true se l'operazione è andata a
	 * 			buon fine false altrimenti	
	 */
	public boolean insert(ICall call)
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
			session.save(call);
			transaction.commit();
			flag = !flag;
		}
		catch (RuntimeException ex)
		{
			if (transaction != null)
			{
				transaction.rollback();
			}
			flag = false;
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
}