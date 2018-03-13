package knights.zerotwo.modules.active;

import knights.zerotwo.IActive;
import knights.zerotwo.Utils;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import javax.annotation.Nonnull;

public class Source implements IActive {
    @Override
    public void apply(@Nonnull MessageReceivedEvent event, @Nonnull String messageContent) {
        event.getChannel().sendMessage(
                "Ara, you want to be inside me? Well, go ahead and try.\nhttps://github.com/edward-shen/02").queue();
    }

    @Override
    public boolean test(@Nonnull MessageReceivedEvent event) {
        return Utils.isCommand(event, "source");
    }
}
