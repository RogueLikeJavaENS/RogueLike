package entity.object;

import entity.living.player.Player;
import gameElement.Dungeon;
import gameElement.GameState;
import generation.DungeonStructure;
import generation.Seed;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StairTest {

    @Test
    void doAction() {
        Seed seed = new Seed();
        Dungeon dungeon = DungeonStructure.createDungeon(seed, 1);
        Player test = new Player(null,100, 100, "test", 100);
        GameState gs = new GameState(test, dungeon);
        Stair test2 = new Stair(null, true);
        int temp = gs.getDungeon().getFloor();
        test2.doAction(gs);
        assertTrue(temp==(gs.getDungeon().getFloor()-1));
    }
}