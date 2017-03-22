import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class SMTPServer
{

	private static final String serverName = "smtp.example.com";

	private DataOutputStream outToClient;
	private String lastInsertedCommand, client = null, sender;
	private ArrayList<String> receivers = new ArrayList<String>(), message = new ArrayList<String>();

	public SMTPServer()
	{

	}

	public void start()
	{
		System.out.println("220 " + serverName);
		String clientSentence;

		try
		{
			ServerSocket welcomeSocket = new ServerSocket(6789);
			Socket connectionSocket = welcomeSocket.accept();
			while (true)
			{

				BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
				outToClient = new DataOutputStream(connectionSocket.getOutputStream());
				clientSentence = inFromClient.readLine();

				read(clientSentence);
			}
		} catch (Exception e)
		{

		}
	}

	private void read(String s)
	{ // Read and send reaction
		String code = getCommand(s);
		if (code.equals("HELO"))
		{
			out(helo(s));
			lastInsertedCommand = code;
		}
    else if (code.equals("EHLO"))
		{
			out(ehlo(s));
			lastInsertedCommand = code;
		}
    else if (code.equals("RCPT"))
		{
			out(rcpt(s));
			lastInsertedCommand = code;
		}
    else if (code.equals("DATA"))
		{
			out(data(s));
			lastInsertedCommand = code;
		}
    else if (code.equals("MAIL"))
		{
			out(mail(s));
			lastInsertedCommand = code;
		}
    else if (code.equals("RSET"))
		{
			out(rset(s));
			lastInsertedCommand = code;
		}
    else if (code.equals("NOOP"))
		{
			out(noop(s));
			lastInsertedCommand = code;
		}
    else if (code.equals("QUIT"))
		{
			out(quit(s));
			lastInsertedCommand = code;
		}
    else
		{
			if (lastInsertedCommand.equals("DATA"))
			{
				if (s.equals("."))
				{
					lastInsertedCommand = null;
					out("250 Mail queued for delivery.");
				}
        else
				{
					data(s);
					out("250 Continue"); // ?
				}
			}
      else
			{
				out("550");
			}
		}
	}
  
	private String helo(String s)
	{
		client = getString(s);
		return "250 Hello " + client + ", I am glad to meet you";
	}

	private String ehlo(String s)
	{
		return helo(s);
	}

	private String rcpt(String s)
	{
		if (isClientKnown())
		{
			if (s.substring(0, 8).equals("RCPT TO:"))
			{
				receivers.add(getMailFromInput(s));

				return "250 Ok";
			}
			return "550 No such user";
		}
		return "550";
	}

	private String data(String s)
	{
		if (isClientKnown())
		{
			if (!s.equals("DATA"))
				message.add(s);
			return "354 End data with <CR><LF>.<CR><LF>";

		}
		return "550";
	}

	private String mail(String s)
	{
		if (isClientKnown() && s.substring(0, 10).equals("MAIL FROM:"))
		{
			sender = getMailFromInput(s);

			return "250 Ok";
		}
		return "550";
	}

	private String rset(String s)
	{
		lastInsertedCommand = "";
		client = "";
		sender = "";

		receivers.clear();
		message.clear();

		return "250 Ok";
	}

	private String noop(String s)
	{
		return "250 Ok";
	}

	private String quit(String s)
	{
		saveMail();
		return "221 Goodbye.";
	}

	private void saveMail()
	{
		String save = "";

		save += "Zender: " + sender + "\r\n";
		for (String s : receivers)
		{
			save += "Ontvanger: " + s + "\r\n";
		}

		save += "Bericht: \r\n";
		for (String s : message)
		{
			save += s + "\r\n";
		}

		FileWrite.writetoFile("C:\\Users\\Remy\\Desktop\\mail.txt", save);
	}
	private boolean isClientKnown()
	{
		return client != null;
	}

	private String getCommand(String inputLine)
	{
		if (inputLine.length() < 5)
		{
			return inputLine;
		}
		return inputLine.substring(0, 4);
	}

	private String getString(String inputLine)
	{
		return inputLine.substring(5);
	}

	private String getMailFromInput(String inputLine)
	{
		inputLine = inputLine.replaceAll("(.*)<", "");
		inputLine = inputLine.replaceAll(">(.*)", "");
		return inputLine;
	}

	public void out(String s)
	{
		try
		{
			outToClient.writeBytes(s + '\r' + '\n');
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
