package jimirc.net;

/**
 * Implemeted to listen for incoming IRC messages on a connection
 */
public interface IRCMessageListener {

    void messageReceived(IRCMessage message);

    void connectionFailed(Exception e);
}
