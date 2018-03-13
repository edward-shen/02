package knights.zerotwo.modules.active;

import knights.zerotwo.IActive;
import knights.zerotwo.Utils;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Roll implements IActive {

    @Override
    public boolean test(@Nonnull MessageReceivedEvent event) {
        return Utils.isCommand(event, "roll");
    }

    @Override
    public void apply(@Nonnull MessageReceivedEvent event, @Nonnull String messageContent) {
        int sublen = "roll".length() + Utils.PREFIX.length() + 1;
        if (messageContent.length() < sublen) {
            event.getChannel().sendMessage("Baka, there are no dice to roll").queue();
            return;
        }

        String diceParam = messageContent.substring(sublen);
        Random rnd = new Random();

        int sum = 0;

        List<String> diceRolls = Arrays.asList(diceParam.split("\\+"));

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
            } else if (numDice > 64) {
                event.getChannel().sendMessage("Why do you need that many dice!?").queue();
                return;
            } else if (numSides > 100) {
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
