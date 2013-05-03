package org.softwaresynthesis.mytalk.server.dao;

import java.util.Iterator;
import java.util.List;
import org.hibernate.Hibernate;
import org.softwaresynthesis.mytalk.server.IMyTalkObject;
import org.softwaresynthesis.mytalk.server.abook.IGroup;

/**
 * Permette di inizializzare le collezioni di dati
 * nei gruppi
 * 
 * @author 	Andrea Meneghinello
 * @version 3.0
 */
final class GetGroupUtil extends GetUtil 
{
	/**
	 * Crea una nuova istanza per l'inizializzazione
	 * delle lista degli utenti appartenenti al gruppo
	 * 
	 * @param manager 	{@link ISessionManager} gestore della
	 * 					sessione di comunicazione con il database
	 */
	public GetGroupUtil(ISessionManager manager)
	{
		super(manager);
	}

	@Override
	protected void doInitialize(List<IMyTalkObject> collection) 
	{
		IGroup group = null;
		Iterator<IMyTalkObject> iterator = collection.iterator();
		while(iterator.hasNext())
		{
			group = (IGroup)iterator.next();
			Hibernate.initialize(group.getAddressBook());
		}
	}
}