package jimirc.net.server;

import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.net.InetSocketAddress;
import java.io.IOException;

class IRCServerAcceptor extends Thread {
    private int port;
    private ServerSocketChannel serverSocketChannel;

    public IRCServerAcceptor(int port) throws IOException {
        this.port = port;
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
                    IncomingIRCConnection connection = new IncomingIRCConnection(channel);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
