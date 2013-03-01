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
}