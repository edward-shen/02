package knights.zerotwo;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.Contract;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;

@ParametersAreNonnullByDefault
public class Utils {
    public static final Set<String> NON_EMOTE_USERS = new HashSet<>();
    public static final Map<String, Set<String>> NEW_USERS = new HashMap<>();
    public static final String ROLE_ID = "421091128645779467";
    public static final String VOUCH_CHANNEL = "422314122428940288";
    public static final int VOUCH_LIMIT = 2;

    public static final String PREFIX = "!";

    /**
     * Checks if a received event contains the specified command.
     *
     * @param event   The received event to test.
     * @param command The command string to look for.
     */
    @Contract(pure = true)
    public static boolean isCommand(MessageReceivedEvent event, String command) {
        return event.getMessage().getContentRaw().trim().toLowerCase()
                .startsWith(Utils.PREFIX + command);
    }

    /**
     * Removes the command keyword from the string, leaving only the content of the command.
     *
     * @param command The command keyword.
     * @param toParse The string to parse.
     */
    @Contract(pure = true)
    public static String getCommandContent(String command, String toParse) {
        if (toParse.contains(Utils.PREFIX + command)) {
            return toParse.trim().substring((toParse.trim().contains(" ") ? toParse.trim().indexOf(" ") : command.length()) + 1);
        }

        return null;
    }

    /**
     * Returns the content of the command arguments in the form of a List of Strings.
     *
     * @param command The command keyword.
     * @param toParse The string to parse.
     */
    @Contract(pure = true)
    public static List<String> getCommandArguments(String command, String toParse) {
        String content = getCommandContent(command, toParse);
        return (content == null) ? null : Arrays.asList(getCommandContent(command, toParse).split(" "));
    }

    /**
     * Check if a string is an integer in base 10.
     *
     * @param s The string to check.
     */
    @Contract(pure = true)
    public static boolean isInteger(String s) {
        return isInteger(s, 10);
    }

    /**
     * Checks if an string is an integer in the specified radix.
     *
     * @param s     The string to check.
     * @param radix The radix our string is in.
     */
    @Contract(pure = true)
    public static boolean isInteger(String s, int radix) {
        if (s.isEmpty())
            return false;
        for (int i = 0; i < s.length(); i++) {
            if (i == 0 && s.charAt(i) == '-') {
                if (s.length() == 1) {
                    return false;
                }

                continue;
            }
            if (Character.digit(s.charAt(i), radix) < 0) {
                return false;
            }
        }
        return true;
    }
}
