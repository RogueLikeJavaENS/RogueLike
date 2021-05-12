package game.entity.living.player.spell.spells;

import game.entity.living.player.spell.AbstractSpell;
import game.entity.living.player.spell.Range;
import game.entity.living.player.spell.SpecialEffect;

public class FireStroke extends AbstractSpell {


    public FireStroke() {
        super("Fire Stroke",
                1.5,
                60,
                new Range(),
                40,
                true,
                5,
                null);
    }

    @Override
    public boolean isZoning() {
        return true;
    }
}
