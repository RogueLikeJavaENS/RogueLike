package entity.object;

import display.HUD;
import entity.living.player.Player;
import gameElement.Dungeon;
import gameElement.GameState;
import generation.DungeonStructure;
import generation.Seed;
import org.junit.jupiter.api.Test;
import utils.Classe;

import static org.junit.jupiter.api.Assertions.*;

class StairTest {

    @Test
    void doAction() {
        Seed seed = new Seed();
        Dungeon dungeon = DungeonStructure.createDungeon(seed, 1);
        Player test = new Player(null,100, 100, "test", 100, Classe.MAGE);
        HUD hud = new HUD(test);
        GameState gs = new GameState(test, dungeon, hud);
        Stair test2 = new Stair(null);
        int temp = gs.getDungeon().getFloor();
        test2.doAction(gs);
        assertEquals(temp, (gs.getDungeon().getFloor() - 1));
    }
}