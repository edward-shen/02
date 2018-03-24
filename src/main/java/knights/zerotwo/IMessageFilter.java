package knights.zerotwo;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public interface IMessageFilter {
    /**
     * Checks if the event is indeed one that we should capture.
     *
     * @param event The message that we received.
     */
    boolean test(MessageReceivedEvent event);
}
