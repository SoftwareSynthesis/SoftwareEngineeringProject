package org.softwaresynthesis.mytalk.server.authentication;

/**
 * Strategia di crittografia adottata dal sistema
 * mytalk
 * 
 * @author 	Andrea Meneghinello
 * @version	%I%, %G%
 */
public interface ISecurityStrategy 
{
	/**
	 * Codifica un testo in chiaro
	 * 
	 * @author	Andrea Meneghinello
	 * @param 	plainText	{@link String} testo in chiaro
	 * 						da codificare
	 * @return	{@link String} con il testo codificato
	 * @throws 	{@link Exception} in caso di fallimento della codifica
	 */
	public String encode(String plainText) throws Exception;
	
	/**
	 * Decodifica un testo codificato tramite questa
	 * strategia
	 * 
	 * @author	Andrea Meneghinello
	 * @param 	encodedTest	{@link String} testo codificato
	 * 						da decodificare
	 * @return	{@link String} con il testo decodificato
	 * @throws	{@link Exception} in caso di fallimento della decofica
	 */
	public String decode(String encodedTest) throws Exception;
}