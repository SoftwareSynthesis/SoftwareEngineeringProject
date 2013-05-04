package org.softwaresynthesis.mytalk.server.dao.util;

import org.hibernate.Session;
import org.softwaresynthesis.mytalk.server.IMyTalkObject;
import org.softwaresynthesis.mytalk.server.dao.ISessionManager;

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
	protected final void doAction(Session session, IMyTalkObject object) 
	{
		session.delete(object);
	}
}