package org.softwaresynthesis.mytalk.server.call;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Rappresenta una chiamata effettuata con
 * il sistema MyTalk
 * 
 * @author 	Andrea Meneghinello
 * @version	3.0
 */
public class Call implements ICall 
{
	private Long id;
	private Date start;
	private Date end;
	private Set<ICallList> calls;
	
	/**
	 * Costruisce una nuova istanza priva
	 * di valori
	 */
	public Call()
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
	public Call(Long identifier)
	{
		this.id = identifier;
		this.calls = new HashSet<ICallList>();
	}
	
	@Override
	public Long getId() 
	{
		return this.id;
	}

	@Override
	public Date getStartDate() 
	{
		return this.start;
	}

	@Override
	public void setStartDate(Date startDate)
	{
		this.start = startDate;
	}

	@Override
	public Date getEndDate() 
	{
		return this.end;
	}

	@Override
	public void setEndDate(Date endDate) 
	{
		this.end = endDate;
	}

	@Override
	public Set<ICallList> getCallList() 
	{
		return this.calls;
	}

	@Override
	public void setCallList(Set<ICallList> callList) 
	{
		this.calls = callList;
	}

	@Override
	public boolean addCall(ICallList call) 
	{
		boolean result = false;
		call.setCall(this);
		result = this.calls.add(call);
		return result;
	}

	@Override
	public boolean removeCall(ICallList call) 
	{
		boolean result = this.calls.remove(call);
		return result;
	}
	
	/**
	 * Determina se due istanze rappresentano lo stesso
	 * oggetto Call
	 * 
	 * @param	obj	{@link Object} istanza da verifcare
	 * @return	true se le due istanze rappresentano lo
	 * 			stesso oggetto, false altrimenti
	 */
	@Override
	public boolean equals(Object obj)
	{
		boolean result = false;
		Call toCompare = null;
		Long toCompareId = null;
		if (obj instanceof Call)
		{
			toCompare = (Call)obj;
			toCompareId = toCompare.getId();
			if (this.id.equals(toCompareId))
			{
				result = true;
			}
		}
		return result;
	}
}