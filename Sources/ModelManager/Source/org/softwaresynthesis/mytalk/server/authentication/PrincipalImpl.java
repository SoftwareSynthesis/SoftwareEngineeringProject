package org.softwaresynthesis.mytalk.server.authentication;

import java.io.Serializable;
import java.security.Principal;

/**
 * Oggetto che rappresenta una caratteristica identificativa
 * dell'utente che ha superato la fase di login
 * 
 * @author 	Andrea Meneghinello
 * @version	%I%, %G%
 */
public class PrincipalImpl implements Principal, Serializable 
{
	private static final long serialVersionUID = 19981917L;
	private String element;
	
	/**
	 * Crea un oggetto contentente una caratteristica
	 * identificativa dell'utente che ha superato la
	 * procedura di autenticazione
	 * 
	 * @param 	element	{@link String} con l'elemento
	 * 					identificativo
	 */
	public PrincipalImpl(String element)
	{
		this.element = element;
	}
	
	/**
	 * Restituisce la caratteristica identificativa
	 * dell'utente
	 * 
	 * @return	{@link String} con la caratteristica
	 * 			identificativa
	 */
	@Override
	public String getName()
	{
		return this.element;
	}
	
	/**
	 * Compara due istanze di PrincipalImpl
	 * 
	 * @param	toCompare	{@link Object} da confrontare
	 * @return	true se le due istanze sono uguali, false
	 * 			altrimenti
	 */
	public boolean equals(Object toCompare)
	{
		PrincipalImpl principal = null;
		boolean result = false;
		if (toCompare instanceof PrincipalImpl)
		{
			principal = (PrincipalImpl)toCompare;
			result = this.element.equals(principal.element);
		}
		return result;
	}
	
	/**
	 * Restituisce l'istanza di PrincipalImpl sotto forma
	 * di {@link String}
	 * 
	 * @return	{@link String} rappresentante l'istanza
	 */
	public String toString()
	{
		String result = String.format("PrincipalImpl[element: %s]", this.element);
		return result;
	}
}