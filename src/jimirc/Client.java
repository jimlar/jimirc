package jimirc;

import java.io.*;
import jimirc.net.*;

public class Client implements IRCMessageListener {
    private IRCConnection connection;

    public Client(String host, int port) {
	try {
	    this.connection = new IRCConnection(host, port, this);
	    connection.send("USER jimmy * * :Jimmy Larsson");
	    connection.send("NICK jim");
	} catch (IOException e) {
	    e.printStackTrace();
	}
        System.out.println("Connected to " + host + ":" + port);
    }

    public void messageReceived(IRCMessage message) {
	System.out.println("Got: " + message);
    }

    public void connectionFailed(Exception e) {
	e.printStackTrace();
    }

    public static void main(String args[]) throws Exception {
	Client client = new Client("localhost", 6667);

	BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	String line;
	while ((line = reader.readLine()) != null) {
	    client.connection.send(line);
	}

    }
}
