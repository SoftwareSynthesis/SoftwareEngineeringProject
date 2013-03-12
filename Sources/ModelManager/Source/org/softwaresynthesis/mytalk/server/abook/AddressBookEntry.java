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
	 * Restituisce l'istanza sotto forma di
	 * {@link String} in formato JSON
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return	{@link String} in formato JSON
	 * 			dell'istanza
	 */
	@Override
	public String toJson() 
	{
		String contactJSON = this.contact.toJson();
		String ownerJSON = this.owner.toJson();
		String groupJSON = this.group.toJson();
		String result = "{\"id\":\"" + this.getId() + "\"";
		result += "\"contact\":\"" + contactJSON + "\"";
		result += "\"owner\":\"" + ownerJSON + "\"";
		result += "\"group\":\"" + groupJSON + "\"";
		result += "\"blocked\":\"" + this.blocked + "\"";
		return result;
	}

	/**
	 * Restituisce l'identificatore univoco del
	 * contatto
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return	{@link Long} che identifica il contatto
	 */
	@Override
	public Long getId()
	{
		return this.id;
	}

	/**
	 * Restituisce il contatto della entry
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
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
	 * @author	Andrea Meneghinello
	 * @version %I%, %G%
	 * @param	contatc	{@link IUserData} contatto
	 * 					della rubrica
	 */
	@Override
	public void setContatct(IUserData contact) 
	{
		this.contact = contact;
	}

	/**
	 * Restituisce il gruppo a cui appartiene
	 * il contatto
	 * 
	 * @author	Andrea Meneghinello
	 * @version %I%, %G%
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
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
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
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
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
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
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
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return	true se il contatto è bloccato, false
	 * 			altrimenti
	 */
	@Override
	public boolean isBlocked()
	{
		return this.blocked;
	}

	/**
	 * Imposta se un contatto è bloccato o
	 * meno
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @param	blocked	true se si vuole bloccare,
	 * 					false altrimenti
	 */
	@Override
	public void setBlocked(boolean blocked) 
	{
		this.blocked = blocked;
	}
}
