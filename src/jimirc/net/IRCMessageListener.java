package jimirc.net;

/**
 * Implemeted to listen for incoming IRC messages on a connection
 */
public interface IRCMessageListener {

    void commandReceived(IRCConnection connection, IRCMessage message);
    void replyReceived(IRCConnection connection, IRCMessage message);
    void errorReceived(IRCConnection connection, IRCMessage message);

    void connectionFailed(IRCConnection connection, Exception e);
}
