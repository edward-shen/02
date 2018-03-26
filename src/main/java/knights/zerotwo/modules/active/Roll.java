package knights.zerotwo.modules.active;

import knights.zerotwo.IActive;
import knights.zerotwo.Utils;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.Contract;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@ParametersAreNonnullByDefault
public class Roll implements IActive {

    private static final String COMMAND_KEYWORD = "roll";
    private static final int MAX_DICE_PER_TYPE = 64;
    private static final int MAX_SIDE_PER_DICE = 100;

    @Override
    @Contract(pure = true)
    public boolean test(MessageReceivedEvent event) {
        return Utils.isCommand(event, COMMAND_KEYWORD);
    }

    @Override
    public void apply(MessageReceivedEvent event, String messageContent) {
        String arguments = Utils.getCommandContent(COMMAND_KEYWORD, messageContent);

        if (arguments.isEmpty()) {
            event.getChannel().sendMessage("Baka, there are no dice to roll!").queue();
            return;
        }

        Random rnd = new Random();
        int sum = 0;

        List<String> diceRolls = Arrays.asList(arguments.split("\\+"));

        for (String dice : diceRolls) {
            String[] diceParams = dice.split("d");

            if (Utils.isInteger(dice)) {
                sum += Integer.parseInt(dice);
                break;
            } else if (diceParams.length != 2 || !Utils.isInteger(diceParams[0])
                    || !Utils.isInteger(diceParams[1])) {
                event.getChannel().sendMessage("malformed dice argument " + dice).queue();
                return;
            }

            int numDice = Integer.parseInt(diceParams[0]);
            int numSides = Integer.parseInt(diceParams[1]);

            if (numSides == 0) {
                event.getChannel().sendMessage("zero sided dice").queue();
                return;
            } else if (numDice <= 0) {
                event.getChannel().sendMessage("That's not a valid amount of dice").queue();
                return;
            } else if (numDice > MAX_DICE_PER_TYPE) {
                event.getChannel().sendMessage("Why do you need that many dice!?").queue();
                return;
            } else if (numSides > MAX_SIDE_PER_DICE) {
                event.getChannel().sendMessage("Wha... too many sides @.@").queue();
                return;
            }

            for (int i = 0; i < numDice; i++) {
                if (numSides > 0) {
                    sum += rnd.nextInt(numSides) + 1;
                } else {
                    sum -= rnd.nextInt(-numSides) + 1;
                }
            }
        }

        event.getChannel().sendMessage(new MessageBuilder(String.valueOf(sum)).build()).queue();
    }


}
