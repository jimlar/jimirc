package jimirc.net.client;

import jimirc.net.IRCConnection;
import jimirc.net.IRCConnectionListener;
import jimirc.net.IRCMessage;
import jimirc.net.util.MessageUtils;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.ArrayList;

/**
 * A connection to an IRC server
 *
 * Note: the connection itself handles pong'ing the pings
 *       (The PING messages are not delivered to the message listener)
 */
public class OutGoingIRCConnection implements IRCConnection {
    private String host;
    private int port;

    private ReceiverThread receiverThread;
    private SenderThread senderThread;
    private List outputQueue;

    private IRCConnectionListener listener;

    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;

    public OutGoingIRCConnection(String host,
                                 int port,
                                 IRCConnectionListener listener,
                                 String nickName,
                                 String userName,
                                 String realName) {
        this(host, port, listener);
        send(new IRCMessage(IRCMessage.COMMAND_NICK, nickName));
        send(new IRCMessage(IRCMessage.COMMAND_USER, new String[]{userName, "0", "*", realName}));
    }

    private OutGoingIRCConnection(String host,
                                  int port,
                                  IRCConnectionListener listener) {
        this.host = host;
        this.port = port;
        this.listener = listener;
        this.outputQueue = new ArrayList();
        this.senderThread = new SenderThread();
    }

    public void send(IRCMessage message) {
        synchronized(outputQueue) {
            outputQueue.add(message);
            outputQueue.notifyAll();
        }
    }

    public boolean isIncoming() {
        return false;
    }

    private IRCMessage getNextFromSendQueueMessage() {
        synchronized(outputQueue) {
            while (outputQueue.isEmpty()) {
                try {
                    outputQueue.wait();
                } catch (InterruptedException e) {
                }
            }
            return (IRCMessage) outputQueue.remove(0);
        }
    }

    private class SenderThread extends Thread {
        public SenderThread() {
            this.start();
        }

        public void run() {
            try {
                OutGoingIRCConnection.this.socket = new Socket(host, port);
                OutGoingIRCConnection.this.in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "ascii"));
                OutGoingIRCConnection.this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "ascii"));
                OutGoingIRCConnection.this.listener.connected(OutGoingIRCConnection.this);
                OutGoingIRCConnection.this.receiverThread = new ReceiverThread();

                while (true) {
                    IRCMessage message = getNextFromSendQueueMessage();

                    if (message != null) {
                        try {
                            out.write(message + "\r\n");
                            out.flush();
                        } catch (IOException e) {
                            listener.connectionFailed(OutGoingIRCConnection.this, e);
                        }
                    }
                }
            } catch (Exception e) {
                listener.connectionFailed(OutGoingIRCConnection.this, e);
            }

            /* Close connection */
            try {
                in.close();
            } catch (Exception e) {
            }
            try {
                out.close();
            } catch (Exception e) {
            }
            try {
                socket.close();
            } catch (Exception e) {
            }
        }
    }

    private class ReceiverThread extends Thread {
        public ReceiverThread() {
            this.start();
        }

        public void run() {

            try {
                while (true) {
                    String line = in.readLine();
                    IRCMessage message = MessageUtils.getInstance().parseMessage(line);

                    if (message != null) {
                        reportMessage(message);
                    } else {
                        listener.connectionFailed(OutGoingIRCConnection.this, new IOException("Got bad message: " + line));
                        break;
                    }
                }
            } catch (Exception e) {
                listener.connectionFailed(OutGoingIRCConnection.this, e);
            }

            /* Close connection */
            try {
                in.close();
            } catch (Exception e) {
            }
            try {
                out.close();
            } catch (Exception e) {
            }
            try {
                socket.close();
            } catch (Exception e) {
            }
        }

        private void reportMessage(IRCMessage message) throws IOException {

            if (message.getMessageId() == IRCMessage.COMMAND_PING) {
                send(new IRCMessage(IRCMessage.COMMAND_PONG, message.getParameters()[0]));
            } else {
                if (message.isCommand()) {
                    listener.commandReceived(OutGoingIRCConnection.this, message);
                } else if (message.isReply()) {
                    listener.replyReceived(OutGoingIRCConnection.this, message);
                } else if (message.isError()) {
                    listener.errorReceived(OutGoingIRCConnection.this, message);
                }
            }
        }
    }
}
