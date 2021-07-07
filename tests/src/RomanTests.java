import org.junit.jupiter.api.Test;
import war.kata.Roman;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RomanTests {
    @Test
    void Blah() {
        Roman roman = new Roman();
        String result = roman.GetNumerals(1000);
        assertEquals("M", result);
    }
}
