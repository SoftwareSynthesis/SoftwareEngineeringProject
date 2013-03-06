package org.softwaresynthesis.mytalk.server.authentication;

import java.io.IOException;
import java.security.Principal;
import java.util.Map;
import java.util.Set;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import javax.security.auth.Subject;
import org.softwaresynthesis.mytalk.server.abook.IUserData;
import org.softwaresynthesis.mytalk.server.dao.UserDataDAO;

/**
 * Modulo di autenticazione utilizzato dal sistema
 * mytalk
 * 
 * @author 	Andrea Meneghinello
 * @version	%I%, %G%
 */
public class AuthenticationModule implements LoginModule 
{
	private boolean login;
	private boolean commit;
	private CallbackHandler handler;
	private char[] password;
	private String username;
	private Principal principal;
	private Subject subject;
	
	/**
	 * Inizializzazione dello stato interno del modulo
	 * di autenticazione del sistema mytalk
	 * 
	 * @author 	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @param 	subject		soggetto che deve essere autenticato
	 * @param	handler		carica le credenziali di accesso per la procedura
	 * @param	sharedState	stato condiviso con altri moduli di login
	 * @param	option		opzioni settate nel file di configurazione per il modulo di login
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void initialize(Subject subject, CallbackHandler handler, Map sharedState, Map option)
	{
		this.login = false;
		this.commit = false;
		this.handler = handler;
		this.subject = subject;
		this.password = null;
		this.username = null;
	}
	
	/**
	 * Esegue la procedura di login con le credenziali fornite
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return 	true se l'operazione di login è andata a buon fine
	 * @throws	{@link LoginException} se l'operazione è fallita
	 * 			{@link FailedLoginException} se le credenziali non corrispondono a nessun utente
	 */
	@Override
	public boolean login() throws LoginException
	{
		Callback[] callbacks = null;
		char[] tmpPassword = null;
		IUserData user = null;
		String userPass = null;
		UserDataDAO userDAO = null;
		if (this.handler == null)
		{
			throw new LoginException("Nessun handler definito per la procedura di login");
		}
		else
		{
			callbacks = new Callback[2];
			callbacks[0] = new NameCallback("username");
			callbacks[1] = new PasswordCallback("password", false);
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
			this.username = ((NameCallback)callbacks[0]).getName();
			tmpPassword = ((PasswordCallback)callbacks[1]).getPassword();
			this.password = new char[tmpPassword.length];
			System.arraycopy(tmpPassword, 0, this.password, 0, tmpPassword.length);
			((PasswordCallback)callbacks[1]).clearPassword();
			userDAO = new UserDataDAO();
			user = userDAO.getByEmail(this.username);
			if(user != null)
			{
				userPass = user.getPassword();
				if (userPass.equals(this.password))
				{
					this.login = true;
					return true;
				}
				else
				{
					this.login = false;
					this.username = null;
					this.password = null;
					throw new FailedLoginException("Credenziali di accesso non corrette, riprovare.");
				}
			}
		}
		return false;
	}
	
	/**
	 * Aggiunge le caratteristiche identificative al subject
	 * in modo che la sua identità possa essere facilmente
	 * ritrovata nel resto del sistema mytalk
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return	true se l'operazione ha avuto successo
	 * @throws	{@link LoginException} se l'operazione ha riscontrato errori
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
	 * Termina la procedura di login cancellando tutti i dati di
	 * elaborazione
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return	true se l'operazione è andata a buon fine
	 * @throws	{@link LoginException} se l'operazione ha riscontrato errori
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
	 * Effettua il logout dal sistema mytalk
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return 	true se l'operazione è andata a buon fine
	 * @throws	{@link LoginException} se l'operazione ha riscontrato errori
	 */
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