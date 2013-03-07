import org.softwaresynthesis.mytalk.server.authentication.*;

public class MyMain
{
	public static void main(String[] args)
	{
		try
		{
			AESAlgorithm aes = new AESAlgorithm();
			String cr = aes.encrypt("ciao");
			System.out.println(cr);
			System.out.println(cr.length());
		}
		catch (Exception ex) {}
	}
}
