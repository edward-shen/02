package knights.zerotwo;

import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UtilsTest {

    @Test
    void testIsCommand() {
        String commandName = "mock";
        String command = Utils.PREFIX + commandName;
        String invalidCommand = Utils.PREFIX + commandName.replace(commandName.charAt(0), (char) (command.charAt(0) + 1));
        MessageReceivedEvent msgNoArgs = new MessageReceivedEvent(null, 0L, new MessageBuilder(command).build());
        MessageReceivedEvent msgArgs = new MessageReceivedEvent(null, 0L, new MessageBuilder(command + "1 2").build());
        MessageReceivedEvent msgUntrimmed = new MessageReceivedEvent(null, 0L,
                new MessageBuilder(" " + command + " 1 2 ").build());
        MessageReceivedEvent msgInvalid = new MessageReceivedEvent(null, 0L, new MessageBuilder(invalidCommand).build());
        MessageReceivedEvent msgUppercase = new MessageReceivedEvent(null, 0L, new MessageBuilder(command.toUpperCase()).build());

        Assertions.assertTrue(Utils.isCommand(msgNoArgs, command));
        Assertions.assertTrue(Utils.isCommand(msgArgs, command));
        Assertions.assertTrue(Utils.isCommand(msgUntrimmed, command));
        Assertions.assertFalse(Utils.isCommand(msgInvalid, command));
        Assertions.assertTrue(Utils.isCommand(msgUppercase, command));
    }

    @Test
    void testIsInteger() {
        Assertions.assertTrue(Utils.isInteger("10"));
        Assertions.assertTrue(Utils.isInteger("-10"));
        Assertions.assertFalse(Utils.isInteger("1.0"));
        Assertions.assertFalse(Utils.isInteger("1.-0"));
        Assertions.assertFalse(Utils.isInteger("1.4"));
        Assertions.assertFalse(Utils.isInteger(""));
        Assertions.assertFalse(Utils.isInteger("apple"));

        Assertions.assertTrue(Utils.isInteger("ff", 16));
        Assertions.assertFalse(Utils.isInteger("ff", 15));
        Assertions.assertFalse(Utils.isInteger("g", 16));
        Assertions.assertTrue(Utils.isInteger("-3fa42", 16));
        Assertions.assertFalse(Utils.isInteger("-3fa42", 0));
        Assertions.assertFalse(Utils.isInteger("-3fa42", -4));
    }
}
