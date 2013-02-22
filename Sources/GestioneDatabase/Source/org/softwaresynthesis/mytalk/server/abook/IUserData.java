package org.softwaresynthesis.mytalk.server.abook;

/**
 *	Utente del sistema mytalk 
 *
 * @author 	Andrea Meneghinello
 * @version	%I%, %G%
 */
public interface IUserData
{
	/**
	 * Restituisce l'identificativo univoco per ogni utente 
	 * del sistema mytalk
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return	Identificativo univoco dell'utente
	 */
	public Long getId();
	
	/**
	 * Restituisce l'indirizzo e-mail utilizzato dall'utente in 
	 * fase di registrazione al sistema mytalk
	 * 
	 * @author 	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return	Indirizzo e-mail utilizzato da un utente in fase 
	 * 			di registrazione al sistema mytalk
	 */
	public String getEmail();
	
	/**
	 * Restituisce la password scelta dall'utente per l'accesso 
	 * al sistema mytalk
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return	Ritorna la password impostata dall'utente per 
	 * 			l'accesso al sistema mytalk
	 */
	public String getPassword();
	
	/**
	 * Imposta la password scelta dall'utente per l'accesso al sistema mytalk
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @param	password	Password scelta dall'utente per l'accesso al sistema 
	 * 						mytalk
	 */
	public void setPassword(String password);
	
	/**
	 * Restituisce la domanda segreta scelta dall'utente da usare 
	 * per il recupero della password smarrita
	 * 
	 * @author	Andrea Meneghinello
	 * @version %I%, %G%;
	 * @return	Ritorna la domanda segreta scelta dall'utente da usare 
	 * 			per il recupero della password smarrita
	 */
	public String getQuestion();
	
	/**
	 * Imposta la domanda segreta scelta dall'utente da usare per il recupero 
	 * della password smarrita
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @param	question	Domanda segreta scelta dall'utente da usare per il recupero 
	 * 						della password smarrita
	 */
	public void setQuestion(String question);
	
	/**
	 * Restituisce la risposta alla domanda segreta utilizzata per il recupero della password
	 * 
	 * @author 	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return 	Ritorna la risposta alla domanda segreta utilizzata per il recupero della password
	 */
	public String getAnswer();
	
	/**
	 * Imposta la risposta alla domanda segreta utilizzata per il recupero della password
	 * 
	 * @author 	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @param	answer	Risposta alla domanda segreta utilizzata per il recupero della password
	 */
	public void setAnswer(String answer);
	
	/**
	 * Restituisce il nome dell'utente
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return 	Restituisce il nome dell'utente
	 */
	public String getName();
	
	/**
	 * Imposta il nome dell'utente
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @param 	name	Nome dell'utente
	 */
	public void setName(String name);
	
	/**
	 * Restituisce il cognome dell'utente
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return	Restituisce il cognome dell'utente
	 */
	public String getSurname();
	
	/**
	 * Imposta il cognome dell'utente
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @param 	surname	Cognome dell'utente
	 */
	public void setSurname(String surname);
	
	/**
	 * Restituisce il percorso all'immagine profilo dell'utente
	 * 
	 * @author 	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return	Restituisce il percorso, all'interno dell'applicazione
	 * 			nel server, dell'immagine profilo dell'utente
	 */
	public String getImage();
	
	/**
	 * Imposta l'immagine profilo dell'utente
	 * 
	 * @author 	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @param	path	Percorso, all'interno dell'applicazione nel server, dell'immagine
	 * 					del profilo dell'utente
	 */
	public void setImage(String path);
}