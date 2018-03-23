package knights.zerotwo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class QuoteGeneratorTest {

    static QuoteGenerator validLoc;
    static QuoteGenerator invalidLoc;

    @BeforeAll
    public static void initData() {
        validLoc = new QuoteGenerator("tests/QuotesGeneratorTest");
        invalidLoc = new QuoteGenerator("0");
    }

    @Test
    public void testGetQuote() {
        Assertions.assertEquals(validLoc.getQuote(), "test");
        Assertions.assertEquals(invalidLoc.getQuote(), "");
    }
}