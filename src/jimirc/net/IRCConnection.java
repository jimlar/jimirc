package jimirc.net;

/**
 * An IRC connection, one of the following
 * 1. A clients connection to a server
 * 2. A servers connection to a client
 * 3. A servers connection to another server
 */
public interface IRCConnection {

    void send(IRCMessage message);
}
