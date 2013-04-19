package org.softwaresynthesis.mytalk.server.call;

import org.softwaresynthesis.mytalk.server.IMyTalkObject;
import org.softwaresynthesis.mytalk.server.abook.IUserData;

public interface ICallList extends IMyTalkObject {
	/**
	 * Imposta l'identificativo della lista chiamata
	 * 
	 * @param id identificativo da impostare
	 */
	public void setId(Long id);
	
	/**
	 * Restituisce l'identificativo univoco
	 * della lista chiamata
	 * 
	 * @return	identificativo del messaggio di
	 * 			tipo {@link Long}
	 */
	public Long getId();
	
	/**
	 * Imposta l'id della chiamata 
	 * 
	 * @param 	idCall	{@link Long}
	 * 					id della chiamata
	 */
	public void setIdCall(Long idCall);
	
		/**
	 * Restituisce l'Id della chiamata
	 * 
	 * @return	{@link Long} rappresentante
	 * 			l'id della chiamata
	 */
	public Long getIdCall();
	
	/**
	 * Imposta l'id dell'utente
	 * coinvolto nella chiamata 
	 * 
	 * @param 	idUser	{@link Long}
	 * 					id dell'utente
	 */
	public void setIdUser(Long idUser);
	
		/**
	 * Restituisce l'Id dell'utente coinvolto
	 * nella chiamata
	 * 
	 * @return	{@link Long} rappresentante
	 * 			l'id dell'utente
	 */
	public Long getIdUser();
	
	/**
	 * Imposta l'attributo caller a true se
	 * l'utente e' il chiamante, false altrimenti
	 * 
	 * @param 	caller	{@link boolean}
	 */
	public void setCaller(boolean caller);
	
		/**
	 * Restituisce true se l'utente in questione
	 * e' il chiamante, false altrimenti
	 * 
	 * @return	{@link boolean}
	 */
	public Long getCaller();
}
