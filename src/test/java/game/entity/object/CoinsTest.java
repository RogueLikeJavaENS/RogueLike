package game.entity.object;

import game.entity.object.elements.Coins;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoinsTest {

    @Test
    void getValue() {
        Coins test = new Coins(null);
        assertTrue(test.getValue()>0);
    }
}