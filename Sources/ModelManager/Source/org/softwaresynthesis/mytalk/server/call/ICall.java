package org.softwaresynthesis.mytalk.server.call;

import java.util.Date;
import java.util.Set;
import org.softwaresynthesis.mytalk.server.IMyTalkObject;

/**
 * Rappresenta una chiamata effettuata attraverso il
 * sistema Mytalk
 * 
 * @author	Andrea Meneghinello
 * @version	3.0
 */
public interface ICall extends IMyTalkObject
{
	/**
	 * Restituisce la data di inizio della conversazione
	 * 
	 * @return	{@link Date} data di inizio conversazione
	 */
	public Date getStartDate();
	
	/**
	 * Imposta la data di inizio della conversazione
	 * 
	 * @param 	startDate	{@link Date} data di inizio
	 */
	public void setStartDate(Date startDate);
	
	/**
	 * Restituisce la data di fine della conversazione
	 * 
	 * @return {@link Date} data di fine conversazione
	 */
	public Date getEndDate();
	
	/**
	 * Imposta la data di fine conversazione
	 * 
	 * @param 	endDate	{@link Date} data di fine
	 */
	public void setEndDate(Date endDate);
	
	/**
	 * Restituisce la lista dei partecipanti alla chiamata
	 * 
	 * @return	{@link Set<ICallList>} lista dei partecipanti
	 */
	public Set<ICallList> getCallList();
	
	/**
	 * Imposta la lista dei partecipanti alla chiamata
	 * 
	 * @param 	callList	{@link Set<ICallList>} lista dei
	 * 						partecipanti
	 */
	public void setCallList(Set<ICallList> callList);
	
	/**
	 * Aggiunge un partecipante alla chiamata
	 * 
	 * @param 	call	{@link ICallList} partecipante alla
	 * 					chiamata
	 */
	public boolean addCall(ICallList call);
	
	/**
	 * Rimuove una partecipante alla chiamata
	 * 
	 * @param 	call	{@link ICallList} partecipante alla
	 * 					chiamata
	 */
	public boolean removeCall(ICallList call);
}