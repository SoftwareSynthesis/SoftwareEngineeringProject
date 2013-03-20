package org.softwaresynthesis.mytalk.server.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.softwaresynthesis.mytalk.server.abook.IAddressBookEntry;

public class AddressBookEntryDAO 
{
	/**
	 * Cancella il {@link IAddressBookEntry} dal database se presente
	 * 
	 * @param 	group	{@link IAddressBookEntry} che deve essere eliminato
	 * 					dal database
	 * @return	true se l'operazione è andata a buon fine, false altrimenti
	 */
	public boolean delete(IAddressBookEntry entry)
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
	 * Aggiorna un {@link IAddressBookEntry} nel database
	 * 
	 * @param 	group	{@link IAddressBookEntry} che deve
	 * 					essere aggiornato
	 * @return	true se l'operazione è andata buon fine,
	 * 			false altrimenti
	 */
	public boolean update(IAddressBookEntry entry)
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
	 * Inserisce un nuovo {@link IAddressBookEntry} nel database
	 * 
	 * @param 	user	{@link IAddressBookEntry} da inserire}
	 * @return	true se l'operazione va a buon fine, false
	 * 			altrimenti
	 */
	public boolean insert(IAddressBookEntry entry)
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