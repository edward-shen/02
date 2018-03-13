package knights.zerotwo.modules.active;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;

import com.vdurmont.emoji.EmojiManager;
import com.vdurmont.emoji.EmojiParser;

import knights.zerotwo.IActive;
import knights.zerotwo.Utils;
import net.dv8tion.jda.core.entities.Message.MentionType;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Clap implements IActive {

    @Override
    public boolean test(@Nonnull MessageReceivedEvent event) {
        return Utils.isCommand(event, "clap");
    }

    private static final Pattern ascii = Pattern.compile("[a-zA-Z0-9]");

    @Override
    public void apply(@Nonnull MessageReceivedEvent event, @Nonnull String messageContent) {
        int sublen = "clap".length() + Utils.PREFIX.length() + 1;
        if (messageContent.length() < sublen) {
            event.getChannel().sendMessage(
                    "you :clap: can't :clap: just :clap: !clap :clap: without :clap: a :clap: message")
                    .queue();
            return;
        }

        String command = messageContent.substring(sublen).trim();
        String[] args = command.replaceAll("\\s+", " ").split(" ");
        String emote = args[0];
        int startIndex = 1;
        if ((EmojiParser.extractEmojis(emote).isEmpty() || ascii.matcher(emote).find())
                && !MentionType.EMOTE.getPattern().matcher(emote).matches()) {
            startIndex = 0;
            emote = ":clap:";
        }
        if (args.length < startIndex + 2) {
            event.getChannel().sendMessage("baka, you can't clap a single word!").queue();
            return;
        }
        StringBuilder output = new StringBuilder();
        output.append(args[startIndex]);
        for (int i = startIndex + 1; i < args.length; i++) {
            output.append(' ');
            output.append(emote);
            output.append(' ');
            output.append(args[i]);
        }
        event.getChannel().sendMessage(output.toString()).queue();
    }
}
