package org.softwaresynthesis.mytalk.server.authentication;

import java.io.Serializable;
import java.security.Principal;

/**
 * Oggetto che permette di identificate univocamente
 * uno {@link IUserData} memorizzato nel database del
 * sistema mytalk
 * 
 * @author 	Andrea Meneghinello
 * @version	%I%, %G%
 */
public class PrincipalImpl implements Principal, Serializable
{
	private static final long serialVersionUID = 19981017L;
	private String mail;
	
	/**
	 * Crea un oggetto PrincipalImpl che permetterà
	 * di determinare univocamente lo {@link IUserData}
	 * che ha effettuato il login
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @param 	mail	indirizzo e-mail dello {@link IUserData}
	 * 					che ha effettuato la procedura di login
	 */
	public PrincipalImpl(String mail)
	{
		this.mail = mail;
	}
	
	/**
	 * Restituisce l'elemento identificativo dello {@link IUserData}
	 * che ha effettuato la procedura di login
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return	stringa rappresentante l'elemento identificativo
	 */
	@Override
	public String getName()
	{
		return this.mail;
	}
	
	/**
	 * Verifica l'uguaglianza di due oggetti PrincipalImpl
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @param	obj	Oggetto di cui si vuole verificare
	 * 				l'uguaglianza
	 * @return	true se obj e l'oggetto di invocazione sono
	 * 			uguali, altrimenti false
	 */
	public boolean equals(Object obj)
	{
		boolean result = false;
		PrincipalImpl principal = null;
		if (obj instanceof PrincipalImpl)
		{
			principal = (PrincipalImpl)obj;
			result = this.mail.equals(principal.mail);
		}
		return result;
	}
	
	/**
	 * Restituisc eil codice hash dell'oggetto di invocazione
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return	codice hash dell'oggetto di invocazione
	 */
	public int hashCode()
	{
		return this.mail.hashCode();
	}
	
	/**
	 * Restituisce l'istanza dell'oggetto sotto forma di
	 * stringa
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return	stringa rappresentante l'istanza dell'oggetto
	 */
	public String toString()
	{
		return String.format("Elemento identificativo: %s", this.mail);
	}
}