package knights.zerotwo.modules.active;

import knights.zerotwo.IActive;
import knights.zerotwo.Utils;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import javax.annotation.Nonnull;

public class Mock implements IActive {
    @Override
    public boolean test(@Nonnull MessageReceivedEvent event) {
        return Utils.isCommand(event, "mock");
    }

    @Override
    public void apply(@Nonnull MessageReceivedEvent event, @Nonnull String messageContent) {
        event.getChannel().getHistoryBefore(event.getMessage(), 1).queue(messageHistory -> {
            Message mockMessage = messageHistory.getRetrievedHistory().get(0);
            char[] mesToMock = mockMessage.getContentRaw().toLowerCase().toCharArray();

            for (int i = 0; i < mesToMock.length; i += 2) {
                while (mesToMock[i] == ' ') { //if the character is blank, move to the next index
                    i++;
                }
                mesToMock[i] = Character.toUpperCase(mesToMock[i]); //capitalize
            }

            event.getChannel().sendFile(
                    Mock.class.getResourceAsStream("/mock/spongebob.jpg"),
                    "spongemock.jpg",
                    new MessageBuilder((mockMessage.getAuthor().isBot() ? "" : mockMessage.getAuthor().getAsMention() + ": ") + new String(mesToMock)).build()).queue();
        });

    }

}
