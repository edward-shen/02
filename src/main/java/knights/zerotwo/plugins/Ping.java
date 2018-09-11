package knights.zerotwo.plugins;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.plugface.core.annotations.Plugin;

@Plugin("ping")
public class Ping implements Module {
    @Override
    public String registerActiveKeyword() {
        return "ping";
    }

    @Override
    public void active(MessageReceivedEvent event) {
        event.getChannel().sendMessage("pong!").queue();
    }
}
