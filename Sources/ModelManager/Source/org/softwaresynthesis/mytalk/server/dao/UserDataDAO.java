package org.softwaresynthesis.mytalk.server.dao;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.softwaresynthesis.mytalk.server.abook.IUserData;
import org.softwaresynthesis.mytalk.server.abook.UserData;

/**
 * Espone le funzione per la manipolazione di oggetti
 * di tipo {@link IUserData} con il database
 * 
 * @author 	Andrea Meneghinello
 * @version	%I%, %G%
 */
public class UserDataDAO 
{
	/**
	 * Cancella lo {@link IUserData} dal database se presente
	 * 
	 * @param 	user	{@link IUserData} che deve essere eliminato
	 * 					dal database
	 * @return	true se l'operazione è andata a buon fine, false altrimenti
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
			factory = util.getFactory();
			session = factory.openSession();
			transaction = session.beginTransaction();
			session.delete(user);
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
	 * Aggiorna uno {@link IUserData} nel database
	 * 
	 * @param 	user	{@link IUserData} che deve
	 * 					essere aggiornato
	 * @return	true se l'operazione è andata buon fine,
	 * 			false altrimenti
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
			factory = util.getFactory();
			session = factory.openSession();
			transaction = session.beginTransaction();
			session.update(user);
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
	 * Inserisce un nuovo {@link IUserData} nel database
	 * 
	 * @param 	user	{@link IUserData} da inserire}
	 * @return	true se l'operazione va a buon fine, false
	 * 			altrimenti
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
			factory = util.getFactory();
			session = factory.openSession();
			transaction = session.beginTransaction();
			session.save(user);
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
	 * {@link IUserData} che si è registrato con la
	 * mail fornita in input
	 * 
	 * @param 	mail	{@link String} indirizzo mail
	 * 					con cui si è registrato l'utente
	 * @return	istanza di {@link IUserData} che si è
	 * 			registrato con tale indirizzo mail, null
	 * 			se nessun utente si è registrato con
	 * 			l'indirizzo specificzato
	 */
	@SuppressWarnings("unchecked")
	public IUserData getByEmail(String mail)
	{
		HibernateUtil util = null;
		List<UserData> users = null;
		Query query = null;
		Session session = null;
		SessionFactory factory = null;
		String hqlQuery = "from UserData as u where u.mail = :mail";
		Transaction transaction = null;
		
		java.io.File file;
		java.io.FileOutputStream s;
		java.io.PrintStream str = null;
		
		try
		{
				file = new java.io.File("Output.txt");
				s = new java.io.FileOutputStream(file);
				str = new java.io.PrintStream(s);
				str.println("AVVIO");
			util = HibernateUtil.getInstance();
			str.println("ISTANZA");
			factory = util.getFactory();
			str.println("FACTORY");
			session = factory.openSession();
			str.println("SESSIONE");
			transaction = session.beginTransaction();
			str.println("TRANS");
			query = session.createQuery(hqlQuery);
			query.setString("mail", mail);
			str.println(query.toString());
			str.println(mail);
			str.println("QUERY");
			users = (List<UserData>)query.list();
			str.println("ESECUZIONE");
			transaction.commit();
		}
		catch (RuntimeException ex)
		{
			ex.printStackTrace(str);
			if (transaction != null)
			{
				transaction.rollback();
			}
			users = null;
			
		}catch (Exception ex){}
		finally
		{str.close();
			if (session != null)
			{
				session.flush();
				session.close();
			}
		}
		if (users != null && users.size() == 1)
		{
			return (IUserData)users.get(0);
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * Interroga il database per ottenere un istanza 
	 * {@link IUserData} che ha quell'identificatore
	 * 
	 * @param 	identifier	idenficatore dello {@link IUserData}
	 * @return	istanza di {@link IUserData} che è
	 * 			registrato con l'identificatore fornito
	 * 			in input, altrimenti null
	 */
	@SuppressWarnings("unchecked")
	public IUserData getByID(Long identifier)
	{
		HibernateUtil util = null;
		List<IUserData> users = null;
		Query query = null;
		Session session = null;
		SessionFactory factory = null;
		String hqlQuery = "from UserData as u where u.id = :id";
		Transaction transaction = null;
		try
		{
			util = HibernateUtil.getInstance();
			factory = util.getFactory();
			session = factory.openSession();
			query = session.createQuery(hqlQuery);
			query.setString("id", identifier.toString());
			transaction = session.beginTransaction();
			users = (List<IUserData>)query.list();
			transaction.commit();
		}
		catch (RuntimeException ex)
		{
			if (transaction != null)
			{
				transaction.rollback();
			}
			users = null;
		}
		finally
		{
			if (session != null)
			{
				session.flush();
				session.close();
			}
		}
		if (users != null && users.size() == 1)
		{
			return (IUserData)users.get(0);
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * Avvia una ricerca generica, cercando
	 * se il parametro fonrito in ingresso
	 * ha qualche corrispondenza o con la
	 * mail o con il nome oppure con il cognome
	 * di uno {@link IUserData} nel database
	 * del sistema mytalk
	 * 
	 * @param 	parameter	parametro di tipo
	 * 						{@link String} con
	 * 						cui effettuare la
	 * 						ricerca
	 * @return	Una {@link List} con gli {@link IUserData}
	 * 			che hanno almeno un match con il parametro
	 * 			in ingresso
	 */
	@SuppressWarnings("unchecked")
	public List<IUserData> searchGeneric(String parameter)
	{
		HibernateUtil util = null;
		List<IUserData> users = null;
		Query query = null;
		Session session = null;
		SessionFactory factory = null;
		String hqlQuery = "from UserData as us where u.mail like :mail or u.name like :name or u.surname like :surname";
		Transaction transaction = null;
		try
		{
			util = HibernateUtil.getInstance();
			factory = util.getFactory();
			session = factory.openSession();
			query = session.createQuery(hqlQuery);
			query.setString("mail", parameter);
			query.setString("name", parameter);
			query.setString("surname", parameter);
			transaction = session.beginTransaction();
			users = (List<IUserData>)query.list();
			transaction.commit();
		}
		catch (Exception ex)
		{
			if (session != null)
			{
				transaction.rollback();
			}
			users = null;
		}
		finally
		{
			if (session != null)
			{
				session.flush();
				session.close();
			}
		}
		return users;
	}
	
	/**
	 * Restituisce l'istanza in forma di {@link String}
	 * 
	 * @return	{@link String} rappresentante l'istanza
	 */
	public String toString()
	{
		return "UserDataDAO";
	}
}