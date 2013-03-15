package org.softwaresynthesis.mytalk.server.dao;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil
{
	private static HibernateUtil instance = null;
	
	private SessionFactory factory;
	
	private HibernateUtil()
	{
		Configuration conf = null;
		try
		{
			conf = new Configuration();
			System.out.println("1");
			conf = conf.configure();
			System.out.println("2");
			this.factory = conf.buildSessionFactory();
			System.out.println("3");
		}
		catch (Throwable ex)
		{
			System.out.println(ex.getMessage());
			System.out.println("------");
			ex.printStackTrace();
			throw new ExceptionInInitializerError("Errore durante la creazione della factory per le sessioni");
		}
	}
	
	/**
	 * Punto di accesso all'unica instanza
	 * della classe
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return 	Istanza della classe
	 * @throw	{@link ExceptionInInitializerError} se l'inizializzazione
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
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return	{@link SessionFactory} per creare sessioni con
	 * 			il database
	 */
	public SessionFactory getFactory()
	{
		return this.factory;
	}
}
