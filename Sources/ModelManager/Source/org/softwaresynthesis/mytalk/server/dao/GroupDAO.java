package org.softwaresynthesis.mytalk.server.dao;

import org.softwaresynthesis.mytalk.server.abook.IGroup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * Punto di accesso al database del sistema per la gestione dei gruppi
 * 
 * @author Diego Beraldin
 * @version %I%, %G%
 */
public class GroupDAO
{
	/**
	 * Rimuove un {@link IGroup} dal database del sistema
	 * 
	 * @author Diego Beraldin
	 * @version %I%, %G%
	 * @param group	{@link IGroup} da rimuovere
	 * @return true se l'operazione va a buon fine, false altrimenti
	 */
	public boolean delete(IGroup group)
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
			session.delete(group);
			transaction.commit();
			flag = !flag;
		}
		catch (RuntimeException ex)
		{
			if(transaction != null)
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
	 * Aggiorna le informazioni relative a un {@link IGroup} 
	 * contenute nel database del sistema
	 * 
	 * @author Diego Beraldin
	 * @version %I%, %G%
	 * @param group	{@link IGroup} da aggiornare
	 * @return true se l'operazione va a buon fine, false altrimenti
	 */
	public boolean update(IGroup group)
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
			session.update(group);
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
	 * Inserisce un nuovo {@link IGroup} nel database del sistema
	 * 
	 * @author Diego Beraldin
	 * @version %I%, %G%
	 * @param group	{@link IGroup} da inserire nel database
	 * @return true se l'operazione va a buon fine, false altrimenti
	 */
	public boolean insert(IGroup group)
	{
		boolean flag = false;
		HibernateUtil util = null;
		Session session = null;
		SessionFactory factory = null;
		Transaction transaction = null;
		try
		{
			util = HibernateUtil.getInstance();
			factory = util.getSessionFactory();
			session = factory.openSession();
			transaction = session.beginTransaction();
			session.save(group);
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

