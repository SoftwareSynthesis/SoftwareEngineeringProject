package org.softwaresynthesis.mytalk.server.call;

/**
 * 
 * @author Andrea
 *
 */
public interface ICall 
{
	public Long getId();
	
	public void sedId(Long id);
	
	public String getStartDate();
	
	public void setStartDate(String dateTime);
	
	public String getEndDate();
	
	public void setEndDate(String dateTime);
}