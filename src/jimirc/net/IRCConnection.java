package jimirc.net;

/**
 * An IRC connection, one of the following
 * 1. A clients connection to a server
 * 2. A servers connection to a client
 * 3. A servers connection to another server
 */
public interface IRCConnection {

    /**
     * @return true if this connection is incoming
     *         false if its an tougoing connection
     *
     * Note: an outgoing connection is a connection to a server, and incoming
     *       is from a client or another server
     */
    boolean isIncoming();

    /**
     * Send a message to the connection
     */
    void send(IRCMessage message);
}
