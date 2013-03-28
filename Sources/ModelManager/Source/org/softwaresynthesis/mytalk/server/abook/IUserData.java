package org.softwaresynthesis.mytalk.server.abook;

import java.util.Set;
import org.softwaresynthesis.mytalk.server.IMyTalkObject;

/**
 * Rappresentazione di un utente del sistema
 * mytalk
 * 
 * @author 	Andrea Meneghinello
 * @version	1.0
 */
public interface IUserData extends IMyTalkObject
{	
	/**
	 * Imposta l'identificativo dell'utente
	 * 
	 * @param id identificativo da impostare
	 */
	public void setId(Long id);
	
	/**
	 * Restituisce l'identificativo univoco
	 * dell'utente del sistema mytalk
	 * 
	 * @return	identificativo dell'utente di
	 * 			tipo {@link Long}
	 */
	public Long getId();
	
	/**
	 * Restituisce l'indirizzo e-mail con cui
	 * si è registrato un utente nel sistema
	 * mytalk
	 * 
	 * @return	{@link String} rappresentante
	 * 			l'indirizzo e-mail dell'utente
	 */
	public String getMail();
	
	/**
	 * Imposta l'indirizzo e-mail con cui
	 * l'utente si vuole registrare nel 
	 * sistema mytalk
	 * 
	 * @param 	eMail	{@link String} con
	 * 					l'e-mail con cui vuole
	 * 					registrarsi l'utente
	 */
	public void setMail(String eMail);
	
	/**
	 * Restituisce la password, crittografata
	 * secondo la strategia {@link org.softwaresynthesis.mytalk.server.authentication.ISecurityStrategy}
	 * con l'utente accede al sistema
	 * 
	 * @return	{@link String} con la password
	 * 			dell'utente
	 */
	public String getPassword();
	
	/**
	 * Imposta la password che l'utente utilizzerà
	 * per accedere al sistema mytalk
	 * 
	 * @param 	password	{@link String} con la
	 * 						password scelta dall'utente
	 */
	public void setPassword(String password);
	
	/**
	 * Restituisce la domanda segreta che l'utente
	 * ha impostato per il recupero della password
	 * 
	 * @return	{@link String} con la domanda segreta
	 */
	public String getQuestion();
	
	/**
	 * Imposta la domanda segreta utilizzata dall'utente
	 * per il recupero della propria password
	 * 
	 * @param 	question	{@link String} con la domanda
	 * 						segreta scelta dall'utente
	 */
	public void setQuestion(String question);
	
	/**
	 * Restituisce la risposta alla domanda segreta
	 * per il recupero della password di accesso
	 * al sistema mytalk
	 * 
	 * @return	{@link String} con la risposta alla
	 * 			domanda segreta
	 */
	public String getAnswer();
	
	/**
	 * Imposta la risposta alla domanda segreta
	 * per il recupero della password di accesso
	 * al sistema mytalk
	 * 
	 * @param 	answer	{@link String} con la
	 * 					risposta alla domanda segreta
	 * 					per il recupero della password
	 */
	public void setAnswer(String answer);
	
	/**
	 * Restituisce il nome dell'utente
	 * 
	 * @return	{@link String} con il nome dell'utente
	 */
	public String getName();
	
	/**
	 * Imposta il nome dell'utente
	 * 
	 * @param 	name {@link String} con il nome dell'utente
	 */
	public void setName(String name);
	
	/**
	 * Restituisce il cognome dell'utente
	 * 
	 * @return	{@link String} con il cognome dell'utente
	 */
	public String getSurname();
	
	/**
	 * Imposta il cognome dell'utente
	 * 
	 * @param 	surname	{@link String} con il cognome
	 * 					dell'utnete
	 */
	public void setSurname(String surname);
	
	/**
	 * Restituisce il link all'immagine profilo scelta
	 * dall'utente
	 * 
	 * @return	{@link String} con il percorso realativo
	 * 			all'immagine profilo dell'utente
	 */
	public String getPath();
	
	/**
	 * Imposta il percorso dell'immagine profilo scelta
	 * dall'utente
	 * 
	 * @param 	path	{@link String} con il percorso
	 * 					dove sarà salvata nel server
	 * 					l'immagine profilo
	 */
	public void setPath(String path);
	
	/**
	 * Restituisce la rubrica dell'utente
	 * 
	 * @return	{@link Set} contente le entry
	 * 			della rubrica
	 */
	public Set<IAddressBookEntry> getAddressBook();
	
	/**
	 * Aggiunge alla rubrica personale un
	 * nuovo contatto
	 * 
	 * @param 	addressBook	{@link Set} contentente
	 * 						la rubrica utente
	 */
	public void setAddressBook(Set<IAddressBookEntry> addressBook);
	
	/**
	 * Aggiunge un nuovo contatto alla rubrica
	 * 
	 * @param	entry	{@link AddressBookEntry}
	 * 					da aggiungere alla rubrica
	 */
	public void addAddressBookEntry(IAddressBookEntry entry);
	
	/**
	 * Rimuove un contatto dalla rubrica
	 * 
	 * @param 	entry	{@link AddressBookEntry} da rimuovere
	 */
	public void removeAddressBookEntry(IAddressBookEntry entry);
}