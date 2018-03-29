package knights.zerotwo.pagination;

import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LeftRightOnlyController implements IPaginationController {

    int index = 0;
    List<Page> pages;

    LeftRightOnlyController() {
        pages = new ArrayList<>();
    }

    LeftRightOnlyController(List<Page> pages) {
        this.pages = pages;
    }

    LeftRightOnlyController(Page... pages) {
        this(Arrays.asList(pages));
    }

    @Override
    public void apply(MessageReactionAddEvent event) {
    }

    @Override
    public boolean test(MessageReactionAddEvent event) {
        return event.getUser().isBot() && event.getUser().equals(event.getJDA().getSelfUser());
    }

    public void sendMessage(@Nonnull TextChannel channel) {
        MessageBuilder msgBuilder = new MessageBuilder();
        msgBuilder.appendCodeBlock(pages.get(index).getPageText(), String.valueOf(index));
        channel.sendMessage(msgBuilder.build()).queue(success -> {
            if (index != 0) {
                success.addReaction("arrow_left").queue();
            }

            if (index != pages.size() - 1) {
                success.addReaction("arrow_right").queue();
            }
        });
    }

    private String getEmoji(int i) {
        switch (i) {
            case 1:
                return "one";
            case 2:
                return "two";
            case 3:
                return "three";
            case 4:
                return "four";
            case 5:
                return "five";
            case 6:
                return "six";
            case 7:
                return "seven";
            case 8:
                return "eight";
            case 9:
                return "nine";
            case 10:
                return "keycap_ten";
            default:
                return null;
        }
    }
}
