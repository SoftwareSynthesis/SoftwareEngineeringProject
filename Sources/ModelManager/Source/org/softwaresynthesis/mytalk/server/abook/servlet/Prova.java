package org.softwaresynthesis.mytalk.server.abook.servlet;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;


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
		catch (IOException exception){}
	}
}
