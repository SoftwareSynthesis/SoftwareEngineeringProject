package org.softwaresynthesis.mytalk.server.dao;

import java.util.List;
import org.softwaresynthesis.mytalk.server.IMyTalkObject;
import org.softwaresynthesis.mytalk.server.abook.IGroup;
import org.softwaresynthesis.mytalk.server.abook.IUserData;
import org.softwaresynthesis.mytalk.server.dao.util.GetUtil;
import org.softwaresynthesis.mytalk.server.dao.util.ModifyUtil;
import org.softwaresynthesis.mytalk.server.dao.util.UtilFactory;
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
	 * Restituisce un gruppo della rubrica
	 * 
	 * @param 	id	{@link Long} id del gruppo da ricercare
	 * @return	{@link IGroup} restituisce il gruppo ricercato
	 */

	public IGroup getGroup(Long id)
	{
		GetUtil select = this.factory.getGroupUtil(this.manager);
		IGroup result = null;
		List<IMyTalkObject> collection = null;
		String query = "from Groups as g where g.id = " + "'" + id + "'";
		collection = select.execute(query);
		if (collection != null && collection.isEmpty() == false)
		{
			result = (IGroup)collection.get(0);
		}
		return result;
	}
	
	/**
	 * Restituisce i gruppi della rubrica
	 * 
	 * @param 	owner	{@link IUserData} proprietario del gruppo da ricercare
	 * @return	{@link List<IGroup>} restituisce la lista dei gruppi ricercata
	 */
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<IGroup> getGroup(IUserData owner)
	{
		GetUtil select = this.factory.getGroupUtil(this.manager);
		List<IGroup> result = null;
		List<IMyTalkObject> collection = null;
		String query = "from Groups as g where g.owner = " + "'" + owner + "'";
		collection = select.execute(query);
		if (collection != null && collection.isEmpty() == false)
		{
			result = (List)collection;
		}
		return result;
	}
	
	/**
	 * Restituisce l'id del prossimo messaggio di segreteria
	 *
	 * @return	{@link Long} restituisce l'id del prossimo messaggio in segreteria
	 */
	
	public Long getMessageNewKey()
	{
		GetUtil select = this.factory.getGenericUtil(this.manager);
		Long id = null;
		Long result = null;
		String query = "max(id) from Messages";
		id = select.uniqueResult(query);
		if (id != null)
		{
			result = (Long)id + 1;
		}
		return result;
	}
	
	/**
	 * Restituisce un messaggio di segreteria
	 * 
	 * @param 	id	{@link Long} id del messaggio da ricercare
	 * @return	{@link IMessage} restituisce il messaggio di segreteria ricercato
	 */
	
	public IMessage getMessage(Long id)
	{
		GetUtil select = this.factory.getGenericUtil(this.manager);
		IMessage result = null;
		List<IMyTalkObject> collection = null;
		String query = "from Messages as m where m.id = " + "'" + id + "'";
		collection = select.execute(query);
		if (collection != null && collection.isEmpty() == false)
		{
			result = (IMessage)collection.get(0);
		}
		return result;
	}
	
	/**
	 * Restituisce una lista di messaggi di segreteria
	 * 
	 * @param 	receiver {@link IUserData} destinatario del messaggio da ricercare
	 * @return	{@link List<IMessage>} lista di messaggi ricercati
	 */
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<IMessage> getMessages(IUserData receiver)
	{
		GetUtil select = this.factory.getGenericUtil(this.manager);
		List<IMessage> result = null;
		List<IMyTalkObject> collection = null;
		String query = "from Messages as m where m.receiver = " + "'" + receiver + "'";
		collection = select.execute(query);
		if (collection != null && collection.isEmpty() == false)
		{
			result = (List)collection;
		}
		return result;
	}
	
	/**
	 * Cerca un utente con una determinata mail
	 * 
	 * @param 	mail	{@link String} mail associata all'utente
	 * 					che si desidera cercare nella base di dati 
	 * @return	{@link IUserData} se esiste un utente che si e' registrato
	 * 			con la mail fornita in input, altrimenti null
	 */
	public IUserData getUserData(String mail)
	{
		GetUtil select = this.factory.getUserDataUtil(this.manager);
		IUserData result = null;
		List<IMyTalkObject> collection = null;
		String query = "from UserData as u where u.mail = " + "'" + mail + "'";
		collection = select.execute(query);
		if (collection != null && collection.isEmpty() == false)
		{
			result = (IUserData)collection.get(0);
		}
		return result;
	}
	
	/**
	 * Cerca un utente con un determinato ID
	 * 
	 * @param 	id	{@link Long} id associato all'utente
	 * 				che si desidera cercare nella base di dati
	 * @return	{@link IUserData} se esiste un utente con tale ID,
	 * 			altrimenti null
	 */
	public IUserData getUserData(Long id)
	{
		GetUtil select = this.factory.getUserDataUtil(this.manager);
		IUserData result = null;
		List<IMyTalkObject> collection = null;
		String query = "from UserData as u where u.id = " + id;
		collection = select.execute(query);
		if (collection != null && collection.isEmpty() == false)
		{
			result = (IUserData)collection.get(0);
		}
		return result;
	}
	
	/**
	 * Cerca un utente nella base di dati
	 * 
	 * @param 	mail		{@link String} indirizzo mail con cui un utente potrebbe essersi registrato
	 * @param 	name		{@link String} nome con cui l'utente potrebbe essersi registrato
	 * @param 	surname		{@link String} cognome con cui l'utente potrebbe essersi registrato;
	 * @return	{@link List<IUserData>} con i risultati della ricerca
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<IUserData> getUserDatas(String mail, String name, String surname)
	{
		GetUtil select = this.factory.getUserDataUtil(this.manager);
		List<IUserData> result = null;
		String query = "from UserData as u where u.mail like '" + mail + "' or u.name like '" + name + "' or u.surname like '" + surname + "'";
		result = (List)select.execute(query);
		return result;
	}
}