package org.softwaresynthesis.mytalk.server.abook;

import org.softwaresynthesis.mytalk.server.abook.IUserData.State;
import org.softwaresynthesis.mytalk.server.authentication.ISecurityStrategy;

/**
 * Rappresentazione dell'utente del sistema mytalk
 *
 * @author 	Andrea Meneghinello
 * @version	%I%, %G%
 */
public class UserData implements IUserData 
{
	private Long id;
	private State state;
	private String mail;
	private String password;
	private String question;
	private String answer;
	private String name;
	private String surname;
	private String path;
	
	/**
	 * Crea un utente privo di dati
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 */
	public UserData()
	{
	}
	
	/**
	 * Crea un utente con un particolare
	 * identificativo, che però verrà ignorato
	 * nelle operazioni di insert nel database
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @param 	identifier	{@link Long} che identifica
	 * 						un utente
	 */
	public UserData(Long identifier)
	{
		this.setId(identifier);
	}
	
	/**
	 * Restituisce l'istanza sottoforma di stringa
	 * JSON in modo che possa essere utilizzata
	 * nella parte client
	 * 
	 * @author 	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return	{@link String} in formato JSON
	 * 			dell'istanza
	 */
	@Override
	public String toJson() 
	{
		String result = "{\"name\":\"" + this.getName() + "\"";
		result += ", \"surname\":\"" + this.getSurname() + "\"";
		result += ", \"mail\":\"" + this.getEmail() + "\"";
		result += ", \"state\":\"" + this.getState() + "\"";
		result += ", \"picturePath\":\"" + this.getPicturePath() + "\"";
		result += ", \"id\":\"" + this.getId() + "\"}";
		return result;
	}

	/**
	 * Restituisce l'identificativo univoco
	 * dell'utente del sistema mytalk
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return	identificativo dell'utente di
	 * 			tipo {@link Long}
	 */
	@Override
	public Long getId() 
	{
		return this.id;
	}
	
	/**
	 * Imposta l'identificativo univoco di un
	 * utente
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @param 	identifier	{@link Long} che identifica
	 * 						un utente
	 */
	protected void setId(Long identifier)
	{
		this.id = identifier;
	}

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
	@Override
	public String getEmail() 
	{
		return this.mail;
	}

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
	@Override
	public void setEmail(String eMail) 
	{
		this.mail = eMail;
	}

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
	@Override
	public String getPassword() 
	{
		return password;
	}

	/**
	 * Imposta la password che l'utente utilizzerà
	 * per accedere al sistema mytalk
	 * 
	 * @author	Andrea Meneghinello
	 * @param 	password	{@link String} con la
	 * 						password scelta dall'utente
	 */
	@Override
	public void setPassword(String password)
	{
		this.password = password;
	}

	/**
	 * Restituisce la domanda segreta che l'utente
	 * ha impostato per il recupero della password
	 * 
	 * @author 	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return	{@link String} con la domanda segreta
	 */
	@Override
	public String getQuestion() 
	{
		return this.question;
	}

	/**
	 * Imposta la domanda segreta utilizzata dall'utente
	 * per il recupero della propria password
	 * 
	 * @author	Andrea Meneghinello
	 * @version %I%, %G%
	 * @param 	question	{@link String} con la domanda
	 * 						segreta scelta dall'utente
	 */
	@Override
	public void setQuestion(String question) 
	{
		this.question = question;
	}

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
	@Override
	public String getAnswer() 
	{
		return this.answer;
	}

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
	@Override
	public void setAnswer(String answer) 
	{
		this.answer = answer;
	}

	/**
	 * Restituisce il nome dell'utente
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return	{@link String} con il nome dell'utente
	 */
	@Override
	public String getName()
	{
		return this.name;
	}

	/**
	 * Imposta il nome dell'utente
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @param 	name {@link String} con il nome dell'utente
	 */
	@Override
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * Restituisce il cognome dell'utente
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return	{@link String} con il cognome dell'utente
	 */
	@Override
	public String getSurname() 
	{
		return this.surname;
	}

	/**
	 * Imposta il cognome dell'utente
	 * 
	 * @author	Andrea Meneghinello
	 * @param 	surname	{@link String} con il cognome
	 * 					dell'utnete
	 */
	@Override
	public void setSurname(String surname)
	{
		this.surname = surname;
	}

	/**
	 * Restituisce il link all'immagine profilo scelta
	 * dall'utente
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return	{@link String} con il percorso realativo
	 * 			all'immagine profilo dell'utente
	 */
	@Override
	public String getPicturePath() 
	{
		return this.path;
	}

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
	@Override
	public void setPicturePath(String path)
	{
		this.path = path;
	}
	
	/**
	 * Restituisce lo stato in cui si trova un
	 * utente
	 * 
	 * @author 	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @return 	{@link State} in cui si trova
	 * 			l'utente
	 */
	public State getState()
	{
		return this.state;
	}
	
	/**
	 * Imposta lo stato in cui si trova un utente
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @param 	state	{@link State} che rappresenta
	 * 					lo stato in cui si è spostato
	 * 					l'utente
	 */
	public void setState(State state)
	{
		this.state = state;
	}
	
	/**
	 * Verifica se due istanze sono uguali
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @param	obj	{@link Object} con cui effettuare
	 * 				confronto
	 * @return 	true se le due istanze sono uguali,
	 * 			false altrimenti
	 */
	public boolean equals(Object obj)
	{
		boolean result = false;
		UserData user = null;
		if (obj instanceof UserData)
		{
			user = (UserData)obj;
			result = this.mail.equals(user.mail);
		}
		return result;
	}
}