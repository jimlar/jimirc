package jimirc.net;

public class IRCMessage {
    private String prefix;
    private String command;
    private String parameters[];


    public IRCMessage(String command, String parameter) {
	this(command, new String[] { parameter });
    }

    public IRCMessage(String command, String parameters[]) {
	this(null, command, parameters);
    }

    public IRCMessage(String prefix, String command, String parameters[]) {
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
	return parameters.length > 0;
    }

    /**
     * @return parameter list or empty string array if no parameters
     */
    public String[] getParameters() {
	return parameters;
    }

    public String toString() {
	return (hasPrefix() ? (":" + prefix + " ") : "")
	    + command
	    + (hasParameters() ? (" " + parametersToString()) : "");
    }

    private String parametersToString() {
	StringBuffer result = new StringBuffer();
	for (int i = 0; i < parameters.length; i++) {
	    String parameter = parameters[i];
	    
	    if (i > 0) {
		result.append(" ");
	    }
	    if (i == parameters.length - 1) {
		    if (parameter.indexOf(" ") != -1) {
			result.append(":");
		    }
	    }
	    result.append(parameter);
	}
	return result.toString();
    }
}
