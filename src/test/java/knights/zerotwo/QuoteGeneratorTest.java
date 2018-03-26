package knights.zerotwo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class QuoteGeneratorTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuoteGeneratorTest.class);

    private static QuoteGenerator validLocString;
    private static QuoteGenerator invalidLocString;
    private static QuoteGenerator validLocClass;
    private static QuoteGenerator invalidLocClass;
    private static QuoteGenerator emptyJSON;

    @BeforeAll
    static void initData() {
        validLocString = new QuoteGenerator("zerotwo/QuoteGeneratorTest");
        LOGGER.info("We should receive a warning that a quote file doesn't exist at location 0.");
        invalidLocString = new QuoteGenerator("0");
        validLocClass = new QuoteGenerator(QuoteGeneratorTest.class);
        LOGGER.info("We should receive a warning that a quote file doesn't exist for the Main class.");
        invalidLocClass = new QuoteGenerator(Main.class);
        emptyJSON = new QuoteGenerator("zerotwo/QuoteGeneratorTestEmpty");
    }

    @Test
    void testGetQuote() {
        Assertions.assertEquals("test", validLocString.getQuote());
        LOGGER.info("We should receive an error that we tried to get a quote from a null list.");
        Assertions.assertEquals("", invalidLocString.getQuote());
        Assertions.assertEquals("test", validLocClass.getQuote());
        LOGGER.info("We should receive an error that we tried to get a quote from a null list.");
        Assertions.assertEquals("", invalidLocClass.getQuote());
        LOGGER.info("We should receive an error that we tried to get a quote from a existing but empty file.");
        Assertions.assertEquals("", emptyJSON.getQuote());
    }
}