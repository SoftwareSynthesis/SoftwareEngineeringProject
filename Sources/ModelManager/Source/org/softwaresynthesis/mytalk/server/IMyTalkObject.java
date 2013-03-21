package org.softwaresynthesis.mytalk.server;

/**
 * Definisce che l'oggetto che la implementa ha la
 * possibilità di essere convertito in formato
 * json in modo da poter essere trasferito al
 * client
 * 
 * @author 	Andrea Meneghinello
 * @version 1.0
 */
public interface IMyTalkObject 
{
	/**
	 * Converte l'oggetto di invocazione in una
	 * stringa json interpretabile dai client
	 * 
	 * @return	rappresentazione in {@link String}
	 * 			dell'oggetto di invocazione 
	 */
	public String toJson();
}