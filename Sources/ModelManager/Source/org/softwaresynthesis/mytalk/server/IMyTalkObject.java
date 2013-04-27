package org.softwaresynthesis.mytalk.server;

/**
 * Rappresenta un generico oggetto dell'applicativo
 * MyTalk
 * 
 * @author 	Andrea Meneghinello
 * @version	3.0
 */
public interface IMyTalkObject 
{
	/**
	 * Restituisce l'identificativo dell'oggetto
	 * 
	 * @return	{@link Long} rappresentante univocamente
	 * 			l'oggetto
	 */
	public Long getId();
	
	/**
	 * Compara due oggetti per determinare l'uguaglianza
	 * 
	 * @param 	object	{@link Object} che dovrà essere
	 * 					comparato con l'istanza
	 * @return	true se le due istanze rappresentano lo
	 * 			stesso oggetto, false altriementi
	 */
	@Override
	public boolean equals(Object object);
}