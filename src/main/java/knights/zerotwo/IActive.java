package knights.zerotwo;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import javax.annotation.Nonnull;

public interface IActive extends IMessageFilter {
    void apply(@Nonnull MessageReceivedEvent event, @Nonnull String messageContent);
}
