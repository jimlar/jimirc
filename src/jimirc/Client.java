package jimirc;

public class Client {
    private IRCConnection connection;

    public Client(String host, int port) {
	this.connection = new IRCConnection(host, port);
    }


    public static void main(String args[]) {
	Client client = new Client("localhost", 6667);
    }
}
