package org.softwaresynthesis.mytalk.server.message;

/**
 * Rappresentazione di un messaggio in segreteria del sistema mytalk
 *
 * @author 	Marco Schivo
 * @version	1.0
 */
public class Message implements IMessage 
{
	private Long id;
	private Long sender;
	private Long receiver;
	private Long newer;
	private Long video;
	private Date date;
	
	/**
	 * Crea un messaggio privo di dati
	 */
	public Message() {}
	
	/**
	 * Restituisce l'istanza sottoforma di stringa
	 * JSON in modo che possa essere utilizzata
	 * nella parte client
	 * 
	 * @return	{@link String} in formato JSON
	 * 			dell'istanza
	 */
	@Override
	public String toJson() 
	{
		//TODO da implementare?
	}

	/**
	 * Imposta l'identificativo del messaggio
	 * 
	 * @param id identificativo da impostare
	 */
	public void setId(Long id)
	{
		this.id = id;
	}
	
	/**
	 * Restituisce l'identificativo univoco
	 * del messaggio di segreteria
	 * 
	 * @return	identificativo del messaggio di
	 * 			tipo {@link Long}
	 */
	public Long getId()
	{
		return this.id;
	}
	
	/**
	 * Restituisce l'Id dell'utente che ha
	 * inviato il messaggio di segreteria
	 * 
	 * @return	{@link Long} rappresentante
	 * 			l'id dell'utente mittente
	 */
	public Long getSender()
	{
		return this.sender;
	}
	
	/**
	 * Imposta l'id dell'utente mittente
	 * del messaggio in segreteria 
	 * 
	 * @param 	id	{@link Long}
	 * 					id dell'utente
	 * 					mittente
	 */
	public void setSender(Long id)
	{
		this.sender = id;
	}
	
		/**
	 * Restituisce l'Id dell'utente destinatario
	 * del messaggio di segreteria
	 * 
	 * @return	{@link Long} rappresentante
	 * 			l'id dell'utente destinatario
	 */
	public Long getReceiver()
	{
		return this.receiver;
	}
	
	/**
	 * Imposta l'id dell'utente destinatario
	 * del messaggio in segreteria 
	 * 
	 * @param 	id	{@link Long}
	 * 					id dell'utente
	 * 					destinatario
	 */
	public void setReceiver(Long id)
	{
		this.receiver = id;
	}
	
	/**
	 * Restituisce 1 se il messaggio non e'
	 * ancora stato letto, 0 altrimenti
	 * 
	 * @return	{@link Long} che rappresenta se il messaggio e' nuovo o meno
	 */
	public Long getNewer()
	{
		return this.newer;
	}
	
	/**
	 * Imposta lo stato del messagggio, 
	 * 1 se e' nuovo, 0 altrimenti
	 * 
	 * @param 	newer	{@link Long} con lo stato
	 * 						del messaggio (1 se nuovo, 0 altrimenti)
	 */
	public void setNewer(Long newer)
	{
		this.newer = newer;
	}
	
	/**
	 * Restituisce 1 se il messaggio e' di tipo
	 * video, 0 se solo audio
	 * 
	 * @return	{@link Long} che rappresenta il
	 * 			tipo di messaggio
	 */
	public Long getVideo()
	{
		return this.video;
	}
	
	/**
	 * Imposta il tipo del messaggio in segreteria
	 * 
	 * @param 	video	{@link Long} che identifica se il
	 * 					messaggio e' di tipo video (1)
	 * 					o solo audio (0)
	 */
	public void setVideo(Long video)
	{
		this.video = video;
	}
	
	/**
	 * Restituisce la data del messaggio in segreteria
	 * 
	 * @return	{@link Date} con la data del messaggio di segreteria
	 */
	public Date getDate()
	{
		return this.date;
	}
	
	/**
	 * Imposta la data del messaggio
	 * 
	 * @param 	date {@link Date} con la data del messaggio di segreteria
	 */
	public void setDate(Date date)
	{
		this.date= date;
	}
}