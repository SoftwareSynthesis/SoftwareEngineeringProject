package org.softwaresynthesis.mytalk.server.abook;
/**
 * Implementazione dell'interfaccia {@link IGroup}
 * @version	%I%, %G%
 * @author diego
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
