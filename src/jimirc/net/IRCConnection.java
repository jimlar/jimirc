package jimirc.net;

import java.io.*;
import java.net.*;

/**
 * A connection to an IRC server
 * 
 */
public class IRCConnection {
    private InputReader inputReader;
    private IRCMessageListener listener;

    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;

    public IRCConnection(String host, int port, IRCMessageListener listener) throws IOException {
	this.listener = listener;
	this.socket = new Socket(host, port);
	this.in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "ascii"));
	this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "ascii"));
	this.inputReader = new InputReader();
    }

    public void send(IRCMessage message) throws IOException {
	out.write(message + "\r\n");
	out.flush();
	System.out.println("SEND--->" + message);
    }

    private class InputReader extends Thread {
	public InputReader() {
	    this.start();
	}

	public void run() {

	    try {
		while (true) {
		    String line = in.readLine();
		    IRCMessage message = MessageDecoder.parseMessage(line);

		    if (message != null) {
			listener.messageReceived(message);
		    } else {
			System.err.println("Got bad message: " + line);
		    }
		}
	    } catch (Exception e) {
		listener.connectionFailed(e);
	    }

	    /* Close connection */
	    try {
		in.close();
	    } catch (Exception e) {}
	    try {
		out.close();
	    } catch (Exception e) {}
	    try {
		socket.close();
	    } catch (Exception e) {}
	}
    }
}
