package jimirc.net.server;

import jimirc.net.IRCConnectionListener;

import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.net.InetSocketAddress;
import java.io.IOException;

class IRCServerAcceptor extends Thread {
    private int port;
    private IRCConnectionListener listener;
    private ConnectionManager connectionManager;
    private ServerSocketChannel serverSocketChannel;

    public IRCServerAcceptor(int port,
                             IRCConnectionListener listener,
                             ConnectionManager connectionManager)
            throws IOException
    {
        this.port = port;
        this.listener = listener;
        this.connectionManager = connectionManager;
        this.serverSocketChannel = ServerSocketChannel.open();
        this.serverSocketChannel.configureBlocking(true);
        this.serverSocketChannel.socket().bind(new InetSocketAddress(this.port), 50);
        this.serverSocketChannel.configureBlocking(false);
    }

    public void run() {

        while (true) {
            try {
                SocketChannel channel = serverSocketChannel.accept();
                if (channel != null) {
                    IncomingIRCConnection connection = new IncomingIRCConnection(channel, listener);
                    System.out.println("Got new connection " + connection);
                    connectionManager.addConnection(connection);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
