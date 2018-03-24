package knights.zerotwo.modules.active;

import knights.zerotwo.IActive;
import knights.zerotwo.QuoteGenerator;
import knights.zerotwo.Utils;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class Cube implements IActive {
    private char[][] field;

    // Using math and Discord's 2000 character limit, we can determine the longest message length we can send.
    private static final int MAXIMUM_SUPPORTED_CHARACTERS = 21;

    /**
     * Checks if the command is equal to "cube".
     *
     * @param event The message that we received.
     */
    @Override
    public boolean test(MessageReceivedEvent event) {
        return Utils.isCommand(event, "cube");
    }

    /**
     * Mutates the field, and sends out a cube form of the message parameters.
     *
     * @param event          The captured event.
     * @param messageContent The text content of the event.
     */
    @Override
    public void apply(MessageReceivedEvent event, String messageContent) {
        // Remove the prefix
        int sublen = "cube".length() + Utils.PREFIX.length() + 1;

        // Checks for empty cube params
        if (messageContent.length() < sublen) {
            event.getChannel().sendMessage("What do you want to cube?").complete();
            return;
        }

        String toCube = messageContent.substring(sublen);
        int cubeLen = toCube.length();

        // Checks if it's short enough
        if (cubeLen > MAXIMUM_SUPPORTED_CHARACTERS) {
            event.getChannel().sendMessage(QuoteGenerator.FAIL_QUOTES.getQuote()).queue();
            return;
        }

        // Checks if we should reverse the text, by looking if the first and last character are equal.
        boolean shouldReverseText = toCube.charAt(0) != toCube.charAt(cubeLen - 1);

        // Determine the front and back box's offset.
        int offset = (toCube.length()) / 2;

        // Initializes our field.
        this.field = new char[cubeLen + offset][(cubeLen + offset) * 2 - 1];
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                field[i][j] = ' ';
            }
        }

        // Draws our cube
        drawDiagonal(cubeLen, offset);
        drawBox(toCube, shouldReverseText, offset * 2, 0);
        drawBox(toCube, shouldReverseText, 0, offset);

        // Send our cube off!
        Guild guild = event.getGuild();
        String msg = "```\n" + flattenMessage(cubeLen) + "\n```";
        guild.getController()
                .setNickname(guild.getSelfMember(), event.getMessage().getAuthor().getName())
                .complete();
        event.getChannel().sendMessage(msg).complete();
        guild.getController().setNickname(guild.getSelfMember(), "").complete();
    }

    /**
     * Draws a box at the specified x and y coordinates.
     *
     * @param str       The string to draw the box with.
     * @param shouldRev Whether or not we should draw the bottom and right lines in reverse order.
     * @param x         The x coordinate of the top-left corner of the box.
     * @param y         The y coordinate of the top-left corner of the box.
     */
    private void drawBox(String str, boolean shouldRev, int x, int y) {
        int length = str.length();
        // Magic numbers are good as long as they're 1 or 2 right?
        for (int i = 0; i < length; i++) {
            field[y + i][x] = str.charAt(i);
            field[y][x + i * 2] = str.charAt(i);
            field[y + (length - 1)][x + (length - 1 - i) * 2] = shouldRev ? str.charAt(i)
                    : str.charAt(length - i - 1);
            field[y + (length - 1) - i][x + (length - 1) * 2] = shouldRev ? str.charAt(i)
                    : str.charAt(length - i - 1);
        }
    }

    /**
     * Draws the diagonals of the box.
     *
     * @param length The length of the front facing side of the box.
     * @param offset The number of diagonal lines to draw.
     */
    private void drawDiagonal(int length, int offset) {
        for (int x = 1; x < offset; x++) {
            field[offset - x][x * 2] = '/';
            field[length - x + offset - 1][x * 2] = '/';
            field[offset - x][(x + length - 1) * 2] = '/';
            field[length - x + offset - 1][(x + length - 1) * 2] = '/';
        }
    }

    /**
     * Converts our internal representation of the field into a string.
     *
     * @param length The side length of the cube.
     */
    private String flattenMessage(int length) {
        StringBuilder output = new StringBuilder();
        for (int y = 0; y < field.length; y++) {
            StringBuilder line = new StringBuilder();
            for (int x = 0; x < field[0].length; x++) {
                line.append(field[y][x]);
            }

            output.append(y >= length ? rtrim(line.toString()) : line);
            output.append("\n");
        }
        return output.toString();
    }

    /**
     * Removes all whitespace from the right side of the string.
     *
     * @param s The string to remove the whitespace from.
     */
    private String rtrim(String s) {
        int i = s.length() - 1;
        while (i > 0 && Character.isWhitespace(s.charAt(i))) {
            i--;
        }
        return s.substring(0, i + 1);
    }
}
