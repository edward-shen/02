package knights.zerotwo.modules.passive;

import knights.zerotwo.IPassive;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.requests.restaction.MessageAction;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@ParametersAreNonnullByDefault
public class Desu implements IPassive {

    @Nonnull
    private static final List<RandomEvent> messageList = new ArrayList<>();

    private interface RandomEvent {
        @Nonnull
        MessageAction accept(MessageChannel channel);
    }

    static {
        messageList.add(channel -> channel.sendMessage("です。"));
        messageList.add(channel -> channel.sendMessage("desu."));
        // @formatter:off
        messageList.add(channel -> channel.sendMessage(new MessageBuilder().appendCodeBlock(
                "ででででででででででで　　　　　　すす\n" +
                        "　　　　　ででで　　　　　すすすすすすすすす\n" +
                        "　　　　でで　　でで　　　　　　すす\n" +
                        "　　　でで　　　でで　　　　　すすす\n" +
                        "　　でで　　　　　　　　　　　す　す\n" +
                        "　　でで　　　　　　　　　　　すすす\n" +
                        "　　　でで　　　　　　　　　　　すす\n" +
                        "　　　　でで　　　　　　　　　　すす\n" +
                        "　　　　　でで　　　　　　　　すす", "").build()));
        // @formatter:on
        // https://imgur.com/a/yOb5n
        messageList.add(channel -> channel.sendMessage(new MessageBuilder()
                .setContent("https://www.youtube.com/watch?v=60mLvBWOMb4").build()));
        messageList.add(channel -> channel.sendFile(Desu.class.getResourceAsStream("/desu/desu.jpg"), "desu.jpg"));
        messageList.add(channel -> channel.sendMessage("desu~"));
    }

    @Override
    public boolean test(MessageReceivedEvent event) {
        return event.getMessage().getContentRaw().endsWith("desu");
    }

    @Override
    public void apply(MessageReceivedEvent event) {
        final float chance = 0.1f;

        if (Math.random() < chance) {
            messageList.get(new Random().nextInt(messageList.size())).accept(event.getChannel()).queue();
        }
    }

}
