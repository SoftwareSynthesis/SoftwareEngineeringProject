package org.softwaresynthesis.mytalk.server.dao;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Punto di accesso per la creazione
 * di sessioni di comunicazione con il
 * database
 * 
 * @author 	Andrea Meneghinello
 * @version	1.0
 */
public class HibernateUtil
{
	private static HibernateUtil instance = null;
	
	private SessionFactory factory;
	
	/**
	 * Inizializza l'unica istanza della
	 * classe HibernateUtil configurando
	 * la {@link SessionFactory} per la
	 * comunicazione con il database
	 */
	private HibernateUtil()
	{
		Configuration conf = null;
		try
		{
			conf = new Configuration();
			conf = conf.configure();
			this.factory = conf.buildSessionFactory();
		}
		catch (Throwable ex)
		{
			ex.printStackTrace();
			throw new ExceptionInInitializerError("Errore durante la creazione della factory per le sessioni");
		}
	}
	
	/**
	 * Punto di accesso all'unica instanza
	 * della classe
	 * 
	 * @return 	Istanza della classe
	 * @throws	{@link ExceptionInInitializerError} se l'inizializzazione
	 * 			da problemi
	 */
	public static HibernateUtil getInstance()
	{
		if(HibernateUtil.instance == null)
			HibernateUtil.instance = new HibernateUtil();
		return HibernateUtil.instance;
	}
	
	/**
	 * Restituisce una factory di tipo {@link SessionFactory}
	 * per creare ed avviare sessioni di comunicazione con il
	 * database
	 * 
	 * @return	{@link SessionFactory} per creare sessioni con
	 * 			il database
	 */
	public SessionFactory getFactory()
	{
		return this.factory;
	}
}
