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
	private Long id;
	private Set<AddressBookEntry> addressBook;
	private String name;
	
	/**
	 * Crea un oggetto gruppo privo
	 * di valori
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 */
	public Group()
	{
		this.addressBook = new HashSet<AddressBookEntry>();
	}
	
	public Group(Long identifier)
	{
		this();
		this.setId(identifier);
	}

	/**
	 * Restituisce l'istanza dell'oggetto
	 * come stringa in formato JSON
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
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
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
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
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
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
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
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
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @param	name	{@link String} nome del
	 * 					gruppo
	 */
	@Override
	public void setName(String name) 
	{
		this.name = name;
	}
	
	/**
	 * Restituisce i contatti appartenenti
	 * al gruppo
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return	{@link Set} con i contatti del
	 * 			gruppo
	 */
	@Override
	public Set<AddressBookEntry> getAddressBook()
	{
		return this.addressBook;
	}
	
	/**
	 * Aggiunge un nuovo contatto al gruppo
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @param	entry	{@link AddressBookEntry} da
	 * 					aggiungere al gruppo
	 */
	@Override
	public void addAddressBook(AddressBookEntry entry)
	{
		this.addressBook.add(entry);
		entry.setGroup(this);
	}
	
	/**
	 * Compare due istanze di Group
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @param	obj	{@link Object} che deve
	 * 				essere comparato
	 * @result	true se le due istanze rappresentano
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
}