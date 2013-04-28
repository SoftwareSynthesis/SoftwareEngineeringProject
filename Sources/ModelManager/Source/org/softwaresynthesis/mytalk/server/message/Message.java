package org.softwaresynthesis.mytalk.server.message;

import java.util.Date;
import org.softwaresynthesis.mytalk.server.abook.IUserData;

/**
 * Rappresenta un messaggio lasciato nella
 * segreteria di un utente del sistema
 * MyTalk
 * 
 * @author 	Andrea Meneghinello
 * @version	3.0
 */
public class Message implements IMessage 
{
	private Long id;
	private IUserData sender;
	private IUserData receiver;
	private boolean newer;
	private boolean video;
	private Date date;
	
	/**
	 * Costruisce una nuova istanza priva
	 * di valori
	 */
	public Message()
	{
		this(-1L);
	}
	
	/**
	 * Costruisce una nuova istanza assegnadogli
	 * un identificativo
	 * 
	 * @param 	identifier	{@link Long} idenfiticativo
	 * 						associato
	 */
	public Message(Long identifier)
	{
		this.id = identifier;
	}
	
	@Override
	public Long getId() 
	{
		return this.id;
	}

	@Override
	public IUserData getSender()
	{
		return this.sender;
	}

	@Override
	public void setSender(IUserData sender)
	{
		this.sender = sender;
	}

	@Override
	public IUserData getReceiver() 
	{
		return this.receiver;
	}

	@Override
	public void setReceiver(IUserData receiver) 
	{
		this.receiver = receiver;
	}

	@Override
	public boolean getNewer() 
	{
		return this.newer;
	}

	@Override
	public void setNewer(boolean newer) 
	{
		this.newer = newer;
	}

	@Override
	public boolean getVideo() 
	{
		return this.video;
	}

	@Override
	public void setVideo(boolean video) 
	{
		this.video = video;
	}

	@Override
	public Date getDate()
	{
		return this.date;
	}

	@Override
	public void setDate(Date date) 
	{
		this.date = date;
	}
	
	/**
	 * Determina se due istanze rappresentano lo stesso
	 * oggetto Message
	 * 
	 * @param	obj	{@link Object} istanza da verificare
	 * @return	true se le due istanze rappresentano lo
	 * 			stesso oggetto, false altrimenti
	 */
	@Override
	public boolean equals(Object obj)
	{
		boolean result = false;
		Date toCompareDate = null;
		IUserData toCompareSender = null;
		IUserData toCompareReceiver = null;
		Message toCompare = null;
		if (obj instanceof Message)
		{
			toCompare = (Message)obj;
			toCompareSender = toCompare.getSender();
			toCompareReceiver = toCompare.getReceiver();
			toCompareDate = toCompare.getDate();
			if (this.sender.equals(toCompareSender) && this.receiver.equals(toCompareReceiver) && this.date.equals(toCompareDate))
			{
				result = true;
			}
		}
		return result;
	}
}