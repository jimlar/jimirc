package jimirc.net.server;

import jimirc.net.IRCConnectionListener;

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

    public IRCServer(int port, IRCConnectionListener connectionListener) {
        this.port = port;
        this.connectionListener = connectionListener;
    }

    /**
     * Start server
     */
    public void start() throws IOException {
        this.acceptor = new IRCServerAcceptor(port);
        this.acceptor.start();
    }

    public static void main(String args[]) throws Exception {
        IRCServer server = new IRCServer(6667, null);
        server.start();
    }
}
