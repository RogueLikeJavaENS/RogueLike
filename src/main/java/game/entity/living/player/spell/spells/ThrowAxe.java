package game.entity.living.player.spell.spells;

import game.entity.living.player.spell.AbstractSpell;
import game.entity.living.player.spell.Range;
import game.entity.living.player.spell.SpecialEffect;

public class ThrowAxe extends AbstractSpell {
    public ThrowAxe() {
        super("Throw Axe",
                1.5,
                30,
                new Range(),
                20,
                true,
                3,
                null);
    }

    @Override
    public boolean isZoning() {
        return true;
    }
}
