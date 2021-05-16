package game.entity.living.player.spell.spells;

import game.entity.living.player.spell.Spell;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FireBoltTest {

    @Test
    void isZoning() {
        Spell test = new FireBolt();
        assertTrue(test.isZoning());
        assertFalse(test.isMovement());
    }
}