package org.softwaresynthesis.mytalk.server.authentication;

/**
 * Contiene le credenziali fornite in input
 * dall'utente per avviare la procedura di
 * login
 * 
 * @author 	Andrea Meneghinello
 * @version	%I%, %G%
 */
public interface IAuthenticationData 
{
	/**
	 * Restituisce lo username fornito in input
	 * dall'utente durante la procedura di login
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return	{@link String} rappresentate lo
	 * 			username inserito dall'utente
	 */
	public String getUsername();
	
	/**
	 * Resituisce la password fornita in input
	 * dall'utente durante la procedura di login
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return	{@link String} rappresentante la
	 * 			password, in chiaro, inserita
	 * 			dall'utente
	 */
	public String getPassword();
}