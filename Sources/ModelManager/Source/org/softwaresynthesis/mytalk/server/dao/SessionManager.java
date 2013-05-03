package org.softwaresynthesis.mytalk.server.dao;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Gestore delle sessioni di connessione
 * verso la base di dati del sistema MyTalk
 * 
 * @author 	Andrea Meneghinello
 * @version 3.0
 */
class SessionManager implements ISessionManager 
{
	private static SessionManager instance = null;
	
	private SessionFactory factory;
	
	/**
	 * Costruisce ed inizilizza il gestore delle
	 * sessioni di comunicazione con la base di dati
	 * del sistema MyTalk
	 */
	private SessionManager()
	{
		Configuration configuration = null;
		try
		{
			configuration = new Configuration();
			configuration = configuration.configure();
			this.factory = configuration.buildSessionFactory();
		}
		catch (Throwable ex)
		{
			throw new ExceptionInInitializerError("Errore durante la creazione della factory per le sessioni");
		}
	}

	@Override
	public SessionFactory getSessionFactory() 
	{
		return this.factory;
	}
	
	/**
	 * Punto di ingresso per l'unica istanza
	 * del gestore delle sessioni di connessione
	 * 
	 * @return 	{@link SessionManager} istanza del
	 * 			gestore sessioni
	 */
	public static SessionManager getInstance()
	{
		if (SessionManager.instance == null)
		{
			SessionManager.instance = new SessionManager();
		}
		return SessionManager.instance;
	}
}