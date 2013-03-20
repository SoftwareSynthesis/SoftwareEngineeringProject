package org.softwaresynthesis.mytalk.server.dao;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.softwaresynthesis.mytalk.server.abook.IGroup;

public class GroupDAO 
{
	/**
	 * Cancella il {@link IGroup} dal database se presente
	 * 
	 * @param 	group	{@link IGroup} che deve essere eliminato
	 * 					dal database
	 * @return	true se l'operazione è andata a buon fine, false altrimenti
	 */
	public boolean delete(IGroup group)
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
			session.delete(group);
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
	 * Aggiorna un {@link IGroup} nel database
	 * 
	 * @param 	group	{@link IGroup} che deve
	 * 					essere aggiornato
	 * @return	true se l'operazione è andata buon fine,
	 * 			false altrimenti
	 */
	public boolean update(IGroup group)
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
	 * Inserisce un nuovo {@link IGroup} nel database
	 * 
	 * @param 	user	{@link IGroup} da inserire}
	 * @return	true se l'operazione va a buon fine, false
	 * 			altrimenti
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
			factory = util.getFactory();
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
	
	/**
	 * Interroga il database per ottenere un istanza 
	 * {@link IGroup} che ha quell'identificatore
	 * 
	 * @param 	identifier	idenficatore dello {@link IGroup}
	 * @return	istanza di {@link IGroup} che è
	 * 			registrato con l'identificatore fornito
	 * 			in input, altrimenti null
	 */
	@SuppressWarnings("unchecked")
	public IGroup getByID(Long identifier)
	{
		HibernateUtil util = null;
		List<IGroup> groups = null;
		Query query = null;
		Session session = null;
		SessionFactory factory = null;
		String hqlQuery = "from Group as g where g.id = :id";
		Transaction transaction = null;
		try
		{
			util = HibernateUtil.getInstance();
			factory = util.getFactory();
			session = factory.openSession();
			query = session.createQuery(hqlQuery);
			query.setString("id", identifier.toString());
			transaction = session.beginTransaction();
			groups = (List<IGroup>)query.list();
			transaction.commit();
		}
		catch (RuntimeException ex)
		{
			if (transaction != null)
			{
				transaction.rollback();
			}
			groups = null;
		}
		finally
		{
			if (session != null)
			{
				session.flush();
				session.close();
			}
		}
		if (groups != null && groups.size() == 1)
		{
			return (IGroup)groups.get(0);
		}
		else
		{
			return null;
		}
	}
}