package game.entity.living.player.spell.spells;

import game.entity.living.player.spell.Spell;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TeleportTest {

    @Test
    void isZoning() {
        Spell test = new Teleport();
        assertTrue(test.isZoning());
    }

    @Test
    void isMovement() {
        Spell test2 = new Teleport();
        assertTrue(test2.isMovement());
    }
}