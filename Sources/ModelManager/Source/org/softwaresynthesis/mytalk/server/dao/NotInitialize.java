package org.softwaresynthesis.mytalk.server.dao;

import java.util.List;

import org.softwaresynthesis.mytalk.server.IMyTalkObject;

/**
 * Crea un componente che non ha bisogno
 * di inizializzare collezioni di dati
 * 
 * @author 	Andrea Meneghinello
 * @version	3.0
 */
final class NotInitialize extends GetUtil 
{
	/**
	 * Crea una nuova istanza per le componenti che
	 * non hanno bisogno di inizializzare delle
	 * collezioni di dati
	 * 
	 * @param manager 	{@link ISessionManager} gestore della
	 * 					sessione di comunicazione con il database
	 */
	public NotInitialize(ISessionManager manager)
	{
		super(manager);
	}

	@Override
	protected void doInitialize(List<IMyTalkObject> collection) 
	{
	}
}
