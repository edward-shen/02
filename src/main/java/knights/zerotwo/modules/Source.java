package knights.zerotwo.modules;

import knights.zerotwo.IActive;
import knights.zerotwo.Utils;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Source implements IActive {
    @Override
    public void apply(MessageReceivedEvent event, String messageContent) {
        event.getChannel().sendMessage(
                "Ara, you want to be inside me? Well, go ahead and try.\nhttps://github.com/edward-shen/02").queue();
    }

    @Override
    public boolean test(MessageReceivedEvent event) {
        return Utils.isCommand(event, "source");
    }
}
