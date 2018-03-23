package knights.zerotwo;

import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UtilsTest {

    @Test
    public void testIsCommand() {
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
}
