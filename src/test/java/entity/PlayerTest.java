package entity;

import classeSystem.InGameClasses;
import entity.living.player.Player;
import org.junit.jupiter.api.Test;
import utils.Position;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void testStats() {
        Player player = new Player(new Position(0,2),100,100,"hey", InGameClasses.DUMMY, 1);
        player.getPlayerStats().gainMoney(100);
        assertEquals(100, player.getPlayerStats().getMoneyCount());

        int baseAtk = player.getPlayerStats().getDamageNatural();
        player.getPlayerStats().changeDamageNatural(10);
        assertEquals(player.getPlayerStats().getDamageTotal(), 10+baseAtk);

        player.getPlayerStats().grantXP(player.getPlayerStats().getXpRequired());
        assertEquals(player.getPlayerStats().getXp(), 0);
        assertEquals(player.getPlayerStats().getLevel(), 2);
    }
}