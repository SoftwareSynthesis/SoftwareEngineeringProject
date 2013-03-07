package org.softwaresynthesis.mytalk.server.abook;
/**
 * Implementazione dell'interfaccia {@link IGroup}
 * @version	%I%, %G%
 * @author Diego Beraldin
 */
public class Group implements IGroup
{
	
	private Long id;
	private String name;
	
	@Override
	public Long getId()
	{
		return this.id;
	}
	
	/**
	 * Definisce un identificatore per il gruppo
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @param 	id	identificatore del gruppo
	 */
	protected void setId(Long id)
	{
		this.id = id;
	}

	@Override
	public String getName()
	{
		return this.name;
	}

	@Override
	public void setName(String name)
	{
		this.name = name;
	}

}
