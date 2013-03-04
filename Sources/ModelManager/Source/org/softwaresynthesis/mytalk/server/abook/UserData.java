package org.softwaresynthesis.mytalk.server.abook;

import java.util.Observable;

/**
 * Implementazione dell'interfaccia {@link IUserData}
 * @author 	Andrea Meneghinello
 * @version %I%, %G%
 */
public class UserData implements IUserData 
{
	private Long id;
	private String mail;
	private String password;
	private String question;
	private String answer;
	private String name;
	private String surname;
	private String path;
	
	/**
	 * Metodo richiamato quanto un osservato esegue il nofifyObservers()
	 * 
	 * @author 	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @param	user 	{@link IUserData} che ha cambiato stato
	 * @param	state	nuovo stato assunto dallo {@link IUserData}
	 */
	@Override
	public void update(Observable user, Object state) 
	{
		// TODO Auto-generated method stub
	}

	@Override
	public Long getId() 
	{
		return this.id;
	}

	@Override
	public String getEmail() 
	{
		return this.mail;
	}

	@Override
	public void setEmail(String mail) 
	{
		this.mail = mail;
	}

	@Override
	public String getPassword() 
	{
		return this.password;
	}

	@Override
	public void setPassword(String password) 
	{
		this.password = password;
	}

	@Override
	public String getQuestion() 
	{
		return this.question;
	}

	@Override
	public void setQuestion(String question) 
	{
		this.question = question;
	}

	@Override
	public String getAnswer() 
	{
		return this.answer;
	}

	@Override
	public void setAnswer(String answer)
	{
		this.answer = answer;
	}

	@Override
	public String getName() 
	{
		return this.name;
	}

	@Override
	public void setName(String name) 
	{
		this.name = name;
	}

	@Override
	public String getSurname() 
	{
		return this.surname;
	}

	@Override
	public void setSurname(String surname)
	{
		this.surname = surname;
	}

	@Override
	public String getPicturePath() 
	{
		return this.path;
	}

	@Override
	public void setPicturePath(String path) 
	{
		this.path = path;
	}
}