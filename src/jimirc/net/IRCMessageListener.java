package jimirc.net;

/**
 * Implemeted to listen for incoming IRC messages on a connection
 */
public interface IRCMessageListener {

    void messageReceived(String message);

    void connectionFailed(Exception e);
}
