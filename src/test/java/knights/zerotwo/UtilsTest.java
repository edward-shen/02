package knights.zerotwo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

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
        Assertions.assertEquals(null, Utils.getCommandContent("", ""));
        Assertions.assertEquals(null, Utils.getCommandContent("clap", ""));
        Assertions.assertEquals(null, Utils.getCommandContent("", "asdf"));
        Assertions.assertEquals("", Utils.getCommandContent("clap", "!clap"));
        Assertions.assertEquals("hello world", Utils.getCommandContent("clap", "!clap hello world"));
        Assertions.assertEquals("hello world", Utils.getCommandContent("clap", "      !clap hello world     "));
        Assertions.assertEquals("helloworld", Utils.getCommandContent("clap", "!claphelloworld"));
    }

    @Test
    void getCommandArguments() {
        Assertions.assertEquals(null, Utils.getCommandArguments("", ""));
        Assertions.assertEquals(null, Utils.getCommandArguments("clap", ""));
        Assertions.assertEquals(null, Utils.getCommandArguments("", "asdf"));
        Assertions.assertIterableEquals(Collections.singletonList(""), Utils.getCommandArguments("clap", "!clap"));
        Assertions.assertIterableEquals(Arrays.asList("hello", "world"), Utils.getCommandArguments("clap", "!clap hello world"));
        Assertions.assertIterableEquals(Arrays.asList("hello", "world"), Utils.getCommandArguments("clap", "   !clap hello world     "));
        Assertions.assertIterableEquals(Collections.singletonList("helloworld"), Utils.getCommandArguments("clap", "!claphelloworld"));
    }

    @Test
    void isInteger() {
        Assertions.assertTrue(Utils.isInteger("10"));
        Assertions.assertTrue(Utils.isInteger("-10"));
        Assertions.assertFalse(Utils.isInteger("1.0"));
        Assertions.assertFalse(Utils.isInteger("1.-0"));
        Assertions.assertFalse(Utils.isInteger("1.4"));
        Assertions.assertFalse(Utils.isInteger(""));
        Assertions.assertFalse(Utils.isInteger("-"));
        Assertions.assertFalse(Utils.isInteger("apple"));
    }

    @Test
    void isIntegerWithRadix() {
        Assertions.assertTrue(Utils.isInteger("ff", 16));
        Assertions.assertFalse(Utils.isInteger("ff", 15));
        Assertions.assertFalse(Utils.isInteger("g", 16));
        Assertions.assertTrue(Utils.isInteger("-3fa42", 16));
        Assertions.assertFalse(Utils.isInteger("-3fa42", 0));
        Assertions.assertFalse(Utils.isInteger("-3fa42", -4));
    }
}
