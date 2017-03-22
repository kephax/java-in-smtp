public class Apl
{
	public static void main(String args[])
	{
		SMTPServer server = new SMTPServer();
		server.start();

		SMTPClient client = new SMTPClient();
		client.start();
	}
}
