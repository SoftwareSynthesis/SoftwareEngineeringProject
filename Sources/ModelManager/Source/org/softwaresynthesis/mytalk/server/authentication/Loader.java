package org.softwaresynthesis.mytalk.server.authentication;

import java.io.IOException;
import javax.security.auth.callback.Callback;
import javax.servlet.http.HttpServletRequest;
import org.softwaresynthesis.mytalk.server.authentication.security.ISecurityStrategy;

/**
 * Caricatore generico di credenziali
 * 
 * @author 	Andrea Meneghinello
 * @version	3.0
 */
abstract class Loader implements Callback 
{
	private	Callback callback;
	private ISecurityStrategy strategy;
	
	/**
	 * Crea il caricatore con la giusta istanza di callback
	 * 
	 * @param 	callback	{@link Callback} callback da inizializzare
	 */
	public Loader(Callback callback)
	{
		this.callback = callback;
	}
	
	/**
	 * Inizializza la callback con una parte della
	 * delle credenziali di accesso per entrare nel
	 * sistema MyTalk
	 * 
	 * @param 	request	{@link HttpServletRequest}
	 * 					contiene i vari input
	 */
	public abstract void load(HttpServletRequest request) throws IOException;
	
	/**
	 * Resituisce il dato memorizzato nella
	 * callback
	 * 
	 * @return	{@link String} dato memorizzato
	 */
	public abstract String getData();
	
	/**
	 * Restituisce la callback inizializzata
	 * 
	 * @return	{@link Callback} callback inizializzata
	 */
	protected Callback getCallback()
	{
		return this.callback;
	}
	
	/**
	 * Restituisce la strategia di crittografia
	 * 
	 * @return	{@link ISecurityStrategy} strategia di crittografia
	 * 			adottata
	 */
	protected ISecurityStrategy getSecurityStrategy()
	{
		return this.strategy;
	}
	
	/**
	 * Imposta la strategia di crittografica
	 * 
	 * @param 	strategy	{@link ISecurityStrategy} strategia
	 * 						di crittografia
	 */
	protected void setSecurityStrategy(ISecurityStrategy strategy)
	{
		this.strategy = strategy;
	}
}