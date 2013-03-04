package org.softwaresynthesis.mytalk.server.message;

import java.util.Date;
import org.softwaresynthesis.mytalk.server.abook.IUserData;

/**
 * Implementazione dell'interfaccia {@link IMessage}
 * @version	%I%, %G%
 * @author Diego Beraldin
 *
 */
public class Message implements IMessage
{
	
	private Long id;
	private IUserData sender;
	private IUserData receiver;
	private boolean status;
	private boolean video;
	private Date date;

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
	public boolean isNew()
	{
		return this.status;
	}

	@Override
	public void setNew(boolean status)
	{
		this.status = status;
	}

	@Override
	public boolean isVideo()
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

}
