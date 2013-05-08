package org.softwaresynthesis.mytalk.server.abook;

import org.softwaresynthesis.mytalk.server.IMyTalkObject;

/**
 * Rappresenta un contatto della rubrica di un utente
 * del sistema MyTalk
 * 
 * @author 	Andrea Meneghinello
 * @version	3.0
 */
public interface IAddressBookEntry extends IMyTalkObject 
{
	/**
	 * Restituisce il contatto della rubrica
	 * 
	 * @return	{@link IUserData} contatto della rubrica
	 * 			di un utente
	 */
	public IUserData getContact();
	
	/**
	 * Imposta il contatto della rubrica di un utente
	 * 
	 * @param 	contact	{@link IUserData} contatto da
	 * 					aggiungere
	 */
	public void setContact(IUserData contact);
	
	/**
	 * Restituisce il gruppo a cui appartiene il contatto
	 * 
	 * @return {@link IGroup} a cui appartiene il contatto
	 */
	public IGroup getGroup();
	
	/**
	 * Imposta il gruppo a cui appartiene l'utente
	 * 
	 * @param 	group	{@link IGroup} gruppo a cui appartiene
	 * 					il contatto
	 */
	public void setGroup(IGroup group);
	
	/**
	 * Restituisce il proprietario del contatto
	 * 
	 * @return {@link IUserData} proprietario del contatto
	 */
	public IUserData getOwner();
	
	/**
	 * Imposta il proprietario del contatto
	 * 
	 * @param 	owner	{@IUserData} proprietario del contatto
	 */
	public void setOwner(IUserData owner);
	
	/**
	 * Restituisce true se il contatto è bloccato, false
	 * altrimenti
	 * 
	 * @return	{@link boolean} che determina se il contatto è
	 * 			bloccato
	 */
	public boolean getBlocked();
	
	/**
	 * Impostare a true se si desidera bloccare l'utente, false
	 * altrimenti
	 * 
	 * @param 	blocked	{@link boolean} che determina se il contatto
	 * 					è bloccato
	 */
	public void setBlocked(boolean blocked);
}