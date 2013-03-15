import org.softwaresynthesis.mytalk.server.abook.IUserData;
import org.softwaresynthesis.mytalk.server.dao.UserDataDAO;


public class Main 
{
	public static void main(String[] args) 
	{
		UserDataDAO dao = new UserDataDAO();
		IUserData user = dao.getByEmail("indirizzo5@dominio.it");
		System.out.println("CIO");
	}
}