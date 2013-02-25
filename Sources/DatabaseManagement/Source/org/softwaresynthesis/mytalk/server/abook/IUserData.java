package org.softwaresynthesis.mytalk.server.abook;

import java.util.Observer;
import org.softwaresynthesis.mytalk.server.state.IState;

/**
 * Utente del sistema mytalk
 * 
 * @author 	Andrea Meneghinello
 * @version	%I%, %G%
 */
public interface IUserData extends Observer
{
	/**
	 * Ottiene l'identificatore univoco di un utente del sistema mytalk
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return	l'identificatore univoco di un utente del sistema mytalk
	 */
	public Long getId();
	
	/**
	 * Ottiene l'indirizzo e-mail di un utente del sistema mytalk
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return	l'indirizzo e-mail di un utente del sistema mytalk
	 */
	public String getEmail();
	
	/**
	 * Imposta l'indirizzo e-mail di un utente del sistema mytalk
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @param 	mail	indirizzo e-mail di un utente del sistema
	 * 					mytalk
	 */
	public void setEmail(String mail);
	
	/**
	 * Ottiene la password dell'utente del sistema mytalk
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return	password dell'utente del sistema mytalk
	 */
	public String getPassword();
	
	/**
	 * Imposta la password di un utente mytalk
	 * 
	 * @author 	Andrea Meneghinello
	 * version	%I%, %G%
	 * @param 	password	password dell'utente per il sistema mytalk
	 */
	public void setPassword(String password);
	
	/**
	 * Ottiene la domanda segreta per il recupero della password di un utente
	 * del sistema mytalk
	 * 
	 * @author 	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return 	domanda segreta per il recupero della password di un utente
	 * 			del sistema mytalk
	 */
	public String getQuestion();
	
	/**
	 * Imposta la domanda segreta per il recupero della password di un utente
	 * del sistema mytalk
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @param 	question	domanda segreta per il recupero della password di
	 * 						un utente del sistema mytalk
	 */
	public void setQuestion(String question);
	
	/**
	 * Ottiene la risposta alla domanda segreta per il recupero della password
	 * di un utente del sistema mytalk
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return	risposta alla domanda segreta per il recupero della password
	 * 			di un utente del sistema mytalk
	 */
	public String getAnswer();
	
	/**
	 * Imposta la risposta alla domanda segreta per il recupero della password
	 * di utente del sistema mytalk
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @param 	answer	risposta alla domanda segreta per il recupero della
	 * 					password di utente del sistema mytalk
	 */
	public void setAnswer(String answer);
	
	/**
	 * Ottiene il nome di un utente del sistema mytalk
	 * 
	 * @author 	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return	il nome di un utente del sistema mytalk
	 */
	public String getName();
	
	/**
	 * Imposta il nome di un utente del sistema mytalk
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @param 	name	nome di un utente del sistema mytalk
	 */
	public void setName(String name);
	
	/**
	 * Ottiene il cognome di un utente del sistema mytalk
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return	cognome di un utente del sistema mytalk
	 */
	public String getSurname();
	
	/**
	 * Imposta il cognome di un utente del sistema mytalk
	 * 
	 * @author	Andrea Meneghinello
	 * @param 	surname	cognome di un utente del sistema mytalk
	 */
	public void setSurname(String surname);
	
	/**
	 * Ottiene il percorso, nel server, dove è memorizzata l'immagine
	 * profilo di un utente del sistema mytalk
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return	stringa contenente il percorso, nel server, dove è
	 * 			memorizzata l'immagine di un utente del sistema mytalk
	 */
	public String getImage();
	
	/**
	 * Imposta il percorso, nel server, dove sarà memorizzata l'immagine
	 * profilo di un utente del sistema mytalk
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @param 	path	percorso, nel server, dove sarà memorizzata
	 * 					l'immagine profilo di un utente del sistema
	 * 					mytalk
	 */
	public void setImage(String path);
	
	/**
	 * Ottiene lo stato in cui si trova un utente del sistema mytalk
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return	{@link IState} in cui si trova l'utente del sistema mytalk
	 */
	public IState getState();
	
	/**
	 * Imposta lo stato in cui si trova un utente del sistema mytalk
	 * 
	 * @author 	Andrea Meneghinello
	 * @param 	state	nuovo stato in cui si trova un utente del
	 * 					sistema mytalk
	 */
	public void setState(IState state);
}