package org.softwaresynthesis.mytalk.server.dao;

import org.hibernate.Session;
import org.softwaresynthesis.mytalk.server.IMyTalkObject;

/**
 * Oggetto per la rimozione di un record
 * dalla base di dati del sistema MyTalk
 * 
 * @author 	Andrea Meneghinello
 * @version 3.0
 */
final class DeleteUtil extends ModifyUtil 
{
	/**
	 * Crea un istanza per la cancellazione di un record
	 * nella base di dati
	 * 
	 * @param 	manager	{@link ISessionManager}
	 */
	public DeleteUtil(ISessionManager manager)
	{
		super(manager);
	}

	@Override
	protected void doAction(Session session, IMyTalkObject object) 
	{
		session.delete(object);
	}
}