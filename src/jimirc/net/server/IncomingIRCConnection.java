package jimirc.net.server;

import jimirc.net.IRCConnection;
import jimirc.net.IRCMessage;
import jimirc.net.IRCConnectionListener;
import jimirc.net.util.MessageUtils;

import java.nio.channels.SocketChannel;
import java.nio.ByteBuffer;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;

class IncomingIRCConnection implements IRCConnection {
    private static final byte CRLF_BYTES[] = new byte[] { (byte) '\r', (byte) '\n' };

    private SocketChannel channel;
    private IRCConnectionListener listener;

    private List outputQueue;

    private ByteBuffer outputBuffer;
    private ByteBuffer inputBuffer;

    public IncomingIRCConnection(SocketChannel channel,
                                 IRCConnectionListener listener)
            throws IOException
    {
        this.channel = channel;
        this.listener = listener;
        this.channel.configureBlocking(false);
        this.outputQueue = Collections.synchronizedList(new ArrayList());

        this.inputBuffer = ByteBuffer.allocateDirect(IRCMessage.MAX_BYTE_SIZE);
        this.inputBuffer.clear();
        this.inputBuffer.limit(IRCMessage.MAX_BYTE_SIZE);

        this.outputBuffer = ByteBuffer.allocateDirect(IRCMessage.MAX_BYTE_SIZE);
        this.outputBuffer.clear();
        this.outputBuffer.limit(0);
    }

    public SocketChannel getChannel() {
        return this.channel;
    }

    public boolean isIncoming() {
        return true;
    }

    public void send(IRCMessage message) {
        outputQueue.add(message);
        try {
            processOutput();
        } catch (Exception e) {
            listener.connectionFailed(this, e);
        }
    }

    public void process() {
        try {
            if (channel.isConnectionPending()) {
                channel.finishConnect();
            }

            processOutput();
            processInput();
        } catch (Exception e) {
            listener.connectionFailed(this, e);
        }
    }


    private void processInput() throws IOException {
        if (inputBuffer.hasRemaining()) {
            channel.read(inputBuffer);
        }

        IRCMessage message = decodeIncomingMessage();
        if (message != null) {
            if (message.isCommand()) {
                this.listener.commandReceived(this, message);
            } else if (message.isReply()) {
                this.listener.replyReceived(this, message);
            } else if (message.isError()) {
                this.listener.errorReceived(this, message);
            }
        }
    }

    private int indexOfCRLF() {
        for (int i = 0; i < inputBuffer.position(); i++) {

            if (inputBuffer.get(i) == CRLF_BYTES[0]
                    && i < inputBuffer.capacity()
                    && inputBuffer.get(i + 1) == CRLF_BYTES[1])
            {
                return i;
            }
        }
        return -1;
    }

    private IRCMessage decodeIncomingMessage() throws IOException {

        int i = indexOfCRLF();
        if (i == -1) {
            return null;
        }

        byte[] messageBytes = new byte[i];
        inputBuffer.rewind();
        inputBuffer.get(messageBytes, 0, i);
        String messageLine = new String(messageBytes, "ascii");

        /* discard the cr/lf */
        inputBuffer.get();
        inputBuffer.get();
        inputBuffer.compact();

        return MessageUtils.getInstance().parseMessage(messageLine);
    }

    private synchronized void processOutput() throws IOException {
        if (outputBuffer.hasRemaining()) {
            channel.write(outputBuffer);
        } else {
            prepareNextOutput();
        }
    }

    /**
     * prepare next message to be sent
     */
    private void prepareNextOutput() throws IOException {

        IRCMessage message = null;
        try {
            message = (IRCMessage) outputQueue.remove(0);
        } catch (IndexOutOfBoundsException e) {}

        if (message == null) {
            return;
        }

        byte bytes[] = (message.toString() + "\r\n").getBytes("ascii");

        /* Truncate message if neccecary */
        if (bytes.length > IRCMessage.MAX_BYTE_SIZE) {
            byte truncated[] = new byte[IRCMessage.MAX_BYTE_SIZE];
            System.arraycopy(bytes, 0, truncated, 0, IRCMessage.MAX_BYTE_SIZE);
            byte crlf[] = "\r\n".getBytes("ascii");
            truncated[IRCMessage.MAX_BYTE_SIZE - 2] = crlf[0];
            truncated[IRCMessage.MAX_BYTE_SIZE - 1] = crlf[1];
            bytes = truncated;
        }

        outputBuffer.clear();
        outputBuffer.put(bytes);
        outputBuffer.limit(bytes.length);
        outputBuffer.rewind();

        /* Start write right away */
        channel.write(outputBuffer);
    }
}
