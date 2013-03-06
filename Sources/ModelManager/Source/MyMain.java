import org.softwaresynthesis.mytalk.server.authentication.*;

public class MyMain
{
	public static void main(String[] args)
	{
		try
		{
			AESAlgorithm aes = new AESAlgorithm();
			System.out.println(aes.encrypt("password"));
		}
		catch (Exception ex) {}
	}
}
