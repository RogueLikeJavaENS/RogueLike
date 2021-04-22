package entity.object.potion;

import display.HUD;
import entity.living.player.Player;
import gameElement.Dungeon;
import gameElement.GameState;
import generation.DungeonStructure;
import generation.Seed;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PotionHealthTest {

    @Test
    void usePotion() {
        Seed seed = new Seed();
        Dungeon dungeon = DungeonStructure.createDungeon(seed, 1);
        Player test = new Player(null,100, 100, "test", 1);
        HUD hud = new HUD(test);
        GameState gs = new GameState(test, dungeon, hud);
        PotionFactory usine = new PotionFactory();
        Potion test2 = usine.getPotion(0);
        assertFalse(test2.usePotion(gs));
    }
}