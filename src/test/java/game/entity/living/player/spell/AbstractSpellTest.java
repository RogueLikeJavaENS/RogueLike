package game.entity.living.player.spell;

import game.entity.living.player.spell.spells.Charge;
import game.entity.living.player.spell.spells.IronSkin;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AbstractSpellTest {
    Spell test = new IronSkin();
    Spell test2 = new Charge();

    @Test
    void isMovement() {
        assertFalse(test.isMovement());
        assertTrue(test2.isMovement());
    }

    @Test
    void isDamaging() {
        assertFalse(test.isDamaging());
        assertTrue(test2.isDamaging());
    }

    @Test
    void isZoning() {
        assertFalse(test.isZoning());
        assertTrue(test2.isZoning());
    }
}