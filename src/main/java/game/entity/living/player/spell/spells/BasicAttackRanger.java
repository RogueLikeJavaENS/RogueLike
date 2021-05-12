package game.entity.living.player.spell.spells;

import game.entity.living.player.spell.AbstractSpell;
import game.entity.living.player.spell.Range;
import utils.Direction;
import utils.Position;

public class BasicAttackRanger extends AbstractSpell {
    public BasicAttackRanger() {
        super("Attack",
                1.0,
                12,
                new Range(),
                0,
                true,
                3,
                null
        );
    }
}
