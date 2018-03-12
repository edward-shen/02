package knights.zerotwo.modules;

import knights.zerotwo.IActive;
import knights.zerotwo.Utils;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import javax.annotation.Nonnull;

public class Ping implements IActive {

    @Override
    public boolean test(@Nonnull MessageReceivedEvent event) {
        return Utils.isCommand(event, "ping");
    }

    @Override
    public void apply(@Nonnull MessageReceivedEvent event, @Nonnull String content) {
        event.getChannel().sendMessage("pong!").queue();
    }

}
