package jimirc.net.server;

import jimirc.net.IRCConnection;
import jimirc.net.IRCMessage;

import java.nio.channels.SocketChannel;
import java.nio.ByteBuffer;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;

class IncomingIRCConnection implements IRCConnection {
    private SocketChannel channel;

    private List outputQueue;

    private ByteBuffer outputBuffer;
    private ByteBuffer inputBuffer;

    public IncomingIRCConnection(SocketChannel channel) throws IOException {
        this.channel = channel;
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
        processOutput();
    }

    public void process() {
        processOutput();
        processInput();
    }


    private void processInput() {
        if (inputBuffer.hasRemaining()) {
            channel.read(inputBuffer);
        }


    }

    private synchronized void processOutput() {
        if (outputBuffer.hasRemaining()) {
            channel.write(outputBuffer);
        } else {
            prepareNextOutput();
        }
    }

    /**
     * prepare next message to be sent
     */
    private void prepareNextOutput() {

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
