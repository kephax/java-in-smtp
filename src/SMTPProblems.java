import java.util.ArrayList;

public class SMTPProblems
{
	private static ArrayList<String> problems = new ArrayList<String>();
	private static String server = "smtp.example.com";
	public SMTPProblems()
	{

	}

	public static ArrayList<String> problem1()
	{
		start(problems);

		problems.add("MAIL FROM:<bob@example.org>");
		problems.add("RCPT TO:<alice@example.com>");
		problems.add("RCPT TO:<theboss@example.com>");
		problems.add("QUIT");

		return problems;
	}

	public static ArrayList<String> problem2()
	{
		start(problems);

		problems.add("MAIL FROM:<bob@example.org>");
		problems.add("RCPT TO:<alice@example.com>");
		problems.add("RCPT TO:<theboss@example.com>");
		problems.add("DATA");
		problems.add("Test1");
		problems.add("Test2");
		problems.add("Test3");
		problems.add("Test4");
		problems.add(".");
		problems.add("QUIT");

		return problems;
	}

	public static ArrayList<String> problem3()
	{
		start(problems);

		problems.add("MAIL FROM:<bob@example.org>");
		problems.add("RCPT TO:<alice@example.com>");
		problems.add("RCPT TO:<theboss@example.com>");
		problems.add("DATA");
		problems.add("From: \"Bob Example\" <bob@example.org>");
		problems.add("To: \"Alice Example\" <alice@example.com>");
		problems.add("Cc: theboss@example.com");
		problems.add("Date: Tue, 15 Jan 2008 16:02:43 -0500");
		problems.add("Subject: Test message");
		problems.add("");
		problems.add("Hello Alice.");
		problems.add("This is a test message with 5 header fields and 4 lines in the message body.");
		problems.add("Your friend,");
		problems.add("Bob");
		problems.add(".");
		problems.add("QUIT");

		return problems;
	}

	public static ArrayList<String> problem4()
	{
		start(problems);
		problems.add("MAIL FROM:<bob@example.org>");
		problems.add("RCPT TO:<alice@example.com>");
		problems.add("RCPT TO:<theboss@example.com>");
		problems.add("DATA");
		problems.add("From: \"Bob Example\" <bob@example.org>");
		problems.add("To: \"Alice Example\" <alice@example.com>");
		problems.add("Cc: theboss@example.com");
		problems.add("Date: Tue, 15 Jan 2008 16:02:43 -0500");
		problems.add("Subject: Test message");
		problems.add("");
		problems.add("Hello Alice.");
		problems.add("This is a test message with 5 header fields and 4 lines in the message body.");
		problems.add("Your friend,");
		problems.add("Bob");
		problems.add(".");
		problems.add("RSET");
		problems.add("MAIL FROM:<bob@example.org>");
		problems.add("RCPT TO:<alice@example.com>");
		problems.add("RCPT TO:<theboss@example.com>");
		problems.add("DATA");
		problems.add("From: \"Bob Example\" <bob@example.org>");
		problems.add("To: \"Alice Example\" <alice@example.com>");
		problems.add("Cc: theboss@example.com");
		problems.add("Date: Tue, 15 Jan 2008 16:02:43 -0500");
		problems.add("Subject: Test message");
		problems.add("");
		problems.add("Hello Alice.");
		problems.add("This is a test message with 5 header fields and 4 lines in the message body.");
		problems.add("Your friend,");
		problems.add("Bob");
		problems.add(".");
		problems.add("QUIT");

		return problems;
	}

	private static void start(ArrayList<String> problem)
	{
		problem.add("HELO " + server);
	}
}
