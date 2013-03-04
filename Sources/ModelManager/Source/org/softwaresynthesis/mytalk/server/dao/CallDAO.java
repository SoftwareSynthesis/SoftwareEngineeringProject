package org.softwaresynthesis.mytalk.server.dao;

import org.softwaresynthesis.mytalk.server.call.ICall;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * Punto di accesso al database del sistema per la gestione delle chiamate
 * 
 * @author Diego Beraldin
 * @version %I%, %G%
 *
 */
public class CallDAO
{
	/**
	 * Elimina una chiamata ({@link ICall}) dal database
	 * 
	 * @author Diego Beraldin
	 * @version %I%, %G%
	 * @param call	{@link ICall} da eliminare
	 * @return true se l'operazione è andata a buon fine, 
	 * false altrimenti
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
			factory = util.getSessionFactory();
			session = factory.openSession();
			transaction = session.beginTransaction();
			session.delete(call);
			transaction.commit();
			flag  = !flag;
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
	 * Aggiorna i dati relativi a una chiamata ({@link ICall}) 
	 * contenuta nel database
	 * 
	 * @author Diego Beraldin
	 * @version %I%, %G%
	 * @param call	{@link ICall} da aggiornare
	 * @return true se l'operazione di aggiornamento va a buon fine,
	 * false altrimenti
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
			factory = util.getSessionFactory();
			session = factory.openSession();
			transaction = session.beginTransaction();
			session.update(call);
			transaction.commit();
			flag  = !flag;
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
	 * Inserisce una nuova chiamata ({@link ICall}) nel database
	 * 
	 * @author Diego Beraldin
	 * @version %I%, %G%
	 * @param call	{@link ICall} da inserire nel database
	 * @return true se l'operazione in inserimento va a buon fine,
	 * false altrimenti
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
			factory = util.getSessionFactory();
			session = factory.openSession();
			transaction = session.beginTransaction();
			session.save(call);
			transaction.commit();
			flag  = !flag;
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
}
