package org.softwaresynthesis.mytalk.server.dao.util;

import org.hibernate.Session;
import org.softwaresynthesis.mytalk.server.IMyTalkObject;
import org.softwaresynthesis.mytalk.server.dao.ISessionManager;

/**
 * Oggetto per l'aggiornamento di un record
 * nella base di dati del sistema MyTalk
 * 
 * @author 	Andrea Meneghinello
 * @version	3.0
 */
class UpdateUtil extends ModifyUtil 
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
	protected final void doAction(Session session, IMyTalkObject object) 
	{
		session.update(object);
	}
}