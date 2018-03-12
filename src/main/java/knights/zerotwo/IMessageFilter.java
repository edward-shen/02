package knights.zerotwo;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import javax.annotation.Nonnull;

public interface IMessageFilter {
    boolean test(@Nonnull MessageReceivedEvent event);
}
