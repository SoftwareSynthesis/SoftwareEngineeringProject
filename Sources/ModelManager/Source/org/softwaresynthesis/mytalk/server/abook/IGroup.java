package org.softwaresynthesis.mytalk.server.abook;

/**
 * Interfaccia rappresentante un gruppo di
 * una rubrica utente del sistema mytalk
 * 
 * @author 	Andrea Meneghinello
 */
public interface IGroup 
{
	/**
	 * Restituisce l'idenitificativo univoco di uno
	 * gruppo di una rubrica utente
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return	long rappresentante l'identificativo
	 * 			univoco di un gruppo di una rubrica
	 * 			utente
	 */
	public Long getId();
	
	/**
	 * Restituisce il nome di un gruppo di una rubrica
	 * utente
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return	stringa rappresentante il nome di un gruppo
	 * 			di una rubrica utente
	 */
	public String getName();
	
	/**
	 * Imposta il nome di un gruppo di una rubrica utente
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @param 	name	stringa rappresentante il nome di
	 * 					un gruppo di una rubrica utente
	 */
	public void setName(String name);
}