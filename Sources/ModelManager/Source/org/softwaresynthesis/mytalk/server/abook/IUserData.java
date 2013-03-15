package org.softwaresynthesis.mytalk.server.abook;

import java.util.Set;
import org.softwaresynthesis.mytalk.server.IMyTalkObject;

/**
 * Rappresentazione di un utente del sistema
 * mytalk
 * 
 * @author 	Andrea Meneghinello
 * @version	%I%, %G%
 */
public interface IUserData extends IMyTalkObject
{	
	/**
	 * Restituisce l'identificativo univoco
	 * dell'utente del sistema mytalk
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return	identificativo dell'utente di
	 * 			tipo {@link Long}
	 */
	public Long getId();
	
	/**
	 * Restituisce l'indirizzo e-mail con cui
	 * si è registrato un utente nel sistema
	 * mytalk
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return	{@link String} rappresentante
	 * 			l'indirizzo e-mail dell'utente
	 */
	public String getEmail();
	
	/**
	 * Imposta l'indirizzo e-mail con cui
	 * l'utente si vuole registrare nel 
	 * sistema mytalk
	 * 
	 * @author 	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @param 	eMail	{@link String} con
	 * 					l'e-mail con cui vuole
	 * 					registrarsi l'utente
	 */
	public void setEmail(String eMail);
	
	/**
	 * Restituisce la password, crittografata
	 * secondo la strategia {@link ISecurityStrategy},
	 * con l'utente accede al sistema
	 * 
	 * @author 	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return	{@link String} con la password
	 * 			dell'utente
	 */
	public String getPassword();
	
	/**
	 * Imposta la password che l'utente utilizzerà
	 * per accedere al sistema mytalk
	 * 
	 * @author	Andrea Meneghinello
	 * @param 	password	{@link String} con la
	 * 						password scelta dall'utente
	 */
	public void setPassword(String password);
	
	/**
	 * Restituisce la domanda segreta che l'utente
	 * ha impostato per il recupero della password
	 * 
	 * @author 	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return	{@link String} con la domanda segreta
	 */
	public String getQuestion();
	
	/**
	 * Imposta la domanda segreta utilizzata dall'utente
	 * per il recupero della propria password
	 * 
	 * @author	Andrea Meneghinello
	 * @version %I%, %G%
	 * @param 	question	{@link String} con la domanda
	 * 						segreta scelta dall'utente
	 */
	public void setQuestion(String question);
	
	/**
	 * Restituisce la risposta alla domanda segreta
	 * per il recupero della password di accesso
	 * al sistema mytalk
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return	{@link String} con la risposta alla
	 * 			domanda segreta
	 */
	public String getAnswer();
	
	/**
	 * Imposta la risposta alla domanda segreta
	 * per il recupero della password di accesso
	 * al sistema mytalk
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @param 	answer	{@link String} con la
	 * 					risposta alla domanda segreta
	 * 					per il recupero della password
	 */
	public void setAnswer(String answer);
	
	/**
	 * Restituisce il nome dell'utente
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return	{@link String} con il nome dell'utente
	 */
	public String getName();
	
	/**
	 * Imposta il nome dell'utente
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @param 	name {@link String} con il nome dell'utente
	 */
	public void setName(String name);
	
	/**
	 * Restituisce il cognome dell'utente
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return	{@link String} con il cognome dell'utente
	 */
	public String getSurname();
	
	/**
	 * Imposta il cognome dell'utente
	 * 
	 * @author	Andrea Meneghinello
	 * @param 	surname	{@link String} con il cognome
	 * 					dell'utnete
	 */
	public void setSurname(String surname);
	
	/**
	 * Restituisce il link all'immagine profilo scelta
	 * dall'utente
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return	{@link String} con il percorso realativo
	 * 			all'immagine profilo dell'utente
	 */
	public String getPicturePath();
	
	/**
	 * Imposta il percorso dell'immagine profilo scelta
	 * dall'utente
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @param 	path	{@link String} con il percorso
	 * 					dove sarà salvata nel server
	 * 					l'immagine profilo
	 */
	public void setPicturePath(String path);
	
	/**
	 * Restituisce la rubrica dell'utente
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return	{@link Set} contente le entry
	 * 			della rubrica
	 */
	public Set<AddressBookEntry> getAddressBook();
	
	/**
	 * Aggiunge alla rubrica personale un
	 * nuovo contatto
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @param entry
	 */
	public void addAddressBookEntry(AddressBookEntry entry);
}