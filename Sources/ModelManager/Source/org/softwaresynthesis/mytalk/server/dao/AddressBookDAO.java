package org.softwaresynthesis.mytalk.server.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.softwaresynthesis.mytalk.server.abook.IAddressBookEntry;

/**
 * Crea un punto di accesso verso il database per
 * la gestione delle entry di una rubrica
 * 
 * @author 	Andrea Meneghinello
 * @version	%I%, %G%
 */
public class AddressBookDAO
{
	/**
	 * Cancella una entry della rubrica dal database
	 * del sistema mytalk
	 * 
	 * @author	Andrea Meneghinello
	 * @param 	entry	{@link IAddressBookEntry} che
	 * 					verrà cancellata dal database
	 * @return	true se l'operazione è andata a buon fine,
	 * 			false altrimenti
	 */
	public boolean delete(IAddressBookEntry entry)
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
			session.delete(entry);
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
	 * Aggiorna l'entry della rubrica nel database
	 * del sistema mytalk
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @param 	entry	{@link IAddressBookEntry} da
	 * 					modificare nel database
	 * @return	true se l'operazione è andata a buon fine,
	 * 			altrimenti false
	 */
	public boolean update(IAddressBookEntry entry)
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
			session.update(entry);
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
	 * Inserisce una nuova entry nel database del
	 * sistema mytalk
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @param 	entry	{@link IAddressBookEntry} da
	 * 					aggiungere al database del sistema
	 * 					mytalk
	 * @return	true se l'operazione è andata a buon fine,
	 * 			false altrimenti
	 */
	public boolean insert(IAddressBookEntry entry)
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
			session.save(entry);
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