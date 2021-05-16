package game.stuff;

import game.stuff.item.keys.FloorKey;

import static org.junit.jupiter.api.Assertions.*;

class AbstractStuffTest {

    void testEquals(){
        FloorKey fk = new FloorKey();
        assertEquals(true, fk.equals(new FloorKey()));
    }

}