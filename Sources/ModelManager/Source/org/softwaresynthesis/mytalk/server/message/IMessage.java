package org.softwaresynthesis.mytalk.server.message;

import java.util.Date;
import org.softwaresynthesis.mytalk.server.abook.IUserData;

/**
 * Interfaccia che rappresenta un messaggio di segreteria
 * del sistema mytalk
 *
 * @author 	Andrea Meneghinello
 * @version %I%, %G%
 */
public interface IMessage 
{
	/**
	 * Restituisce l'identificativo univoco del messaggio
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return	long rappresentante l'identificativo univoco
	 * 			del messaggio
	 */
	public Long getId();
	
	/**
	 * Restituisce lo {@link IUserData} mittente del messaggio
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return	{@link IUserData} mittente del messaggio
	 */
	public IUserData getSender();
	
	/**
	 * Imposta lo {@link IUserData} mittente del messaggio
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @param 	sender	{@link IUserData} mittente del messaggio
	 */
	public void setSender(IUserData sender);
	
	/**
	 * Restituisce lo {@link IUserData} destinatario del messaggio
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return	{@link IUserData} destinatario del messaggio
	 */
	public IUserData getReceiver();
	
	/**
	 * Imposta lo {@link IUserData} destinatario del messaggio
	 * 
	 * @author	Andrea Meneghinello
	 * @version %I%, %G%
	 * @param 	receiver	{@link IUserData} destinatario del
	 * 						messaggio
	 */
	public void setReceiver(IUserData receiver);
	
	/**
	 * Restituisce lo stato per determinare se il messaggio è
	 * già stato ascoltato o meno
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return	true se il messaggio non è stato ascoltato, false
	 * 			altrimenti
	 */
	public boolean isNew();
	
	/**
	 * Imposta se il messaggio deve risultare come già ascoltato
	 * o meno
	 * 
	 * @author 	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @param 	status	true se si vuole impostare il messaggio come
	 * 					non ascoltato, false altrimenti
	 */
	public void setNew(boolean status);
	
	/**
	 * Restituisce un booleano che determina se si tratta di un messaggio
	 * audio oppure audio-video
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return	true se si tratta di un messaggio audio-video, false se 
	 * 			si tratta di un messaggio audio
	 */
	public boolean isVideo();
	
	/**
	 * Imposta se un messaggio è di tipo audio oppure audio-video
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @param 	video	true
	 */
	public void setVideo(boolean video);
	
	/**
	 * Restituisce la data in cui il mittente ha lasciato il messaggio
	 * nella segreteria del destinatario
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return	{@link Date} in cui è stato lasciato il messaggio nella
	 * 			segreteria
	 */
	public Date getDate();
	
	/**
	 * Imposta la data in cui il mittente ha lasciato il messaggio nella
	 * segreteria del destinatario
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @param 	date	{@link Date} in cui è stato lasciato il messaggio
	 * 					in segreteria
	 */
	public void setDate(Date date);
}