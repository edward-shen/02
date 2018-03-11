package knights.zerotwo.modules;

import knights.zerotwo.IActive;
import knights.zerotwo.Utils;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Mock implements IActive {
    @Override
    public boolean test(MessageReceivedEvent event) {
        return Utils.isCommand(event, "mock");
    }

    @Override
    public void apply(MessageReceivedEvent event, String messageContent) {
        event.getChannel().getHistoryBefore(event.getMessage(), 1).queue(messageHistory -> {
            char[] mesToMock = messageHistory.getRetrievedHistory().get(0).getContentRaw().toLowerCase().toCharArray();

            for (int i = 0; i < mesToMock.length; i += 2) {
                while (mesToMock[i] == ' ') { //if the character is blank, move to the next index
                    i++;
                }
                mesToMock[i] = Character.toUpperCase(mesToMock[i]); //capitalize
            }

            event.getChannel().sendMessage(new String(mesToMock)).queue();
        });

    }

}
