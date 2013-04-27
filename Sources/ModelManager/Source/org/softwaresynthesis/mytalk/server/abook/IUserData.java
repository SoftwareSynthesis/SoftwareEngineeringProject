package org.softwaresynthesis.mytalk.server.abook;

import java.util.Set;
import org.softwaresynthesis.mytalk.server.IMyTalkObject;
import org.softwaresynthesis.mytalk.server.call.ICallList;
import org.softwaresynthesis.mytalk.server.message.IMessage;

/**
 * Rappresenta un utente del sistema MyTalk
 * 
 * @author 	Andrea Meneghinello
 * @version	3.0
 */
public interface IUserData extends IMyTalkObject 
{
	/**
	 * Restituisce l'indirizzo e-mail di un utente
	 * 
	 * @return	{@link String} rappresentante l'indirizzo
	 * 			mail
	 */
	public String getMail();
	
	/**
	 * Imposta l'indirizzo mail con cui l'utente
	 * si vuole registrare presso il sistema MyTalk
	 * 
	 * @param 	mail	{@link String} con l'indirizzo
	 * 					mail
	 */
	public void setMail(String mail);
	
	/**
	 * Restituisce la password con cui l'utente
	 * accede al sistema mytalk. La password è
	 * crittografata
	 * 
	 * @return	{@link String} password di accesso
	 * 			al sistema MyTalk
	 */
	public String getPassword();
	
	/**
	 * Imposta la password con cui l'utente accederà
	 * al sistema MyTalk. La password deve essere
	 * fornita in chiaro
	 * 
	 * @param 	password	{@link String} password
	 * 						di accesso al sistema
	 * 						MyTalk
	 */
	public void setPassword(String password);
	
	/**
	 * Resituisce la domanda, scelta dall'utente, per
	 * il recupero della password di accesso al sistema
	 * MyTalk
	 * 
	 * @return	{@link String} domanda per il recupero
	 * 			della password
	 */
	public String getQuestion();
	
	/**
	 * Imposta la domanda per il recupero della password
	 * di accesso al sistema MyTalk
	 * 
	 * @param 	question	{@link String} domanda per il
	 * 						recupero della password
	 */
	public void setQuestion(String question);
	
	/**
	 * Restituisce la risposta alla domanda segreta per il
	 * recupero della password. La risposta è crittografata
	 * 
	 * @return	{@link String} risposta alla domanda segreta
	 */
	public String getAnswer();
	
	/**
	 * Imposta la risposta alla domanda segreta per il recupero
	 * della password. La risposta deve essere fornita in chiaro
	 * 
	 * @param 	answer	{@link String} risposta alla domanda segreta
	 */
	public void setAnswer(String answer);
	
	/**
	 * Restituisce il nome dell'utente
	 * 
	 * @return	{@link String} nome dell'utente
	 */
	public String getName();
	
	/**
	 * Imposta il nome dell'utente
	 * 
	 * @param 	name	{@link String} nome dell'utente
	 */
	public void setName(String name);
	
	/**
	 * Restituisce il cognome dell'utente
	 * 
	 * @return	{@link String} cognome dell'utente
	 */
	public String getSurname();
	
	/**
	 * Imposta il cognome dell'utente
	 * 
	 * @param 	surname	{@link String} cognome dell'utente
	 */
	public void setSurname(String surname);
	
	/**
	 * Resitituisce il percorso, sul server, dell'immagine profilo
	 * dell'utente
	 * 
	 * @return {@link String} percorso dell'immagine di profilo
	 */
	public String getPath();
	
	/**
	 * Imposta il percorso dell'immagine profilo dell'utente.
	 * Il percorso si riferisce al server in cui viene ospitata
	 * l'applicazione
	 * 
	 * @param 	picturePath	{@link String} percorso dell'immagine
	 * 						di profilo
	 */
	public void setPath(String picturePath);
	
	/**
	 * Restituisce la rubrica personale di un utente del sistema
	 * MyTalk
	 * 
	 * @return	{@link Set<IAddressBookEntry>}	rubrica dell'utente
	 */
	public Set<IAddressBookEntry> getAddressBook();
	
	/**
	 * Imposta la rubrica di un utente del sistema MyTalk
	 * 
	 * @param 	addressBook	{@link Set<IAddressBookEntry>}
	 * 						rubrica dell'utente
	 */
	public void setAddressBook(Set<IAddressBookEntry> addressBook);
	
	/**
	 * Restituisce la lista delle chiamate effettuate dall'utente
	 * 
	 * @return {@link Set<ICallList>} lista delle chiamate
	 */
	public Set<ICallList> getCallList();
	
	/**
	 * Imposta la lista chiamate dell'utente del sistema
	 * MyTalk
	 * 
	 * @param 	callList	{@link Set<ICallList>} lista
	 * 						delle chiamate
	 */
	public void setCallList(Set<ICallList> callList);
	
	/**
	 * Restituisce la lista dei messaggi presenti in segreteria
	 * 
	 * @return	{@link Set<IMessage>} lista dei messaggi
	 */
	public Set<IMessage> getMessage();
	
	/**
	 * Imposta la lista dei messaggi presenti in segreteria
	 * 
	 * @param 	messages	{@link Set<IMessage>} lista dei
	 * 						messaggi
	 */
	public void setMessage(Set<IMessage> messages);
	
	/**
	 * Aggiunge un nuovo contatto all rubrica di un utente
	 * 
	 * @param 	entry	{@link IAddressBookEntry} contatto da aggiungere
	 */
	public boolean addAddressBookEntry(IAddressBookEntry entry);
	
	/**
	 * Aggiunge una nuova chiamata allo storico dell'utente
	 * 
	 * @param 	call	{@link ICallList} nuova chiamata da
	 * 					aggiungere
	 */
	public boolean addCall(ICallList call);
	
	/**
	 * Aggiunge un nuovo messaggio alla segreteria dell'utente
	 * 
	 * @param 	message	{@link IMessage} nuovo messaggio da lasciare
	 * 					in segreteria
	 */
	public boolean addMessage(IMessage message);
	
	/**
	 * Rimuove un contatto dall rubrica di un utente
	 * 
	 * @param 	entry	{@link IAddressBookEntry} contatto da rimuovere 
	 */
	public boolean removeAddressBookEntry(IAddressBookEntry entry);
	
	/**
	 * Rimuove una chiamata dallo storico del contatto
	 * 
	 * @param 	call	{@link ICallList} chiamata da rimuore
	 * 					dallo storico
	 */
	public boolean removeCall(ICallList call);
	
	/**
	 * Rimuove un messaggio dalla segreteria dell'utente
	 * 
	 * @param 	message	{@link IMessage} da rimuovere dalla
	 * 					segreteria
	 */
	public boolean removeMessage(IMessage message);
}