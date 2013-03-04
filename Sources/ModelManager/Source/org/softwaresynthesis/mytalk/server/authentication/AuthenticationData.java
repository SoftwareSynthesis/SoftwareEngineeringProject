package org.softwaresynthesis.mytalk.server.authentication;

/**
 * Oggetto contenente i dati di accesso utilizzati da
 * un utente che vuole accedere al sistema mytalk
 * 
 * @author 	Andrea Meneghinello
 * @version	%I%, %G%
 */
public class AuthenticationData 
{
	private String username;
	private String password;
	
	/**
	 * Crea un istanza di AuthenticationData, con i dati
	 * forniti dall'utente che sarà utilizzata per la
	 * procedura di login
	 * 
	 * @author	Andrea Meneghinello
	 * @param 	username	username dell'utente
	 * @param 	password	password dell'utente
	 */
	public AuthenticationData(String username, String password)
	{
		this.username = username;
		this.password = password;
	}
	
	/**
	 * Restituisce lo username dell'utente che sta effettuando
	 * la procedura di login
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return	stringa rappresentante lo username dell'utente
	 * 			che sta effettuando il login.
	 */
	public String getUsername()
	{
		return this.username;
	}
	
	/**
	 * Restituisce la password dell'utente che sta effettuando
	 * la prodedura di login
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return	stringa rappresentante la password dell'utente
	 * 			che sta effettuando il login
	 */
	public String getPassword()
	{
		return this.password;
	}
	
	/**
	 * Restituisce il codice hash di questa istanza
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return 	codice hash dell'istanza
	 */
	public int hashCode()
	{
		return this.username.hashCode() + this.password.hashCode();
	}
	
	/**
	 * Determina l'uguaglianza di due istanze
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @param	obj		istanza di cui si vuole verificare l'uguaglianza
	 * @return	true se le due istanze sono uguali, false altrimenti
	 */
	public boolean equals(Object obj)
	{
		boolean result = false;
		AuthenticationData credential = null;
		if (obj instanceof AuthenticationData)
		{
			credential = (AuthenticationData)obj;
			result = this.username.equals(credential.username) && this.password.equals(credential.password);
		}
		return result;
	}
	
	/**
	 * Resituisce l'istanza sotto forma di stringa
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return	stringa rappresentante l'istanza di questo oggetto	
	 */
	public String toString()
	{
		return String.format("Credenziali di accesso dell'utente: %s", this.username);
	}
}