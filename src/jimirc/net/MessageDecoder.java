package jimirc.net;

import java.util.*;

class MessageDecoder {

    public static IRCMessage parseMessage(String line) {
	String prefix = null;
	String command = null;
	
	boolean hasPrefix = line.startsWith(":");
	
	if (hasPrefix) {
	    int i = line.indexOf(" ");
	    if (i == -1) {
		return null;
	    }
	    prefix = line.substring(1, i);
	    line = line.substring(i + 1);
	}
	
	int i = line.indexOf(" ");
	if (i == -1) {
	    return null;
	}
	command = line.substring(0, i);
	
	return new IRCMessage(prefix, command, parseParameters(line.substring(i + 1)));
    }
    
    private static String[] parseParameters(String parameterLine) {
	
	if (parameterLine == null || parameterLine.equals("")) {
	    return new String[0];
	}
	List result = new ArrayList();
	
	while (!parameterLine.equals("")) {
	    
	    int i = parameterLine.indexOf(" ");
	    
	    if (parameterLine.startsWith(":")) {
		
		/* Trailing parameter? */
		result.add(parameterLine.substring(1));
		break;
		
	    } else if (i == -1) {
		
		/* Last parameter */
		result.add(parameterLine);
		break;
		
	    } else {
		/* Middle parameter */
		result.add(parameterLine.substring(0, i));
		parameterLine = parameterLine.substring(i + 1);
	    }
	}
	
	return (String[]) result.toArray(new String[result.size()]);
    }
}
