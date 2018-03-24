package knights.zerotwo;

import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public interface IReaction extends IReactionFilter {
    void apply(MessageReactionAddEvent event);
}
