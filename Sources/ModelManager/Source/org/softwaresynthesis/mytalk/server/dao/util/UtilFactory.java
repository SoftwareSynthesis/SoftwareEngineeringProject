package org.softwaresynthesis.mytalk.server.dao.util;

import org.softwaresynthesis.mytalk.server.dao.ISessionManager;

/**
 * Ha il compito di creare gli oggetti adatti
 * per la comunicazione con la base di dati
 * del sistema MyTalk
 * 
 * @author 	Andrea Meneghinello
 * @version	3.0
 */
public class UtilFactory 
{
	/**
	 * Ritorna una utility per la cancellazione
	 * di un record
	 * 
	 * @param 	manager	{@link ISessionManager} gestore della
	 * 					sessione di comunicazione
	 * @return {@link ModifyUtil} utility per la cancellazione
	 */
	public ModifyUtil getDeleteUtil(ISessionManager manager)
	{
		return new DeleteUtil(manager);
	}
	
	/**
	 * Ritorna una utility per l'inserimento
	 * di un record
	 * 
	 * @param 	manager	{@link ISessionManager} gestore della
	 * 					sessione di comunicazione
	 * @return {@link ModifyUtil} utility per l'inserimento
	 */
	public ModifyUtil getInsertUtil(ISessionManager manager)
	{
		return new InsertUtil(manager);
	}
	
	/**
	 * Ritorna una utility per l'aggiornamento
	 * di un record
	 * 
	 * @param 	manager	{@link ISessionManager} gestore della
	 * 					sessione di comunicazione
	 * @return	{@link ModifyUtil} utility per l'aggiornamento
	 */
	public ModifyUtil getUpdateUtil(ISessionManager manager)
	{
		return new UpdateUtil(manager);
	}
	
	/**
	 * Ritorna una utility per inizializzare correttamente
	 * oggetti rappresentanti una chiamata
	 * 
	 * @param 	manager	{@link ISessionManager} gestore della
	 * 					sessione di comunicazione
	 * @return	{@link GetUtil} utility per l'inizializzazione
	 * 			delle chiamate
	 */
	public GetUtil getCallUtil(ISessionManager manager)
	{
		return new GetCallUtil(manager);
	}
	
	/**
	 * Ritorna una utility per inizializzare correttamente
	 * oggetti rappresentanti gruppi di utenti
	 * 
	 * @param 	manager	{@link ISessionManager} gestore della
	 * 					sessione di comunicazione
	 * @return	{@link GetUtil} utility per l'inizializzazione
	 * 			dei gruppi
	 */
	public GetUtil getGroupUtil(ISessionManager manager)
	{
		return new GetGroupUtil(manager);
	}
	
	/**
	 * Ritorna una utility per inizializzare correttamente
	 * oggetti rappresentanti utenti del sistama MyTalk
	 * 
	 * @param 	manager	{@link ISessionManager} gestore della
	 * 					sessione di comunicazione
	 * @return	{@link GetUtil} utility per l'inizializzazione
	 * 			degli utenti
	 */
	public GetUtil getUserDataUtil(ISessionManager manager)
	{
		return new GetUserDataUtil(manager);
	}
	
	/**
	 * Ritorna una utility per inizializzare correttamente
	 * oggetti che non hanno bisogno di essere inizializzati
	 * 
	 * @param 	manager	{@link ISessionManager} gestore della
	 * 					sessione di comunicazione
	 * @return	{@link GetUtil} utility per l'inizializzazione
	 * 			di oggetti che non hanno bisogno di essere 
	 * 			inizializzati
	 */
	public GetUtil getGenericUtil(ISessionManager manager)
	{
		return new NotInitialize(manager);
	}
}
