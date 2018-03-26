package knights.zerotwo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class IWrapTest {

    @Test
    void testNullActive() {
        Assertions.assertFalse(IWrap.NULL_ACTIVE.test(null));
    }
}
