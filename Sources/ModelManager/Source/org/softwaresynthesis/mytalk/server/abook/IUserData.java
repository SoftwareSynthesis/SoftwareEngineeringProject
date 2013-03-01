package org.softwaresynthesis.mytalk.server.abook;

/**
 * Interfaccia rappresentante un utente del sistema
 * mytalk
 * 
 * @author 	Andrea Meneghinello
 * @version %I%, %G%
 */
public interface IUserData 
{
	/**
	 * Restituisce l'identificatore univoco di uno
	 * IUserData
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return	long rappresentante l'identificatore
	 * 			univoco di uno IUserData
	 */
	public Long getId();
	
	/**
	 * Restituisce l'indirizzo e-mail con cui
	 * uno IUserData si è registrato nel sistema
	 * mytalk
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return 	stringa rappresentante l'indirizzo
	 * 			e-mail
	 */
	public String getEmail();
	
	/**
	 * Imposta l'indirizzo e-mail con cui si registra
	 * nel sistema mytalk uno IUserData
	 * 
	 * @author 	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @param	mail	stringa rappresentante l'indirizzo
	 * 					e-mail
	 */
	public void setEmail(String mail);
	
	/**
	 * Restituisce la password di accesso al sistema mytalk
	 * di uno IUserData
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return 	stringa rappresentante la password di accesso
	 */
	public String getPassword();
	
	/**
	 * Imposta la password di accesso al sistema di uno IUserData
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @param 	password	stringa rappresentante la password di
	 * 						accesso al sistema mytalk
	 */
	public void setPassword(String password);
	
	/**
	 * Restituisce la domanda segreta, scelta da uno IUserData,
	 * per il recupero della password smarrita di accesso al
	 * sistema mytalk
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return 	stringa rappresentante la domanda segreta
	 */
	public String getQuestion();
	
	/**
	 * Imposta la domanda segreta, scelta da uno IUserData,
	 * per il recupero della password smarrita di accesso
	 * al sistema mytalk
	 * 
	 * @author	Andrea Meneghinello
	 * @param 	question	stringa rappresentante la domanda
	 * 						segreta
	 */
	public void setQuestion(String question);
	
	/**
	 * Restituisce la risposta alla domanda per il recupero
	 * della password smarrita di accesso al sistema mytalk.
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return	stringa rappresentante la risposta alla
	 * 			domanda segreta
	 */
	public String getAnswer();
	
	/**
	 * Imposta la risposta alla domanda segreta per il recupero
	 * della password di accesso al sistema mytalk
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @param 	answer	stringa rappresentante la risposta
	 * 					alla domanda per il recupero della
	 * 					password di accesso al sistema mytalk
	 */
	public void setAnswer(String answer);
	
	/**
	 * Restituisce il nome di uno IUserData
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return	stringa rappresentante il nome di uno IUserData
	 */
	public String getName();
	
	/**
	 * Imposta il nome di uno IUserData
	 * 
	 * @author	Andrea Meneghinello
	 * @param 	name	stringa rappresentante il nome di uno
	 * 					IUserData
	 */
	public void setName(String name);
	
	/**
	 * Resituisce il cognome di uno IUserData
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return	stringa rappresentante il cognome di uno IUserData
	 */
	public String getSurname();
	
	/**
	 * Imposta il cognome di uno IUserData
	 * 
	 * @author	Andrea Meneghinello
	 * @version %I%, %G%
	 * @param 	surname	stringa rappresentante il cognome di uno
	 * 					IUserData
	 */
	public void setSurname(String surname);
	
	/**
	 * Restituisce una stringa con il percorso dell'immagine
	 * del profilo di uno IUserData
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return	stringa con il percorso dell'immagine del profilo
	 * 			di uno IUserData
	 */
	public String getPicturePath();
	
	/**
	 * Imposta il percorso dell'immagine profilo di uno IUserData
	 * 
	 * @author	Andrea Meneghinello
	 * @param 	path	stringa rappresentante il percorso dove
	 * 					salvare l'immagine profilo di uno IUserData
	 */
	public void setPicturePath(String path);
}