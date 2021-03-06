package knights.zerotwo;

import knights.zerotwo.IWrap.WrapResult;
import knights.zerotwo.modules.active.*;
import knights.zerotwo.modules.passive.Desu;
import knights.zerotwo.modules.wrapper.CustomEmotes;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.security.auth.login.LoginException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@ParametersAreNonnullByDefault
public class Main extends ListenerAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    private final List<IPassive> passiveModules;
    private List<IActive> activeModules;
    private List<IWrap> wrapperModules;
    private List<IReaction> reactionModules;


    public static void main(String[] args) {
        LOGGER.info("Starting");
        try {
            new JDABuilder(AccountType.BOT).setToken(System.getenv("token"))
                    .addEventListener(new Main()).buildAsync();
        } catch (LoginException e) {
            LOGGER.error("Login error", e);
        }
    }

    private Main() {
        passiveModules = Arrays.asList(new Desu());
        activeModules = Arrays.asList(new Ping(), new Crosspost(), new Clap(), new Roll(),
                new Cube(), new EmoteConfig(), new Mock(), new Source(),
                new ListEmotes(), new Bug());
        wrapperModules = Arrays.asList(new CustomEmotes());
        reactionModules = Arrays.asList();

    }

    private static final ThreadPoolExecutor exec = new ThreadPoolExecutor(1, 1, 0,
            TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(3, true));

    @Override
    public void onReady(@Nonnull ReadyEvent event) {
        LOGGER.info("Ready, id={}", event.getJDA().getSelfUser().getId());
        event.getJDA().getPresence().setPresence(Game.playing("with my darling~ ❤"), false);
    }

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        // We don't want to reply to bots, sorry :P
        if (event.getAuthor().isBot()) {
            return;
        }

        if (event.getMessage().getContentRaw().trim().equals("!roleid")) {
            event.getChannel().sendMessage("```\n" + event.getGuild().getRoles().stream()
                    .map(Role::toString).collect(Collectors.joining("\n")) + "\n```").queue();
            return;
        }

        List<IPassive> passive = passiveModules.stream().filter(m -> m.test(event))
                .collect(Collectors.toList());
        Optional<IActive> active = activeModules.stream().filter(m -> m.test(event)).findAny();
        Optional<IWrap> wrapper = wrapperModules.stream().filter(m -> m.test(event)).findAny();

        if (!passive.isEmpty() || active.isPresent() || wrapper.isPresent()) {
            try {
                exec.submit(() -> {
                    try {
                        passive.forEach(p -> p.apply(event));

                        wrapper.ifPresent(iWrap -> {
                            WrapResult result = iWrap.preAction(event);
                            active.orElse(result.defaultActive).apply(event, result.content);
                            iWrap.postAction(event);
                        });

                        active.ifPresent(iActive -> iActive.apply(event, event.getMessage().getContentRaw()));
                    } catch (Exception e) {
                        LOGGER.error("Bot error", e);
                    }
                });
            } catch (RejectedExecutionException e) {
                LOGGER.warn("Overloaded queue", e);
                // event.getChannel().sendMessage("Too many commands @.@").queue();
            }
        }
    }

    @Override
    public void onMessageReactionAdd(@Nonnull MessageReactionAddEvent event) {
        reactionModules.forEach(module -> {
            if (module.test(event)) {
                module.apply(event);
            }
        });

    }

    @Override
    public void onGuildMemberJoin(@Nonnull GuildMemberJoinEvent event) {
        Utils.NEW_USERS.put(event.getUser().getId(), new HashSet<>());
        event.getGuild().getTextChannelById(Utils.VOUCH_CHANNEL)
                .sendMessage(
                        event.getUser().getAsMention() + " joined the server! Type `" + Utils.PREFIX
                                + "vouch @" + event.getUser().getName() + "` to vouch for them")
                .queue();
    }
}