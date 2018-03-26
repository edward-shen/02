package knights.zerotwo;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.ParametersAreNonnullByDefault;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@ParametersAreNonnullByDefault
public class QuoteGenerator {
    private static final Logger LOGGER = LoggerFactory.getLogger(QuoteGenerator.class);
    private static final Random RND = new Random();

    public static final QuoteGenerator FAIL_QUOTES = new QuoteGenerator("fail");

    private List<String> db;

    /**
     * Takes in a class reference and automatically generates the respective quote resource.
     *
     * @param c the class that needs its quote database to be initialized.
     */
    public QuoteGenerator(Class c) {
        String packageName = c.getPackageName().substring(c.getPackageName().lastIndexOf(".") + 1);
        String location = "/quotes/" + packageName + "/" + c.getSimpleName() + ".json";
        InputStream res = this.getClass().getResourceAsStream(location);
        if (res != null) {
            String output = new BufferedReader(new InputStreamReader(res)).lines().collect(Collectors.joining(""));
            db = Arrays.asList(new Gson().fromJson(output, String[].class));
        } else {
            LOGGER.warn("Quote json file for " + c.getSimpleName() + " does not exist at location " + location);
        }

    }

    /**
     * Attempts to load a quote json array from the following location
     *
     * @param location the location of the json array file.
     */
    public QuoteGenerator(String location) {
        InputStream res = this.getClass().getResourceAsStream("/quotes/" + location + ".json");
        if (res != null) {
            String output = new BufferedReader(new InputStreamReader(res)).lines().collect(Collectors.joining(""));
            String[] parsed = new Gson().fromJson(output, String[].class);
            if (parsed != null) {
                db = Arrays.asList(parsed);
            } else {
                LOGGER.warn("Quote json file exists at " + location + ", but is empty!");
            }
        } else {
            LOGGER.warn("Quote json file does not exist at location " + location);
        }
    }

    /**
     * Returns a random quote in the list of quotes. If the database was unsuccessfully loaded, throw a warning
     * and return an empty string.
     */
    public String getQuote() {
        if (db == null) {
            LOGGER.error("Tried to get a quote from a null list!");
            return "";
        }

        return db.get(RND.nextInt(db.size()));
    }
}