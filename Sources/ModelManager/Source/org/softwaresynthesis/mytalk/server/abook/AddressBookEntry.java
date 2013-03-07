package org.softwaresynthesis.mytalk.server.abook;

/**
 * Implementazione dell'interfaccia {@link IAddressBookEntry}
 * 
 * @author 	Andrea Meneghinello
 * @version %I%, %G%
 */
public class AddressBookEntry implements IAddressBookEntry 
{
	private Long id;
	private IGroup group;
	private IUserData contact;
	private IUserData owner;
	private boolean blocked;

	@Override
	public Long getId() 
	{
		return this.id;
	}
	
	/**
	 * Imposta l'id di una entry della rubrica del
	 * sistema mytalk
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @param 	id	Identificatore della entry
	 */
	protected void setId(Long id)
	{
		this.id = id;
	}

	@Override
	public IUserData getEntry() 
	{
		return this.contact;
	}

	@Override
	public void setEntry(IUserData contact) 
	{
		this.contact = contact;
	}

	@Override
	public IGroup getGroup() 
	{
		return this.group;
	}

	@Override
	public void setGroup(IGroup group) 
	{
		this.group = group;
	}

	@Override
	public IUserData getOwner() 
	{
		return this.owner;
	}

	@Override
	public void setOwner(IUserData owner) 
	{
		this.owner = owner;
	}

	@Override
	public boolean isBlocked() 
	{
		return this.blocked;
	}

	@Override
	public void setBlocked(boolean status) 
	{
		this.blocked = status;
	}
}
