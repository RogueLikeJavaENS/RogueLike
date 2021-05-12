package game.entity.living.player.spell.spells;

import game.entity.living.player.spell.AbstractSpell;
import game.entity.living.player.spell.Range;
import utils.Direction;
import utils.Position;

public class Sniper extends AbstractSpell {
    public Sniper() {
        super("Sniper",
                1.0,
                12,
                new Range(),
                25,
                true,
                20,
                null
        );
    }

    @Override
    public boolean isZoning() {
        return true;
    }
}
