package org.softwaresynthesis.mytalk.server.authentication;

import java.io.IOException;
import javax.security.auth.callback.NameCallback;
import javax.servlet.http.HttpServletRequest;

/**
 * Caricatore per lo username. Ha il compito
 * di caricare e preparare lo username fornito
 * dall'utente per la successiva fase di autenticazione
 * 
 * @author 	Andrea Meneghinello
 * @version	3.0
 */
public class NameLoader extends Loader 
{
	/**
	 * Costruisce un caricatore per lo
	 * username
	 */
	public NameLoader()
	{
		super(new NameCallback("username"));
	}
	
	@Override
	public void load(HttpServletRequest request) throws IOException 
	{
		NameCallback callback = (NameCallback)super.getCallback();
		String username = request.getParameter("username");
		if (username != null)
		{
			callback.setName(username);
		}
		else
		{
			throw new IOException("Username non pervenuto");
		}
	}
	
	@Override
	public String getData()
	{
		NameCallback callback = (NameCallback)super.getCallback();
		String username = callback.getName();
		return username;
	}
}
