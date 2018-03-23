package knights.zerotwo.modules.active;

import knights.zerotwo.IActive;
import knights.zerotwo.QuoteGenerator;
import knights.zerotwo.Utils;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class Source implements IActive {
    QuoteGenerator quoteGen = new QuoteGenerator(this.getClass());

    @Override
    public void apply(MessageReceivedEvent event, String messageContent) {
        event.getChannel().sendMessage(quoteGen.getQuote() + "\nhttps://github.com/edward-shen/02").queue();
    }

    @Override
    public boolean test(MessageReceivedEvent event) {
        return Utils.isCommand(event, "source");
    }
}
