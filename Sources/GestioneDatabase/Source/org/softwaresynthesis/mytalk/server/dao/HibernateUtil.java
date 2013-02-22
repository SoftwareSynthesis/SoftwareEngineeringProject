package org.softwaresynthesis.mytalk.server.dao;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * La classe ha il compito di creare sessioni con la base di dati quando 
 * è necessario interagire con essa
 * 
 * @author 	Andrea Meneghinello
 * @version	%I%, %G%
 */
public class HibernateUtil 
{
	private static HibernateUtil instance = null;
	private SessionFactory factory = null;
	
	/**
	 * Inizializzazione dell'oggetto HibernateUtil
	 */
	private HibernateUtil()
	{
		try
		{
			this.factory = new Configuration().configure().buildSessionFactory();
		}
		catch (Throwable ex)
		{
			System.err.print("Creazione iniziale della session factory fallita. " + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}
	
	/**
	 * Restituisce l'unica istanza della classe HibernateUtil che sarà utilizzata
	 * per instanziare delle sessioni con la base di dati
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return	Restituisce l'unica istanza della classe HibernateUtil che sarà 
	 * utilizzate per instanziare delle sessioni con la base di dati
	 */
	public static HibernateUtil getInstance()
	{
		if(HibernateUtil.instance == null)
			HibernateUtil.instance = new HibernateUtil();
		return HibernateUtil.instance;
	}
	
	/**
	 * Restituisce un istanza della classe {@link SessionFactory} per creare una 
	 * sessione di comunicazione con la base di dati
	 *
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return	Restituisce un istanza della classe {@link SessionFactory} per 
	 * creare una sessione di comunicazione con la base di dati
	 */
	public SessionFactory getSessionFactory()
	{
		return this.factory;
	}
}