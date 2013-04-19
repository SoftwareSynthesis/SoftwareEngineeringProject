package org.softwaresynthesis.mytalk.server.call;

import java.util.HashSet;
import java.util.Set;

public class Call implements ICall 
{
	private Long id;
	private String start;
	private String end;
	private Set<ICallList> callLists;
	
	public Call()
	{
		this.callLists = new HashSet<ICallList>();
	}
	
	public Call(Long id)
	{
		this();
		this.setId(id);
	}
	
	@Override
	public Long getId() 
	{
		return this.id;
	}
	
	/**
	 * Imposta l'identificativo della chiamata
	 * 
	 * @param 	id	{@link Long} che identifica
	 * 				la chiamata
	 */
	protected void setId(Long id)
	{
		this.id = id;
	}

	@Override
	public String getStartDate() 
	{
		return this.start;
	}

	@Override
	public void setStartDate(String dateTime)
	{
		this.start = dateTime;
	}

	@Override
	public String getEndDate() 
	{
		return end;
	}

	@Override
	public void setEndDate(String dateTime) 
	{
		this.end = dateTime;
	}
	
	@Override
	public Set<ICallList> getCallList()
	{
		return this.callLists;
	}
	
	@Override
	public void setCallList(Set<ICallList> callList)
	{
		this.callLists = callList;
	}
	
	@Override
	public void addCallList(ICallList callList)
	{
		this.callLists.add(callList);
	}
	
	@Override
	public void removeCallList(ICallList callList)
	{
		this.callLists.remove(callList);
	}
	
	@Override
	public boolean equals(Object obj)
	{
		boolean result = false;
		Call toCompare = null;
		if (obj instanceof Call)
		{
			toCompare = (Call)obj;
			result = this.id.equals(toCompare.getId());
		}
		return result;
	}
}
