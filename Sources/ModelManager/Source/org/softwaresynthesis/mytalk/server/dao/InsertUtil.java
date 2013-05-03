package org.softwaresynthesis.mytalk.server.dao;

import org.hibernate.Session;
import org.softwaresynthesis.mytalk.server.IMyTalkObject;

/**
 * Oggetto per l'inserimento di un record
 * nella base di dati del sistema MyTalk
 * 
 * @author 	Andrea Meneghinello
 * @version	3.0
 */
final class InsertUtil extends ModifyUtil 
{
	/**
	 * Crea un istanza per l'inserimento di un record
	 * nella base di dati
	 * 
	 * @param 	manager	{@link ISessionManager}
	 */
	public InsertUtil(ISessionManager manager)
	{
		super(manager);
	}

	@Override
	protected void doAction(Session session, IMyTalkObject object)
	{
		session.save(object);
	}
}