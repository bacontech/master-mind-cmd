import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ColorTest {

    @Test
    void test() {
        Color picked = Color.values()[0];
        assertEquals(Color.WHITE, picked);

        picked = Color.values()[1];
        assertEquals(Color.BLACK, picked);

        picked = Color.values()[2];
        assertEquals(Color.GREEN, picked);

        picked = Color.values()[3];
        assertEquals(Color.RED, picked);

        picked = Color.values()[4];
        assertEquals(Color.BLUE, picked);

        picked = Color.values()[5];
        assertEquals(Color.YELLOW, picked);

        picked = Color.values()[6];
        assertEquals(Color.EMPTY, picked);


    }
}