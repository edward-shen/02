package knights.zerotwo;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public interface IActive extends IMessageFilter {
    void apply(MessageReceivedEvent event, String messageContent);
}
