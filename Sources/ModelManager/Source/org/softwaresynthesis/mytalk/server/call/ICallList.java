package org.softwaresynthesis.mytalk.server.call;

import org.softwaresynthesis.mytalk.server.IMyTalkObject;
import org.softwaresynthesis.mytalk.server.abook.IUserData;

/**
 * Rappresenta informazioni di dettaglio sui partecipanti
 * ad una chiamata effettuata con il sistema MyTalk
 * 
 * @author 	Andrea Meneghinello
 * @version	3.0
 */
public interface ICallList extends IMyTalkObject 
{
	/**
	 * Restituisce l'utente coinvolto nella chiamta
	 * 
	 * @return	{@link IUserData} utente coinvolto
	 * 			nella chiamata
	 */
	public IUserData getUser();
	
	/**
	 * Imposto l'utente coinvolto nella chiamata
	 * 
	 * @param 	user	{@link IUserData} utente
	 * 					coinvolto nella chiamata
	 */
	public void setUser(IUserData user);
	
	/**
	 * Restituisce la chiamata a cui ha partecipato
	 * l'utente
	 * 
	 * @return {@link ICall} chiamata a cui ha partecipato
	 */
	public ICall getCall();
	
	/**
	 * Imposto la chiamata in cui ha partecipato l'utente
	 * 
	 * @param 	call	{@link ICall} chiamata a cui ha partecipato
	 */
	public void setCall(ICall call);
	
	/**
	 * Restituisce true se l'utente ha avviato la chiamata,
	 * false altrimenti
	 * 
	 * @return	{@link boolean} valore che determina se l'utente
	 * 			è il chiamante o meno
	 */
	public boolean getCaller();
	
	/**
	 * Imposta true se l'utente è il chiamante, false altrimenti
	 * 
	 * @param 	caller	{@link boolean} valore che determina se l'utente
	 * 					è il chiamante o meno
	 */
	public void setCaller(boolean caller);
}