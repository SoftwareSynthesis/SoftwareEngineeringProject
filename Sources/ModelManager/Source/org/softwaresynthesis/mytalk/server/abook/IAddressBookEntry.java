package org.softwaresynthesis.mytalk.server.abook;

import org.softwaresynthesis.mytalk.server.IMyTalkObject;

/**
 * Rappresenta un contatto della rubrica di un
 * {@link IUserData}
 * 
 * @author 	Andrea Meneghinello
 * @version %I%, %G%
 */
public interface IAddressBookEntry extends IMyTalkObject
{
	/**
	 * Restituisce l'identificatore univoco
	 * del contatto della rubrica
	 * 
	 * @return	{@link Long} che identifica
	 * 			il contatto
	 */
	public Long getId();
	
	/**
	 * Restituisce il contatto della rubrica
	 * 
	 * @return	{@link IUserData} che rappresenta
	 * 			il contatto della rubrica
	 */
	public IUserData getContact();
	
	/**
	 * Imposta il contatto della rubrica
	 * 
	 * param	contact	{@link IUserData} contatto da
	 * 					settare
	 */
	public void setContact(IUserData contact);
	
	/**
	 * Restituisce il gruppo a cui appertiene l'utente
	 * 
	 * @return	oggetto {@link IGroup} a cui appartiene
	 * 			il contatto altrimenti null
	 */
	public IGroup getGroup();
	
	/**
	 * Imposta il gruppo a cui appartiene l'utente
	 * 
	 * @param 	group	{@link IGroup} a cui deve
	 * 					appartenere l'utente
	 */
	public void setGroup(IGroup group);
	
	/**
	 * Restituisce il proprietario del contatto
	 * 
	 * @return	{@link IUserData} proprietario
	 * 			del contatto
	 */
	public IUserData getOwner();
	
	/**
	 * Imposta il proprietario del contatto
	 * 
	 * @param	owner	{@link IUserData} proprietario
	 * 					del contatto
	 */
	public void setOwner(IUserData owner);
	
	/**
	 * Restituisce se il contatto è bloccato
	 * e non quindi contattarci
	 * 
	 * @return	true se il contatto è bloccato,
	 * 			false altrimenti
	 */
	public boolean getBlocked();
	
	/**
	 * Imposta un contatto come bloccato o meno
	 * 
	 * @param 	blocked	true se l'utente deve essere bloccato,
	 * 					false altrimenti
	 */
	public void setBlocked(boolean blocked);
}