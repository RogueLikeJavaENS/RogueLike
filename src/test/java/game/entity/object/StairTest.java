package game.entity.object;

import game.entity.living.player.classeSystem.InGameClasses;
import display.HUD;
import game.entity.living.player.Player;
import game.element.Dungeon;
import game.element.GameState;
import game.entity.object.elements.Stair;
import generation.DungeonStructure;
import generation.Seed;
import org.junit.jupiter.api.Test;
import utils.ScanPanel;

import static org.junit.jupiter.api.Assertions.*;

class StairTest {

    @Test
    void doAction() {
        Seed seed = new Seed();
        Dungeon dungeon = DungeonStructure.createDungeon(seed, 1, InGameClasses.MAGE);
        Player test = new Player(null, "test", InGameClasses.DUMMY, 100);
        HUD hud = new HUD(test);
        GameState gs = new GameState(test, dungeon, hud, new ScanPanel());
        Stair test2 = new Stair(null);
        int temp = gs.getDungeon().getFloor();
        test2.doAction(gs);
        assertEquals(temp, (gs.getDungeon().getFloor() - 1));
    }
}