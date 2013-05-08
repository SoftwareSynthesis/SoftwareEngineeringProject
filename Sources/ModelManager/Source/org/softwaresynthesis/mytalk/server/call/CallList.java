package org.softwaresynthesis.mytalk.server.call;

import org.softwaresynthesis.mytalk.server.abook.IUserData;

/**
 * Rappresenta informazioni di dettaglio per
 * una chiamata effettuata con il sistema
 * MyTalk
 * 
 * @author 	Andrea Meneghinello
 * @version 3.0
 */
public class CallList implements ICallList
{
	private Long id;
	private IUserData user;
	private ICall call;
	private boolean caller;
	
	/**
	 * Costruisce una nuova istanza priva
	 * di valori
	 */
	public CallList()
	{
		this(-1L);
	}
	
	/**
	 * Costruisce una nuova istanza assegnandogli
	 * un identificativo
	 * 
	 * @param 	identifier	{@link Long} identificativo
	 * 						associato
	 */
	public CallList(Long identifier)
	{
		this.id = identifier;
	}
	
	@Override
	public Long getId()
	{
		return this.id;
	}
	
	@Override
	public void setId(Long id)
	{
		this.id = id;
	}

	@Override
	public IUserData getUser() 
	{
		return this.user;
	}

	@Override
	public void setUser(IUserData user) 
	{
		this.user = user;
	}

	@Override
	public ICall getCall()
	{
		return this.call;
	}

	@Override
	public void setCall(ICall call) 
	{
		this.call = call;
	}

	@Override
	public boolean getCaller() 
	{
		return this.caller;
	}

	@Override
	public void setCaller(boolean caller) 
	{
		this.caller = caller;
	}
	
	/**
	 * Determina se due istanze rappresenta lo stesso
	 * oggetto CallList
	 * 
	 * @param	obj	{@link Object} istanza da verificare
	 * @return 	true se le due istanze rappresentano lo
	 * 			stesso oggetto, false altrimenti
	 */
	@Override
	public boolean equals(Object obj)
	{
		boolean result = false;
		ICall toCompareCall = null;
		CallList toCompare = null;
		IUserData toCompareUser = null;
		if (obj instanceof CallList)
		{
			toCompare = (CallList)obj;
			toCompareCall = toCompare.getCall();
			toCompareUser = toCompare.getUser();
			if (this.call.equals(toCompareCall) && this.user.equals(toCompareUser))
			{
				result = true;
			}
		}
		return result;
	}
}