package org.softwaresynthesis.mytalk.server.dao;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.softwaresynthesis.mytalk.server.abook.IGroup;
import org.softwaresynthesis.mytalk.server.message.IMessage;

/**
 * Espone le funzione per la manipolazione di oggetti
 * di tipo {@link IMessage} con il database
 * 
 * @author 	Andrea Meneghinello
 * @version	1.0
 */
public class MessageDAO 
{
	/**
	 * Cancella l' {@link IMessage} dal database se presente
	 * 
	 * @param 	message	{@link IMessage} che deve essere eliminato
	 * 					dal database
	 * @return	true se l'operazione e' andata a buon fine, false altrimenti
	 */
	public boolean delete(IMessage message)
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
	 * Aggiorna un {@link IMessage} nel database
	 * 
	 * @param 	message	{@link IMessage} che deve
	 * 					essere aggiornato
	 * @return	true se l'operazione e' andata buon fine,
	 * 			false altrimenti
	 */
	public boolean update(IMessage message)
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
	 * Inserisce un nuovo {@link IMessage} nel database
	 * 
	 * @param 	message	{@link IMessage} da inserire}
	 * @return	true se l'operazione va a buon fine, false
	 * 			altrimenti
	 */
	public boolean insert(IMessage message)
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
	
	/**
	 * Interroga il database per ottenere il valore
	 * massimo dell'ID dei messaggi
	 * 
	 * @return	istanza di {@link Long} che rappresenta
	 * 			la massima chiave presente nel database
	 */
	public Long getMaxKey()
	{
		HibernateUtil util = null;
		Long result = null;
		Query query = null;
		Session session = null;
		SessionFactory factory = null;
		String hqlQuery = null;
		Transaction transaction = null;
		try
		{
			util = HibernateUtil.getInstance();
			factory = util.getFactory();
			hqlQuery = "max(id) from Messages";
			session = factory.openSession();
			query = session.createQuery(hqlQuery);
			transaction = session.beginTransaction();
			result = (Long)query.uniqueResult();
			transaction.commit();
			
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
		return result;
	}
	
	/**
	 * Interroga il database per ottenere un istanza 
	 * {@link IMessage} che ha quell'identificatore
	 * 
	 * @param 	identifier	idenficatore dello {@link IMessage}
	 * @return	istanza di {@link IMessage} che e'
	 * 			registrato con l'identificatore fornito
	 * 			in input, altrimenti null
	 */
	@SuppressWarnings("unchecked")
	public IMessage getByID(Long identifier)
	{
		HibernateUtil util = null;
		IMessage message = null;
		List<IMessage> messages = null;
		Query query = null;
		Session session = null;
		SessionFactory factory = null;
		String hqlQuery = "from Messages as m where m.id = :id";
		Transaction transaction = null;
		try
		{
			util = HibernateUtil.getInstance();
			factory = util.getFactory();
			session = factory.openSession();
			query = session.createQuery(hqlQuery);
			query.setString("id", identifier.toString());
			transaction = session.beginTransaction();
			messages = (List<IMessage>)query.list();
			if (messages.size() == 1 && messages.get(0) != null)
			{
				message = messages.get(0);
			}
			transaction.commit();
		}
		catch (RuntimeException ex)
		{
			if (transaction != null)
			{
				transaction.rollback();
			}
			messages = null;
		}
		finally
		{
			if (session != null)
			{
				session.flush();
				session.close();
			}
		}
		return message;
	}
}