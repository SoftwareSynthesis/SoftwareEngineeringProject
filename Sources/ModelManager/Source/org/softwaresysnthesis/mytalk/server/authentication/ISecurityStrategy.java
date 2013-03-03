package org.softwaresysnthesis.mytalk.server.authentication;

/**
 * Strategia generica di crittografia dei dati
 * 
 * @author 	Andrea Meneghinello
 * @version %I%, %G%
 */
public interface ISecurityStrategy 
{
	/**
	 * Cripta la stringa di testo ricevuta come parametro
	 * 
	 * @author	Andrea Meneghinello
	 * @param 	plainText	testo in chiaro da codificare
	 * @return	testo codificato
	 * @throws 	{@link Exception} se si riscontrano errori durante il crypting
	 */
	public String encrypt(String plainText) throws Exception;
	
	/**
	 * Decripta la stringa di testo ricevuta come parametro
	 * 
	 * @author	Andrea Meneghinello
	 * @param 	encryptedText	testo codificato da decriptare
	 * @return	testo decriptato
	 * @throws	{@link Exception} se si riscontrano errori durante il decrypting
	 */
	public String decrypt(String encryptedText) throws Exception;
}