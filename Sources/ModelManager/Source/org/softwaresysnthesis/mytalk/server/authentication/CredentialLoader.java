package org.softwaresysnthesis.mytalk.server.authentication;

import java.io.IOException;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;

/**
 * Permette di caricare le credenziali di autenticazione,
 * fornite dall'utente, per preparare la fase di login
 * 
 * @author 	Andrea Meneghinello
 * @version	%I%, %G%
 */
public class CredentialLoader implements CallbackHandler
{
	private AuthenticationData credential;
	private ISecurityStrategy security;
	
	/**
	 * Crea un istanza con le credenziali fornite dall'utente
	 * 
	 * @author 	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @param 	credential	credenziali di accesso fornite dall'utente
	 */
	public CredentialLoader(AuthenticationData credential, ISecurityStrategy security)
	{
		this.credential = credential;
		this.security = security;
	}
	
	/**
	 * Effettua il caricamento e la crittografa le credenziali
	 * fornite dall'utente per la fase di login
	 * 
	 * @author 	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @param	callbacks	contiene la tipologia di dati richiesti
	 * 						dalla procedura di login che vengonono
	 * 						popolati in questa procedura
	 * @throws	{@link IOException} se ci sono problemi di input
	 * 			{@link UnsupportedCallbackException} se ci sono callback non supportati
	 */
	public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException
	{
		String cryptedValue = null;
		NameCallback nc = null;
		PasswordCallback pc = null;
		for(int i = 0; i < callbacks.length; i++)
		{
			if(callbacks[i] instanceof NameCallback)
			{
				nc = (NameCallback)callbacks[i];
				try
				{
					cryptedValue = security.encrypt(this.credential.getUsername());
				}
				catch (Exception ex)
				{
					throw new IOException("Errori durante la conversione dello username");
				}
				nc.setName(cryptedValue);
				cryptedValue = null;
			}
			else
			{
				if(callbacks[i] instanceof PasswordCallback)
				{
					pc = (PasswordCallback)callbacks[i];
					try
					{
						cryptedValue = security.encrypt(this.credential.getPassword());
					}
					catch (Exception ex)
					{
						throw new IOException("Errori durante la conversione della password");
					}
					pc.setPassword(cryptedValue.toCharArray());
					cryptedValue = null;
				}
				else
				{
					throw new UnsupportedCallbackException(callbacks[i], "Callback non supportato");
				}
			}
		}
	}
}