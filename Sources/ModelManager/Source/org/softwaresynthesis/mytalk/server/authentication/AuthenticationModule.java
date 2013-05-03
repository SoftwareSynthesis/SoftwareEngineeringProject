package org.softwaresynthesis.mytalk.server.authentication;

import java.io.IOException;
import java.security.Principal;
import java.util.Map;
import java.util.Set;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

public final class AuthenticationModule implements LoginModule 
{
	private boolean login;
	private boolean commit;
	private CredentialLoader handler;
	private String password;			//TODO controllare se veramente necessario
	private String username;			//TODO controllare se veramente necessario
	private Principal principal;
	private Subject subject;

	/**
	 * Termina la procedura di login cancellando
	 * tutti i dati di eleborazione, comprese le 
	 * credenziali utente
	 * 
	 * @throws 	{@link LoginException} se l'operazione non
	 * 			dovesse andare a buone fine
	 */
	@Override
	public boolean abort() throws LoginException 
	{
		if (this.login == false)
		{
			return false;
		}
		else
		{
			if (this.commit == true)
			{
				this.login = false;
				this.username = null;
				this.password = null;
				this.principal = null;
			}
			else
			{
				this.logout();
			}
			return true;
		}
	}

	/**
	 * Aggiunge le caratteristiche identificative al
	 * {@link Subject} in modo che la sua identità
	 * possa essere facilmente recuperata
	 * 
	 * @throws 	{@link LoginException} se l'operazione
	 * 			non dovesse andare a buon fine
	 */
	@Override
	public boolean commit() throws LoginException
	{
		this.principal = new PrincipalImpl(this.username);
		Set<Principal> principals = this.subject.getPrincipals();
		if (principals.contains(this.principal) == false)
		{
			principals.add(this.principal);
		}
		this.username = null;
		this.password = null;
		this.commit = true;
		return true;
	}

	/**
	 * Inizializzazione del modulo di login
	 * 
	 * @param 	subject			{@link Subject} soggetto che deve essere autenticato
	 * @param	handler			{@link CallbackHandler} handler per il caricamento delle credenziali
	 * @param 	sharedState		{@link Map} mappa per un eventuale condivisione di stato
	 * @param 	option			{@link Map} mappa per eventuali opzioni di login
	 */
	@Override
	public void initialize(Subject subject, CallbackHandler handler, Map<String, ?> sharedState, Map<String, ?> option)
	{
		this.login = false;
		this.commit = false;
		this.handler = (CredentialLoader)handler;
		this.subject = subject;
		this.principal = null;
		this.username = null;
		this.password = null;
	}

	/**
	 * Effettua l'autenticazione di un utente
	 * nel sistema MyTalk
	 * 
	 * @throws 	{@link LoginException} se l'operazione non
	 * 			dovesse andarea a buon fine
	 */
	@Override
	public boolean login() throws LoginException 
	{
		Loader[] callbacks = null;
		if (this.handler != null)
		{
			callbacks = new Loader[2];
			callbacks[0] = new NameLoader();
			callbacks[1] = new PasswordLoader(this.handler.getSecurityStrategy());
			try
			{
				this.handler.handle(callbacks);
			}
			catch (IOException ex)
			{
				throw new LoginException(ex.getMessage());
			}
			catch (UnsupportedCallbackException ex)
			{
				throw new LoginException(ex.getMessage());
			}
		}
		this.username = callbacks[0].getData();
		this.password = callbacks[1].getData();
		
		//TODO Implementare la connessione con il DAO
		return false;
	}

	/**
	 * Effettua il logout dal sistema MyTalk
	 * 
	 * @throws	{@link LoginException} se l'operazione non
	 * 			dovesse andare a buon fine
	 */
	@Override
	public boolean logout() throws LoginException 
	{
		Set<Principal> principals = this.subject.getPrincipals();
		principals.remove(this.principal);
		this.login = false;
		this.commit = false;
		this.username = null;
		this.password = null;
		return true;
	}

}
