package jimirc;

import java.io.*;
import jimirc.net.*;

public class Client implements IRCMessageListener {
    private IRCConnection connection;

    public Client(String host, int port) {
	try {
	    this.connection = new IRCConnection(host, port, this);
	    connection.send(new IRCMessage(IRCMessage.COMMAND_USER, new String[] { "jimmy", "0", "*", "Jimmy Larsson" }));
	    connection.send(new IRCMessage(IRCMessage.COMMAND_NICK, "jim"));
	} catch (IOException e) {
	    e.printStackTrace();
	}
        System.out.println("Connected to " + host + ":" + port);
    }

    public void messageReceived(IRCMessage message) {
	System.out.println("RECV--->" + message);

	try {
	    if (message.getMessageId() == IRCMessage.REPLY_WELCOME) {
		connection.send(new IRCMessage(IRCMessage.COMMAND_JOIN, "#test"));
	    }

	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    public void connectionFailed(Exception e) {
	e.printStackTrace();
    }

    public static void main(String args[]) throws Exception {
	Client client = new Client("localhost", 6667);

	BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	String line;
	while ((line = reader.readLine()) != null) {
	    client.connection.send(new IRCMessage(IRCMessage.COMMAND_PRIVMSG, new String[] { "#test", line}));
	}

    }
}
