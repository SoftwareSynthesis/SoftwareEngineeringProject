package org.softwaresynthesis.mytalk.server.call;

import java.util.Date;

/**
 * Implementazione dell'interfaccia {@link ICall}
 * @version	%I%, %G%
 * @author Diego Beraldin
 *
 */
public class Call implements ICall
{
	
	private Long id;
	private Date startDate;
	private Date endDate;
	
	@Override
	public Long getId()
	{
		return this.id;
	}

	@Override
	public Date getStartDate()
	{
		return this.startDate;
	}

	@Override
	public void setStartDate(Date startDate)
	{
		this.startDate = startDate;

	}

	@Override
	public Date getEndDate()
	{
		return this.endDate;
	}

	@Override
	public void setEndDate(Date endDate)
	{
		this.endDate = endDate;

	}

}
