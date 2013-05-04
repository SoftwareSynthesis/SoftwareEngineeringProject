package org.softwaresynthesis.mytalk.server.dao;

import org.hibernate.SessionFactory;

/**
 * Rappresenta il gestore delle sessioni
 * di connessione verso la base di dati
 * 
 * @author 	Andrea Meneghinello
 * @version	3.0
 */
public interface ISessionManager 
{
	/**
	 * Restituisce una factory configurata
	 * per la gestione di sessioni di connessione
	 * con la base di dati del sistema MyTalk
	 * 
	 * @return 	{@link SessionFactory} factory per la
	 * 			creazione di sessioni di connessione
	 * 			con la base di dati
	 */
	public SessionFactory getSessionFactory();
}