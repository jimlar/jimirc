package jimirc;

import jimirc.net.*;

import java.io.*;

public class Client implements IRCMessageListener {
    private OutGoingIRCConnection connection;

    public Client(String host, int port) {
        try {
            this.connection = new OutGoingIRCConnection(host, port, this, "JimIRC", "jimmy", "Jimmy Larsson");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void commandReceived(IRCConnection connection, IRCMessage message) {
        System.out.println("CMD--->" + message);
    }

    public void replyReceived(IRCConnection connection, IRCMessage message) {
        System.out.println("***" + message);
        if (message.getMessageId() == IRCMessage.REPLY_WELCOME) {
            connection.send(new IRCMessage(IRCMessage.COMMAND_JOIN, "#test"));
        }
    }

    public void errorReceived(IRCConnection connection, IRCMessage message) {
        System.out.println("ERROR: " + message);
    }

    public void connectionFailed(IRCConnection connection, Exception e) {
        e.printStackTrace();
    }

    public static void main(String args[]) throws Exception {
        Client client = new Client("192.168.1.1", 6667);

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = reader.readLine()) != null) {
            client.connection.send(new IRCMessage(IRCMessage.COMMAND_PRIVMSG, new String[]{"#test", line}));
        }
    }
}
