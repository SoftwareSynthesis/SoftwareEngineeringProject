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
 * Modulo di autenticazione al sistema mytalk. Offre
 * le operazione di accesso ed uscita dal sistema
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
	 * Inizializzazione del modulo di login
	 * 
	 * @param	subject		{@link Subject} che deve essere autenticato
	 * @param	handler		{@link CallbackHandler} per il caricamento delle credenzialie
	 * @param	sharedState	{@link Map} con lo stato condiviso
	 * @param	option		{@link Map} con le opzioni
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void initialize(Subject subject, CallbackHandler handler, Map sharedState, Map option)
	{
		this.login = false;
		this.commit = false;
		this.handler = handler;
		this.subject = subject;
		this.principal = null;
		this.username = null;
		this.password = null;
	}
	
	/**
	 * Effettua il login con le credenziali
	 * di accesso fornite da un utente
	 * 
	 * @throws	{@link LoginException} se le credenziali
	 * 			di accesso sono errate
	 */
	@Override
	public boolean login() throws LoginException
	{
		Callback[] callbacks = null;
		char[] tmpPassword = null;
		IUserData user = null;
		String userPassword = null;
		UserDataDAO userDAO = null;
		if (this.handler != null)
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
				throw new LoginException("Errori durante il caricamento delle credenziali di accesso");
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
			tmpPassword = null;
			userDAO = new UserDataDAO();
			user = userDAO.getByEmail(this.username);
			if (user != null)
			{
				userPassword = user.getPassword();
				if (userPassword.equals(new String(this.password)))
				{
					this.login = true;
					return true;
				}
				else
				{
					this.login = false;
					this.username = null;
					this.password = null;
					throw new FailedLoginException("Password errata");
				}
			}
			else
			{
				throw new FailedLoginException("Username errato");
			}
		}
		else
		{
			throw new LoginException("Nessun handler definito per la procedura di login");
		}
	}
	
	/**
	 * Aggiunge le caratteristiche identificative al
	 * {@link Subject} in modo che la sua identità
	 * possa essere facilmente ritrovata
	 * 
	 * @throws	{@link LoginException} se l'operazione
	 * 			non dovessere andare a buon fine
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
	 * Termina la procedura di login cancellando
	 * tutti i dati di elaborazione, comprese le 
	 * credenziali
	 * 
	 * @throws	{@link LoginException} se l'operazione
	 * 			non dovesse andare a buon fine
	 */
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
	 * Effettua il logout di un utente cancellando
	 * le credenziali di accesso e le informazioni
	 * che permettono di ricondurre a lui
	 * 
	 * @throws 	{@link LoginException} se l'operazione
	 * 			non dovesse andare a buon fine
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
	
	/**
	 * Restituisce l'istanza in formato {@link String}
	 * 
	 * @return	{@link String} rappresentante l'istanza
	 */
	@Override
	public String toString()
	{
		return String.format("AuthenticationModule[username: %s]", this.username);
	}
}