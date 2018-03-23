package knights.zerotwo;

import com.google.gson.Gson;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class QuoteGenerator {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Main.class);
    private static final Random rnd = new Random();

    private List<String> db;

    public QuoteGenerator(String dbLocation) {
        String location = "/quotes/" + dbLocation + ".json";
        InputStream res = this.getClass().getResourceAsStream(location);
        if (res != null) {
            String output = new BufferedReader(new InputStreamReader(res)).lines().collect(Collectors.joining(""));
            System.out.println(output);
            db = Arrays.asList(new Gson().fromJson(output, String[].class));
        } else {
            logger.warn("Quote json file for " + dbLocation + " does not exist at location " + location);
        }

    }

    public String getQuote() {
        if (db == null) {
            logger.error("Tried to get a quote from a null list!");
            return "";
        }

        return db.get(rnd.nextInt(db.size()));
    }
}