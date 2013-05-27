package org.softwaresynthesis.mytalk.server.abook;

import java.util.HashSet;
import java.util.Set;
import org.softwaresynthesis.mytalk.server.call.ICallList;
import org.softwaresynthesis.mytalk.server.message.IMessage;

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
	private Set<IAddressBookEntry> addressBook;
	private Set<ICallList> calls;
	private Set<IMessage> messages;
	
	/**
	 * Costruisce una nuova istanza priva
	 * di valori
	 */
	public UserData()
	{
		this(-1L);
	}
	
	/**
	 * Costruisce una nuova istanza assegnandogli
	 * un identificativo
	 * 
	 * @param 	identifier	{@link Long} identificativo
	 * 						associato
	 */
	public UserData(Long identifier)
	{
		this.id = identifier;
		this.addressBook = new HashSet<IAddressBookEntry>();
		this.calls = new HashSet<ICallList>();
		this.messages = new HashSet<IMessage>();
	}
	
	@Override
	public Long getId() 
	{
		return this.id;
	}
	
	@Override
	public void setId(Long id)
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
	public String getPath()
	{
		return this.path;
	}

	@Override
	public void setPath(String picturePath) 
	{
		this.path = picturePath;
	}

	@Override
	public Set<IAddressBookEntry> getAddressBook() 
	{
		return this.addressBook;
	}

	@Override
	public void setAddressBook(Set<IAddressBookEntry> addressBook) 
	{
		this.addressBook = addressBook;
	}

	@Override
	public Set<ICallList> getCalls() 
	{
		return this.calls;
	}

	@Override
	public void setCalls(Set<ICallList> callList) 
	{
		this.calls = callList;
	}

	@Override
	public Set<IMessage> getMessages()
	{
		return this.messages;
	}

	@Override
	public void setMessages(Set<IMessage> messages) 
	{
		this.messages = messages;
	}

	@Override
	public boolean addAddressBookEntry(IAddressBookEntry entry) 
	{
		boolean result = false;
		entry.setOwner(this);
		result = this.addressBook.add(entry);
		return result;
	}

	@Override
	public boolean addCall(ICallList call) 
	{
		boolean result = false;
		call.setUser(this);
		result = this.calls.add(call);
		return result;
	}

	@Override
	public boolean addMessage(IMessage message)
	{
		boolean result = false;
		message.setReceiver(this);
		result = this.messages.add(message);
		return result;
	}

	@Override
	public boolean removeAddressBookEntry(IAddressBookEntry entry) 
	{
		boolean result = this.addressBook.remove(entry);
		return result;
	}

	@Override
	public boolean removeCall(ICallList call) 
	{
		boolean result = this.calls.remove(call);
		return result;
	}

	@Override
	public boolean removeMessage(IMessage message) 
	{
		boolean result = this.messages.remove(message);
		return result;
	}
	
	/**
	 * Determina se due istanze rappresentano lo stesso
	 * oggetto UserData
	 * 
	 * @param	obj	{@link Object} istanza da verificare
	 * @return	true se le due istanze rappresentano lo
	 * 			stesso oggetto, false altrimenti
	 */
	@Override
	public boolean equals(Object obj)
	{
		boolean result = false;
		IUserData toCompare = null;
		String toCompareMail = "";
		if (obj instanceof UserData)
		{
			toCompare = (UserData)obj;
			toCompareMail = toCompare.getMail();
			if (this.mail.equals(toCompareMail))
			{
				result = true;
			}
		}
		return result;
	}
}