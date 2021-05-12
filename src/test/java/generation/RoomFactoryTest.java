package generation;

import game.entity.Entity;
import game.entity.living.npc.monster.Monster;
import game.entity.object.elements.Coins;
import game.elements.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class RoomFactoryTest {
    RoomFactory roomFactory1;
    RoomFactory roomFactory2;
    Room roomlevel1;
    Room roomlevel2;
    Seed seed;
    int[] nextList;
    List<Entity> entities1;
    List<Entity> entities2;

    @BeforeEach
    public void setUp() {
        seed = new Seed();
        nextList = new int[6];
        nextList[0] = -1; // North
        nextList[1] = 0; // East
        nextList[2] = 2; // South
        nextList[3] = 3; // West
        nextList[4] = 4; // abs
        nextList[5] = 3; // ord
        this.roomFactory1 = new RoomFactory(15,11,2,1);
        this.roomlevel1 = roomFactory1.getRoom(seed, RoomType.MONSTER, 1, nextList);

        this.roomFactory2 = new RoomFactory(15,11,2,2);
        this.roomlevel2 = roomFactory2.getRoom(seed, RoomType.MONSTER, 1, nextList);

        entities1 = roomlevel1.getEntities();
        entities2 = roomlevel2.getEntities();
    }

    @Test
    void monsterLevelByLevel() {
        for (Entity entity: entities1) {
            if (entity instanceof Monster) {
                int level = ((Monster) entity).getMonsterStats().getLevel();
                assertTrue(level < 4 && level > 0);
            }
        }
        for (Entity entity: entities2) {
            if (entity instanceof Monster) {
                int level = ((Monster) entity).getMonsterStats().getLevel();
                assertTrue(level < 7 && level > 3);
            }
        }
    }


    @Test
    void containPotions() {
//        Room treasureRoom = roomFactory1.getRoom(seed, RoomType.TREASURE, 1, nextList);
//        int nbPotions = 0;
//        for (Entity game.entity : treasureRoom.getEntities()) {
//            if (game.entity instanceof Potion) {
//                nbPotions++;
//            }
//        }
//        assertTrue(nbPotions>=3 && nbPotions <=6);
    }

    @Test
    void containBTC() {
        Room treasureRoom = roomFactory1.getRoom(seed, RoomType.TREASURE, 1, nextList);
        int nbCoins = 0;
        for (Entity entity : treasureRoom.getEntities()) {
            if (entity instanceof Coins) {
                nbCoins++;
            }
        }
        assertTrue(nbCoins >= 8 && nbCoins <= 15);
    }

    @Test
    void nbMonsters() {
        int nbMonsters = 0;
        for (Entity entity : entities1) {
            if (entity instanceof Monster) {
                nbMonsters++;
            }
        }
        assertTrue(nbMonsters >= 2 && nbMonsters <=4);
    }
}