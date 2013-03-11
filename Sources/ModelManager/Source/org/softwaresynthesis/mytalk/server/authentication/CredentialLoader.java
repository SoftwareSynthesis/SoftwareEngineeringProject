package org.softwaresynthesis.mytalk.server.authentication;

import java.io.IOException;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;

public class CredentialLoader implements CallbackHandler 
{
	private AuthenticationData credential;
	private ISecurityStrategy strategy;

	/**
	 * Crea un istanza di un oggetto che ha il compito
	 * di preparare le credenziali per la fase di login
	 * 
	 * @author 	Andrea Meneghinello
	 * @param 	credential	{@link String} credenziali di accesso
	 * 						fornite dall'utente
	 * @param 	strategy	{@link ISecurityStrategy} di codifica
	 * 						da utilizzare
	 */
	public CredentialLoader(AuthenticationData credential, ISecurityStrategy strategy)
	{
		this.credential = credential;
		this.strategy = strategy;
	}
	
	/**
	 * Prepara le credenziali di accesso fonrite per la
	 * procedura di login
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @param	vettore {@link Callback} da popolare con
	 * 			le credenziali
	 */
	@Override
	public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException 
	{
		char[] cryptedStringArray = null;
		String cryptedValue = null;
		NameCallback name = null;
		PasswordCallback password = null;
		if (this.credential == null)
		{
			throw new IOException("Nessuna credenziale di accesso reperita");
		}
		for (int i = 0; i < callbacks.length; i++)
		{
			if (callbacks[i] instanceof NameCallback)
			{
				name = (NameCallback)callbacks[i];
				name.setName(this.credential.getUsername());
			}
			else
			{
				if (callbacks[i] instanceof PasswordCallback)
				{
					password = (PasswordCallback)callbacks[i];
					try
					{
						cryptedValue = this.strategy.encode(this.credential.getPassword());
					}
					catch (Exception ex)
					{
						throw new IOException("Errori durante la codifica");
					}
					cryptedStringArray = cryptedValue.toCharArray();
					password.setPassword(cryptedStringArray);
					cryptedStringArray = null;
					cryptedValue = null;
				}
				else
				{
					throw new UnsupportedCallbackException(callbacks[i], "Callback non supportata dal sistema");
				}
			}
		}
		this.credential = null;
	}

}
