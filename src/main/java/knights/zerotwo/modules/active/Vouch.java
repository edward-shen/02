package knights.zerotwo.modules.active;

import knights.zerotwo.IActive;
import knights.zerotwo.Utils;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message.MentionType;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;

@ParametersAreNonnullByDefault
public class Vouch implements IActive {

    @Override
    public boolean test(MessageReceivedEvent event) {
        return Utils.isCommand(event, "vouch");
    }

    @Override
    public void apply(MessageReceivedEvent event, String messageContent) {

        if (!event.getMember().getRoles().contains(event.getGuild().getRoleById(Utils.ROLE_ID))) {
            event.getChannel().sendMessage("You don't have permissions to vouch!").queue();
            return;
        }

        Matcher m = MentionType.USER.getPattern().matcher(messageContent);
        if (!m.find()) {
            event.getChannel().sendMessage("Who are you vouching for?").queue();
            return;
        }

        String id = m.group(1);
        Member member = event.getGuild().getMemberById(id);
        if (member == null) {
            event.getChannel().sendMessage("Baka, that's not a user!").queue();
            return;
        }
        if (!Utils.NEW_USERS.containsKey(id)) {
            if (member.getRoles().stream().noneMatch(r -> r.getId().equals(Utils.ROLE_ID))) {
                Utils.NEW_USERS.put(id, new HashSet<>());
            } else {
                event.getChannel().sendMessage("Baka, that's not a new user!").queue();
                return;
            }
        }
        Set<String> people = Utils.NEW_USERS.get(id);
        people.add(event.getAuthor().getId());

        if (people.size() < Utils.VOUCH_LIMIT) {
            event.getChannel()
                    .sendMessage(event.getAuthor().getAsMention() + " vouched for " + m.group())
                    .queue();
        } else {
            event.getChannel().sendMessage("Adding role for " + m.group()).queue();
            event.getGuild().getController()
                    .addRolesToMember(member, event.getGuild().getRoleById(Utils.ROLE_ID))
                    .complete();
            Utils.NEW_USERS.remove(id);
        }
    }

}
