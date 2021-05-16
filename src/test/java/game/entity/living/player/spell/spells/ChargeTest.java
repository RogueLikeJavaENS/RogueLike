package game.entity.living.player.spell.spells;

import game.entity.living.player.spell.Spell;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChargeTest {

    @Test
    void isZoning() {
        Spell test = new Charge();
        assertTrue(test.isZoning());
    }

    @Test
    void isMovement() {
        Spell test2 = new Charge();
        assertTrue(test2.isMovement());
    }
}