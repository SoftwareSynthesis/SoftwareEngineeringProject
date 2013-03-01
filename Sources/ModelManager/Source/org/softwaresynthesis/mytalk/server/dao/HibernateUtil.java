package org.softwaresynthesis.mytalk.server.dao;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Inializza un unica factory per le sessioni, utilizzate da Hibernate,
 * per comunicare con il database
 * 
 * @author	Andrea Meneghinello
 * @version	%I%, %G%
 */
public class HibernateUtil 
{
	private static HibernateUtil instance = null;
	
	private SessionFactory sessionFactory;
	
	private HibernateUtil()
	{
		try
		{
			this.sessionFactory = new Configuration().configure().buildSessionFactory();
		}
		catch (Throwable ex)
		{
			throw new ExceptionInInitializerError("Errore durante la creazione della Session Factory. Impossibile comunicare con la base di dati");
		}
	}
	
	/**
	 * Punto di accesso per l'unica istanza alla classe HibernateUtil
	 * 
	 * @author 	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return	istanza della classe HibernateUtil
	 */
	public static HibernateUtil getInstance()
	{
		if(HibernateUtil.instance == null)
			HibernateUtil.instance = new HibernateUtil();
		return HibernateUtil.instance;
	}
	
	/**
	 * Restituisce una {@link SessionFactory} configurata per la
	 * comunicazione con il database mytalk
	 * 
	 * @author 	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return	istanza della classe {@link SessionFactory} per
	 * 			la comunicazione con il database del sistema
	 * 			mytalk
	 */
	public SessionFactory getSessionFactory()
	{
		return this.sessionFactory;
	}
}