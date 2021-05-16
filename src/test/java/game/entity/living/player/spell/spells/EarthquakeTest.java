package game.entity.living.player.spell.spells;

import game.entity.living.player.spell.Spell;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EarthquakeTest {

    @Test
    void isZoning() {
        Spell test = new Earthquake();
        assertTrue(test.isZoning());
    }
}