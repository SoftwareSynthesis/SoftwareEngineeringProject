package org.softwaresynthesis.mytalk.server.message;

import org.softwaresynthesis.mytalk.server.IMyTalkObject;
import org.softwaresynthesis.mytalk.server.abook.IUserData;

/**
 * Rappresentazione di un messaggio di segreteria
 * del sistema mytalk
 * 
 * @author 	Marco Schivo
 * @version	1.0
 */
public interface IMessage extends IMyTalkObject
{	
	/**
	 * Imposta l'identificativo del messaggio
	 * 
	 * @param id identificativo da impostare
	 */
	public void setId(Long id);
	
	/**
	 * Restituisce l'identificativo univoco
	 * del messaggio di segreteria
	 * 
	 * @return	identificativo del messaggio di
	 * 			tipo {@link Long}
	 */
	public Long getId();
	
	/**
	 * Restituisce l'Id dell'utente che ha
	 * inviato il messaggio di segreteria
	 * 
	 * @return	{@link Long} rappresentante
	 * 			l'id dell'utente mittente
	 */
	public IUserData getSender();
	
	/**
	 * Imposta l'id dell'utente mittente
	 * del messaggio in segreteria 
	 * 
	 * @param 	id	{@link Long}
	 * 					id dell'utente
	 * 					mittente
	 */
	public void setSender(IUserData id);
	
		/**
	 * Restituisce l'Id dell'utente destinatario
	 * del messaggio di segreteria
	 * 
	 * @return	{@link Long} rappresentante
	 * 			l'id dell'utente destinatario
	 */
	public IUserData getReceiver();
	
	/**
	 * Imposta l'id dell'utente destinatario
	 * del messaggio in segreteria 
	 * 
	 * @param 	id	{@link Long}
	 * 					id dell'utente
	 * 					destinatario
	 */
	public void setReceiver(IUserData id);
	
	/**
	 * Restituisce 1 se il messaggio non e'
	 * ancora stato letto, 0 altrimenti
	 * 
	 * @return	{@link Long} che rappresenta se il messaggio e' nuovo o meno
	 */
	public Long getNewer();
	
	/**
	 * Imposta lo stato del messagggio, 
	 * 1 se e' nuovo, 0 altrimenti
	 * 
	 * @param 	newer	{@link Long} con lo stato
	 * 						del messaggio (1 se nuovo, 0 altrimenti)
	 */
	public void setNewer(Long newer);
	
	/**
	 * Restituisce 1 se il messaggio e' di tipo
	 * video, 0 se solo audio
	 * 
	 * @return	{@link Long} che rappresenta il
	 * 			tipo di messaggio
	 */
	public Long getVideo();
	
	/**
	 * Imposta il tipo del messaggio in segreteria
	 * 
	 * @param 	video	{@link Long} che identifica se il
	 * 					messaggio e' di tipo video (1)
	 * 					o solo audio (0)
	 */
	public void setVideo(Long video);
	
	/**
	 * Restituisce la data del messaggio in segreteria
	 * 
	 * @return	{@link Date} con la data del messaggio di segreteria
	 */
	public String getDate();
	
	/**
	 * Imposta la data del messaggio
	 * 
	 * @param 	date {@link Date} con la data del messaggio di segreteria
	 */
	public void setDate(String date);
}