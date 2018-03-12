package knights.zerotwo.modules;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import knights.zerotwo.IActive;
import knights.zerotwo.IWrap;
import knights.zerotwo.Utils;
import net.dv8tion.jda.core.entities.Emote;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Icon;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.requests.RequestFuture;

public class CustomEmotes implements IWrap {
    static class CustomEmoteDefaultAction implements IActive {
        @Override
        public boolean test(@Nonnull MessageReceivedEvent event) {
            return false;
        }

        @Override
        public void apply(@Nonnull MessageReceivedEvent event, @Nonnull String messageContent) {
            Guild guild = event.getGuild();

            logger.debug("Sending message");
            guild.getController()
                    .setNickname(guild.getSelfMember(), event.getMessage().getAuthor().getName())
                    .complete();

            event.getChannel().sendMessage(messageContent).complete();

            guild.getController().setNickname(guild.getSelfMember(), "").complete();
            logger.debug("Done sending message");
        }
    }

    private static final Logger logger = LoggerFactory.getLogger(CustomEmotes.class);

    private static final Pattern EMOTES = Pattern.compile(":([A-Za-z0-9_\\s]+):");
    private static final IActive DEFAULT_ACTIVE = new CustomEmoteDefaultAction();

    @Override
    public boolean test(@Nonnull MessageReceivedEvent event) {
        if (Utils.NON_EMOTE_USERS.contains(event.getAuthor().getId())) {
            return false;
        }
        String raw = event.getMessage().getContentRaw();
        Matcher m = EMOTES.matcher(raw);
        while (m.find()) {
            int start = m.start();
            if (start == 0 || raw.charAt(start - 1) != '<') {
                return true;
            }
        }
        return false;
    }

    private List<Emote> emotesPendingDeletion = new ArrayList<>();

    @Override
    public WrapResult preAction(MessageReceivedEvent event) {
        logger.debug("Starting emote replacement");
        String raw = event.getMessage().getContentRaw();
        Matcher m = EMOTES.matcher(raw);

        StringBuilder sb = new StringBuilder();
        boolean replaced = false;
        while (m.find()) {
            int start = m.start();
            if (start > 0 && raw.charAt(start - 1) == '<') {
                m.appendReplacement(sb, m.group());
                continue;
            }

            String emote = m.group(1);
            emote = emote.replace(" ", "_");
            if (event.getGuild().getEmotesByName(emote, false).size() != 0) {
                List<Emote> emotes1 = event.getGuild().getEmotesByName(emote, false);
                if (emotes1.size() != 0) {
                    m.appendReplacement(sb, emotes1.get(0).getAsMention());
                }
                continue;
            }

            InputStream img = this.getClass()
                    .getResourceAsStream("/custom-emotes/" + emote + ".png");
            if (img != null) {
                logger.debug("Uploading {}", emote);
                Icon icon = null;
                try {
                    icon = Icon.from(img);
                } catch (IOException e1) {
                    logger.error("Failed to load image???", e1);
                    m.appendReplacement(sb, m.group());
                    continue;
                }
                RequestFuture<Emote> req = event.getGuild().getController().createEmote(emote, icon)
                        .submit();
                try {
                    // Mfw can't just req.orTimeout
                    Emote result = CompletableFuture.supplyAsync(() -> {
                        try {
                            return req.get();
                        } catch (Exception e) {
                            logger.error("Emote error", e);
                            return null;
                        }
                    }).orTimeout(3, TimeUnit.SECONDS).get();
                    if (emote == null) {
                        throw new Exception("Emote failed to upload");
                    }

                    m.appendReplacement(sb, result.getAsMention());

                    emotesPendingDeletion.add(result);
                    replaced = true;
                } catch (Exception e) {
                    logger.error("Failed to upload", e);
                    req.cancel(true);
                    m.appendReplacement(sb, m.group());

                    if (e.getCause() instanceof TimeoutException) {
                        event.getChannel()
                                .sendMessage(
                                        "N-nani?? Emote upload timed out")
                                .queue();
                    }

                    break;
                }
            } else {
                List<Emote> emotes1 = event.getGuild().getEmotesByName(emote, false);
                if (emotes1.size() != 0) {
                    m.appendReplacement(sb, emotes1.get(0).getAsMention());
                }
            }
        }
        m.appendTail(sb);

        logger.debug("Finished emote replacement");
        if (!replaced) {
            return new WrapResult(event.getMessage().getContentRaw(), NULL_ACTIVE);
        } else if (sb.length() < 2000) {
            return new WrapResult(sb.toString(), DEFAULT_ACTIVE);
        } else {
            event.getChannel()
                    .sendMessage("Only my darling can handle me like that. Don't even try.")
                    .queue();
            return new WrapResult(event.getMessage().getContentRaw(), NULL_ACTIVE);
        }
    }

    @Override
    public void postAction(MessageReceivedEvent event) {
        logger.debug("Clean up emotes");
        emotesPendingDeletion.forEach(emote -> emote.delete().complete());
        emotesPendingDeletion.clear();
    }

}
