package jimirc.net;

public class IRCMessage {
    private String prefix;
    private String command;
    private String parameters;

    public IRCMessage(String prefix, String command, String parameters) {
	this.prefix = prefix;
	this.command = command;
	this.parameters = parameters;
    }

    public boolean hasPrefix() {
	return prefix != null;
    }

    public String getPrefix() {
	return prefix;
    }

    public String getCommand() {
	return command;
    }

    public boolean hasParameters() {
	return parameters != null;
    }

    public String getParameters() {
	return parameters;
    }

    public String toString() {
	return (hasPrefix() ? (":" + prefix + " ") : "")
	    + command
	    + (hasParameters() ? (" " + parameters) : "");
    }
}
