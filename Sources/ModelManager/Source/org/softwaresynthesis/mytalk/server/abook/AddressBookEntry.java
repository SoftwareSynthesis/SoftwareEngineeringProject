package org.softwaresynthesis.mytalk.server.abook;

/**
 * Rappresenta un contatto della rubrica di
 * un utente
 * 
 * @author 	Andrea Meneghinello
 * @version	%I%, %G%
 */
public class AddressBookEntry implements IAddressBookEntry 
{
	private Long id;
	private IUserData contact;
	private IUserData owner;
	private IGroup group;
	private boolean blocked;
	
	/**
	 * Crea un AddressBookEntry vuoto
	 */
	public AddressBookEntry()
	{
		this.blocked = false;
	}
	
	/**
	 * Crea un AddressBookEntry con un ID
	 * associato
	 * 
	 * @param 	identifier	{@link Long} che identifica
	 * 						l'AddressBookEntry
	 */
	public AddressBookEntry(Long identifier)
	{
		this.setId(identifier);
	}
	
	/**
	 * Restituisce l'istanza sotto forma di
	 * {@link String} in formato JSON
	 * 
	 * @return	{@link String} in formato JSON
	 * 			dell'istanza
	 */
	@Override
	public String toJson() 
	{
		String contactJSON = this.contact.toJson();
		String groupJSON = this.group.toJson();
		String result = "{\"id\":\"" + this.getId() + "\"";
		result += ", \"contact\":\"" + contactJSON + "\"";
		result += ", \"group\":\"" + groupJSON + "\"";
		result += ", \"blocked\":\"" + this.blocked + "\"}";
		return result;
	}

	/**
	 * Restituisce l'identificatore univoco del
	 * contatto
	 * 
	 * @return	{@link Long} che identifica il contatto
	 */
	@Override
	public Long getId()
	{
		return this.id;
	}
	
	/**
	 * Imposta l'identificatore dell'istanze
	 * 
	 * @param 	identifier	{@link Long} che identifica
	 * 						l'istanza
	 */
	protected void setId(Long identifier)
	{
		this.id = identifier;
	}

	/**
	 * Restituisce il contatto della entry
	 * 
	 * @return	contatto della rubrica di tipo
	 * 			{@link IUserData}
	 */
	@Override
	public IUserData getContact() 
	{
		return this.contact;
	}

	/**
	 * Imposta il contatto della rubrica
	 * 
	 * @param	contatc	{@link IUserData} contatto
	 * 					della rubrica
	 */
	@Override
	public void setContact(IUserData contact) 
	{
		this.contact = contact;
	}

	/**
	 * Restituisce il gruppo a cui appartiene
	 * il contatto
	 * 
	 * @return	{@link IGroup} a cui appartiene il
	 * 			contatto, null se non appartiene a
	 * 			nessun gruppo
	 */
	@Override
	public IGroup getGroup()
	{
		return this.group;
	}

	/**
	 * Imposta il gruppo a cui appartiene il contatto
	 * 
	 * @param	group 	{@link IGroup} a cui appartiene
	 * 					il contatto	
	 */
	@Override
	public void setGroup(IGroup group) 
	{
		this.group = group;
	}

	/**
	 * Restituisce il proprietario della
	 * rubrica
	 * 
	 * @return	{@link IUserData} proprietario
	 * 			del contatto
	 */
	@Override
	public IUserData getOwner() 
	{
		return this.owner;
	}

	/**
	 * Imposta il proprietario del contatto
	 * 
	 * @param	owner	{@link IUserData} proprietario
	 * 					del contatto
	 */
	@Override
	public void setOwner(IUserData owner)
	{
		this.owner = owner;
	}

	/**
	 * Restituisce un valore booleano che informa
	 * se il contatto è bloccato
	 * 
	 * @return	true se il contatto è bloccato, false
	 * 			altrimenti
	 */
	@Override
	public boolean getBlocked()
	{
		return this.blocked;
	}

	/**
	 * Imposta se un contatto è bloccato o
	 * meno
	 * 
	 * @param	blocked	true se si vuole bloccare,
	 * 					false altrimenti
	 */
	@Override
	public void setBlocked(boolean blocked) 
	{
		this.blocked = blocked;
	}
	
	/**
	 * Testa l'uguaglianza di due istanze
	 * 
	 * @param	obj	{@link Object} che deve essere
	 * 				confrontato
	 * @return	true se le due istanze sono uguali,
	 * 			false altrimenti
	 */
	public boolean equals(Object obj)
	{
		boolean result = false;
		AddressBookEntry entry = null;
		if (obj instanceof AddressBookEntry)
		{
			entry = (AddressBookEntry)obj;
			result = this.id.equals(entry.id);
		}
		return result;
	}
	
	/**
	 * Restituisce l'istanza in forma di {@link String}
	 * 
	 * @return	{@link String} rappresentante l'istanza	
	 */
	public String toString()
	{
		return String.format("AddressBookEntry[contact: %s, owner: %s, group: %s]", this.contact, this.owner, this.group);
	}
}