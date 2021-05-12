package game.entity.living.player.spell.spells;


import game.entity.living.player.spell.AbstractSpell;
import game.entity.living.player.spell.Range;
import utils.Direction;
import utils.Position;

public class BasicAttackWarrior extends AbstractSpell {

    public BasicAttackWarrior() {
        super("Attack",
                1.0,
                12,
                new Range(),
                0,
                true,
                1,
                null
        );
    }
}
