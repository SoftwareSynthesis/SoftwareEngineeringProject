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
	 * Imposta la chiamata a cui appartiene 
	 * 
	 * @param 	call	{@link ICall}
	 * 					chiamata a cui si riferisce
	 */
	public void setIdCall(ICall call);
	
		/**
	 * Restituisce la chiamata
	 * 
	 * @return	{@link ICall} rappresentante
	 * 			la chiamata
	 */
	public ICall getIdCall();
	
	/**
	 * Imposta l'utente
	 * coinvolto nella chiamata 
	 * 
	 * @param 	idUser	{@link Long}
	 * 					utente che partecipa
	 */
	public void setIdUser(IUserData idUser);
	
		/**
	 * Restituisce l'utente coinvolto
	 * nella chiamata
	 * 
	 * @return	{@link IUserData} rappresentante
	 * 			l'utente
	 */
	public IUserData getIdUser();
	
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
