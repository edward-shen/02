package knights.zerotwo.modules.active;

import knights.zerotwo.IActive;
import knights.zerotwo.Utils;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class Bug implements IActive {
    @Override
    public void apply(MessageReceivedEvent event, String messageContent) {
        event.getChannel().sendMessage("Ugh, it's probably Dr. Franxx's fault. Go tell him here:\nhttps://github.com/edward-shen/02/issues/new").queue();
    }

    @Override
    public boolean test(MessageReceivedEvent event) {
        return Utils.isCommand(event, "bug");
    }
}
