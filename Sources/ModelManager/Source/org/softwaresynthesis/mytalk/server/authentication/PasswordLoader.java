package org.softwaresynthesis.mytalk.server.authentication;

import java.io.IOException;
import javax.security.auth.callback.PasswordCallback;
import javax.servlet.http.HttpServletRequest;
import org.softwaresynthesis.mytalk.server.authentication.security.ISecurityStrategy;

/**
 * Caricatore per la password. Ha il compito
 * di caricare e preparare la password fornita
 * dall'utente per la successiva fase di autenticazione
 * 
 * @author 	Andrea Meneghinello
 * @version	3.0
 */
final class PasswordLoader extends Loader 
{	
	/**
	 * Costruisce un caricatore per la
	 * password
	 */
	public PasswordLoader()
	{
		super(new PasswordCallback("password", false));
	}
	
	@Override
	public void load(HttpServletRequest request) throws IOException 
	{
		char[] cryptedStringArray = null;
		ISecurityStrategy strategy = null;
		PasswordCallback callback = (PasswordCallback)super.getCallback();
		String cryptedValue = null;
		String password = request.getParameter("password");
		try
		{
			strategy = super.getSecurityStrategy();
			cryptedValue = strategy.encode(password);
			cryptedStringArray = cryptedValue.toCharArray();
			callback.setPassword(cryptedStringArray);
		}
		catch (Exception ex)
		{
			throw new IOException("Errore durante la preparazione della password");
		}
	}
	
	@Override
	public String getData()
	{
		PasswordCallback callback = (PasswordCallback)super.getCallback();
		char[] arrayCharPassword = callback.getPassword();
		char[] password = new char[arrayCharPassword.length];
		String result = null;
		System.arraycopy(arrayCharPassword, 0, password, 0, arrayCharPassword.length);
		result = new String(password);
		return result;
	}
}