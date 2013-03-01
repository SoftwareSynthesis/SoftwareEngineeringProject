package org.softwaresynthesis.mytalk.server.message;

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
}