package org.softwaresynthesis.mytalk.server.authentication;

/**
 * Oggetto utilizzato in fase di autenticazione
 * per validare un utente
 * 
 * @author 	Andrea Meneghinello
 * @version 1.0
 */
public class AuthenticationData implements IAuthenticationData
{
	private String username;
	private String password;
	
	/**
	 * Crea l'oggetto per la fase di login
	 * 
	 * @param 	username	{@link String} username dell'utente
	 * @param 	password	{@link String} password dell'utente
	 */
	public AuthenticationData(String username, String password)
	{
		this.username = username;
		this.password = password;
	}
	
	/**
	 * Restituisce lo username dell'utente
	 * 
	 * @return	{@link String} con lo username inserito in fase
	 * 			di autenticazione
	 */
	public String getUsername()
	{
		return this.username;
	}
	
	/**
	 * Restituisce la password dell'utente
	 * 
	 * @return	{@link String} con la password inserita in fase
	 * 			di autenticazione
	 */
	public String getPassword()
	{
		return this.password;
	}
	
	/**
	 * Determina se due istanze di AuthenticationData sono uguali
	 * 
	 * @param	toCompare	{@link Object} oggetto da confrontare
	 * @return	true se le due istanze sono uguali, false altrimenti
	 */
	@Override
	public boolean equals(Object toCompare)
	{
		AuthenticationData credential = null;
		boolean result = false;
		if (toCompare instanceof AuthenticationData)
		{
			credential = (AuthenticationData)toCompare;
			result =  this.username.equals(credential.username);
		}
		return result;
	}
	
	/**
	 * Restituisce l'oggetto sotto forma di {@link String}
	 * 
	 * @return	{@link String} rappresentante l'istanza dell'oggetto
	 */
	@Override
	public String toString()
	{
		String instance = String.format("AuthenticationData[username: %s]", this.username);
		return instance;
	}
}