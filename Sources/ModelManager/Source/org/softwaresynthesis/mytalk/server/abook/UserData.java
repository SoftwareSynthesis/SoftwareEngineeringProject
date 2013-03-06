package org.softwaresynthesis.mytalk.server.abook;

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
	private String picturePath;
	private State state;

	@Override
	public Long getId() 
	{
		return this.id;
	}
	
	/**
	 * Permette alle sottoclassi di settare un id
	 * per lo UserData
	 * 
	 * @author	Andrea Meneghinello
	 * @version	%I%, %G%
	 * @param 	id	Nuovo id da assegnare all'utente
	 */
	protected void setId(Long id)
	{
		this.id = id;
	}

	@Override
	public String getMail() 
	{
		return this.mail;
	}

	@Override
	public void setMail(String mail) 
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
		return this.picturePath;
	}

	@Override
	public void setPicturePath(String path) 
	{
		this.picturePath = path;
	}
	
	@Override
	public State getState()
	{
		return this.state;
	}
	
	@Override
	public void setState(State state)
	{
		this.state = state;
	}
}