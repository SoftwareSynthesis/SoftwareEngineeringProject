package org.softwaresynthesis.mytalk.server.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.softwaresynthesis.mytalk.server.message.IMessage;

/**
 * Crea un punto di accesso verso il database per
 * la gestione dei messaggi
 * 
 * @author 	Andrea Meneghinello
 * @version	%I%, %G%
 */
public class MessageDAO
{
	/**
	 * Cancella un messaggio dal database del sistema mytalk
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @param 	message	messaggio di segreteria da eliminare
	 * @return	true se l'operazione è andata a buon fine,
	 * 			false altrimenti
	 */
	public boolean delete(IMessage message)
	{
		boolean flag = false;
		HibernateUtil util = null;
		SessionFactory factory = null;
		Session session = null;
		Transaction transaction = null;
		try
		{
			util = HibernateUtil.getInstance();
			factory = util.getSessionFactory();
			session = factory.openSession();
			transaction = session.beginTransaction();
			session.delete(message);
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
	 * Aggiorna un messaggio di segreteria nel database del
	 * sistema mytalk
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @param 	message	{@Link IMessage} da aggiornare nel
	 * 					database del sistema mytalk
	 * @return	true se l'operazione è andata a buon fine,
	 * 			false altrimenti
	 */
	public boolean update(IMessage message)
	{
		boolean flag = false;
		HibernateUtil util = null;
		SessionFactory factory = null;
		Session session = null;
		Transaction transaction = null;
		try
		{
			util = HibernateUtil.getInstance();
			factory = util.getSessionFactory();
			session = factory.openSession();
			transaction = session.beginTransaction();
			session.update(message);
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
	 * Inserisce un nuovo messaggio nel database
	 * del sistema mytalk
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @param 	message
	 * @return
	 */
	public boolean insert(IMessage message)
	{
		boolean flag = false;
		HibernateUtil util = null;
		SessionFactory factory = null;
		Session session = null;
		Transaction transaction = null;
		try
		{
			util = HibernateUtil.getInstance();
			factory = util.getSessionFactory();
			session = factory.openSession();
			transaction = session.beginTransaction();
			session.save(message);
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
}