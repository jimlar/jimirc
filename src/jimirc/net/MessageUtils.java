package jimirc.net;

import java.util.*;

class MessageUtils {
    private static MessageUtils instance;

    private Map commandsByMessageId;
    private Map messageIdsByCommand;


    static MessageUtils getInstance() {
        if (instance == null) {
            instance = new MessageUtils();
        }
        return instance;
    }

    private MessageUtils() {
        this.commandsByMessageId = new HashMap();
        this.messageIdsByCommand = new HashMap();

        addMapping(IRCMessage.COMMAND_PASS, "PASS");
        addMapping(IRCMessage.COMMAND_NICK, "NICK");
        addMapping(IRCMessage.COMMAND_USER, "USER");
        addMapping(IRCMessage.COMMAND_OPER, "OPER");
        addMapping(IRCMessage.COMMAND_MODE, "MODE");
        addMapping(IRCMessage.COMMAND_SERVICE, "SERVICE");
        addMapping(IRCMessage.COMMAND_QUIT, "QUIT");
        addMapping(IRCMessage.COMMAND_SQUIT, "SQUIT");
        addMapping(IRCMessage.COMMAND_JOIN, "JOIN");
        addMapping(IRCMessage.COMMAND_PART, "PART");
        addMapping(IRCMessage.COMMAND_TOPIC, "TOPIC");
        addMapping(IRCMessage.COMMAND_NAMES, "NAMES");
        addMapping(IRCMessage.COMMAND_LIST, "LIST");
        addMapping(IRCMessage.COMMAND_INITE, "INITE");
        addMapping(IRCMessage.COMMAND_KICK, "KICK");
        addMapping(IRCMessage.COMMAND_PRIVMSG, "PRIVMSG");
        addMapping(IRCMessage.COMMAND_NOTICE, "NOTICE");
        addMapping(IRCMessage.COMMAND_MOTD, "MOTD");
        addMapping(IRCMessage.COMMAND_LUSERS, "LUSERS");
        addMapping(IRCMessage.COMMAND_VERSION, "VERSION");
        addMapping(IRCMessage.COMMAND_STATS, "STATS");
        addMapping(IRCMessage.COMMAND_LINKS, "LINKS");
        addMapping(IRCMessage.COMMAND_TIME, "TIME");
        addMapping(IRCMessage.COMMAND_CONNECT, "CONNECT");
        addMapping(IRCMessage.COMMAND_TRACE, "TRACE");
        addMapping(IRCMessage.COMMAND_ADMIN, "ADMIN");
        addMapping(IRCMessage.COMMAND_INFO, "INFO");
        addMapping(IRCMessage.COMMAND_SERVLIST, "SERVLIST");
        addMapping(IRCMessage.COMMAND_SQUERY, "SQUERY");
        addMapping(IRCMessage.COMMAND_WHO, "WHO");
        addMapping(IRCMessage.COMMAND_WHOIS, "WHOIS");
        addMapping(IRCMessage.COMMAND_WHOWAS, "WHOWAS");
        addMapping(IRCMessage.COMMAND_KILL, "KILL");
        addMapping(IRCMessage.COMMAND_PING, "PING");
        addMapping(IRCMessage.COMMAND_PONG, "PONG");
        addMapping(IRCMessage.COMMAND_ERROR, "ERROR");
        addMapping(IRCMessage.COMMAND_AWAY, "AWAY");
        addMapping(IRCMessage.COMMAND_REHASH, "REHASH");
        addMapping(IRCMessage.COMMAND_DIE, "DIE");
        addMapping(IRCMessage.COMMAND_RESTART, "RESTART");
        addMapping(IRCMessage.COMMAND_SUMMON, "SUMMON");
        addMapping(IRCMessage.COMMAND_USERS, "USERS");
        addMapping(IRCMessage.COMMAND_WALLOPS, "WALLOPS");
        addMapping(IRCMessage.COMMAND_USERHOST, "USERHOST");
        addMapping(IRCMessage.COMMAND_ISON, "ISON");

        addMapping(IRCMessage.REPLY_WELCOME, "001");
        addMapping(IRCMessage.REPLY_YOURHOST, "002");
        addMapping(IRCMessage.REPLY_CREATED, "003");
        addMapping(IRCMessage.REPLY_MYINFO, "004");
        addMapping(IRCMessage.REPLY_BOUNCE, "005");
        addMapping(IRCMessage.REPLY_USERHOST, "302");
        addMapping(IRCMessage.REPLY_ISON, "303");
        addMapping(IRCMessage.REPLY_AWAY, "301");
        addMapping(IRCMessage.REPLY_UNAWAY, "305");
        addMapping(IRCMessage.REPLY_NOAWAY, "306");
        addMapping(IRCMessage.REPLY_WHOISUSER, "311");
        addMapping(IRCMessage.REPLY_WHOISSERVER, "312");
        addMapping(IRCMessage.REPLY_WHOISOPERATOR, "313");
        addMapping(IRCMessage.REPLY_WHOISIDLE, "317");
        addMapping(IRCMessage.REPLY_ENDOFWHOIS, "318");
        addMapping(IRCMessage.REPLY_WHOISCHANNELS, "319");
        addMapping(IRCMessage.REPLY_WHOWASUSER, "314");
        addMapping(IRCMessage.REPLY_ENDOFWHOWAS, "369");
        addMapping(IRCMessage.REPLY_LISTSTART, "321");
        addMapping(IRCMessage.REPLY_LIST, "322");
        addMapping(IRCMessage.REPLY_LISTEND, "323");
        addMapping(IRCMessage.REPLY_UNIQOPIS, "325");
        addMapping(IRCMessage.REPLY_CHANNELMODEIS, "324");
        addMapping(IRCMessage.REPLY_NOTOPIC, "331");
        addMapping(IRCMessage.REPLY_TOPIC, "332");
        addMapping(IRCMessage.REPLY_INVITING, "341");
        addMapping(IRCMessage.REPLY_SUMMONING, "342");
        addMapping(IRCMessage.REPLY_INVITELIST, "346");
        addMapping(IRCMessage.REPLY_ENDOFINVITELIST, "347");
        addMapping(IRCMessage.REPLY_EXCEPTLIST, "348");
        addMapping(IRCMessage.REPLY_ENDOFEXCEPTLIST, "349");
        addMapping(IRCMessage.REPLY_VERSION, "351");
        addMapping(IRCMessage.REPLY_WHOREPLY, "352");
        addMapping(IRCMessage.REPLY_ENDOFWHO, "315");
        addMapping(IRCMessage.REPLY_NAMREPLY, "353");
        addMapping(IRCMessage.REPLY_ENDOFNAMES, "366");
        addMapping(IRCMessage.REPLY_LINKS, "364");
        addMapping(IRCMessage.REPLY_ENDOFLINKS, "365");
        addMapping(IRCMessage.REPLY_BANLIST, "367");
        addMapping(IRCMessage.REPLY_ENDOFBANLIST, "368");
        addMapping(IRCMessage.REPLY_INFO, "371");
        addMapping(IRCMessage.REPLY_ENDOFINFO, "374");
        addMapping(IRCMessage.REPLY_MOTDSTART, "375");
        addMapping(IRCMessage.REPLY_MOTD, "372");
        addMapping(IRCMessage.REPLY_ENDOFMOTD, "376");
        addMapping(IRCMessage.REPLY_YOUREOPER, "381");
        addMapping(IRCMessage.REPLY_REHASHING, "382");
        addMapping(IRCMessage.REPLY_YOURESERVICE, "383");
        addMapping(IRCMessage.REPLY_TIME, "391");
        addMapping(IRCMessage.REPLY_USERSSTART, "392");
        addMapping(IRCMessage.REPLY_USERS, "393");
        addMapping(IRCMessage.REPLY_ENDOFUSERS, "394");
        addMapping(IRCMessage.REPLY_NOUSERS, "395");
        addMapping(IRCMessage.REPLY_TRACELINK, "200");
        addMapping(IRCMessage.REPLY_TRACECONNECTING, "201");
        addMapping(IRCMessage.REPLY_TRACEHANDSHAKE, "202");
        addMapping(IRCMessage.REPLY_TRACEUNKNOWN, "203");
        addMapping(IRCMessage.REPLY_TRACEOPERATOR, "204");
        addMapping(IRCMessage.REPLY_TRACEUSER, "205");
        addMapping(IRCMessage.REPLY_TRACESERVER, "206");
        addMapping(IRCMessage.REPLY_TRACESERVICE, "207");
        addMapping(IRCMessage.REPLY_TRACENEWTYPE, "208");
        addMapping(IRCMessage.REPLY_TRACECLASS, "209");
        addMapping(IRCMessage.REPLY_TRACERECONNECT, "210");
        addMapping(IRCMessage.REPLY_TRACELOG, "261");
        addMapping(IRCMessage.REPLY_TRACEEND, "262");
        addMapping(IRCMessage.REPLY_STATSLINKINFO, "211");
        addMapping(IRCMessage.REPLY_STATSCOMMANDS, "212");
        addMapping(IRCMessage.REPLY_ENDOFSTATS, "219");
        addMapping(IRCMessage.REPLY_STATSUPTIME, "242");
        addMapping(IRCMessage.REPLY_STATSONLINE, "243");
        addMapping(IRCMessage.REPLY_UMODEIS, "221");
        addMapping(IRCMessage.REPLY_SERVLIST, "234");
        addMapping(IRCMessage.REPLY_SERVLISTEND, "235");
        addMapping(IRCMessage.REPLY_LUSERCLIENT, "251");
        addMapping(IRCMessage.REPLY_LUSEROP, "252");
        addMapping(IRCMessage.REPLY_LUSERUNKNOWN, "253");
        addMapping(IRCMessage.REPLY_LUSERCHANNELS, "254");
        addMapping(IRCMessage.REPLY_LUSERME, "255");
        addMapping(IRCMessage.REPLY_ADMINME, "256");
        addMapping(IRCMessage.REPLY_ADMINLOC1, "257");
        addMapping(IRCMessage.REPLY_ADMINLOC2, "258");
        addMapping(IRCMessage.REPLY_ADMINEMAIL, "259");
        addMapping(IRCMessage.REPLY_TRYAGAIN, "263");

        addMapping(IRCMessage.ERROR_NOSUCHNICK, "401");
        addMapping(IRCMessage.ERROR_NOSUCHSERVER, "402");
        addMapping(IRCMessage.ERROR_NOSUCHCHANNEL, "403");
        addMapping(IRCMessage.ERROR_CANNOTSENDTOCHAN, "404");
        addMapping(IRCMessage.ERROR_TOOMANYCHANNELS, "405");
        addMapping(IRCMessage.ERROR_WASNOSUCHNICK, "406");
        addMapping(IRCMessage.ERROR_TOOMANYTARGETS, "407");
        addMapping(IRCMessage.ERROR_NOSUCHSERVICE, "408");
        addMapping(IRCMessage.ERROR_NOORIGIN, "409");
        addMapping(IRCMessage.ERROR_NORECIPIENT, "411");
        addMapping(IRCMessage.ERROR_NOTEXTTOSEND, "412");
        addMapping(IRCMessage.ERROR_NOTOPLEVEL, "413");
        addMapping(IRCMessage.ERROR_WILDTOPLEVEL, "414");
        addMapping(IRCMessage.ERROR_BADMASK, "415");
        addMapping(IRCMessage.ERROR_UNKNOWNCOMMAND, "421");
        addMapping(IRCMessage.ERROR_NOMOTD, "422");
        addMapping(IRCMessage.ERROR_NOADMININFO, "423");
        addMapping(IRCMessage.ERROR_FILEERROR, "424");
        addMapping(IRCMessage.ERROR_NONICKNAMEGIVEN, "431");
        addMapping(IRCMessage.ERROR_ERRONEUSNICKNAME, "432");
        addMapping(IRCMessage.ERROR_NICKNAMEINUSE, "433");
        addMapping(IRCMessage.ERROR_NICKCOLLISION, "436");
        addMapping(IRCMessage.ERROR_UNAVAILRESOURCE, "437");
        addMapping(IRCMessage.ERROR_USERNOTINCHANNEL, "441");
        addMapping(IRCMessage.ERROR_NOTONCHANNEL, "442");
        addMapping(IRCMessage.ERROR_USERONCHANNEL, "443");
        addMapping(IRCMessage.ERROR_NOLOGIN, "444");
        addMapping(IRCMessage.ERROR_SUMMONDISABLED, "445");
        addMapping(IRCMessage.ERROR_USERSDISABLED, "446");
        addMapping(IRCMessage.ERROR_NOTREGISTERED, "451");
        addMapping(IRCMessage.ERROR_NEEDMOREPARAMS, "461");
        addMapping(IRCMessage.ERROR_ALREADYREGISTERED, "462");
        addMapping(IRCMessage.ERROR_NOPERMFORHOST, "463");
        addMapping(IRCMessage.ERROR_PASSWORDMISMATCH, "464");
        addMapping(IRCMessage.ERROR_YOUREBANNEDCREEP, "465");
        addMapping(IRCMessage.ERROR_YOUWILLBEBANNED, "466");
        addMapping(IRCMessage.ERROR_KEYSET, "467");
        addMapping(IRCMessage.ERROR_CHANNELISFULL, "471");
        addMapping(IRCMessage.ERROR_UNKNOWNMODE, "472");
        addMapping(IRCMessage.ERROR_INVITEONLYCHAN, "473");
        addMapping(IRCMessage.ERROR_BANNEDFROMCHAN, "474");
        addMapping(IRCMessage.ERROR_BADCHANNELKEY, "475");
        addMapping(IRCMessage.ERROR_BADCHANNELMASK, "476");
        addMapping(IRCMessage.ERROR_NOCHANMODES, "477");
        addMapping(IRCMessage.ERROR_BANLISTFULL, "478");
        addMapping(IRCMessage.ERROR_NOPRIVILEGES, "481");
        addMapping(IRCMessage.ERROR_CHANOPPRIVSNEEDED, "482");
        addMapping(IRCMessage.ERROR_CANTKILLSERVER, "483");
        addMapping(IRCMessage.ERROR_RESTRICTED, "484");
        addMapping(IRCMessage.ERROR_UNIQOPPRIVSNEEDED, "485");
        addMapping(IRCMessage.ERROR_NOOPERHOST, "491");
        addMapping(IRCMessage.ERROR_UMODEUNKNOWNFLAG, "501");
        addMapping(IRCMessage.ERROR_USERSDONTMATCH, "502");
    }

    IRCMessage parseMessage(String line) {
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

        return new IRCMessage(prefix,
                getMessageId(command),
                parseParameters(line.substring(i + 1)));
    }

    String getCommandString(int messageId) {
        return (String) commandsByMessageId.get(new Integer(messageId));
    }

    private int getMessageId(String command) {
        return ((Integer) messageIdsByCommand.get(command)).intValue();
    }

    private String[] parseParameters(String parameterLine) {

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

    private void addMapping(int messageId, String command) {
        if (this.commandsByMessageId.put(new Integer(messageId), command) != null) {
            throw new IllegalStateException("Message id " + messageId + " mapped twice");
        }
        if (this.messageIdsByCommand.put(command, new Integer(messageId)) != null) {
            throw new IllegalStateException("command " + command + " mapped twice");
        }
    }
}
