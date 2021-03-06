package knights.zerotwo.modules.active;

import knights.zerotwo.IActive;
import knights.zerotwo.Utils;
import net.dv8tion.jda.core.entities.Message.MentionType;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;

@ParametersAreNonnullByDefault
public class Crosspost implements IActive {
    @Nonnull
    private static final String COMMAND_KEYWORD = "crosspost";

    @Override
    public boolean test(MessageReceivedEvent event) {
        return Utils.isCommand(event, COMMAND_KEYWORD);
    }

    @Override
    public void apply(MessageReceivedEvent event, String messageContent) {
        String arguments = Utils.getCommandContent(COMMAND_KEYWORD, messageContent);
        if (arguments.isEmpty()) {
            event.getChannel().sendMessage("Darling, what do you want me to crosspost~?").queue();
            return;
        }

        String prefix = event.getMessage().getAuthor().getAsMention() + " crossposted from <#"
                + event.getMessage().getChannel().getId() + ">:\n";
        Matcher channelMatcher = MentionType.CHANNEL.getPattern().matcher(arguments);
        Set<String> channelIds = new HashSet<>();
        int end = 0;
        while (channelMatcher.find()) {
            channelIds.add(channelMatcher.group(1));
            end = channelMatcher.end();
        }
        String text = arguments.substring(end);
        channelIds.remove(event.getChannel().getId());
        if (channelIds.isEmpty()) {
            event.getChannel().sendMessage("I didn't find any channels to crosspost to!").queue();
            return;
        }
        channelIds.forEach(channelId -> event.getJDA().getTextChannelById(channelId)
                .sendMessage(prefix + text).queue());
    }
}
