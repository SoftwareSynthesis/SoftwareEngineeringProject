package org.softwaresynthesis.mytalk.server.authentication;

import java.io.IOException;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.servlet.http.HttpServletRequest;
import org.softwaresynthesis.mytalk.server.authentication.security.ISecurityStrategy;

/**
 * La classe ha il compito di predisporre le credenziali
 * di accesso, fornite dall'utente, per la successiva fase
 * di autenticazione.
 * 
 * @author 	Andrea Meneghinello
 * @version	3.0
 */
public final class CredentialLoader implements CallbackHandler
{
	private HttpServletRequest input;
	private ISecurityStrategy strategy;
	
	/**
	 * Crea una nuova istanza del caricatore di credenziali
	 * 
	 * @param 	inputData	{@link HttpServletRequest} dati in input
	 * @param 	strategy	{@link ISecurityStrategy} strategia di codifica
	 */
	public CredentialLoader(HttpServletRequest inputData, ISecurityStrategy strategy)
	{
		this.input = inputData;
		this.strategy = strategy;
	}
	
	@Override
	public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException 
	{
		System.out.println("FIN QUI ARRIVO");
		Loader loader = null;
		for(int i = 0; i < callbacks.length; i++)
		{
			loader = (Loader)callbacks[i];
			loader.setSecurityStrategy(this.strategy);
			loader.load(this.input);
		}
	}
	
	/**
	 * Restituisce la strategia di codifica
	 * delle credenziali
	 * 
	 * @return 	{@link ISecurityStrategy} strategia di codifica
	 */
	ISecurityStrategy getSecurityStrategy()
	{
		return this.strategy;
	}
}