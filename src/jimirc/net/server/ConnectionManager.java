package jimirc.net.server;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.io.IOException;

/**
 * This class manages/drives the async connections (channels)
 */
class ConnectionManager extends Thread {
    private List connections;
    private Selector selector;

    public ConnectionManager() throws IOException {
        this.connections = new ArrayList();
        this.selector = Selector.open();
        this.start();
    }

    public void addConnection(IncomingIRCConnection connection) throws IOException {
        connections.add(connection);
        connection.getChannel().register(selector,
                                         connection.getChannel().validOps(),
                                         connection);
        selector.wakeup();
    }

    public void removeConnection(IncomingIRCConnection connection) {
        SelectionKey key = connection.getChannel().keyFor(selector);
        if (key != null) {
            key.cancel();
        }
        selector.wakeup();
    }

    public void run() {
        while (true) {
            try {
                List readyConnections = waitForReadyConnections();
                for (Iterator i = readyConnections.iterator(); i.hasNext();) {
                    IncomingIRCConnection connection = (IncomingIRCConnection) i.next();
                    connection.process();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Block until at least one connection is ready for i/o
     */
    private List waitForReadyConnections() throws IOException {
        List result = new ArrayList();
        selector.select();
        Set selectedKeys = selector.selectedKeys();
        for (Iterator i = selectedKeys.iterator(); i.hasNext();) {
            SelectionKey key = (SelectionKey) i.next();
            result.add(key.attachment());
        }
        selectedKeys.clear();
        return result;
    }
}
