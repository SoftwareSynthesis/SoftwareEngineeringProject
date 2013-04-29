package org.softwaresynthesis.mytalk.server.authentication;

import java.io.IOException;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.servlet.http.HttpServletRequest;

/**
 * La classe ha il compito di predisporre le credenziali
 * di accesso, fornite dall'utente, per la successiva fase
 * di autenticazione.
 * 
 * @author 	Andrea Meneghinello
 * @version	3.0
 */
public class CredentialLoader implements CallbackHandler
{
	private HttpServletRequest input;
	
	/**
	 * Crea una nuova istanza del caricatore di credenziali
	 * 
	 * @param 	inputData	{@link HttpServletRequest} dati in input
	 * @param 	strategy	{@link ISecurityStrategy} strategia di codifica
	 */
	public CredentialLoader(HttpServletRequest inputData)
	{
		this.input = inputData;
	}
	
	@Override
	public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException 
	{
		Loader loader = null;
		for(int i = 0; i < callbacks.length; i++)
		{
			loader = (Loader)callbacks[i];
			loader.load(this.input);
		}
	}
}