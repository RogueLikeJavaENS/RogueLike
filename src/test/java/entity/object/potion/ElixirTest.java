package entity.object.potion;

import entity.living.player.Player;
import gameElement.Dungeon;
import gameElement.GameState;
import generation.DungeonStructure;
import generation.Seed;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ElixirTest {

    @Test
    void usePotion() {
        Seed seed = new Seed();
        Dungeon dungeon = DungeonStructure.createDungeon(seed, 1);
        Player test = new Player(null,100, 100, "test", 1);
        GameState gs = new GameState(test, dungeon);
        PotionFactory usine = new PotionFactory();
        Potion test2 = usine.getPotion(1);
        assertFalse(test2.usePotion(gs));
    }
}