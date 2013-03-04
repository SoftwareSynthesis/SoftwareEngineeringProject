package org.softwaresynthesis.mytalk.server.dao;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.softwaresynthesis.mytalk.server.abook.IUserData;

/**
 * Crea un punto di accesso verso il database per
 * la gestione degli utenti
 * 
 * @author 	Andrea Meneghinello
 * @version	%I%, %G%
 */
public class UserDataDAO 
{
	/**
	 * Elimina uno {@link IUserData} dal database del
	 * sistema mytalk
	 * 
	 * @author	Andrea Meneghinello
	 * @param 	user	{@link IUserData} che deve essere
	 * 					eliminato
	 * @return	true se l'operazione di cancellazione è andata
	 * 			a buon fine, false altrimenti
	 */
	public boolean delete(IUserData user)
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
			session.delete(user);
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
			session.flush();
			session.close();
		}
		return flag;
	}
	
	/**
	 * Aggiorna uno {@link IUserData} nel database del
	 * sistema mytalk
	 * 
	 * @author	Andrea Meneghinello
	 * @param 	user	{@link IUserData} da aggiornare
	 * @return	true se l'operazione di aggiornamento è
	 * 			andata a buon fine, false altrimenti
	 */
	public boolean update(IUserData user)
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
			session.update(user);
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
			session.flush();
			session.close();
		}
		return flag;
	}
	
	/**
	 * Inserisce uno {@link IUserData} nel database del
	 * sistema del sistema mytalk
	 * 
	 * @author 	Andrea Meneghinello
	 * @param 	user	{@link IUserData} da inserire
	 * @return	true se l'operazione di inserimento è
	 * 			andata a buon fine, false altrimenti
	 */
	public boolean insert(IUserData user)
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
			session.save(user);
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
			session.flush();
			session.close();
		}
		return flag;
	}
	/**
	 * 
	 * @param name
	 * @return
	 */
  @SuppressWarnings("unchecked")
  public List<IUserData> getByNameAndSurname(String name, String surname) {
	  List<IUserData> list = null;
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
	    String hqlquery = "from UserData as u where u.name = :name or u.surname = :surname";
	    Query q = session.createQuery(hqlquery);
	    q.setString("name", name);
	    q.setString("surname", surname);
	    list = (List<IUserData>) q.list();
	    transaction.commit();
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
	    session.flush();
	    session.close();
	  }
	  return list;
	}
}