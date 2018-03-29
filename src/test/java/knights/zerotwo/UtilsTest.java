package knights.zerotwo;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
class UtilsTest {

    @Test
    void isCommand() {
        // We can't test it without a live channel to test it on.

//        String commandName = "mock";
//        String command = Utils.PREFIX + commandName;
//        String invalidCommand = Utils.PREFIX + commandName.replace(commandName.charAt(0), (char) (command.charAt(0) + 1));
//        MessageReceivedEvent msgNoArgs = new MessageReceivedEvent(null, 0L, new MessageBuilder(command).build());
//        MessageReceivedEvent msgArgs = new MessageReceivedEvent(null, 0L, new MessageBuilder(command + "1 2").build());
//        MessageReceivedEvent msgUntrimmed = new MessageReceivedEvent(null, 0L,
//                new MessageBuilder(" " + command + " 1 2 ").build());
//        MessageReceivedEvent msgInvalid = new MessageReceivedEvent(null, 0L, new MessageBuilder(invalidCommand).build());
//        MessageReceivedEvent msgUppercase = new MessageReceivedEvent(null, 0L, new MessageBuilder(command.toUpperCase()).build());
//
//        Assertions.assertTrue(Utils.isCommand(msgNoArgs, command));
//        Assertions.assertTrue(Utils.isCommand(msgArgs, command));
//        Assertions.assertTrue(Utils.isCommand(msgUntrimmed, command));
//        Assertions.assertTrue(Utils.isCommand(msgUppercase, command));
//        Assertions.assertFalse(Utils.isCommand(msgInvalid, command));
    }

    @Test
    void getCommandContent() {
        assertNull(Utils.getCommandContent("", ""));
        assertNull(Utils.getCommandContent("clap", ""));
        assertNull(Utils.getCommandContent("", "asdf"));
        assertEquals("", Utils.getCommandContent("clap", "!clap"));
        assertEquals("hello world", Utils.getCommandContent("clap", "!clap hello world"));
        assertEquals("hello world", Utils.getCommandContent("clap", "      !clap hello world     "));
        assertEquals("helloworld", Utils.getCommandContent("clap", "!claphelloworld"));
    }

    @Test
    void getCommandArguments() {
        assertNull(Utils.getCommandArguments("", ""));
        assertNull(Utils.getCommandArguments("clap", ""));
        assertNull(Utils.getCommandArguments("", "asdf"));
        assertIterableEquals(Collections.singletonList(""), Utils.getCommandArguments("clap", "!clap"));
        assertIterableEquals(Arrays.asList("hello", "world"), Utils.getCommandArguments("clap", "!clap hello world"));
        assertIterableEquals(Arrays.asList("hello", "world"), Utils.getCommandArguments("clap", "   !clap hello world     "));
        assertIterableEquals(Collections.singletonList("helloworld"), Utils.getCommandArguments("clap", "!claphelloworld"));
    }

    @Test
    void isInteger() {
        assertTrue(Utils.isInteger("10"));
        assertTrue(Utils.isInteger("-10"));
        assertFalse(Utils.isInteger("1.0"));
        assertFalse(Utils.isInteger("1.-0"));
        assertFalse(Utils.isInteger("1.4"));
        assertFalse(Utils.isInteger(""));
        assertFalse(Utils.isInteger("-"));
        assertFalse(Utils.isInteger("apple"));
    }

    @Test
    void isIntegerWithRadix() {
        assertTrue(Utils.isInteger("ff", 16));
        assertFalse(Utils.isInteger("ff", 15));
        assertFalse(Utils.isInteger("g", 16));
        assertTrue(Utils.isInteger("-3fa42", 16));
        assertFalse(Utils.isInteger("-3fa42", 0));
        assertFalse(Utils.isInteger("-3fa42", -4));
    }
}
