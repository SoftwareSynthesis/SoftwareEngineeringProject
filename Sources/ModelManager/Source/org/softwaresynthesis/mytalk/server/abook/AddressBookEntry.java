package org.softwaresynthesis.mytalk.server.abook;

public class AddressBookEntry implements IAddressBookEntry 
{
	private Long id;
	private IUserData contact;
	private IGroup group;
	private IUserData owner;
	private boolean blocked;
	
	/**
	 * Costruisce una nuova istanza priva
	 * di valori
	 */
	public AddressBookEntry()
	{
		this(-1L);
	}
	
	/**
	 * Costruisce una nuove istanza assegnandogli
	 * un identificativo
	 * 
	 * @param 	identifier	{@link Long} identificativo
	 * 						associato
	 */
	public AddressBookEntry(Long identifier)
	{
		this.id = identifier;
	}
	
	@Override
	public Long getId() 
	{
		return this.id;
	}

	@Override
	public IUserData getContact()
	{
		return this.contact;
	}

	@Override
	public void setContact(IUserData contact)
	{
		this.contact = contact;
	}

	@Override
	public IGroup getGroup() 
	{
		return group;
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
	public boolean getBlocked() 
	{
		return blocked;
	}

	@Override
	public void setBlocked(boolean blocked) 
	{
		this.blocked = blocked;
	}
	
	/**
	 * Determina se due istanze rappresentano lo stesso
	 * oggetto AddressBookEntry
	 * 
	 * @param	obj	{@link Object} istanza da verificare
	 * @return	true se le due istanze rappresentano lo
	 * 			stesso oggetto, false altrimenti
	 */
	@Override
	public boolean equals(Object obj)
	{
		AddressBookEntry toCompare = null;
		boolean result = false;
		IUserData toCompareContact = null;
		IUserData toCompareOwner = null;
		if (obj instanceof AddressBookEntry)
		{
			toCompare = (AddressBookEntry)obj;
			toCompareContact = toCompare.getContact();
			toCompareOwner = toCompare.getOwner();
			if (this.contact.equals(toCompareContact) && this.owner.equals(toCompareOwner))
			{
				result = true;
			}
		}
		return result;
	}
}