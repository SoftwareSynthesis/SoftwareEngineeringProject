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
}