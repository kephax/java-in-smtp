import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

public class SMTPClient
{
	private Socket clientSocket;
	private DataOutputStream outToServer;
	private BufferedReader inFromServer;

	public SMTPClient()
	{
	}

	public void start()
	{
		System.out.println("Client Started");

		try
		{
			ArrayList<String> problem = SMTPProblems.problem4();

			clientSocket = new Socket("localhost", 6789);
			outToServer = new DataOutputStream(clientSocket.getOutputStream());
			inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

			while (true)
			{
				if (!problem.isEmpty())
				{
					out(problem.get(0));

					String get = get();
					if (!getCommand(get).equals("550"))
					{
						System.out.println("S: " + get);
						problem.remove(0);
					}
				}
			}

		} catch (IOException e)
		{
			e.printStackTrace();
		}

	}
	private void out(String s)
	{
		try
		{
			System.out.println("C: " + s);
			outToServer.writeBytes(s + '\n');
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private String get()
	{
		try
		{
			return inFromServer.readLine();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	private String getCommand(String inputLine)
	{
		if (inputLine.length() < 4)
		{
			return inputLine;
		}
		return inputLine.substring(0, 3);
	}

	public boolean stop()
	{
		System.out.println("Client stopped");
		try
		{
			clientSocket.close();
			return true;
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return false;
	}
}
