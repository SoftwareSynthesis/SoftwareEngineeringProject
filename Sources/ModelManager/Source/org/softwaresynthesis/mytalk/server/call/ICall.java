package org.softwaresynthesis.mytalk.server.call;

import java.util.Set;

/**
 * Rappresentazione della chiamata in MyTalk
 * 
 * @author 	Andrea Meneghinello
 * @version 1.0
 */
public interface ICall 
{
	/**
	 * Restituisce l'identificativo della chiamata
	 * 
	 * @return	{@link Long} rappresentante la chiamata
	 */
	public Long getId();
	
	/**
	 * Restituisce la data e l'ora di avvio
	 * della chiamata
	 * 
	 * @return	{@link String} con data e ora
	 * 			di avvio
	 */
	public String getStartDate();
	
	/**
	 * Imposta la data e ora di inizio della chiamata
	 * 
	 * @param 	dateTime	{@link String} con data e ora
	 * 						di avvio
	 */
	public void setStartDate(String dateTime);
	
	/**
	 * Restituisce la data e l'ora di fine
	 * della chiamata
	 * 
	 * @return	{@link String} con data e ora
	 * 			di fine
	 */
	public String getEndDate();
	
	/**
	 * Imposta la data e ora di fine della chiamata
	 * 
	 * @param 	dateTime	{@link String} con data e ora
	 * 						di fine
	 */
	public void setEndDate(String dateTime);
	
	/**
	 * Restituisce i partecipanti alla chiamata
	 *
	 * @return	{@link Set} partecipanti alla
	 * 			chiamata	
	 */
	public Set<ICallList> getCallList();
	
	/**
	 * Inserisce i partecipanti alla chiamata
	 * 
	 * @param 	callList	{@link Set} partecipanti
	 * 						alla chiamata@param callList
	 */
	public void setCallList(Set<ICallList> callList);
	
	/**
	 * Aggiunge un partecipante alla chiamata
	 * 
	 * @param 	callList	{@link ICallList} con
	 * 						le informazioni del partecipante
	 */
	public void addCallList(ICallList callList);
	
	/**
	 * Rimuove un partecipante dalla chiamata
	 * 
	 * @param	callList 	{@link ICallList} con
	 * 						le informazione del partecipante 						
	 */
	public void removeCallList(ICallList callList);
}