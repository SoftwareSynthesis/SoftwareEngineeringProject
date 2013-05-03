package org.softwaresynthesis.mytalk.server.dao;

import org.hibernate.Session;
import org.softwaresynthesis.mytalk.server.IMyTalkObject;

/**
 * Oggetto per l'aggiornamento di un record
 * nella base di dati del sistema MyTalk
 * 
 * @author 	Andrea Meneghinello
 * @version	3.0
 */
final class UpdateUtil extends ModifyUtil 
{
	/**
	 * Crea un istanza per l'aggiornamento di un record
	 * nella base di dati
	 * 
	 * @param 	manager	{@link ISessionManager}
	 */
	public UpdateUtil(ISessionManager manager)
	{
		super(manager);
	}

	@Override
	protected void doAction(Session session, IMyTalkObject object) 
	{
		session.update(object);
	}
}