package knights.zerotwo;

import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public interface IReactionFilter {
    /**
     * Checks if the event is indeed one that we should capture.
     *
     * @param event The message that we received.
     */
    boolean test(MessageReactionAddEvent event);
}
