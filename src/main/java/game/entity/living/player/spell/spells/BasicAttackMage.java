package game.entity.living.player.spell.spells;

import game.entity.living.player.spell.AbstractSpell;
import game.entity.living.player.spell.Range;
import utils.Direction;
import utils.Position;

public class BasicAttackMage extends AbstractSpell {
    public BasicAttackMage() {
        super("Attack",
                1.0,
                14,
                new Range(),
                0,
                true,
                2,
                null
        );
    }
}
