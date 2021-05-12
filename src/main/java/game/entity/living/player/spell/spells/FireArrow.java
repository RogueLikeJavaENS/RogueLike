package game.entity.living.player.spell.spells;

import game.entity.living.player.spell.AbstractSpell;
import game.entity.living.player.spell.Range;
import game.entity.living.player.spell.SpecialEffect;

public class FireArrow extends AbstractSpell {
    public FireArrow() {
        super("Fire Arrow",
                1.2,
                12,
                new Range(),
                10,
                true,
                5,
                null);
    }

    @Override
    public boolean isZoning() {
        return false;
    }
}
