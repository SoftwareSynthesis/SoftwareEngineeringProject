package org.softwaresynthesis.mytalk.server.dao;

import java.util.List;
import org.softwaresynthesis.mytalk.server.IMyTalkObject;
import org.softwaresynthesis.mytalk.server.abook.IAddressBookEntry;
import org.softwaresynthesis.mytalk.server.abook.IGroup;
import org.softwaresynthesis.mytalk.server.abook.IUserData;

/**
 * Punto di accesso alla basi di dati del sistema
 * MyTalk. Tale classe permette di eseguire query
 * sul database
 * 
 * @author 	Andrea Meneghinello
 * @version	3.0
 */
public class DataPersistanceManager 
{
	private ISessionManager manager;
	private GetUtil get;
	private ModifyUtil modify;
	
	/**
	 * Crea una nuova istanza dell'oggetto
	 */
	public DataPersistanceManager()
	{
		this(SessionManager.getInstance());
	}
	
	/**
	 * Crea una nuova istanza dell'oggetto
	 * 
	 * @param 	manager	{@link ISessionManager} gestore delle
	 * 					sessioni di comunicazione con la base
	 * 					di dati del sistema MyTalk
	 */
	public DataPersistanceManager(ISessionManager manager)
	{
		this.manager = manager;
	}
	
	/**
	 * Cancella un oggetto dalla base di dati del sistema
	 * MyTalk
	 * 
	 * @param 	object	{@link IMyTalkObject} oggetto da rimuovere
	 * @return	true se l'operazione è andata a buon fine, false
	 * 			altrimenti
	 */
	public boolean delete(IMyTalkObject object)
	{
		boolean result = false;
		this.modify = new DeleteUtil(this.manager);
		result = this.modify.execute(object);
		this.modify = null;
		return result;
	}
	
	/**
	 * Inserisce un oggetto dalla base di dati del sistema
	 * MyTalk
	 * 
	 * @param 	object	{@link IMyTalkObject} oggetto da inserire
	 * @return	true se l'operazione è andata a buon fine, false
	 * 			altrimenti
	 */
	public boolean insert(IMyTalkObject object)
	{
		boolean result = false;
		this.modify = new InsertUtil(this.manager);
		result = this.modify.execute(object);
		this.modify = null;
		return result;
	}
	
	/**
	 * Aggiorna un oggetto dalla base di dati del sistema
	 * MyTalk
	 * 
	 * @param 	object	{@link IMyTalkObject} oggetto da aggiornare
	 * @return	true se l'operazione è andata a buon fine, false
	 * 			altrimenti
	 */
	public boolean update(IMyTalkObject object)
	{
		boolean result = false;
		this.modify = new InsertUtil(this.manager);
		result = this.modify.execute(object);
		this.modify = null;
		return result;
	}
	
	/**
	 * Restituisce un contatto della rubrica
	 * 
	 * @param 	contact	{@link IUserData} contatto da ricercare
	 * @param 	owner	{@link IUserData} proprietario del contatto
	 * @param 	group	{@link IGroup} gruppo a cui appartiene
	 * @return	{@link IAddressBookEntry} restituisce il contatto ricercato
	 */
	public IAddressBookEntry getAddressBookEntry(IUserData contact, IUserData owner, IGroup group)
	{
		IAddressBookEntry result = null;
		List<IMyTalkObject> collection = null;
		String query = "from AddressBookEntry as a where a.owner.mail = " + owner.getMail() + " and a.contact.mail = " + contact.getMail() + "and a.group.id = " + group.getId();
		this.get = new NotInitialize(this.manager);
		collection = this.get.execute(query);
		if (collection != null && collection.get(0) != null)
		{
			result = (IAddressBookEntry)collection.get(0);
		}
		return result;
	}
}