package org.softwaresynthesis.mytalk.server.authentication;

import java.io.Serializable;
import java.security.Principal;

public class PrincipalImpl implements Principal, Serializable 
{
	private static final long serialVersionUID = 19981917L;
	
	private String element;
	
	/**
	 * Crea una istanza con l'elemento identificativo
	 * stabilito durante la procedura di login. Tale
	 * elemento permette di identificare univocamente
	 * il soggetto che ha superato la fase di login
	 * 
	 * @param 	identifierElement	{@link String} caratteristica
	 * 								identificativa
	 */
	public PrincipalImpl(String identifierElement)
	{
		this.element = identifierElement;
	}
	
	@Override
	public String getName() 
	{
		return this.element;
	}
	
	/**
	 * Determina se due istanze rappresentano lo stesso
	 * oggetto PrincipalImpl
	 * 
	 * @param	obj	{@link Object} istanza da verificare
	 * @return	true se le due istanze rappresentano lo
	 * 			stesso oggetto, false altrimenti
	 */
	@Override
	public boolean equals(Object obj)
	{
		boolean result = false;
		PrincipalImpl toCompare = null;
		String toCompareElement = null;
		if (obj instanceof PrincipalImpl)
		{
			toCompare = (PrincipalImpl)obj;
			toCompareElement = toCompare.getName();
			if (this.element.equals(toCompareElement))
			{
				result = true;
			}
		}
		return result;
	}
}