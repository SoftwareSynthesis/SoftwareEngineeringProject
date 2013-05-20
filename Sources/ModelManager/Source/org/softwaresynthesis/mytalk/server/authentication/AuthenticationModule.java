package org.softwaresynthesis.mytalk.server.authentication;

import java.io.IOException;
import java.security.Principal;
import java.util.Map;
import java.util.Set;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import org.softwaresynthesis.mytalk.server.abook.IUserData;
import org.softwaresynthesis.mytalk.server.dao.DataPersistanceManager;

public class AuthenticationModule implements LoginModule {
	private boolean login;
	private boolean commit;
	private CallbackHandler handler;
	private String username;
	private Principal principal;
	private Subject subject;

	/**
	 * Termina la procedura di login cancellando tutti i dati di eleborazione,
	 * comprese le credenziali utente
	 * 
	 * @throws {@link LoginException} se l'operazione non dovesse andare a buone
	 *         fine
	 */
	@Override
	public boolean abort() throws LoginException {
		if (this.isLoggedIn() == false) {
			return false;
		} else {
			if (this.isCommitted() == true) {
				this.login = false;
				this.username = null;
				this.principal = null;
			} else {
				this.logout();
			}
			return true;
		}
	}

	/**
	 * Aggiunge le caratteristiche identificative al {@link Subject} in modo che
	 * la sua identità possa essere facilmente recuperata
	 * 
	 * @throws {@link LoginException} se l'operazione non dovesse andare a buon
	 *         fine
	 */
	@Override
	public boolean commit() throws LoginException {
		this.principal = new PrincipalImpl(this.getUsername());
		Set<Principal> principals = getPrincipals();
		if (principals.contains(this.principal) == false) {
			principals.add(this.principal);
		}
		this.username = null;
		this.commit = true;
		return true;
	}

	/**
	 * Inizializzazione del modulo di login
	 * 
	 * @param subject
	 *            {@link Subject} soggetto che deve essere autenticato
	 * @param handler
	 *            {@link CallbackHandler} handler per il caricamento delle
	 *            credenziali
	 * @param sharedState
	 *            {@link Map} mappa per un eventuale condivisione di stato
	 * @param option
	 *            {@link Map} mappa per eventuali opzioni di login
	 */
	@Override
	public void initialize(Subject subject, CallbackHandler handler,
			Map<String, ?> sharedState, Map<String, ?> option) {
		this.login = false;
		this.commit = false;
		this.handler = handler;
		this.subject = subject;
		this.principal = null;
		this.username = null;
	}

	/**
	 * Effettua l'autenticazione di un utente nel sistema MyTalk
	 * 
	 * @throws {@link LoginException} se l'operazione non dovesse andarea a buon
	 *         fine
	 */
	@Override
	public boolean login() throws LoginException {
		DataPersistanceManager dao;
		Loader[] callbacks = null;
		IUserData user = null;
		String toComparePassword = null;
		if (this.handler != null) {
			callbacks = new Loader[2];
			callbacks[0] = createNameLoader();
			callbacks[1] = createPasswordLoader();
			try {
				this.handler.handle(callbacks);
			} catch (IOException ex) {
				throw new LoginException(ex.getMessage());
			} catch (UnsupportedCallbackException ex) {
				throw new LoginException(ex.getMessage());
			}
		}
		this.username = callbacks[0].getData();
		dao = getDAOFactory();
		user = dao.getUserData(username);
		if (user != null) {
			toComparePassword = user.getPassword();
			if (toComparePassword.equals(callbacks[1].getData())) {
				this.login = true;
				return true;
			} else {
				this.login = false;
				this.username = null;
				throw new FailedLoginException("Password errata");
			}
		} else {
			throw new FailedLoginException("Username errato");
		}
	}

	/**
	 * Effettua il logout dal sistema MyTalk
	 * 
	 * @throws {@link LoginException} se l'operazione non dovesse andare a buon
	 *         fine
	 */
	@Override
	public boolean logout() throws LoginException {
		Set<Principal> principals = getPrincipals();
		principals.remove(getPrincipal());
		this.login = false;
		this.commit = false;
		this.username = null;
		return true;
	}

	/*
	 * Mi dispiace ma queste mi servono assolutamente per i test! Ad ogni modo,
	 * spero converrete che hanno un aspetto del tutto innocuo...
	 */
	DataPersistanceManager getDAOFactory() {
		return new DataPersistanceManager();
	}

	boolean isLoggedIn() {
		return login;
	}

	boolean isCommitted() {
		return commit;
	}

	Loader createNameLoader() {
		return new NameLoader();
	}

	Loader createPasswordLoader() {
		return new PasswordLoader();
	}

	Set<Principal> getPrincipals() {
		return subject.getPrincipals();
	}

	String getUsername() {
		return username;
	}

	Principal getPrincipal() {
		return principal;
	}
}