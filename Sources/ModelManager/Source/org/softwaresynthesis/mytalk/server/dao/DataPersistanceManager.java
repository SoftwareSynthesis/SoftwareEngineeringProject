package org.softwaresynthesis.mytalk.server.dao;

import java.util.List;
import org.softwaresynthesis.mytalk.server.IMyTalkObject;
import org.softwaresynthesis.mytalk.server.abook.IAddressBookEntry;
import org.softwaresynthesis.mytalk.server.abook.IGroup;
import org.softwaresynthesis.mytalk.server.abook.IUserData;
import org.softwaresynthesis.mytalk.server.dao.util.GetUtil;
import org.softwaresynthesis.mytalk.server.dao.util.ModifyUtil;
import org.softwaresynthesis.mytalk.server.dao.util.UtilFactory;
import org.softwaresynthesis.mytalk.server.call.ICall;
import org.softwaresynthesis.mytalk.server.message.IMessage;

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
	private UtilFactory factory;
	
	/**
	 * Crea una nuova istanza dell'oggetto
	 */
	public DataPersistanceManager()
	{
		this(SessionManager.getInstance(), new UtilFactory());
	}
	
	/**
	 * Crea una nuova istanza dell'oggetto
	 * 
	 * @param 	manager	{@link ISessionManager} gestore delle
	 * 					sessioni di comunicazione con la base
	 * 					di dati del sistema MyTalk
	 */
	protected DataPersistanceManager(ISessionManager manager, UtilFactory factory)
	{
		this.manager = manager;
		this.factory = factory;
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
		ModifyUtil delete = this.factory.getDeleteUtil(this.manager);
		result = delete.execute(object);
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
		ModifyUtil insert = this.factory.getInsertUtil(this.manager);
		result = insert.execute(object);
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
		ModifyUtil update = this.factory.getUpdateUtil(this.manager);
		result = update.execute(object);
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
//		IAddressBookEntry result = null;
//		List<IMyTalkObject> collection = null;
//		String query = "from AddressBookEntry as a where a.owner.mail = " + owner.getMail() + " and a.contact.mail = " + contact.getMail() + "and a.group.id = " + group.getId();
//		this.get = new NotInitialize(this.manager);
//		collection = this.get.execute(query);
//		if (collection != null && collection.get(0) != null)
//		{
//			result = (IAddressBookEntry)collection.get(0);
//		}
//		return result;
		//TODO
		return null;
	}
	
	public List<ICall> getCallHistory(IUserData user)
	{
		//TODO
		return null;
	}
	
	public IGroup getGroup(Long id)
	{
		//TODO
		return null;
	}
	
	public List<IGroup> getGroup(IUserData owner)
	{
		//TODO
		return null;
	}
	
	public IGroup getGroup(IUserData owner, String name)
	{
		//TODO
		return null;
	}
	
	public Long getMessageNewKey()
	{
		//TODO
		return null;
	}
	
	public IMessage getMessage(Long id)
	{
		//TODO
		return null;
	}
	
	public List<IMessage> getMessages(IUserData receiver)
	{
		//TODO
		return null;
	}
	
	public IUserData getUserData(String mail)
	{
		//TODO
		return null;
	}
	
	public IUserData getUserData(Long id)
	{
		GetUtil select = this.factory.getUserDataUtil(this.manager);
		IUserData result = null;
		List<IMyTalkObject> collection = null;
		String query = "from UserData as u where u.id = " + id;
		collection = select.execute(query);
		if (collection != null && collection.get(0) != null)
		{
			result = (IUserData)collection.get(0);
		}
		return result;
	}
	
	public List<IUserData> getUserDatas(String mail, String name, String surname)
	{
		//TODO
		return null;
	}
}