package jimirc.net.server;

import jimirc.net.IRCConnectionListener;
import jimirc.net.IRCConnection;
import jimirc.net.IRCMessage;

import java.io.IOException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.net.SocketAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * The main IRC server class, instantiate this to start your irc server
 */
public class IRCServer {
    private int port;
    private IRCConnectionListener connectionListener;
    private IRCServerAcceptor acceptor;
    private ConnectionManager connectionManager;

    public IRCServer(int port, IRCConnectionListener connectionListener) throws IOException {
        this.port = port;
        this.connectionListener = connectionListener;
        this.connectionManager = new ConnectionManager();
    }

    /**
     * Start server
     */
    public void start() throws IOException {
        this.acceptor = new IRCServerAcceptor(port, connectionListener, connectionManager);
        this.acceptor.start();
    }

    public static void main(String args[]) throws Exception {
        IRCServer server = new IRCServer(6667, new IRCConnectionListener() {
            public void commandReceived(IRCConnection connection, IRCMessage message) {
                System.out.println("CMD: " + message);
            }

            public void connected(IRCConnection connection) {
                System.out.println("Connected: " + connection);
            }

            public void connectionFailed(IRCConnection connection, Exception e) {
                System.out.println("Connection failed: " + e);
            }

            public void errorReceived(IRCConnection connection, IRCMessage message) {
                System.out.println("ERROR: " + message);
            }

            public void replyReceived(IRCConnection connection, IRCMessage message) {
                System.out.println("REPLY: " + message);
            }
        });
        server.start();
    }
}
