package jimirc.net;

import jimirc.net.util.MessageUtils;

/**
 * Represent and IRC message (command/reply/error message)
 *
 * The command is one of the COMMAND_*, REPLY_* or ERROR_* constants.
 *
 */
public class IRCMessage {

    public static final int COMMAND_PASS = 1000;
    public static final int COMMAND_NICK = 1001;
    public static final int COMMAND_USER = 1002;
    public static final int COMMAND_OPER = 1003;
    public static final int COMMAND_MODE = 1004;
    public static final int COMMAND_SERVICE = 1005;
    public static final int COMMAND_QUIT = 1006;
    public static final int COMMAND_SQUIT = 1007;
    public static final int COMMAND_JOIN = 1008;
    public static final int COMMAND_PART = 1009;
    public static final int COMMAND_TOPIC = 1010;
    public static final int COMMAND_NAMES = 1011;
    public static final int COMMAND_LIST = 1012;
    public static final int COMMAND_INITE = 1013;
    public static final int COMMAND_KICK = 1014;
    public static final int COMMAND_PRIVMSG = 1015;
    public static final int COMMAND_NOTICE = 1016;
    public static final int COMMAND_MOTD = 1017;
    public static final int COMMAND_LUSERS = 1018;
    public static final int COMMAND_VERSION = 1019;
    public static final int COMMAND_STATS = 1020;
    public static final int COMMAND_LINKS = 1021;
    public static final int COMMAND_TIME = 1022;
    public static final int COMMAND_CONNECT = 1023;
    public static final int COMMAND_TRACE = 1024;
    public static final int COMMAND_ADMIN = 1025;
    public static final int COMMAND_INFO = 1026;
    public static final int COMMAND_SERVLIST = 1027;
    public static final int COMMAND_SQUERY = 1028;
    public static final int COMMAND_WHO = 1029;
    public static final int COMMAND_WHOIS = 1030;
    public static final int COMMAND_WHOWAS = 1031;
    public static final int COMMAND_KILL = 1032;
    public static final int COMMAND_PING = 1033;
    public static final int COMMAND_PONG = 1034;
    public static final int COMMAND_ERROR = 1035;
    public static final int COMMAND_AWAY = 1036;
    public static final int COMMAND_REHASH = 1037;
    public static final int COMMAND_DIE = 1038;
    public static final int COMMAND_RESTART = 1039;
    public static final int COMMAND_SUMMON = 1040;
    public static final int COMMAND_USERS = 1041;
    public static final int COMMAND_WALLOPS = 1042;
    public static final int COMMAND_USERHOST = 1043;
    public static final int COMMAND_ISON = 1044;

    public static final int REPLY_WELCOME = 2000;
    public static final int REPLY_YOURHOST = 2001;
    public static final int REPLY_CREATED = 2002;
    public static final int REPLY_MYINFO = 2003;
    public static final int REPLY_BOUNCE = 2004;
    public static final int REPLY_USERHOST = 2005;
    public static final int REPLY_ISON = 2006;
    public static final int REPLY_AWAY = 2007;
    public static final int REPLY_UNAWAY = 2008;
    public static final int REPLY_NOAWAY = 2009;
    public static final int REPLY_WHOISUSER = 2010;
    public static final int REPLY_WHOISSERVER = 2011;
    public static final int REPLY_WHOISOPERATOR = 2012;
    public static final int REPLY_WHOISIDLE = 2013;
    public static final int REPLY_ENDOFWHOIS = 2014;
    public static final int REPLY_WHOISCHANNELS = 2015;
    public static final int REPLY_WHOWASUSER = 2016;
    public static final int REPLY_ENDOFWHOWAS = 2017;
    public static final int REPLY_LISTSTART = 2018;
    public static final int REPLY_LIST = 2019;
    public static final int REPLY_LISTEND = 2020;
    public static final int REPLY_UNIQOPIS = 2021;
    public static final int REPLY_CHANNELMODEIS = 2022;
    public static final int REPLY_NOTOPIC = 2023;
    public static final int REPLY_TOPIC = 2024;
    public static final int REPLY_INVITING = 2025;
    public static final int REPLY_SUMMONING = 2026;
    public static final int REPLY_INVITELIST = 2027;
    public static final int REPLY_ENDOFINVITELIST = 2028;
    public static final int REPLY_EXCEPTLIST = 2029;
    public static final int REPLY_ENDOFEXCEPTLIST = 2030;
    public static final int REPLY_VERSION = 2031;
    public static final int REPLY_WHOREPLY = 2032;
    public static final int REPLY_ENDOFWHO = 2033;
    public static final int REPLY_NAMREPLY = 2034;
    public static final int REPLY_ENDOFNAMES = 2035;
    public static final int REPLY_LINKS = 2036;
    public static final int REPLY_ENDOFLINKS = 2037;
    public static final int REPLY_BANLIST = 2038;
    public static final int REPLY_ENDOFBANLIST = 2039;
    public static final int REPLY_INFO = 2040;
    public static final int REPLY_ENDOFINFO = 2041;
    public static final int REPLY_MOTDSTART = 2042;
    public static final int REPLY_MOTD = 2043;
    public static final int REPLY_ENDOFMOTD = 2044;
    public static final int REPLY_YOUREOPER = 2045;
    public static final int REPLY_REHASHING = 2046;
    public static final int REPLY_YOURESERVICE = 2047;
    public static final int REPLY_TIME = 2048;
    public static final int REPLY_USERSSTART = 2049;
    public static final int REPLY_USERS = 2050;
    public static final int REPLY_ENDOFUSERS = 2051;
    public static final int REPLY_TRACELINK = 2052;
    public static final int REPLY_TRACECONNECTING = 2053;
    public static final int REPLY_TRACEHANDSHAKE = 2054;
    public static final int REPLY_TRACEUNKNOWN = 2055;
    public static final int REPLY_TRACEOPERATOR = 2056;
    public static final int REPLY_TRACEUSER = 2057;
    public static final int REPLY_TRACESERVER = 2058;
    public static final int REPLY_TRACESERVICE = 2059;
    public static final int REPLY_TRACENEWTYPE = 2060;
    public static final int REPLY_TRACECLASS = 2061;
    public static final int REPLY_TRACERECONNECT = 2062;
    public static final int REPLY_TRACELOG = 2063;
    public static final int REPLY_TRACEEND = 2064;
    public static final int REPLY_STATSLINKINFO = 2065;
    public static final int REPLY_STATSCOMMANDS = 2066;
    public static final int REPLY_ENDOFSTATS = 2067;
    public static final int REPLY_STATSUPTIME = 2068;
    public static final int REPLY_STATSONLINE = 2069;
    public static final int REPLY_UMODEIS = 2070;
    public static final int REPLY_SERVLIST = 2071;
    public static final int REPLY_SERVLISTEND = 2072;
    public static final int REPLY_LUSERCLIENT = 2073;
    public static final int REPLY_LUSEROP = 2074;
    public static final int REPLY_LUSERUNKNOWN = 2075;
    public static final int REPLY_LUSERCHANNELS = 2076;
    public static final int REPLY_LUSERME = 2077;
    public static final int REPLY_ADMINME = 2078;
    public static final int REPLY_ADMINLOC1 = 2079;
    public static final int REPLY_ADMINLOC2 = 2080;
    public static final int REPLY_ADMINEMAIL = 2081;
    public static final int REPLY_TRYAGAIN = 2082;
    public static final int REPLY_NOUSERS = 2083;

    public static final int ERROR_NOSUCHNICK = 3000;
    public static final int ERROR_NOSUCHSERVER = 3001;
    public static final int ERROR_NOSUCHCHANNEL = 3002;
    public static final int ERROR_CANNOTSENDTOCHAN = 3003;
    public static final int ERROR_TOOMANYCHANNELS = 3004;
    public static final int ERROR_WASNOSUCHNICK = 3005;
    public static final int ERROR_TOOMANYTARGETS = 3006;
    public static final int ERROR_NOSUCHSERVICE = 3007;
    public static final int ERROR_NOORIGIN = 3008;
    public static final int ERROR_NORECIPIENT = 3009;
    public static final int ERROR_NOTEXTTOSEND = 3010;
    public static final int ERROR_NOTOPLEVEL = 3011;
    public static final int ERROR_WILDTOPLEVEL = 3012;
    public static final int ERROR_BADMASK = 3013;
    public static final int ERROR_UNKNOWNCOMMAND = 3014;
    public static final int ERROR_NOMOTD = 3015;
    public static final int ERROR_NOADMININFO = 3016;
    public static final int ERROR_FILEERROR = 3017;
    public static final int ERROR_NONICKNAMEGIVEN = 3018;
    public static final int ERROR_ERRONEUSNICKNAME = 3019;
    public static final int ERROR_NICKNAMEINUSE = 3020;
    public static final int ERROR_NICKCOLLISION = 3021;
    public static final int ERROR_UNAVAILRESOURCE = 3022;
    public static final int ERROR_USERNOTINCHANNEL = 3023;
    public static final int ERROR_USERONCHANNEL = 3024;
    public static final int ERROR_NOLOGIN = 3025;
    public static final int ERROR_SUMMONDISABLED = 3026;
    public static final int ERROR_USERSDISABLED = 3027;
    public static final int ERROR_NOTREGISTERED = 3028;
    public static final int ERROR_NEEDMOREPARAMS = 3029;
    public static final int ERROR_ALREADYREGISTERED = 3030;
    public static final int ERROR_NOPERMFORHOST = 3031;
    public static final int ERROR_PASSWORDMISMATCH = 3032;
    public static final int ERROR_YOUREBANNEDCREEP = 3033;
    public static final int ERROR_YOUWILLBEBANNED = 3034;
    public static final int ERROR_KEYSET = 3035;
    public static final int ERROR_CHANNELISFULL = 3036;
    public static final int ERROR_UNKNOWNMODE = 3037;
    public static final int ERROR_INVITEONLYCHAN = 3038;
    public static final int ERROR_BANNEDFROMCHAN = 3039;
    public static final int ERROR_BADCHANNELKEY = 3040;
    public static final int ERROR_BADCHANNELMASK = 3041;
    public static final int ERROR_NOCHANMODES = 3042;
    public static final int ERROR_BANLISTFULL = 3043;
    public static final int ERROR_NOPRIVILEGES = 3044;
    public static final int ERROR_CHANOPPRIVSNEEDED = 3045;
    public static final int ERROR_CANTKILLSERVER = 3046;
    public static final int ERROR_RESTRICTED = 3047;
    public static final int ERROR_UNIQOPPRIVSNEEDED = 3048;
    public static final int ERROR_NOOPERHOST = 3049;
    public static final int ERROR_UMODEUNKNOWNFLAG = 3050;
    public static final int ERROR_USERSDONTMATCH = 3051;
    public static final int ERROR_NOTONCHANNEL = 3052;


    private String prefix;
    private int messageId;
    private String parameters[];

    public IRCMessage(int messageId, String parameter) {
        this(messageId, new String[]{parameter});
    }

    public IRCMessage(int messageId, String parameters[]) {
        this(null, messageId, parameters);
    }

    public IRCMessage(String prefix, int messageId, String parameters[]) {
        this.prefix = prefix;
        this.messageId = messageId;
        this.parameters = parameters;
    }

    public boolean hasPrefix() {
        return prefix != null;
    }

    public String getPrefix() {
        return prefix;
    }

    public boolean isCommand() {
        return messageId < 2000;
    }

    public boolean isReply() {
        return messageId >= 2000 && messageId < 3000;
    }

    public boolean isError() {
        return messageId >= 3000;
    }

    public int getMessageId() {
        return messageId;
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
                + MessageUtils.getInstance().getCommandString(messageId)
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
