package org.softwaresynthesis.mytalk.server.authentication;

/**
 * Rappresenta la strategia di codifica
 * e decodifica dei dati
 * 
 * @author 	Andrea Meneghinello
 * @version	3.0
 */
public interface ISecurityStrategy 
{
	/**
	 * Codifica una stringa di testo
	 * 
	 * @param 	plainText {@link String} testo da codificare
	 * @return	{@link String} con il testo codificato
	 */
	public String encode(String plainText) throws Exception;
	
	/**
	 * Decofica una stringa di testo criptato
	 * 
	 * @param 	encodedText	{@link String} testo codificato
	 * 						da decodificare
	 * @return	{@link String} con il testo decodificato
	 */
	public String decode(String encodedText) throws Exception;
}