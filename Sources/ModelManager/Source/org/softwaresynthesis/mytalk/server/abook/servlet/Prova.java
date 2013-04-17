package org.softwaresynthesis.mytalk.server.abook.servlet;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;


public class Prova {

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		org.softwaresynthesis.mytalk.server.authentication.AESAlgorithm a = new org.softwaresynthesis.mytalk.server.authentication.AESAlgorithm();
		try
		{
			System.out.println(a.encode("password"));
		}
		catch (Exception e){}
	}
	
	public static void Scrivi(String text)
	{
		try
		{

			FileOutputStream fw = new FileOutputStream ("DEBUG.txt", true);
			PrintStream outFile = new PrintStream (fw);

			outFile.println(text); 

			outFile.close();
		}
		catch (FileNotFoundException exception){}
	}
}
