package knights.zerotwo.modules.active;

import knights.zerotwo.IActive;
import knights.zerotwo.Utils;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import javax.annotation.Nonnull;

public class ListEmotes implements IActive {

    /*

    getting the entire list of names of possible emotes; in case we want to paginate.

    private static String listOfEmotes;

    static {
        File dir = null;
        StringBuilder sb = new StringBuilder();

        try {
            dir = new File(ClassLoader.getSystemResource("custom-emotes").toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        if (dir != null && dir.listFiles() != null) {
            Arrays.asList(dir.listFiles()).forEach(file -> sb.append(file.getName().substring(0, file.getName().lastIndexOf("."))).append("\n"));

            listOfEmotes = sb.toString();
        }
    }

     */

    @Override
    public void apply(@Nonnull MessageReceivedEvent event, @Nonnull String messageContent) {
        event.getChannel().sendMessage(
                new EmbedBuilder()
                        .setTitle("Github")
                        .setDescription("[Please click here for a list of emotes!](https://github.com/edward-shen/02/tree/master/src/main/resources/custom-emotes)")
                        .setThumbnail("https://raw.githubusercontent.com/edward-shen/02/master/src/main/resources/02.jpg")
                        .build()).queue();
    }

    @Override
    public boolean test(@Nonnull MessageReceivedEvent event) {
        return Utils.isCommand(event, "listemotes");
    }
}
