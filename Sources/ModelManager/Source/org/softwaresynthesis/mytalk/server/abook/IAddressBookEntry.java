package org.softwaresynthesis.mytalk.server.abook;

/**
 * Interfaccia rappresentante una entry di una rubrica
 * utente del sistema mytalk
 * 
 * @author 	Andrea Meneghinello
 * @version	%I%, %G%
 */
public interface IAddressBookEntry 
{
	/**
	 * Resituisce l'identificativo univoco di una
	 * entry di una rubrica utente del sistema mytalk
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return	long rappresentante l'identificativo
	 * 			univoco di una entry di una ribrica
	 * 			utente del sistema mytalk
	 */
	public Long getId();
	
	/**
	 * Restituisce lo {@link IUserData} contatto
	 * della rubrica di uno {@link IUserData}
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return	{@link IUserData} reppresentante il contatto
	 * 			della rubrica
	 */
	public IUserData getEntry();
	
	/**
	 * Imposta lo {@link IUserData} come contatto della
	 * rubrica di uno {@link IUserData}
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @param 	contact	{@link IUserData} rappresentante il
	 * 					contatto da aggiungere alla entry della
	 * 					rubrica
	 */
	public void setEntry(IUserData contact);
	
	/**
	 * Restituisce il gruppo a cui appartiene lo {@link IUserData}
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return	{@link IGroup} a cui appartiene lo {@link IUserData}
	 * 			oppure null se lo {@IUserData} non appartiene ad
	 * 			alcun gruppo 
	 */
	public IGroup getGroup();
	
	/**
	 * Imposta il gruppo di appartenenza dello {@link IUserData}
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @param 	group	{@link IGroup} di appartenenza dello
	 * 					{@link IUserData}
	 */
	public void setGroup(IGroup group);
	
	/**
	 * Restituisce lo {@link IUserData} possesore di questa entry
	 * della rubrica
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return	{@link IUserData} possessore della entry della rubrica
	 */
	public IUserData getOwner();
	
	/**
	 * Imposta lo {@link IUserData} possessore della entry della rubrica
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @param 	owner	{@link IUserData} possessore delle entry
	 */
	public void setOwner(IUserData owner);
	
	/**
	 * Restituisce lo stato della entry della rubrica
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return	true se lo {@link IUserData} è sbloccato, false altrimenti
	 */
	public boolean getStatus();
	
	/**
	 * Imposta lo stato della entry della rubrica
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @param 	status	true se lo {@link IUserData} è sbloccato, false altrimenti
	 */
	public void setStatus(boolean status);
}