package org.softwaresynthesis.mytalk.server.message;

import java.util.Date;
import org.softwaresynthesis.mytalk.server.IMyTalkObject;
import org.softwaresynthesis.mytalk.server.abook.IUserData;

/**
 * Rappresenta un messaggio lasciato nella segreteria
 * di un utente del sistema MyTalk
 * 
 * @author 	Andrea Meneghinello
 * @version	3.0
 */
public interface IMessage extends IMyTalkObject 
{
	/**
	 * Restituisce il mittente del messaggio
	 * 
	 * @return	{@link IUserData} mittente del messaggio
	 */
	public IUserData getSender();
	
	/**
	 * Imposta il mittente del messaggio
	 * 
	 * @param 	sender	{@link IUserData} mittente del
	 * 					messaggio
	 */
	public void setSender(IUserData sender);
	
	/**
	 * Restituisce il destinatario del messaggio
	 * 
	 * @return	{@link IUserData} destinatario del messaggio
	 */
	public IUserData getReceiver();
	
	/**
	 * Imposta il destinatario del messaggio
	 * 
	 * @param 	receiver	{@link IUserData} destinatario del
	 * 						messaggio
	 */
	public void setReceiver(IUserData receiver);
	
	/**
	 * Restituisce true se il messaggio è nuovo, false altrimenti
	 * 
	 * @return	valore {@link boolean} che determina se il messaggio
	 * 			è nuovo o meno
	 */
	public boolean getNewer();
	
	/**
	 * Imposta se un messaggio è già stato ascoltato o meno
	 * 
	 * @param 	newer	{@link boolean} true se il messaggio non è
	 * 					stato ascoltato, false altrimenti
	 */
	public void setNewer(boolean newer);
	
	/**
	 * Restituisce true se il messaggio è audio/video, false altrimenti
	 * 
	 * @return	valore {@link boolean} che determina se si tratta di un
	 * 			messaggio audio/video o meno
	 */
	public boolean getVideo();
	
	/**
	 * Imposta un valore che determina se il messaggio è audio/video
	 * o meno
	 * 
	 * @param 	video	{@link boolean} true se è un messaggio audio/video,
	 * 					false altrimenti
	 */
	public void setVideo(boolean video);
	
	/**
	 * Restituisce la data di quando è stato lasciato il messaggio
	 * 
	 * @return	{@link Date} data in cui è stato registrato il messaggio
	 */
	public Date getDate();
	
	/**
	 * Imposta la data di quanto è stato lasciato il messaggio
	 * 
	 * @param 	date	{@link Date} data di quanto è stato lasciato
	 * 					il messaggio
	 */
	public void setDate(Date date);
}