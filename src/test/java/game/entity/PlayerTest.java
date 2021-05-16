package game.entity;

import display.HUD;
import game.elements.Dungeon;
import game.elements.GameState;
import game.entity.living.player.classeSystem.InGameClasses;
import game.entity.living.player.Player;
import generation.DungeonStructure;
import generation.Seed;
import org.junit.jupiter.api.Test;
import utils.MusicStuff;
import utils.Position;
import utils.ScanPanel;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void testStats() {
        Player player = new Player(new Position(0,2),"hey", InGameClasses.DUMMY);
        MusicStuff musicStuff = new MusicStuff();
        ScanPanel sp = new ScanPanel();
        Seed seed = new Seed();
        Dungeon dungeon = DungeonStructure.createDungeon(seed, 1, InGameClasses.DUMMY);
        GameState gameState = new GameState(player, dungeon, new HUD(player), sp, musicStuff);
        player.getPlayerStats().gainMoney(100);

        int baseAtk = player.getPlayerStats().getDamageNatural();
        player.getPlayerStats().changeDamageNatural(10);
        assertEquals(player.getPlayerStats().getDamageTotal(), 10+baseAtk);

        player.getPlayerStats().grantXP(player.getPlayerStats().getXpRequired(), gameState);
        assertEquals(player.getPlayerStats().getXp(), 0);
        assertEquals(player.getPlayerStats().getLevel(), 2);
    }
}