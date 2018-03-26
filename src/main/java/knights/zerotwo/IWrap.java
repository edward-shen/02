package knights.zerotwo;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.Contract;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault

public interface IWrap extends IMessageFilter {
    class WrapResult {
        public final String content;
        public final IActive defaultActive;

        public WrapResult(String content, IActive defaultActive) {
            this.content = content;
            this.defaultActive = defaultActive;
        }
    }

    IActive NULL_ACTIVE = new IActive() {
        @Override
        @Contract("_ -> false")
        public boolean test(@Nullable MessageReceivedEvent event) {
            return false;
        }

        @Override
        public void apply(@Nullable MessageReceivedEvent event, @Nullable String messageContent) {
        }
    };

    WrapResult preAction(MessageReceivedEvent event);

    void postAction(MessageReceivedEvent event);
}
