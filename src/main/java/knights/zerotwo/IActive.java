package knights.zerotwo;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public interface IActive extends IMessageFilter {
    /**
     * Do something with the valid, captured event.
     *
     * @param event          The captured event.
     * @param messageContent The text content of the event.
     */
    void apply(MessageReceivedEvent event, String messageContent);
}
