package game.stuff.item;

import static org.junit.jupiter.api.Assertions.*;

class MapDungeonTest {

    void testIsUsable(){
        MapDungeon md = new MapDungeon();
        assertTrue(md.isUsable());
    }


}