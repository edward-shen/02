package knights.zerotwo.plugins;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 * This is the interface that all modules (via plugface) use. Rather than have two separate versions of plugins, all
 * plugins will simply extend extend this module, regardless of whether or not the action is active or passive.
 */
public interface Module {

    default String registerActiveKeyword() {
        return null;
    }

    default String registerPassiveKeyword() {
        return null;
    }

    default void active(MessageReceivedEvent event) {
    }

    default void passive(MessageReceivedEvent event) {
    }
}
