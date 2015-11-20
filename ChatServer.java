import java.util.*;

/**
 * <b> CS 180 - Project 4 - Chat Server Skeleton </b>
 * <p>
 * 
 * This is the skeleton code for the ChatServer Class. This is a private chat
 * server for you and your friends to communicate.
 * 
 * @author (name) <(username@purdue.edu)>
 * @author (name) <(username@purdue.edu)>
 *
 * @lab (Your Lab Section)
 * 
 * @version (Today's Date)
 *
 */
public class ChatServer {
	
	public ChatServer(User[] users, int maxMessages) {
		// TODO: Replace the following code with the actual code
		
	}

	/**
	 * Usernames and passwords can only contain alphanumerical values [A-Za-z0-9].
	 * Usernames must be between 1 and 20 characters in length (inclusive).
	 * Password must be between 4 and 40 characters in length (inclusive).
	 * @param args
	 * @return
	 */
	public String addUser(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i] == null) {
                return MessageFactory.makeErrorMessage(MessageFactory.INVALID_VALUE_ERROR);
            }
        }
        if (args[2].matches("[A-Za-z0-9]+") && args[3].matches("[A-Za-z0-9]+")) {
            if (args[2].length() > 1 && args[2].length() < 20) {
                if (args[3].length() > 4 && args[3].length() < 40) {
                    SessionCookie sc = new SessionCookie(Long.parseLong(args[1]));
                    new User(args[2], args[3], sc);
                    return "SUCCESS\r\n";
                }
            }
        }
        return MessageFactory.makeErrorMessage(MessageFactory.USERNAME_LOOKUP_ERROR);
    }

	/**
	 * the user must already have been created earlier through addUser
	 * the given user shouldn't already be authenticated (the SessionCookie associated should be null)
	 * the password must be correct
	 * If every condition is met, then the method generates a new SessionCookie for the user to indicate that she is
	 * now connected.
	 * @param args
	 * @return
	 */
	public String userLogin(String[] args) {

	}

	/**
	 * The name variable is the username of the User sending the message.
	 * For the request to succeed, the message should contain at least 1 character after removing leading and trailing
	 * white spaces from the message.
	 * Messages have no limit on their length.
	 * The username of the poster should be displayed first, followed by a colon, a space, and then the message.
	 * For example, if my username is cs180 and I posted the message “Hello, World” then the server should store
	 * the message as:
	 * "cs180: Hello, World!"
	 * @param args
	 * @param name
	 * @return
	 */
	public String postMessage(String[] args, String name) {

	}

	/**
	 * For the request to succeed, the number of messages requested must be >= 1, otherwise an
	 * INVALID_VALUE_ERROR (error #24) should be returned.
	 * The number of messages required can be higher than the number of available messages,
	 * the function returns as many as possible. It can also return 0 messages if none are available (SUCCESS\r\n).
	 * Messages should be listed in chronological order with the oldest messages at the beginning.
	 * For example, if my username was cs180 and I posted the messages “Hello, World” and then “What's up?”,
	 * the ASCII server response would look like this:
	 * "SUCCESS\t0000) cs180: Hello, World!\t0001) cs180: What's up?\r\n"
	 * @param args
	 * @return
	 */
	public String getMessages(String[] args) {

	}


	/**
	 * This method begins server execution.
	 */
	public void run() {
		boolean verbose = false;
		System.out.printf("The VERBOSE option is off.\n\n");
		Scanner in = new Scanner(System.in);

		while (true) {
			System.out.printf("Input Server Request: ");
			String command = in.nextLine();

			// this allows students to manually place "\r\n" at end of command
			// in prompt
			command = replaceEscapeChars(command);

			if (command.startsWith("kill"))
				break;

			if (command.startsWith("verbose")) {
				verbose = !verbose;
				System.out.printf("VERBOSE has been turned %s.\n\n", verbose ? "on" : "off");
				continue;
			}

			String response = null;
			try {
				response = parseRequest(command);
			} catch (Exception ex) {
				response = MessageFactory.makeErrorMessage(MessageFactory.UNKNOWN_ERROR,
						String.format("An exception of %s occurred.", ex.getMessage()));
			}

			// change the formatting of the server response so it prints well on
			// the terminal (for testing purposes only)
			if (response.startsWith("SUCCESS\t"))
				response = response.replace("\t", "\n");

			// print the server response
			if (verbose)
				System.out.printf("response:\n");
			System.out.printf("\"%s\"\n\n", response);
		}

		in.close();
	}

	/**
	 * Replaces "poorly formatted" escape characters with their proper values.
	 * For some terminals, when escaped characters are entered, the terminal
	 * includes the "\" as a character instead of entering the escape character.
	 * This function replaces the incorrectly inputed characters with their
	 * proper escaped characters.
	 * 
	 * @param str
	 *            - the string to be edited
	 * @return the properly escaped string
	 */
	private static String replaceEscapeChars(String str) {
		str = str.replace("\\r", "\r");
		str = str.replace("\\n", "\n");
		str = str.replace("\\t", "\t");

		return str;
	}

	/**
	 * Determines which client command the request is using and calls the
	 * function associated with that command.
	 * 
	 * @param request
	 *            - the full line of the client request (CRLF included)
	 * @return the server response
	 */
	public String parseRequest(String request) {
		String[] parts = request.split("\t");
        	long sessionCookie = Long.parseLong(parts[1]);
        	SessionCookie sc = new SessionCookie(sessionCookie);
        	switch (parts[0]) {
            case "ADD-USER":
                if (parts.length != 4) {
                    return MessageFactory.makeErrorMessage(MessageFactory.INVALID_VALUE_ERROR);
                } else if (parts[1] == null) {
                    return MessageFactory.makeErrorMessage(MessageFactory.AUTHENTICATION_ERROR);
                } else if (sc.hasTimedOut()) {
                    parts[1] = null;
                    return MessageFactory.makeErrorMessage(MessageFactory.COOKIE_TIMEOUT_ERROR);
                } else {
                    return this.addUser(parts);
                }
            case "USER-LOGIN":
                if (parts.length != 3) {
                    return MessageFactory.makeErrorMessage(MessageFactory.INVALID_VALUE_ERROR);
                } else {
                    return this.userLogin(parts);
                }
            case "POST-MESSAGE":
                if (parts.length != 3) {
                    return MessageFactory.makeErrorMessage(MessageFactory.INVALID_VALUE_ERROR);
                } else if (parts[0] == null) {
                    return MessageFactory.makeErrorMessage(MessageFactory.AUTHENTICATION_ERROR);
                } else if (sc.hasTimedOut()) {
                    parts[1] = null;
                    return MessageFactory.makeErrorMessage(MessageFactory.COOKIE_TIMEOUT_ERROR);
                } else {
                    return this.postMessage(parts);
                }
            case "GET-MESSAGES":
                if (parts.length != 3) {
                    return MessageFactory.makeErrorMessage(MessageFactory.INVALID_VALUE_ERROR);
                } else if (parts[0] == null) {
                    return MessageFactory.makeErrorMessage(MessageFactory.AUTHENTICATION_ERROR);
                } else if (sc.hasTimedOut()) {
                    parts[1] = null;
                    return MessageFactory.makeErrorMessage(MessageFactory.COOKIE_TIMEOUT_ERROR);
                } else {
                    return this.getMessages(parts);
                }
            default:
                return MessageFactory.makeErrorMessage(MessageFactory.UNKNOWN_COMMAND_ERROR);
        }
}
