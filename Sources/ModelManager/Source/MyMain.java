import org.softwaresynthesis.mytalk.server.dao.UserDataDAO;
import org.softwaresynthesis.mytalk.server.abook.IUserData;
import org.softwaresynthesis.mytalk.server.abook.UserData;


public class MyMain 
{
	public static void main(String[] args)
	{
		boolean result = false;
		UserDataDAO dao = new UserDataDAO();
		IUserData user = new UserData();
		user.setMail("indirizzo5@dominio.it");
		user.setPassword("password");
		user.setName("Maria");
		user.setSurname("Goretti");
		user.setQuestion("Come mi chiamo");
		user.setAnswer("Maria");
		result = dao.insert(user);
		if(result)
		{
			System.out.println("OK");
		}
	}
}