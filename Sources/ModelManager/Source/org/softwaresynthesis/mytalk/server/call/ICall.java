package org.softwaresynthesis.mytalk.server.call;

import java.util.Date;

/**
 * Interfaccia che rappresenta una chiamata effettuata dal
 * sistema mytalk
 * 
 * @author 	Andrea Meneghinello
 * @version	%I%, %G%
 */
public interface ICall 
{
	/**
	 * Restituisce l'identificativo univoco della chiamata
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return	long rappresentante l'identificativo univoco
	 * 			della chiamata
	 */
	public Long getId();
	
	/**
	 * Restituisce {@link Date} di avvio della chiamata
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return	{@link Date} di avvio della chiamata
	 */
	public Date getStartDate();
	
	/**
	 * Imposta {@link Date} di avvio della chiamata
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @param 	startDate	{@link Date} di avvio della
	 * 						chiamata
	 */
	public void setStartDate(Date startDate);
	
	/**
	 * Restituisce {@link Date} di terminazione dell chiamata
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return	{@link Date} di terminazione della chiamata
	 */
	public Date getEndDate();
	
	/**
	 * Imposta {@link Date} di terminazione della chiamata
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @param 	endDate	{@link Date} di terminazione della
	 * 					chiamata
	 */
	public void setEndDate(Date endDate);
}