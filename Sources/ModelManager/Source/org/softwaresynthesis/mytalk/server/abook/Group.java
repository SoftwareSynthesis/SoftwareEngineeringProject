package org.softwaresynthesis.mytalk.server.abook;

import java.util.HashSet;
import java.util.Set;

/**
 * Rappresenta un gruppo di una rubrica
 * utente
 * 
 * @author 	Andrea Meneghinello
 * @version	%I%, %G%
 */
public class Group implements IGroup 
{
	private IUserData owner;
	private Long id;
	private Set<IAddressBookEntry> addressBook;
	private String name;
	
	/**
	 * Crea un oggetto gruppo privo
	 * di valori
	 */
	public Group()
	{
		this.addressBook = new HashSet<IAddressBookEntry>();
	}
	
	/**
	 * Crea un gruppo assegnandogli un identificatore
	 * 
	 * @param 	identifier	{@link Long} che identificherà
	 * 						l'istanza
	 */
	public Group(Long identifier)
	{
		this();
		this.setId(identifier);
	}

	/**
	 * Restituisce l'istanza dell'oggetto
	 * come stringa in formato JSON
	 * 
	 * @return	{@link String} dell'istanza
	 * 			in formato JSON
	 */
	@Override
	public String toJson() 
	{
		String result = "{\"id\":\"" + this.getId() + "\"";
		result += ", \"name\":\"" + this.getName() + "\"}";
		return result;
	}

	/**
	 * Restituisce l'identificatore univoco
	 * del gruppo
	 * 
	 * @return	{@link Long} che identifica univocamente
	 * 			il gruppo
	 */
	@Override
	public Long getId() 
	{
		return this.id;
	}
	
	/**
	 * Imposta l'identificatore univoco
	 * del gruppo
	 * 
	 * @param 	identifier	{@link Long} idenficatore
	 * 						del gruppo
	 */
	protected void setId(Long identifier)
	{
		this.id = identifier;
	}

	/**
	 * Restituisce il nome del gruppo
	 * 
	 * @return	{@link String} con il nome
	 * 			del gruppo
	 */
	@Override
	public String getName() 
	{
		return this.name;
	}

	/**
	 * Imposta il nome del gruppo
	 * 
	 * @param	name	{@link String} nome del
	 * 					gruppo
	 */
	@Override
	public void setName(String name) 
	{
		this.name = name;
	}
	
	/**
	 * Restituisce il proprietario del
	 * gruppo
	 * 
	 * @return	{@link IUserData} proprietario
	 * 			del gruppo
	 */
	@Override
	public IUserData getOwner()
	{
		return this.owner;
	}
	
	/**
	 * Imposta il proprietario di un gruppo
	 * 
	 * @param 	owner	{@link IUserData} proprietario
	 * 					del gruppo
	 */
	@Override
	public void setOwner(IUserData owner)
	{
		this.owner = owner;
	}
	
	/**
	 * Restituisce i contatti appartenenti
	 * al gruppo
	 * 
	 * @return	{@link Set} con i contatti del
	 * 			gruppo
	 */
	@Override
	public Set<IAddressBookEntry> getAddressBook()
	{
		return this.addressBook;
	}
	
	/**
	 * Aggiunge un nuovo contatto al gruppo
	 * 
	 * @param	addressBook	{@link AddressBookEntry} da
	 * 						aggiungere al gruppo
	 */
	@Override
	public void setAddressBook(Set<IAddressBookEntry> addressBook)
	{
		this.addressBook = addressBook;
	}
	
	/**
	 * Aggiunge un contatto al gruppo
	 * 
	 * @param	entry	{@link AddressBookEntry} da
	 * 					aggiungere al gruppo
	 */
	@Override
	public void addAddressBookEntry(IAddressBookEntry entry)
	{
		this.addressBook.add(entry);
		entry.setGroup(this);
	}
	
	/**
	 * Rimuove un contatto dal gruppo
	 * 
	 * @param	entry	{@link AddressBookEntry} da
	 * 					rimuovere dal gruppo
	 */
	@Override
	public void removeAddressBookEntry(IAddressBookEntry entry)
	{
		this.addressBook.remove(entry);
		entry.setGroup(null);
	}
	
	/**
	 * Compare due istanze di Group
	 * 
	 * @param	obj	{@link Object} che deve
	 * 				essere comparato
	 * return	true se le due istanze rappresentano
	 * 			lo stesso oggetto, false altrimenti 
	 */
	@Override
	public boolean equals(Object obj)
	{
		boolean result = false;
		Group group = null;
		if (obj instanceof Group)
		{
			group = (Group)obj;
			result = this.id.equals(group.id);
		}
		return result;
	}
	
	/**
	 * Restituisce l'istanza in formato {@link String}
	 * 
	 * @return	{@link String} rappresentante l'istanza
	 */
	@Override
	public String toString()
	{
		return String.format("Group[id: %s, name: %s]", this.id.toString(), this.name);
	}
}