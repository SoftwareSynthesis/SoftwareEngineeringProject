package org.softwaresynthesis.mytalk.server.dao;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Classe singleton per la creazione delle sessioni,
 * sulla base del file di configurazione di hibernate,
 * verso il database
 * 
 * @author 	Andrea Meneghinello
 * @version	%I%, %G%
 */
public class HibernateUtil 
{
	private static HibernateUtil instance;
	private SessionFactory factory;
	
	private HibernateUtil()
	{
		try
		{
			this.factory = new Configuration().configure().buildSessionFactory();
		}
		catch (Throwable ex)
		{
			System.err.println("Inizializzazione della session factory fallita. " + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}
	
	/**
	 * Ottiene l'unica instanza della classe. Se non
	 * esiste viene creata e poi restituita al client.
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return	l'unica istanza della classe HibernateUtil
	 */
	public HibernateUtil getInstance()
	{
		if(HibernateUtil.instance == null)
			HibernateUtil.instance = new HibernateUtil();
		return HibernateUtil.instance;
	}
	
	/**
	 * Ottiene l'unica istanza della classe {@link SessionFactory}
	 * per creare sessioni verso il database
	 * 
	 * @author 	Andrea Meneghinello
	 * @verion	%I%, %G%
	 * @return	Ottiene l'unica istanza della classe {@link SessionFactory}
	 * 			per creare sessioni verso il database
	 */
	public SessionFactory getSessionFactory()
	{
		return this.factory;
	}
}