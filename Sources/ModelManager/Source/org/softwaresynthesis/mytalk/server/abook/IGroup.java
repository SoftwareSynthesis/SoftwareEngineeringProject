package org.softwaresynthesis.mytalk.server.abook;

import java.util.Set;
import org.softwaresynthesis.mytalk.server.IMyTalkObject;

/**
 * Rappresenta un gruppo di una
 * rubrica utente
 * 
 * @author 	Andrea MEneghinello
 * @version %I%, %G%
 */
public interface IGroup extends IMyTalkObject
{
	/**
	 * Restituisce l'identificatore univoco
	 * del gruppo
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return	{@link Long} che identifica
	 * 			univocamente il gruppo
	 */
	public Long getId();
	
	/**
	 * Restituisce il nome del gruppo
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return	{@link String} con il
	 * 			nome del gruppo
	 */
	public String getName();
	
	/**
	 * Imposta il nome del gruppo
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @param 	name	{@link String} con
	 * 					il nome del gruppo
	 */
	public void setName(String name);
	
	/**
	 * Restituisce i contatti appartenenti
	 * al gruppo
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return	{@link Set} con i contatti
	 * 			del gruppo
	 */
	public Set<AddressBookEntry> getAddressBook();
	
	/**
	 * Aggiunge un contatto al gruppo
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @param 	entry	{@link AddressBookEntry} da
	 * 					aggiungere al gruppo
	 */
	public void addAddressBook(AddressBookEntry entry);
}